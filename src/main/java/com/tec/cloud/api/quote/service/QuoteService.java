package com.tec.cloud.api.quote.service;

import com.alibaba.fastjson.JSONObject;
import com.tec.cloud.api.quote.dto.ShanbayDto;
import com.tec.cloud.api.quote.proxy.ShanbayProxy;
import com.tec.platform.common.constants.RedisKeyConstant;
import com.tec.platform.common.core.CommonResult;
import com.tec.platform.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class QuoteService {

    private Logger logger = LoggerFactory.getLogger(QuoteService.class);

    @Autowired
    ShanbayProxy shanbayProxy;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${api.quote.minDate}")
    private String quoteMinDate;
    // html解析backgroundImage
    Pattern background = Pattern.compile("(.*\\()(.*)(\\);?$)");
    // 优化~.png@!fhd_webp和~.png?x-oss-process=image/format,jpg
    Pattern imgPattern = Pattern.compile("(.*)((\\?.*)|(@.*)$)");

    /**
     * 每日一句
     *
     * @return
     */
    public CommonResult<ShanbayDto> todayQuote() {

        // 每天一个key 格式 2019-01-13
        String key = RedisKeyConstant.QUOTE_SHANBAY + ":" + DateUtil.getCurrentDate();

        // 检查缓存
        String quote = stringRedisTemplate.opsForValue().get(key);

        if (StringUtils.isNoneBlank(quote)) {
            CommonResult<ShanbayDto> result = CommonResult.success(JSONObject.parseObject(quote, ShanbayDto.class));
            // 优化~.png @!fhd_webp和~.png?x-oss-process=image/format,jpg
            result.getData().getOriginImgUrls().set(0, imgPattern.matcher(result.getData().getOriginImgUrls().get(0)).replaceAll("$1"));
            return result;
        }

        logger.warn("redis中没有{}的语录", key);
        return this.todayQuoteByShanbayProxy();
    }

    /**
     * 直接从Proxy获取语录
     * <p>
     * 提供给定时任务及todayQuote使用
     *
     * @return
     */
    public CommonResult<ShanbayDto> todayQuoteByShanbayProxy() {
        CommonResult<ShanbayDto> result = shanbayProxy.todayQuote();

        if (CommonResult.SUCCESS.equals(result.getCode())) {
            // 每天一个key 格式 2019-01-13
            String key = RedisKeyConstant.QUOTE_SHANBAY + ":" + DateUtil.getCurrentDate();
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(result.getData()));
        }
        return result;
    }

    /**
     * 请求历史的每日一句，返回HTML并解析
     *
     * @param date
     * @return
     */
    public CommonResult<ShanbayDto> quote(String date) {
        // 每天一个key 格式 2019-01-13
        String key = RedisKeyConstant.QUOTE_SHANBAY + ":" + date;

        // 检查缓存
        String quote = stringRedisTemplate.opsForValue().get(key);

        ShanbayDto dto;
        if (StringUtils.isBlank(quote)) {
            CommonResult<JSONObject> result = shanbayProxy.quote(date);

            // 请求成功，解析html
            if (CommonResult.SUCCESS.equals(result.getCode())) {
                String html = result.getData().getString("html");
                Document document = Jsoup.parse(html);
                Elements div = document.select(".quote-view");
                logger.info("爬虫到的html：{}, {}", date, div.outerHtml());
                String backgroundImageStyle = div.attr("style");
                // 存入redis前纠正~.png@!fhd_webp和~.png?x-oss-process=image/format,jpg
                final String backgroundImage = background.matcher(backgroundImageStyle).replaceAll("$2");

                String content = div.select(".content").text();

                String translation = div.select(".translation").text();

                String contentAuthor = div.select(".author").text();

                dto = new ShanbayDto();
                dto.setAssignDate(date);
                dto.setOriginImgUrls(new ArrayList<String>() {
                    {
                        add(backgroundImage);
                    }
                });
                dto.setContent(content);
                dto.setTranslation(translation);
                dto.setAuthor(contentAuthor);
                logger.info("解析后的语录：{}", dto);
                stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(dto));
            } else {
                return CommonResult.fail(result.getMessage());
            }
        } else {
            dto = JSONObject.parseObject(quote, ShanbayDto.class);
        }
        // 纠正移动端不支持~.png@!fhd_webp和~.png?x-oss-process=image/format,jpg
        // dto.getOriginImgUrls().set(0, background.matcher(dto.getOriginImgUrls().get(0)).replaceAll("$2"));
        return CommonResult.success(dto);
    }

    /**
     * 随机获取一句历史的每日一句
     *
     * @return
     */
    public CommonResult<ShanbayDto> random() {
        // 支持的最小日期
        LocalDate minDate = LocalDate.parse(quoteMinDate);
        // 计算当前日期与最小日期的差值，强转成int
        // 随机数<0，会提示“bound must be positive”
        int randowm = (int) minDate.until(DateUtil.getCurrentDate(), ChronoUnit.DAYS);
        ;// 获取随机数字，并查询语录
        return this.quote(minDate.plusDays(new Random().nextInt(randowm)).toString());
    }

}

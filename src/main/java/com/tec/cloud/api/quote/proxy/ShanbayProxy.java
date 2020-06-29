package com.tec.cloud.api.quote.proxy;

import com.alibaba.fastjson.JSONObject;
import com.tec.cloud.api.quote.dto.ShanbayDto;
import com.tec.platform.common.core.CommonResult;
import com.tec.platform.common.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * 扇贝-代理
 */
@Component
public class ShanbayProxy {

    Logger logger = LoggerFactory.getLogger(ShanbayProxy.class);

    private static final int SUCCESS = 0;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 每日一句 api地址
     */
    @Value("${api.quote.shanbay.today.url}")
    private String quoteTodayUrl;

    @Value("${api.quote.shanbay.url}")
    private String quoteUrl;

    @Value("${api.quote.shanbay.retryTimes}")
    private int quoteRetryTimes;

    /**
     * 每日一句
     *
     * @return
     */
    public CommonResult<ShanbayDto> todayQuote() {

        String apiResult = null;

        // 重试次数
        int requestCount = 0;
        while (requestCount < quoteRetryTimes) {
            requestCount++;
            try {
                apiResult = restTemplate.getForObject(quoteTodayUrl, String.class);
            } catch (Exception e) {
                logger.error("请求扇贝接口失败：{}", ExceptionUtil.stackTrace(e));
            }

            if (StringUtils.isBlank(apiResult)) {
                continue;
            }

            // 判断是否成功
            JSONObject result = JSONObject.parseObject(apiResult);
            if (SUCCESS == result.getIntValue("status_code")) {
                ShanbayDto dto = result.getObject("data", ShanbayDto.class);

                if (StringUtils.isBlank(dto.getContent())) {
                    continue;
                }

                return CommonResult.success(dto);
            }
        }

        logger.error("未能成功请求扇贝接口");
        return CommonResult.fail("未能成功请求扇贝接口");
    }

    /**
     * 历史 - 每日一句
     *
     * @param date
     * @return
     */
    public CommonResult<JSONObject> quote(String date) {
        String apiHtml = null;

        // 重试次数
        int requestCount = 0;
        while (requestCount < quoteRetryTimes) {
            requestCount++;
            try {
                apiHtml = restTemplate.getForObject(quoteUrl + date, String.class);
            } catch (HttpServerErrorException e) {
                if (HttpStatus.INTERNAL_SERVER_ERROR.equals(e.getStatusCode())) {
                    logger.error("扇贝接口返回错误：{}-{}", date, ExceptionUtil.stackTrace(e));
                }
            }

            if (StringUtils.isBlank(apiHtml)) {
                continue;
            }
            JSONObject data = new JSONObject();
            data.put("html", apiHtml);

            return CommonResult.success(data);
        }
        logger.error("未能成功获取每日一句");
        return CommonResult.fail("未能成功获取每日一句，扇贝接口返回错误");
    }
}

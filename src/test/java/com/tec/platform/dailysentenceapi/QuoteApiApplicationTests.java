package com.tec.platform.dailysentenceapi;

import com.alibaba.fastjson.JSONObject;
import com.tec.cloud.api.quote.dto.ShanbayDto;
import com.tec.cloud.api.quote.proxy.ShanbayProxy;
import com.tec.platform.common.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;

// @SpringBootTest(classes = QuoteApiApplication.class)
// @RunWith(SpringRunner.class)
public class QuoteApiApplicationTests {

	private Logger logger = LoggerFactory.getLogger(QuoteApiApplicationTests.class);

	@Resource
	ShanbayProxy shanbayProxy;

	@Resource
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testRedisTemplate() throws UnsupportedEncodingException {
		// String key = "quote:shanbay:2019-02-17";
		String key = "";
		String quote = stringRedisTemplate.opsForValue().get(key);
		System.out.println(quote);
		ShanbayDto shanbayDto = JSONObject.parseObject(quote, ShanbayDto.class);
		shanbayDto.setAuthor(new String(shanbayDto.getAuthor().getBytes(Charset.forName("ISO-8859-1")), "UTF-8"));
		shanbayDto.setTranslation(new String(shanbayDto.getTranslation().getBytes(Charset.forName("ISO-8859-1")), "UTF-8"));
		System.out.println(JSONObject.toJSONString(shanbayDto));
		stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(shanbayDto));
	}

	@Test
	public void getImgUrl() {
		String date = "2018-08-07";

		// Pattern background = Pattern.compile("(.*\\()(.*)(\\?.* | @.*)(\\);$)");
		Pattern background = Pattern.compile("(.*\\()(.*)((\\?.*)|(@.*))(\\);$)");
		// CommonResult<JSONObject> result = shanbayProxy.quote(date);
		//
		// // 请求成功，解析html
		// if (CommonResult.SUCCESS.equals(result.getCode())) {
		//     String html = result.getData().getString("html");
		//     Document document = Jsoup.parse(html);
		//     Elements div = document.select(".quote-preview");
		//     logger.info("爬虫到的html：{}, {}", date, div.outerHtml());
		//     // String backgroundImageStyle = div.attr("style");
		String backgroundImageStyle = "background-image: url(https://media-image1.baydn.com/soup_pub_image/ccdbwr/d3fc40c2c87b0fb43433d2646b795488.f8bf517ae746d66026f72133482d43a8.png?fhd_webp);";
		String backgroundImage = background.matcher(backgroundImageStyle).replaceAll("$2");
		System.out.println(backgroundImage);
		// }
	}
}


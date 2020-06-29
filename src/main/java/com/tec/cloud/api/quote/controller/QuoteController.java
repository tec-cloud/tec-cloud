package com.tec.cloud.api.quote.controller;


import com.tec.cloud.api.quote.dto.ShanbayDto;
import com.tec.cloud.api.quote.service.QuoteService;
import com.tec.platform.common.core.CommonResult;
import com.tec.platform.common.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * 历史的每日一句-摘录
 *
 * @author tec
 */
@RestController
@RequestMapping("/api/quote")
@Api( tags ="每日一句")
public class QuoteController {

    private Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    QuoteService quoteService;

    @Value("${api.quote.minDate}")
    private String quoteMinDate;

    /**
     * 每日一句
     * <p>
     * produces 指定返回值的类型
     */
    @ApiOperation(value = "每日一句", notes = "获取格式丰富的每一句")
    @GetMapping(value = "/")
    public CommonResult<ShanbayDto> todayQuote() {
        return quoteService.todayQuote();
    }

    /**
     * 历史-每日一句
     *
     * @param requestDate 格式：
     * @return
     */
    @ApiOperation(value = "历史-每日一句", notes = "获取指定日期（格式：yyyy-MM-dd）的每日一句")
    @GetMapping(value = "/{requestDate}/")
    public CommonResult<ShanbayDto> quote(@PathVariable String requestDate) {
        try {
            LocalDate formatDate = LocalDate.parse(requestDate);
            LocalDate minDate = LocalDate.parse(quoteMinDate);
            if (minDate.isAfter(formatDate)) {
                throw new IllegalArgumentException("日期不能早于" + quoteMinDate);
            }
            return quoteService.quote(formatDate.toString());
        } catch (DateTimeException e) {
            logger.error("非法的日期：{}", requestDate);
            throw new IllegalArgumentException("非法的日期，格式为" + DateUtil.SHORT_DATE);
        }
    }

    @ApiOperation(value = "随机获取一句历史的每日一句", notes = "随机获取一句历史的每日一句")
    @GetMapping(value = "/history/random/")
    public CommonResult<ShanbayDto> random() {
        return quoteService.random();
    }
}

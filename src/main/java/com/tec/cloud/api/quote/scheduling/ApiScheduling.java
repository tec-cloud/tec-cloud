package com.tec.cloud.api.quote.scheduling;

import com.tec.cloud.api.quote.service.QuoteService;
import com.tec.platform.common.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务
 *
 * @author tec
 */
@EnableScheduling
@Configuration
public class ApiScheduling {

    Logger logger = LoggerFactory.getLogger(ApiScheduling.class);

    @Autowired
    QuoteService quoteService;

    /**
     * 每日一句
     */
    @Scheduled(cron = "${api.quote.cron}")
    public void quote() {
        logger.info("每一一句定时任务开始：{}", DateUtil.getCurrentDateTimeStr());
        quoteService.todayQuoteByShanbayProxy();
        logger.info("每一一句定时任务结束：{}", DateUtil.getCurrentDateTimeStr());
    }
}
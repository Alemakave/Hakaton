package ru.khv1.hakaton.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SQLScanner {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.SECONDS)
    public void scan() {
        logger.info("Scan");
    }
}

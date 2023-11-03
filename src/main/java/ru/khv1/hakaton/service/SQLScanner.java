package ru.khv1.hakaton.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SQLScanner {
    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.SECONDS)
    public void scan() {
        System.out.println("Scan");
    }
}

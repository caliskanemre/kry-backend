package com.cry.core.startup;

import com.cry.core.service.HttpConnectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnProperty(
        prefix = "application.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
@RequiredArgsConstructor
public class AppSetup implements ApplicationRunner {

    private final HttpConnectorService httpConnectorService;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {

        while (true) {
            Thread.sleep(10000);
            httpConnectorService.periodicallyChecker();
        }
    }
}


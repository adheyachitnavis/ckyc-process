package com.docuscan.ckyc.service;

import com.docuscan.ckyc.exception.CsvProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledProcessTriggerService {

    private final CkycService service;

    //@Scheduled(cron = "*/30 * * * * ?")
    public void runEvery30Seconds() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("Task executed at: " + timestamp);
        try {
            service.processAllClients();
        } catch (CsvProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
}

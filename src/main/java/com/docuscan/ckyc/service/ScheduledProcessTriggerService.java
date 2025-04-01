package com.docuscan.ckyc.service;

import com.docuscan.ckyc.exception.CsvProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ScheduledProcessTriggerService {

    private final CkycService service;

    //@Scheduled(cron = "*/30 * * * * ?")
    public void runEvery30Seconds() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("Task executed at: " + timestamp);
        try {
            service.process();
        } catch (CsvProcessingException e) {
            e.printStackTrace();
        }
    }
}

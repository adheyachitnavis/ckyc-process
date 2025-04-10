package com.docuscan.ckyc.controller;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.Customer;
import com.docuscan.ckyc.service.CkycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ckyc")
@RequiredArgsConstructor
class CkycController {
    private final CkycService service;

    @GetMapping("/process/{clientName}")
    public ResponseEntity<String> processCsv(@PathVariable("clientName") String clientName) {
        List<Customer> customers = null;
        try {
            service.process(clientName);
            return ResponseEntity.ok("Done");
        } catch (CsvProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/process_all")
    public ResponseEntity<String> processAll() {
        try {
            service.processAllClients();
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/download")
    public ResponseEntity<String> createDownloadRequest(@RequestParam(name = "username", required = false) String username) {
        try {
            service.createDownloadRequest(username);
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/check_download_response")
    public ResponseEntity<String> checkDownloadResponse() {
        try {
            service.checkDownloadResponse();
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
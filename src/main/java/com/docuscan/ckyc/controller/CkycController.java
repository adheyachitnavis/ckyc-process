package com.docuscan.ckyc.controller;

import com.docuscan.ckyc.model.Customer;
import com.docuscan.ckyc.service.CkycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ckyc")
@RequiredArgsConstructor
class CkycController {
    private final CkycService service;

    @GetMapping("/process")
    public ResponseEntity<List<Customer>> processCsv() {
        List<Customer> customers = null;
        try {
            customers = service.process();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }

    }

    @GetMapping("/process_all")
    public ResponseEntity<String> processAll() {
        List<Customer> customers = null;
        try {
            service.processAllClients();
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
        }

    }

    @GetMapping("/download")
    public ResponseEntity<String> createDownloadRequest() {
        try {
            service.createDownloadRequest();
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
        }

    }

    @GetMapping("/check_download_response")
    public ResponseEntity<String> checkDownloadResponse() {
        try {
            service.checkDownloadResponse();
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
        }

    }
}
package com.docuscan.ckyc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SftpService {

    private final Map<String, DefaultSftpSessionFactory> sftpSessionFactories;
}

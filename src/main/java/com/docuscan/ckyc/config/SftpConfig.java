package com.docuscan.ckyc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import java.util.HashMap;
import java.util.Map;

public class SftpConfig {

    @Value("#{${sftp.servers}}") // Inject all server properties
    private Map<String, Map<String, String>> sftpServers;

    @Bean
    public Map<String, DefaultSftpSessionFactory> sftpSessionFactories() {
        Map<String, DefaultSftpSessionFactory> factories = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : sftpServers.entrySet()) {
            String serverName = entry.getKey();
            Map<String, String> properties = entry.getValue();

            DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
            factory.setHost(properties.get("host"));
            factory.setPort(Integer.parseInt(properties.get("port")));
            factory.setUser(properties.get("username"));
            factory.setPassword(properties.get("password"));
            factory.setAllowUnknownKeys(true);

            factories.put(serverName, factory);
        }
        return factories;
    }
}


package com.docuscan.ckyc.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "CKYC_INQ")
public class CkycInquiry {

    @XmlElement(name = "PID")
    private String pid;

    @XmlElement(name = "SESSION_KEY")
    private String sessionKey;
}

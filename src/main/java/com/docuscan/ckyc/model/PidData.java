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
@XmlRootElement(name = "PID_DATA")
public class PidData {

    @XmlElement(name = "DATE_TIME")
    private String dateTime;

    @XmlElement(name = "ID_TYPE")
    private String idType;

    @XmlElement(name = "ID_NO")
    private String idNo;
}


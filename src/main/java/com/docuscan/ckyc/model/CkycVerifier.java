package com.docuscan.ckyc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CkycVerifier {
    private String name;
    private String designation;
    private String branch;
    private String empCode;

}

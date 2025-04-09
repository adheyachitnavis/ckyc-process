package com.docuscan.ckyc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Name {
    private String prefix;
    private String first;
    private String middle;
    private String last;
    private String full;
}

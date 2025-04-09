package com.docuscan.ckyc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client")
public class Client {

    @Id
    private String id;
    private String name;
    @Field("fi_code")
    private String fiCode;
    @Field("region_code")
    private String regionCode;
    private String status;
    @Field("user_id")
    private String userId;
    @Field("branch_code")
    private String branchCode;
}

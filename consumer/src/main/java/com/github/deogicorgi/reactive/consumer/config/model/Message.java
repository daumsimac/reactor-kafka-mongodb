package com.github.deogicorgi.reactive.consumer.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "rcvmsg")
public class Message {
    @Id
    private String id;
    private String bnk_c;
    private String org_c;
    private String tgrm_dsc;

}

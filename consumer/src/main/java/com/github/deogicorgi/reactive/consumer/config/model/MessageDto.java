package com.github.deogicorgi.reactive.consumer.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String id;
    private String bnk_c;
    private String org_c;
    private String tgrm_dsc;
}

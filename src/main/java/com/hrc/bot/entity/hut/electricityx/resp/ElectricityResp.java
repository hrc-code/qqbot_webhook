package com.hrc.bot.entity.hut.electricityx.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ElectricityResp {

    @JsonProperty("message")
    private String message;
    @JsonProperty("resultData")
    private ResultDataDTO resultData;
    @JsonProperty("success")
    private Boolean success;
}

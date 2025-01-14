package com.hrc.bot.entity.hut.electricityx.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResultDataDTO {
    @JsonProperty("accname")
    private String accname;
    @JsonProperty("eledetail")
    private String eledetail;
    @JsonProperty("elestatus")
    private String elestatus;
}

package com.hrc.bot.entity.hut.grade.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GradeResp {

    @JsonProperty("Msg")
    private String msg;
    @JsonProperty("code")
    private String code;
    @JsonProperty("data")
    private List<DataDTO> data;
}

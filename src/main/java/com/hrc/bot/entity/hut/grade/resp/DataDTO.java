package com.hrc.bot.entity.hut.grade.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class DataDTO {
    /** 成绩*/
    @JsonProperty("achievement")
    private List<AchievementDTO> achievement;
    @JsonProperty("inGrade")
    private String inGrade;
    @JsonProperty("name")
    private String name;
    @JsonProperty("pjcj")
    private String pjcj;
    @JsonProperty("pjxfjd")
    private String pjxfjd;
    @JsonProperty("studentID")
    private String studentID;
    @JsonProperty("xqgpa")
    private List<?> xqgpa;
    @JsonProperty("yxzxf")
    private String yxzxf;
    @JsonProperty("zxfjd")
    private String zxfjd;
}

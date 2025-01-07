package com.hrc.bot.entity.hut.grade.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AchievementDTO {
    @JsonProperty("cj0708id")
    private String cj0708id;
    @JsonProperty("cjbs")
    private String cjbs;
    @JsonProperty("cjdj")
    private String cjdj;
    /** 课程名*/
    @JsonProperty("courseName")
    private String courseName;
    @JsonProperty("courseNature")
    private String courseNature;
    @JsonProperty("credit")
    private Integer credit;
    @JsonProperty("curriculumAttributes")
    private String curriculumAttributes;
    @JsonProperty("examName")
    private String examName;
    @JsonProperty("examinationNature")
    private String examinationNature;
    /** 成绩*/
    @JsonProperty("fraction")
    private String fraction;
    @JsonProperty("jd")
    private Double jd;
    @JsonProperty("kcbh")
    private String kcbh;
    @JsonProperty("sfjg")
    private String sfjg;
}

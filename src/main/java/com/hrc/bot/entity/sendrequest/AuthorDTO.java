package com.hrc.bot.entity.sendrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthorDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("user_openid")
    private String userOpenid;
    @JsonProperty("union_openid")
    private String unionOpenid;
}

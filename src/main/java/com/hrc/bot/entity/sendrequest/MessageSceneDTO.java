package com.hrc.bot.entity.sendrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageSceneDTO {
    @JsonProperty("source")
    private String source;
    @JsonProperty("callback_data")
    private String callbackData;
}

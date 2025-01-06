package com.hrc.bot.entity.sendrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SendRequest {

    @JsonProperty("id")
    private String id;
    @JsonProperty("content")
    private String content;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("author")
    private AuthorDTO author;
    @JsonProperty("message_scene")
    private MessageSceneDTO messageScene;
}

package com.hrc.bot.entity.validate;

import lombok.Data;

@Data
public class ValidationRequest {

    private String plain_token;

    private String event_ts;
}

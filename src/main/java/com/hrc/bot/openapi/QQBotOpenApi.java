package com.hrc.bot.openapi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hrc.bot.entity.Payload;
import com.hrc.bot.entity.sendrequest.SendRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class QQBotOpenApi {

    @Value("${bot.appid}")
    private String appId;
    @Value("${bot.secret}")
    private String appSecret;


    private final static OkHttpClient client = new OkHttpClient();

    // 获取调用凭证
    private  String doGetCertificate() throws IOException {

        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\r\n\"appId\": \"" + appId + "\",\r\n\"clientSecret\": \"" + appSecret + "\"\r\n        }");
        Request request = new Request.Builder().url("https://bots.qq.com/app/getAppAccessToken").post(body).addHeader("Content-Type", "application/json").build();

        Response response = client.newCall(request).execute();
        String bodyString = response.body().string();
        JSONObject jsonObject = JSON.parseObject(bodyString);
        Object accessToken = jsonObject.get("access_token");
        response.close();
        return "QQBot " + accessToken.toString();
    }

    // 发送消息 现在发的是文本
    public void doSendMsg(Payload<SendRequest> payload, String msg) throws IOException {
        String authorization = doGetCertificate();
        String eventId = payload.getId();
        SendRequest d = payload.getD();
        String msgId = d.getId();
        String userId = d.getAuthor().getId();

        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\r\n \"content\": \"" + msg + "\",\r\n \"msg_type\": 0,\r\n  \"event_id\": \"" + eventId + "\",\r\n \"msg_id\": \"" + msgId + "\"\r\n        }");
        Request request = new Request.Builder().url("https://sandbox.api.sgroup.qq.com/v2/users/"+userId+"/messages").post(body).addHeader("Content-Type", "application/json").addHeader("Authorization", authorization).build();

        Response response = client.newCall(request).execute();
        String string = response.body().string();
        log.info(string);
    }
}

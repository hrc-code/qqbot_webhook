package com.hrc.bot.openapi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hrc.bot.utls.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class HutOpenApi {
    Map<String, String> tokenMap = new HashMap<>();

    OkHttpClient client = new OkHttpClient();

    /**
     * 登录
     */
    public void login() throws IOException {
        // 构建一个空的请求体，因为我们已经在URL中提供了所有的参数
        RequestBody body = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url("https://mycas.hut.edu.cn/token/password/passwordLogin?username=22408000511&password=huthrc%40qq.comHRC123&appId=com.supwisdom.hut&geo=&deviceId=Z20bPxiMxVYDAJikw0SeHZ5M&osType=android&clientId=d58daac6b3efd99d21e57ad63c7a619c&mfaState=")
                .post(body)
                .addHeader("Host", "mycas.hut.edu.cn")
                .addHeader("Connection", "Keep-Alive")
                .build();

        Response response = client.newCall(request).execute();
        String string = response.body().string();
        log.info(string);
        JSONObject jsonObject = JSON.parseObject(string);
        // 检查 code 是否为 0，表示成功
        if (jsonObject.getIntValue("code") == 0) {
            // 获取 data 节点下的 idToken
            String idToken = jsonObject.getJSONObject("data").getString("idToken");
            tokenMap.put("22408000511", idToken);
            System.out.println("idToken: " + idToken);
        } else {
            System.out.println("请求失败或未找到 idToken");
        }

        response.close();

    }


    /** 查询电费*/
    public void getElectricity() throws IOException {
        String userToken = tokenMap.get("22408000511");
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"areaid\":\"44517536-4\",\"buildingid\":\"25\",\"factorycode\":\"N002\",\"roomid\":\"4D9BB38D13354D32A75F75D3326696C1\"}");
        Request request = new Request.Builder()
                .url("https://v8mobile.hut.edu.cn/channel/queryRoomDetail?openid=FD868A4AFF8878A33B4BC5443A6C02BBD4317C134B542FE73C5C7FC5F24D0CF2E113BDDEC388C0038475E2EB8243D087")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("Cookie", "userToken="+userToken+"; Domain=v8mobile.hut.edu.cn; Path=/; JSESSIONID=F10A144157055F01FF04BC85335E0DCD")
                .addHeader("Host", "v8mobile.hut.edu.cn")
                .build();

        Response response = client.newCall(request).execute();

        String bodyString = response.body().string();
        JSONObject jsonObject = JSON.parseObject(bodyString);

    }

    /** 查询成绩*/
    public String getGrade(String semester) throws Exception {
        String url = "http://127.0.0.1:32412/grade?semester="+semester;
        String result = HttpUtil.get(url, null);
        System.out.println(result);
        return result;
    }

    /** 获取现在的成绩*/
    public String getNowGrade() throws Exception {
        String url = "http://127.0.0.1:32412/grade/now?";
        String result = HttpUtil.get(url, null);
        System.out.println(result);
        return result;
    }
}

package com.hrc.bot.openapi;

import com.hrc.bot.utls.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HutOpenApi {



    /** 查询电费*/
    public String getElectricity() throws Exception {
        String url = "http://127.0.0.1:32412/getElectricity";
        String result = HttpUtil.get(url, null);
        System.out.println(result);
        return result;
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

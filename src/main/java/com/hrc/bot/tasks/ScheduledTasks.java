package com.hrc.bot.tasks;


import java.util.HashSet;
import java.util.Set;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Data
public class ScheduledTasks {


     private Set<String> rawBodySet = new HashSet<>();


      
    
    /**
     * 清空原始消息体集合。
     * 此方法用于清空存储原始消息体的集合，以便在短时间内多次调用时，只处理第一次调用的结果。
     */ 
   // 每隔一小时执行一次，从第一次调用开始计时
   @Scheduled(fixedRate = 3600000)
   public void performTask() {
       log.info("开始清除rawBodySet");
        rawBodySet.clear();
    }
 
}
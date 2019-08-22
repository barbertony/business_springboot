package com.neuedu.schedule;

import com.neuedu.service.IOrderService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CloseOrderSchedule {
    @Autowired
    IOrderService orderService;
    @Value("${order.close.timeout}")
    private int orderTimeOut;
    @Scheduled(cron = "0 * * * * *")
    public  void closeOrder(){

        Date closeOrderTime=DateUtils.addHours(new Date(),-orderTimeOut);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(closeOrderTime);
        orderService.closeOrder(dateString);
        System.out.println("=======cron========="+System.currentTimeMillis());
    }
}

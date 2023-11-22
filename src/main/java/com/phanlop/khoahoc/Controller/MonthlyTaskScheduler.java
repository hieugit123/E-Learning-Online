package com.phanlop.khoahoc.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.phanlop.khoahoc.Service.MonthlyTaskService;

@Component
public class MonthlyTaskScheduler {

    @Autowired
    private MonthlyTaskService monthlyTaskService;

    // Lập lịch để chạy vào cuối mỗi tháng
    // @Scheduled(cron = "0 0 23 L * ?") //cái này dùng thực tế
    @Scheduled(cron = "0 0/2 * * * ?") //cái này để test
    public void executeMonthlyTask() {
        // Gọi phương thức của service vào cuối mỗi tháng
        monthlyTaskService.processMonthlyTask();
    }
}

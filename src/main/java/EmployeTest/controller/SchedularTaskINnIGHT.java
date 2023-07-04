package EmployeTest.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

@Configuration
@EnableScheduling
public class SchedularTaskINnIGHT {


    @Scheduled(cron="0 0 20 * * *")
    public void doeverydaynight(){

   LocalDate ld= LocalDate.now();
        System.out.println("the task is doing in night "+ld);
        System.out.println("the task is doing in night "+ld);
        System.out.println("the task is doing in night "+ld);
        System.out.println("the task is doing in night "+ld);
        System.out.println("the task is doing in night "+ld);
        System.out.println("the task is doing in night "+ld);

    }






}

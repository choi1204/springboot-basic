package org.prgrms.kdt;

import org.prgrms.kdt.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class CommandLineApplication{
    public static void main(String[] args){
        var springApplication = new SpringApplication(KdtApplication.class);
        springApplication.setAdditionalProfiles("dev");

        ConfigurableApplicationContext applicationContext = springApplication.run();

        applicationContext.getBean(VoucherController.class).start();

        applicationContext.close();
    }
}
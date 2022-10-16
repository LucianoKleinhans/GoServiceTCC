package com.lajotasoftware.goservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledJob {

    @Autowired
    UserService userService;

    @Scheduled(fixedRate = 600000)
    @Async
    public void execute() throws InterruptedException {
        System.out.println("Atualizando as Avaliações...");
        userService.calc_avaliacao_prestador();
        userService.calc_avaliacao_cliente();
    }

}

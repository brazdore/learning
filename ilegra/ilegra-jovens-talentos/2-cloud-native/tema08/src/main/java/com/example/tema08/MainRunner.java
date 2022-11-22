package com.example.tema08;

import com.example.tema08.configs.AppConfig;
import com.example.tema08.rxnetty.RxNettyHandler;
import io.reactivex.netty.RxNetty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRunner {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        RxNetty.createHttpServer(8080, context.getBean(RxNettyHandler.class))
                .startAndWait();
    }
}

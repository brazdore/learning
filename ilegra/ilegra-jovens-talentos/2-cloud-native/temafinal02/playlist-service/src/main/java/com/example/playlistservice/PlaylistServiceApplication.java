package com.example.playlistservice;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PlaylistServiceApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(PlaylistServiceApplication.class, args);
        DiscoveryClient discoveryClient = applicationContext.getBean(DiscoveryClient.class);
        discoveryClient.getApplicationInfoManager().setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }
}

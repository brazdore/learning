package com.example.appservice.config;

import com.example.appservice.eureka.PlaylistEurekaServer;
import com.example.appservice.eureka.SongEurekaServer;
import com.example.appservice.ribbon.PlaylistRibbon;
import com.example.appservice.ribbon.SongRibbon;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> hystrixMetricsStreamServlet() {
        return new ServletRegistrationBean<>(new HystrixMetricsStreamServlet(), "/hystrix.stream");
    }

    @Bean
    public ApplicationInfoManager applicationInfoManager() {
        return new ApplicationInfoManager(new MyDataCenterInstanceConfig(), new ApplicationInfoManager.OptionalArgs());
    }

    @Bean
    public DiscoveryClient discoveryClient(ApplicationInfoManager applicationInfoManager) {
        return new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());
    }

    @Override
    public void run(String... args) {
        SongRibbon songRibbon = new SongRibbon(new SongEurekaServer(discoveryClient(applicationInfoManager())));
        System.out.println("\u001B[33m" + "SONG-SERVICE:");
        songRibbon.getLoadBalancer().getAllServers().forEach(System.out::println);
        System.out.print("\u001B[0m");

        PlaylistRibbon playlistRibbon = new PlaylistRibbon(new PlaylistEurekaServer(discoveryClient(applicationInfoManager())));
        System.out.println("\u001B[33m" + "PLAYLIST-SERVICE:");
        playlistRibbon.getLoadBalancer().getAllServers().forEach(System.out::println);
        System.out.print("\u001B[0m");
    }
}

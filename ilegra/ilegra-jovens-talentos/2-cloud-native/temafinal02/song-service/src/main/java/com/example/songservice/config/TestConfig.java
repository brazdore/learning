package com.example.songservice.config;

import com.example.songservice.entities.Song;
import com.example.songservice.repositories.SongRepository;
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

import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final SongRepository songRepository;

    public TestConfig(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

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

        Song sparks = new Song(1L, "Sparks");
        Song violet = new Song(2L, "Violet");
        Song redHeart = new Song(3L, "Red Heart");
        Song infinity = new Song(4L, "Infinity");
        Song reflect = new Song(5L, "Reflect");
        Song hinotori = new Song(6L, "Hinotori");

        songRepository.saveAll(List.of(sparks, violet, redHeart, infinity, reflect));
    }
}

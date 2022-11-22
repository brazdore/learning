package com.example.playlistservice.config;


import com.example.playlistservice.entities.Playlist;
import com.example.playlistservice.repositories.PlaylistRepository;
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

    private final PlaylistRepository playlistRepository;

    public TestConfig(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
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
        playlistRepository.save(new Playlist(1L, "Holosongs", List.of(1L, 2L, 3L)));
        playlistRepository.save(new Playlist(2L, "Kiara Playlist", List.of(1L, 6L)));
    }
}

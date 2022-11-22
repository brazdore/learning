package com.example.appservice.eureka;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SongEurekaServer implements ServerList<Server> {

    private final DiscoveryClient discoveryClient;

    public SongEurekaServer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<com.netflix.loadbalancer.Server> getInitialListOfServers() {
        return getUpdatedListOfServers();
    }

    @Override
    public List<com.netflix.loadbalancer.Server> getUpdatedListOfServers() {
        List<com.netflix.loadbalancer.Server> servers = new ArrayList<>();
        try {
            List<InstanceInfo> instanceInfos = discoveryClient.getApplications()
                    .getRegisteredApplications("song-service")
                    .getInstances();
            instanceInfos.removeIf(instance -> InstanceInfo.InstanceStatus.DOWN.equals(instance.getStatus()));
            instanceInfos.forEach(server -> servers.add(new com.netflix.loadbalancer.Server(server.getHostName(), server.getPort())));
        } catch (NullPointerException e) {
            return List.of();
        }
        return servers;
    }
}

package com.example.appservice.ribbon;

import com.example.appservice.entities.Song;
import com.example.appservice.eureka.SongEurekaServer;
import com.example.appservice.hystrix.HystrixAppFindAllSongsCommand;
import com.example.appservice.hystrix.HystrixAppFindSongByIdCommand;
import com.example.appservice.hystrix.HystrixAppFindSongListById;
import com.example.appservice.interfaces.ISong;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.optionals.OptionalDecoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SongRibbon {

    private final SongEurekaServer songEurekaServer;

    public SongRibbon(SongEurekaServer songEurekaServer) {
        this.songEurekaServer = songEurekaServer;
    }

    public Optional<Song> findSongById(String id) {
        ISong isong = getFeignClients();
        return new HystrixAppFindSongByIdCommand(isong, id).execute();
    }

    public List<Song> findAllSongs() {
        ISong iSong = getFeignClients();
        return new HystrixAppFindAllSongsCommand(iSong).execute();
    }

    public List<Song> findSongListById(String idList) {
        ISong iSong = getFeignClients();
        return new HystrixAppFindSongListById(iSong, idList).execute();
    }

    private ISong getFeignClients() {
        ILoadBalancer loadBalancer = new BaseLoadBalancer();
        List<Server> serverList = songEurekaServer.getUpdatedListOfServers();
        loadBalancer.addServers(serverList);
        while (true) {
            try {
                Server server = loadBalancer.chooseServer(null);
                String url = "http://" + server.getHostPort();
                ISong iSong = Feign.builder()
                        .decoder(new OptionalDecoder(new GsonDecoder()))
                        .target(ISong.class, url);
                var response = new HystrixAppFindAllSongsCommand(iSong).execute();
                if (Objects.nonNull(response) || loadBalancer.getReachableServers().isEmpty()) {
                    return iSong;
                } else {
                    loadBalancer.markServerDown(server);
                }
            } catch (NullPointerException e) {
                return null;
            }
        }
    }

    public ILoadBalancer getLoadBalancer() {
        ILoadBalancer loadBalancer = new BaseLoadBalancer();
        List<Server> serverList = songEurekaServer.getUpdatedListOfServers();
        loadBalancer.addServers(serverList);
        return loadBalancer;
    }
}

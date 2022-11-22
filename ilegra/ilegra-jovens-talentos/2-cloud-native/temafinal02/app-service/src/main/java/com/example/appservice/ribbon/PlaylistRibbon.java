package com.example.appservice.ribbon;

import com.example.appservice.entities.Playlist;
import com.example.appservice.eureka.PlaylistEurekaServer;
import com.example.appservice.hystrix.HystrixAppFindAllPlaylistsCommand;
import com.example.appservice.hystrix.HystrixAppFindPlaylistByIdCommand;
import com.example.appservice.interfaces.IPlaylist;
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
public class PlaylistRibbon {

    private final PlaylistEurekaServer playlistEurekaServer;

    public PlaylistRibbon(PlaylistEurekaServer playlistEurekaServer) {
        this.playlistEurekaServer = playlistEurekaServer;
    }

    public Optional<Playlist> findPlaylistById(String id) {
        IPlaylist iPlaylist = getFeignClients();
        return new HystrixAppFindPlaylistByIdCommand(iPlaylist, id).execute();
    }

    public List<Playlist> findAllPlaylists() {
        IPlaylist iPlaylist = getFeignClients();
        return new HystrixAppFindAllPlaylistsCommand(iPlaylist).execute();
    }

    private IPlaylist getFeignClients() {
        ILoadBalancer loadBalancer = new BaseLoadBalancer();
        List<Server> serverList = playlistEurekaServer.getUpdatedListOfServers();
        loadBalancer.addServers(serverList);

        while (true) {
            try {
                Server server = loadBalancer.chooseServer(null);
                String url = "http://" + server.getHostPort();
                IPlaylist iPlaylist = Feign.builder()
                        .decoder(new OptionalDecoder(new GsonDecoder()))
                        .target(IPlaylist.class, url);
                var response = new HystrixAppFindAllPlaylistsCommand(iPlaylist).execute();
                if (Objects.nonNull(response) || loadBalancer.getReachableServers().isEmpty()) {
                    return iPlaylist;
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
        List<Server> serverList = playlistEurekaServer.getUpdatedListOfServers();
        loadBalancer.addServers(serverList);
        return loadBalancer;
    }
}

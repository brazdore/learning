package com.example.springmongodb.config;

import com.example.springmongodb.domain.Post;
import com.example.springmongodb.domain.User;
import com.example.springmongodb.dto.AuthorDTO;
import com.example.springmongodb.dto.CommentDTO;
import com.example.springmongodb.repositories.PostRepository;
import com.example.springmongodb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User kiara = new User(null, "Takanashi Kiara", "tenchou@kfp.com");
        User gura = new User(null, "Gawr Gura", "a@gmail.com");
        User ame = new User(null, "Amelia Watson", "ame@gmail.com");

        userRepository.saveAll(Arrays.asList(kiara, gura, ame));

        Post p1 = new Post(null, sdf.parse("21/07/2021"), "Where is my Bread?", "I lost my bread!", new AuthorDTO(gura));
        Post p2 = new Post(null, sdf.parse("27/07/2021"), "I found my bread...", "It is a little wet and smells funny, but I will give it a try.", new AuthorDTO(gura));

        CommentDTO c1 = new CommentDTO("gura...", sdf.parse("22/07/2021"), new AuthorDTO(kiara));
        CommentDTO c2 = new CommentDTO("I have some bread in my house if you want", sdf.parse("22/07/2021"), new AuthorDTO(ame));
        CommentDTO c3 = new CommentDTO("omg dont eat it, i give u chicken", sdf.parse("28/07/2021"), new AuthorDTO(kiara));

        p1.getComments().addAll(Arrays.asList(c1, c2));
        p2.getComments().add(c3);

        postRepository.saveAll(Arrays.asList(p1, p2));

        gura.getPosts().addAll(Arrays.asList(p1, p2));
        userRepository.save(gura);

    }
}

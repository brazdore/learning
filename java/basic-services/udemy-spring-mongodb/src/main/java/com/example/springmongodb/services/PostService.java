package com.example.springmongodb.services;

import com.example.springmongodb.domain.Post;
import com.example.springmongodb.repositories.PostRepository;
import com.example.springmongodb.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    public Post findByID(String id) {
        Optional<Post> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Post not found. ID: " + id + "."));
    }

    public List<Post> findByTitle(String text) {
        return repo.searchByTitle(text);
    }

    public List<Post> searchAll(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return repo.searchAll(text, minDate, maxDate);
    }
}

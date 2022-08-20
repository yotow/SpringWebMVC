package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private static final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong count = new AtomicLong();

    public List<Post> all() {
        List<Post> res = new ArrayList<>();
        for (Map.Entry<Long, Post> entry : posts.entrySet()) {
            res.add(entry.getValue());
        }
        return res;
    }

    public Optional<Post> getById(long id) {
        if (posts.containsKey(id)) {
            return Optional.of(posts.get(id));
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        long id = count.incrementAndGet();
        posts.put(post.getId(), post);
        return getById(post.getId()).orElseThrow(NotFoundException::new);
    }

    public void removeById(long id) {
        try {
            posts.remove(id);
        } catch (NullPointerException e) {
            //ignore
        }
    }
}

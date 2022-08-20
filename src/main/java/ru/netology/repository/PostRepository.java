package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private static final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private AtomicLong count = new AtomicLong();

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
        if (post.getId() == 0) {
            if(count.get() == Long.MAX_VALUE){
                count = new AtomicLong();
            }
            long id = count.incrementAndGet();
            while (posts.containsKey(id)){
                id = count.incrementAndGet();
            }
            post.setId(id);
            posts.put(id, post);
        } else {
            Optional<Post> p = getById(post.getId());
            if (p.isPresent()) {
                posts.put(post.getId(), post);
            }
        }
        return getById(post.getId()).orElseThrow(NotFoundException::new);
    }

    public void removeById(long id) {
        try {
            posts.remove(id);
            count.decrementAndGet();
        } catch (NullPointerException e) {
            //ignore
        }
    }
}

package com.uh635c.task13.controller;

import com.uh635c.task13.model.Post;
import com.uh635c.task13.model.Writer;
import com.uh635c.task13.repository.WriterRepository;
import com.uh635c.task13.repository.gson.GsonWriterRepositoryImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WriterController {
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();
    private final PostController postController = new PostController();

    private Long getMaxPostId(String idWriter) {
        List<Post> listPosts = getById(idWriter).getPosts();
        Post postWithMaxId = listPosts.stream().max(Comparator.comparing(Post::getId)).orElse(null);
        return Objects.nonNull(postWithMaxId) ? postWithMaxId.getId() + 1 : 1;
    }


    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer getById(String id) {
        return writerRepository.getById(Long.parseLong(id));
    }

    public Writer save(String nameWriter, String namePost, String contentPost, String labelsPost) {
        Writer writer = new Writer();
        writer.setName(nameWriter);
        Post post = postController.save(namePost, contentPost, labelsPost);
        post.setId(1L);
        writer.setPosts(Stream.of(post).collect(Collectors.toList()));
        writerRepository.save(writer);
        return writer;
    }

    public Writer update(String id, String name) {
        Writer writer = new Writer();
        writer.setId(Long.parseLong(id));
        writer.setName(name);
        writer.setPosts(getById(id).getPosts());
        writerRepository.update(writer);
        return writer;
    }

    public void delete(String idWriter) {
        writerRepository.deleteById(Long.parseLong(idWriter));
    }

    public Writer savePost(String idWriter, String namePost, String contentPost, String labelsPost){
        Writer writer = new Writer();
        writer.setId(Long.parseLong(idWriter));
        writer.setName(getById(idWriter).getName());
        Post post = postController.save(namePost,contentPost, labelsPost);
        post.setId(getMaxPostId(idWriter));
        List<Post> listPosts = getById(idWriter).getPosts();
        listPosts.add(post);
        writer.setPosts(listPosts);
        writerRepository.update(writer);
        return writer;
    }


    public Writer updatePost(String idWriter, String idPost, String namePost, String contentPost, String labelsPost) {
        Writer writer = new Writer();
        writer.setId(Long.parseLong(idWriter));
        writer.setName(getById(idWriter).getName());
        List<Post> listPosts = getById(idWriter).getPosts();
        Long id = Long.parseLong(idPost);
        listPosts.forEach(a-> {
            if(a.getId().equals(id)){
                a.setName(namePost);
                a.setContent(contentPost);
                a.setLabels(postController.createListOfLabels(labelsPost));
            }
        });
        writer.setPosts(listPosts);
        writerRepository.update(writer);
        return writer;
    }

    public void deletePost(String idWriter, String idPost) {
        List<Post> posts = writerRepository.getById(Long.parseLong(idWriter))
                .getPosts();
        posts.removeIf(a -> a.getId().equals(Long.parseLong(idPost)));
        Writer writer = new Writer();
        writer.setId(Long.parseLong(idWriter));
        writer.setName(getById(idWriter).getName());
        writer.setPosts(posts);
        writerRepository.update(writer);
    }
}

package com.uh635c.task13.controller;

import com.uh635c.task13.model.Label;
import com.uh635c.task13.model.Post;
import com.uh635c.task13.repository.PostRepository;
import com.uh635c.task13.repository.gson.GsonPostRepositoryImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PostController {
    PostRepository postRepository = new GsonPostRepositoryImp();

    List<Label> createListOfLabels(String strLabels) {

        List<Label> list = new ArrayList<>();
        LabelController labelController = new LabelController();

        for (String str : strLabels.split(", ")) {

            if(labelController.getAll().stream().noneMatch(a-> a.getName().equals(str))){
                labelController.save(str);
            }

            list.add(labelController.getAll().stream().filter(a->a.getName().equals(str)).findFirst().orElse(null));
        }
        return list;
    }

    public List<Post> getAll() {
        return Collections.unmodifiableList(postRepository.getAll());
    }

    public Post getById(String id) {
        return postRepository.getById(Long.parseLong(id));
    }

    public Post save(String name, String content, String strLabels) {
        Post post = new Post();
        post.setName(name);
        post.setContent(content);
        post.setLabels(createListOfLabels(strLabels));
        postRepository.save(post);
        return post;
    }

    public Post update(String id, String name, String content, String strLabels){
        Post post = new Post();
        post.setId(Long.parseLong(id));
        post.setName(name);
        post.setContent(content);
        post.setLabels(createListOfLabels(strLabels));
        postRepository.update(post);
        return post;
    }
    public void delete(String id){
        postRepository.deleteById(Long.parseLong(id));
    }
}

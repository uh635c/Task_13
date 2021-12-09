package com.uh635c.task13.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uh635c.task13.model.Post;
import com.uh635c.task13.repository.PostRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.*;

public class GsonPostRepositoryImp implements PostRepository {
    private static final String LABEL_FILE_LOCATION = "src/main/resources/posts.json";

    private Long getMaxId(List<Post> posts) {
        Post postWithMaxId = posts.stream().max(Comparator.comparing(Post::getId)).orElse(null);
        return Objects.nonNull(postWithMaxId) ? postWithMaxId.getId() + 1 : 1;
    }

    private List<Post> getListFromFile() {
        List<Post> posts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(LABEL_FILE_LOCATION)))) {

            String strFromFile = br.lines().reduce("", (a, b) -> a + b);
            Type targetClassType = new TypeToken<List<Post>>() {
            }.getType();

            posts = new Gson().fromJson(strFromFile, targetClassType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    private void writeListToFile(List<Post> posts) {
        String jsonList = new Gson().toJson(posts);
        try (BufferedWriter br = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(LABEL_FILE_LOCATION)))) {
            br.write(jsonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        return getListFromFile();
    }

    @Override
    public Post getById(Long id) {
        return getListFromFile().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Post save(Post post) {
        List<Post> currentPosts = getListFromFile();
        post.setId(getMaxId(currentPosts));
        currentPosts.add(post);
        writeListToFile(currentPosts);
        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> currentPosts = getListFromFile();
        currentPosts.forEach(a -> {
            if (a.getId().equals(post.getId())) {
                a.setName(post.getName());
                a.setContent((post.getContent()));
                a.setLabels(post.getLabels());
            }
        });
        writeListToFile(currentPosts);
        return post;
    }

    @Override
    public void deleteById(Long id) {
        List<Post> currentPosts = getListFromFile();
        currentPosts.removeIf(a -> a.getId().equals(id));
        writeListToFile(currentPosts);
    }
}

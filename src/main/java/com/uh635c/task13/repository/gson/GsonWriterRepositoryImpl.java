package com.uh635c.task13.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uh635c.task13.model.Writer;
import com.uh635c.task13.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private static final String LABEL_FILE_LOCATION = "src/main/resources/writers.json";

    private Long getMaxId(List<Writer> writers) {
        Writer writer = writers.stream().max(Comparator.comparing(Writer::getId)).orElse(null);
        return Objects.nonNull(writer) ? writer.getId() + 1 : 1;
    }

    private List<Writer> getListFromFile() {
        List<Writer> writers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(LABEL_FILE_LOCATION)))) {
            String strFromFile = br.lines().reduce("", (a, b) -> a + b);
            Type targetClassType = new TypeToken<List<Writer>>(){}.getType();

            writers = new Gson().fromJson(strFromFile, targetClassType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writers;
    }

    private void writeListToFile(List<Writer> writers){
        String str = new Gson().toJson(writers);
        try(BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(LABEL_FILE_LOCATION)))){
            bw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Writer> getAll() {
        return getListFromFile();
    }

    @Override
    public Writer getById(Long id) {
        return getListFromFile().stream().filter(a-> a.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> currentWriters = getListFromFile();
        writer.setId(getMaxId(currentWriters));
        currentWriters.add(writer);
        writeListToFile(currentWriters);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> currentWriters = getListFromFile();
        currentWriters.forEach(a->{
            if(a.getId().equals(writer.getId())){
                a.setName(writer.getName());
                a.setPosts(writer.getPosts());
            }
        });
        writeListToFile(currentWriters);
        return writer;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> currentWriters = getListFromFile();
        currentWriters.removeIf(a-> a.getId().equals(id));
        writeListToFile(currentWriters);
    }
}

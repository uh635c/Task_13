package com.uh635c.task13.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uh635c.task13.model.Label;
import com.uh635c.task13.repository.LabelRepository;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private static final String LABEL_FILE_LOCATION = "src/main/resources/labels.json";

    private Long getMaxId(List<Label> list) {
        Label labelWithMaxId = list.stream().max(Comparator.comparing(Label::getId)).orElse(null);
        return Objects.nonNull(labelWithMaxId) ? labelWithMaxId.getId() + 1 : 1;
    }

    private List<Label> getListFromFile() {
        List<Label> listLabels = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(LABEL_FILE_LOCATION)))) {

            String strFromFile = br.lines().reduce("", (a, b) -> a + b);
            Type targetClassType = new TypeToken<ArrayList<Label>>() {
            }.getType();

            listLabels = new Gson().fromJson(strFromFile, targetClassType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listLabels;
    }

    public void writeLabelsToFile(List<Label> labels) {
        String jsonList = new Gson().toJson(labels);
        try (BufferedWriter br = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(LABEL_FILE_LOCATION)))) {
            br.write(jsonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Label getById(Long id) throws RuntimeException {
        return getListFromFile().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Label> getAll() {
        return getListFromFile();
    }

    public Label save(Label label) {
        List<Label> currentLabels = getListFromFile();
        Long newMaxId = getMaxId(currentLabels);
        label.setId(newMaxId);
        currentLabels.add(label);
        writeLabelsToFile(currentLabels);
        return label;
    }

    public Label update(Label label) {
        List<Label> currentLabels = getListFromFile();
        currentLabels.forEach(a -> {
            if(a.getId().equals(label.getId())){
                a.setName(label.getName());
            }
        });
        writeLabelsToFile(currentLabels);
        return label;
    }

    public void deleteById(Long id) {
        List<Label> currentLabels = getListFromFile();
        currentLabels.removeIf(a -> a.getId().equals(id));
        writeLabelsToFile(currentLabels);
    }
}

package com.uh635c.task13;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class LabelRepository {
    public List<Label> ll;
    private Long id;

    public LabelRepository() {
        ll = getListFromFile();
        id = getMaxId(ll);
    }

    private Long getMaxId(List<Label> list) {
        Long maxId = 0L;
        for (Label elem : list) {
            if (maxId < elem.getId()) {
                maxId = elem.getId();
            }
        }
        return maxId;
    }

    private List<Label> getListFromFile() {
        List<Label> listLabels = null;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("src/main/resources/Label.json")))) {
            
            String strFromFile = br.lines().reduce("",(a,b)->a+b);
            Type targetClassType = new TypeToken<ArrayList<Label>>(){}.getType();

            listLabels = new Gson().fromJson(strFromFile, targetClassType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listLabels;
    }

    public void exit(){
        String jsonList = new Gson().toJson(ll);
        try(BufferedWriter br = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("src/main/resources/Label.json")))){
            br.write(jsonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Label getById(Long id) throws RuntimeException {
        for (Label elem : ll) {
            if (elem.getId().equals(id)) {
                return elem;
            }
        }
        throw new RuntimeException("Provided id is incorrect.");
    }

    List<Label> getAll() {
        return ll;
    }

    Label save(Label label) {
        label.setId(++id);
        for(Label elem : ll){
            if(elem.getName().equals(label.getName())){
                throw new RuntimeException("Label you are trying to save is already exists.");
            }
        }
        ll.add(label);
        return label;
    }

    Label update(Label label) {
        for (Label elem : ll) {
            if (elem.getId().equals(label.getId())) {
                elem.setName(label.getName());
                return elem;
            }
        }
        throw new RuntimeException("Updated label is incorrect.");
    }

    void deleteById(Long id){
        Iterator<Label> iterator = ll.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId().equals(id)){
                iterator.remove();
                return;
            }

        }
        throw new RuntimeException("There is no such Label to delete.");
    }
}

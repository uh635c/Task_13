package com.uh635c.task13.controller;

import com.uh635c.task13.model.Label;
import com.uh635c.task13.repository.LabelRepository;
import com.uh635c.task13.repository.gson.GsonLabelRepositoryImpl;

import java.util.*;

public class LabelController {
    LabelRepository gsonLabelRepositoryImpl = new GsonLabelRepositoryImpl();

    public List<Label> getAll(){
        return Collections.unmodifiableList(gsonLabelRepositoryImpl.getAll());
    }

    /*public Label getById(Long id){
        return copyLabel(gsonLabelRepositoryImpl.getById(id));
    }*/

    public Label save(String name){
        Label label = new Label();
        label.setName(name);
        return gsonLabelRepositoryImpl.save(label);
    }

    public Label update(String id, String name){
        Label label = new Label();
        label.setId(Long.parseLong(id));
        label.setName(name);
        return gsonLabelRepositoryImpl.update(label);
    }

    public void delete(String id){
        gsonLabelRepositoryImpl.deleteById(Long.parseLong(id));
    }
}

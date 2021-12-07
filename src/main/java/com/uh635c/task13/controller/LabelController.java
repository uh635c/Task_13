package com.uh635c.task13.controller;

import com.uh635c.task13.model.Label;
import com.uh635c.task13.repository.LabelRepository;
import com.uh635c.task13.repository.gson.GsonLabelRepositoryImpl;

import java.util.*;

public class LabelController {
    LabelRepository gsonLabelRepositoryImpl = new GsonLabelRepositoryImpl();

    private Label copyLabel(Label label){
        Label copyLabel = new Label();
        copyLabel.setId(label.getId());
        copyLabel.setName(label.getName());
        return copyLabel;
    }

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

    public Label update(Long id, String name){
        Label label = new Label();
        label.setId(id);
        label.setName(name);
        return gsonLabelRepositoryImpl.update(label);
    }

    public void delete(Long id){
        gsonLabelRepositoryImpl.deleteById(id);
    }
}

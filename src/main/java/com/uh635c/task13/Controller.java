package com.uh635c.task13;

import java.util.*;

public class Controller {
    LabelRepository labelRepository = new LabelRepository();

    private Label copyLabel(Label label){
        Label copyLabel = new Label();
        copyLabel.setId(label.getId());
        copyLabel.setName(label.getName());
        return copyLabel;
    }

    public List<Label> getAll(){
        List<Label> list = new ArrayList<>();
        for(Label elem : labelRepository.getAll()){
            list.add(copyLabel(elem));
        }
        return list;
    }

    public Label getById(Long id){
        return copyLabel(labelRepository.getById(id));
    }

    public Label save(Label label){
        Label labelToRep = new Label(label.getId(),label.getName());
        return labelRepository.save(labelToRep);
    }

    public Label update(Label label){
        return labelRepository.update(label);
    }

    public void delete(Long id){
        labelRepository.deleteById(id);
    }
}

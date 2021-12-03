package com.uh635c.task13;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        LabelRepository labelRepository = new LabelRepository();

        //get all Label objects
        List<Label> list = labelRepository.getAll();
        list.stream().forEach(System.out::println);
        System.out.println();

        //save created Label objects
        labelRepository.save(new Label(0L,"weather"));
        labelRepository.save(new Label(0L,"news"));

        //get all Label objects
        list = labelRepository.getAll();
        list.stream().forEach(System.out::println);
        System.out.println();

        //update Label with id=3
        labelRepository.update(new Label(3L,"BBC news"));

        //get all Label objects
        list = labelRepository.getAll();
        list.stream().forEach(System.out::println);
        System.out.println();

        //get Label by id (id=2)
        System.out.println(labelRepository.getById(2L).toString());
        System.out.println();

        //delete by id (id=2)
        labelRepository.deleteById(2L);

        //get all Label objects
        list = labelRepository.getAll();
        list.stream().forEach(System.out::println);

        //exit with uploading all Labels to the Label.json file
        labelRepository.exit();
    }
}

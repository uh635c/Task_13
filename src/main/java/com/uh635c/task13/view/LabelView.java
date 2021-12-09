package com.uh635c.task13.view;

import com.uh635c.task13.controller.LabelController;
import com.uh635c.task13.model.Label;

import java.util.*;

public class LabelView {
    private final LabelController controller = new LabelController();
    private final Scanner userInput = new Scanner(System.in);

    private void showAllLabels(){
        System.out.println();
        controller.getAll().stream().forEach(System.out::println);
        System.out.println();
        System.out.print("action: ");
    }

    public void startApplication(){

        System.out.print("Enter the required action:\n" +
                "    delete - to delete a Label,\n" +
                "    save - to save a Label\n" +
                "    update - to update a Label\n" +
                "    exit - to exit from the application\n");

        showAllLabels();

        while(userInput.hasNext()){

            switch(userInput.next()){
                case "update":
                    System.out.print("Enter ID of the required Label: ");
                    String id = userInput.next();
                    System.out.print("Enter a new NAME of the required Label: ");
                    String nameUpdate = userInput.next();
                    controller.update(id, nameUpdate);
                    showAllLabels();
                    break;
                case "save":
                    System.out.print("Enter a NAME of a new Label: ");
                    String nameSave = userInput.next();
                    controller.save(nameSave);
                    showAllLabels();
                    break;
                case "delete":
                    System.out.print("Enter ID of the required Label to delete: ");
                    controller.delete(userInput.next());
                    showAllLabels();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command, try again");
                    System.out.println("action: ");
            }
        }
    }
}

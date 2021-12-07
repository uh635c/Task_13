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

    private Label toClearLabel(Label label){
        label.setId(0L);
        label.setName(null);
        return label;
    }

    public void startApplication(){

        boolean exit = false;

        System.out.print("Enter the required action. For example:\n" +
                "    delete - for deleting Label,\n" +
                "    save - for saving Label\n" +
                "    update - for updating Label\n" +
                "    exit - for exit from application\n");

        showAllLabels();

        while(userInput.hasNext()){

            switch(userInput.next()){
                case "update":
                    System.out.print("Enter ID of the required Label: ");
                    Long id = userInput.nextLong();
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
                    controller.delete(userInput.nextLong());
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

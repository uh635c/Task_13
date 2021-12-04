package com.uh635c.task13;

import java.util.*;

public class View {
    Controller controller = new Controller();
    Scanner userInput = new Scanner(System.in);


    /*private void showStringConsole(String str){
        System.out.println(str + "\r\n");
    }*/
    private void showAllLabels(){
        System.out.println();
        controller.getAll().stream().forEach(System.out::println);
        System.out.println();
        System.out.print("action: ");
    }

    private void exit(){
        controller.labelRepository.exit();
    }

    private Label toClearLabel(Label label){
        label.setId(0L);
        label.setName(null);
        return label;
    }

    public void startApplication(){
        Label label = new Label();
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
                    label.setId(userInput.nextLong());
                    System.out.print("Enter a new NAME of the required Label: ");
                    label.setName(userInput.next());
                    controller.update(label);
                    showAllLabels();
                    toClearLabel(label);
                    break;
                case "save":
                    label.setId(0L);
                    System.out.print("Enter a NAME of a new Label: ");
                    label.setName(userInput.next());
                    controller.save(label);
                    showAllLabels();
                    toClearLabel(label);
                    break;
                case "delete":
                    System.out.print("Enter ID of the required Label to delete: ");
                    controller.delete(userInput.nextLong());
                    showAllLabels();
                    break;
                case "exit":
                    exit();
                    return;
                default:
                    System.out.println("Unknown command, try again");
                    System.out.println("action: ");
            }
        }
    }
}

package com.uh635c.task13.view;

import com.uh635c.task13.controller.WriterController;
import com.uh635c.task13.model.Post;
import com.uh635c.task13.model.Writer;

import java.util.*;


public class WriterView {
    private final WriterController controller = new WriterController();
    private final Scanner userInput = new Scanner(System.in).useDelimiter("\n");

    private void showAllWriters() {
        System.out.println();
        controller.getAll().forEach(System.out::println);
        System.out.println();
        System.out.print("action: ");
    }

    private void showAllPosts(String idW) {
        System.out.println();
        Writer writer = controller.getById(idW);
        System.out.println("--- All posts of  " + writer.getName() + " ---");
        writer.getPosts().forEach(a -> System.out.println("id: " + a.getId() + ", " + "name: " + a.getName()));
        System.out.println();
        System.out.print("action: ");
    }

    public void startApplication() {
        boolean exitPosts = false;

        System.out.println("Enter the required action:\n" +
                "    enter - to enter into a Writer/Post,\n" +
                "    delete - to delete a Writer/Post,\n" +
                "    save - to save a Writer/Post,\n" +
                "    update - to update a Writer/Post,\n" +
                "    exit - to exit from layer or application.\n");

        showAllWriters();

        while (userInput.hasNext()) {
            switch (userInput.next()) {
                case "enter":
                    System.out.print("Enter id of the required Writer: ");
                    String idW = userInput.next();
                    showAllPosts(idW);

                    while (userInput.hasNext()) {
                        exitPosts = false;
                        switch (userInput.next()) {
                            case "enter":
                                System.out.print("Enter id of the required Post: ");
                                String idP = userInput.next();
                                Post post = controller.getById(idW).getPosts().stream()
                                        .filter(a -> a.getId().equals(Long.parseLong(idP)))
                                        .findFirst()
                                        .orElse(null);
                                System.out.println("\n--- Content of the post name - " + post.getName() + " ---");
                                System.out.print(post.getContent() + "\nLabels: ");
                                post.getLabels().forEach(a -> System.out.print(a.getName() + " "));
                                System.out.println();
                                showAllPosts(idW);
                                break;
                            case "delete":
                                System.out.print("Enter id of the required Post: ");
                                controller.deletePost(idW, userInput.next());
                                showAllPosts(idW);
                                break;
                            case "save":
                                System.out.print("Enter a new post name: ");
                                String nameNewPost = userInput.next();
                                System.out.println("Enter a content of the new post: ");
                                String content = userInput.next();
                                System.out.println("Enter labels for the new post using \", \" as a delimiter: ");
                                String labels = userInput.next();
                                controller.savePost(idW, nameNewPost, content, labels);
                                showAllPosts(idW);
                                break;
                            case "update":
                                System.out.print("Enter id of the required Post: ");
                                String idPost = userInput.next();
                                System.out.print("Enter an updated post name: ");
                                String nameUpdtPost = userInput.next() + userInput.nextLine();
                                System.out.println("Enter an updated content of the post: ");
                                String contentUpdt = userInput.next() + userInput.nextLine();
                                System.out.println("Enter updated labels for the post using \", \" as a delimiter: ");
                                String labelsUpdt = userInput.next() + userInput.nextLine();
                                controller.updatePost(idW, idPost, nameUpdtPost, contentUpdt, labelsUpdt);
                                showAllPosts(idW);
                                break;
                            case "exit":
                                exitPosts= true;
                                break;
                            default:
                                System.out.println("Unknown command, try again");
                                System.out.println("action: ");
                        }
                        if(exitPosts){
                            break;
                        }
                    }
                    showAllWriters();
                    break;
                case "delete":
                    System.out.print("Enter id of the Writer to delete: ");
                    controller.delete(userInput.next());
                    showAllWriters();
                    break;
                case "save":
                    System.out.print("Enter new name of Writer: ");
                    String nameWriter = userInput.next() + userInput.nextLine();
                    System.out.print("Enter a new post name: ");
                    String namePost = userInput.next() + userInput.nextLine();
                    System.out.println("Enter a content of the new post: ");
                    String content = userInput.next() + userInput.nextLine();
                    System.out.println("Enter labels for the new post using \", \" as a delimiter: ");
                    String labels = userInput.next() + userInput.nextLine();
                    controller.save(nameWriter, namePost, content, labels);
                    showAllWriters();
                    break;
                case "update":
                    System.out.print("Enter id of Writer to update: ");
                    String id = userInput.next();
                    System.out.print("Enter a new name of Writer " + controller.getById(id).getName() + ": ");
                    String nameNew = userInput.next() + userInput.nextLine();
                    controller.update(id, nameNew);
                    showAllWriters();
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

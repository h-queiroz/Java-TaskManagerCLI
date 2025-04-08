package com.hqueiroz.taskmanager;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {

    static TaskManager taskManager = new TaskManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        loadTasks();

        while(true) {
            System.out.println("\nWelcome to my Task Manager CLI Program");
            System.out.println("List of current tasks:");
            taskManager.listTasks();

            int choice = showMenu();

            if (choice != 0)
                handleChoice(choice);
        }
    }

    static int showMenu(){
        System.out.println("List of Actions:");
        System.out.println("\t1. Add a task");
        System.out.println("\t2. Mark a task as Done");
        System.out.println("\t3. Delete a specific task");
        System.out.println("\t4. Delete all Done tasks");
        System.out.println("\t5. Exit");

        try{
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Cleaning Buffer

            if(choice > 0 && choice < 6)
                return choice;

            System.out.println("Invalid choice. Try again");
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void handleChoice(int choice){
        switch(choice){
            case 1: {
                try{
                    System.out.print("Insert a task title: ");
                    String taskTitle = scanner.nextLine();

                    System.out.print("Insert a task description: ");
                    String taskDescription = scanner.nextLine();

                    System.out.print("Insert a due time for your task (DD/MM/YYYY hh:mm): ");
                    String inputDateTime = scanner.nextLine();
                    LocalDateTime dateTimeParsed = LocalDateTime.parse(inputDateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                    taskManager.addTask(new Task(taskTitle, taskDescription, dateTimeParsed));

                }catch(DateTimeParseException e){
                    System.out.println("Please insert a valid dateTime.");
                }
                break;
            }

            case 2: {
                System.out.print("Insert the task title: ");
                String taskTitle = scanner.nextLine();

                if(taskManager.markDone(taskTitle))
                    System.out.println("\""+taskTitle+"\"pi marked as Done");
                else
                    System.out.println("Task not found.");

                break;
            }

            case 3: {
                System.out.print("Insert the task title: ");
                String taskTitle = scanner.nextLine();

                if(taskManager.removeTask(taskTitle))
                    System.out.println("\""+taskTitle+"\" task removed");
                else
                    System.out.println("Task not found.");

                break;
            }

            case 4: {
                taskManager.removeAllDone();
                System.out.println("All Done tasks removed.");
                break;
            }

            case 5: {
                System.out.println("Goodbye.");
                scanner.close();
                System.exit(0);
                break;
            }

            default:
        }
    }

    static void loadTasks(){
        try(BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))){

            String line;
            while((line = reader.readLine()) != null){
                String[] itens = line.split(";");
                taskManager.addTask(new Task(itens[0], itens[1], LocalDateTime.parse(itens[2], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), Task.Status.valueOf(itens[3])));
            }
            System.out.println("Tasks loaded");
        } catch (FileNotFoundException e) {
            System.out.println("No saved tasks");
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
    }
}

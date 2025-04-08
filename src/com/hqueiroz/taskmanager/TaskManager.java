package com.hqueiroz.taskmanager;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskManager {

    private final ArrayList<Task> taskList = new ArrayList<>();

    public void addTask(Task task){
        taskList.add(task);
    }

    public void listTasks(){
        if(taskList.isEmpty())
            System.out.println("\tYou don't have any tasks.");
        else
            for(Task task : taskList) {
                System.out.println("\t- " + task.getTitle());
                System.out.println("\t\tDescription: " + task.getDescription());
                System.out.println("\t\tDue Date: " + task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                if(task.getStatus() == Task.Status.NOT_DONE && task.getDueDate().isBefore(LocalDateTime.now()))
                    task.setStatus(Task.Status.OUTDATED);
                System.out.println("\t\tStatus: " + task.getStatus());
                System.out.println();
            }
    }

    public boolean markDone(String title){
        if(!taskList.isEmpty()) {
            for (Task task : taskList)
                if (task.getTitle().equalsIgnoreCase(title)) {
                    task.setStatus(Task.Status.DONE);
                    return true;
                }
            return false;
        }
        return false;
    }

    public boolean removeTask(String title){
        if(!taskList.isEmpty()) {
            for (Task task : taskList)
                if (task.getTitle().equalsIgnoreCase(title)) {
                    taskList.remove(task);
                    return true;
                }
            return false;
        }
        return false;
    }

    public void removeAllDone(){
        if(!taskList.isEmpty()) {
            ArrayList<Task> tasksToBeRemoved = new ArrayList<>();

            for (Task task : taskList)
                if (task.getStatus().toString().equals("Done"))
                    tasksToBeRemoved.add(task);

            for (Task task : tasksToBeRemoved)
                taskList.remove(task);
        }
    }

    public String[] returnTasksInLines(){
        String[] tasksInString = new String[taskList.size()];
        int i = 0;
        for(Task task : taskList){
            String line = task.getTitle()+";"+task.getDescription()+";"+task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+";"+task.getStatus().toSave();
            if(i+1 < taskList.size())
                line += '\n';
            tasksInString[i++] = line;
        }

        return tasksInString;
    }
}

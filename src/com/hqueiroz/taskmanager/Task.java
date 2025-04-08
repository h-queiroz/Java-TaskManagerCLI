package com.hqueiroz.taskmanager;

import java.time.LocalDateTime;

public class Task {

    public enum Status{
        DONE{
            @Override
            public String toString(){
                return "Done";
            }
        },
        NOT_DONE{
            @Override
            public String toString(){
                return "Not Done";
            }
        },
        OUTDATED{
            @Override
            public String toString(){
                return "Outdated";
            }
        }
    }

    private final String title;
    private final String description;
    private final LocalDateTime dueDate;
    private Status status = Status.NOT_DONE;

    Task(String title, String description, LocalDateTime dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        if(dueDate.isBefore(LocalDateTime.now()))
            this.status = Status.OUTDATED;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public LocalDateTime getDueDate(){
        return this.dueDate;
    }

    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Task.Status newStatus){
        this.status = newStatus;
    }
}
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
        PENDING{
            @Override
            public String toString(){
                return "Pending";
            }
        },
        OUTDATED{
            @Override
            public String toString(){
                return "Outdated";
            }
        };

        public String toSave() {
            return switch(this){
                case DONE -> "DONE";
                case PENDING -> "PENDING";
                case OUTDATED -> "OUTDATED";
                default -> "";
            };
        }
    }

    private final String title;
    private final String description;
    private final LocalDateTime dueDate;
    private Status status = Status.PENDING;

    Task(String title, String description, LocalDateTime dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        if(dueDate.isBefore(LocalDateTime.now()))
            this.status = Status.OUTDATED;
    }

    Task(String title, String description, LocalDateTime dueDate, Task.Status status){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        if(dueDate.isBefore(LocalDateTime.now()))
            this.status = Status.OUTDATED;
        else
            this.status = status;
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
package com.example.todolist.persistence.data;

import com.example.todolist.persistence.enums.Status;

public class Note {
    int id;

    String message;
    Status status;

    public Note(int id, String message) {
        this.id = id;
        this.message = message;
        this.status = Status.NOT_DONE;
    }
    public int getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

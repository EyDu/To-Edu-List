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
}

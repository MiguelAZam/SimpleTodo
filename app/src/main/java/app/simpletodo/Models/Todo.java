package app.simpletodo.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Todos")
public class Todo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;
    private String dueDate;
    private boolean isCompleted;

    public Todo(String title, String description, int priority, String dueDate){
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

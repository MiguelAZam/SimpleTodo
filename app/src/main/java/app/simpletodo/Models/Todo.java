package app.simpletodo.Models;

public class Todo {

    private String title;
    private String description;
    private int priority;
    private String dueDate;
    private boolean isCompleted;

    public Todo(String title, String description, int priority, String dueDate, boolean isCompleted){
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}

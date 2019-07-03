package app.simpletodo.Models;

public class Todo {

    private String title;
    private String description;
    private String priority;
    private String dueDate;

    public Todo(String title, String description, String priority, String dueDate){
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }
}

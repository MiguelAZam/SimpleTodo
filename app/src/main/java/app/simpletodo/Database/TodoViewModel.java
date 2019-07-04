package app.simpletodo.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import app.simpletodo.Models.Todo;

public class TodoViewModel extends AndroidViewModel {

    private DatabaseRepository todosRepo;
    private LiveData<List<Todo>> todos;

    public TodoViewModel(Application app){
        super(app);
        todosRepo = new DatabaseRepository(app);
        todos = todosRepo.getTodos();
    }

    //Get list of tod-os in the database
    public LiveData<List<Todo>> getTodos(){
        return todos;
    }

    //Get to-do based in given position
    public Todo getTodo(int position){
        return todos.getValue().get(position);
    }

    //Insert to-do in the database
    public void insertTodo(Todo todo){
        todosRepo.insert(todo);
    }

    //Update to-do in the database
    public void updateTodo(Todo todo){
        todosRepo.update(todo);
    }

    //Delete to-do from the database
    public void deleteTodo(Todo todo){
        todosRepo.delete(todo);
    }

}

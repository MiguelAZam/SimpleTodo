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

    public LiveData<List<Todo>> getTodos(){
        return todos;
    }

    public Todo getTodo(int position){
        return todos.getValue().get(position);
    }

    public void insertTodo(Todo todo){
        todosRepo.insert(todo);
    }

    public void updateTodo(Todo todo){
        todosRepo.update(todo);
    }

    public void deleteTodo(Todo todo){
        todosRepo.delete(todo);
    }

}

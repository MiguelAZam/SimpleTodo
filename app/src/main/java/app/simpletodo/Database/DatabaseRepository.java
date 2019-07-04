package app.simpletodo.Database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import app.simpletodo.Models.Todo;

public class DatabaseRepository {

    private static TodoDao todoDao;
    private static LiveData<List<Todo>> todos;

    //Async actions
    private final static int INSERT_TODO = 0;
    private final static int UPDATE_TODO = 1;
    private final static int DELETE_TODO = 2;

    public DatabaseRepository(Context c){
        SimpleTodoDatabase db = SimpleTodoDatabase.getInstance(c);
        todoDao = db.TodoDao();
        todos = todoDao.getTodos();
    }

    //Get list of todos from database
    public LiveData<List<Todo>> getTodos(){
        return todos;
    }

    //Insert a to-do in database
    public void insert(Todo todo){
        new dbAsyncTask(todoDao, INSERT_TODO).execute(todo);
    }

    //Update a to-do in database
    public void update(Todo todo){
        new dbAsyncTask(todoDao, UPDATE_TODO).execute(todo);
    }

    //Delete a to-do in database
    public void delete(Todo todo){
        new dbAsyncTask(todoDao, DELETE_TODO).execute(todo);
    }

    //Async Task class to handle async operations (insert, update, delete)
    private static class dbAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao asyncDao;
        private int OPERATION;

        dbAsyncTask(TodoDao todoDao, int OPERATION){
            asyncDao = todoDao;
            this.OPERATION = OPERATION;
        }

        @Override
        protected Void doInBackground(final Todo... todos) {
            //Switch to identify which action must be perform
            switch (OPERATION){
                case INSERT_TODO:
                    asyncDao.insertTodo(todos[0]);
                    break;
                case UPDATE_TODO:
                    asyncDao.updateTodo(todos[0]);
                    break;
                case DELETE_TODO:
                    asyncDao.deleteTodo(todos[0]);
                    break;
                default:
                    break;
            }
            return null;
        }
    }



}

package app.simpletodo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import app.simpletodo.Adapters.TodoAdapter;
import app.simpletodo.Database.TodoViewModel;
import app.simpletodo.Models.Todo;

public class MainActivity extends AppCompatActivity {

    //Numberic code to identify activity action
    public final static int CREATE_REQUEST_CODE = 10;
    public final static int EDIT_REQUEST_CODE = 20;

    //Keys used for passing data between activities
    public final static String TITLE = "activityTitle";
    public final static String BUTTON = "buttonText";
    public final static String TODO = "todo";

    //Views
    RecyclerView rv_todos;

    //View Model that will observe changes in the database
    private static TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        rv_todos = findViewById(R.id.rv_todos);

        //Set database observer
        setDatabaseObserver();

    }

    //Method to fill recycler view with a list of to-dos
    private void setRecyclerView(List<Todo> todoList){
        if(todoList != null){
            //Initialize adapter and layout manager
            TodoAdapter adapter = new TodoAdapter(this, todoList);
            RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);

            //Configure recycler view
            rv_todos.setLayoutManager(mLayout);
            rv_todos.setItemAnimator(new DefaultItemAnimator());
            rv_todos.setAdapter(adapter);

            //Create item touch helper to detect if user swiped a to-do
            //If a to-do was swiped delete it
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    //Delete swiped to-do
                    int position = viewHolder.getAdapterPosition();
                    todoViewModel.deleteTodo(todoViewModel.getTodo(position));
                    adapter.notifyItemRemoved(position);
                    //Notify user
                    showToast("Item deleted!");
                }
            });
            helper.attachToRecyclerView(rv_todos);
        }
    }

    //Method to set database observer
    public void setDatabaseObserver(){
        //Fill recycler view with to-dos
        todoViewModel = new TodoViewModel(getApplication());
        todoViewModel.getTodos().observe(this, todos -> setRecyclerView(todos));
    }

    //onClick method when FAB is clicked
    //It creates an intent with information to create to-do
    public void createTodo(View v){
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        intent.putExtra(TITLE, "Create Todo");
        intent.putExtra(BUTTON, "Create");
        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    //Method to receive results from EditItem Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If action was to create to-do
        if(resultCode == RESULT_OK && requestCode == CREATE_REQUEST_CODE){
            //Get to-do
            Todo createdTodo = (Todo) data.getExtras().getSerializable(TODO);
            //Create to-do in database
            todoViewModel.insertTodo(createdTodo);
            //Notify user
            showToast("Item created!");
            //Else if action was to update to-do
        } else if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE){
            //Get to-do
            Todo updatedTodo = (Todo) data.getExtras().getSerializable(TODO);
            //Update to-do
            todoViewModel.updateTodo(updatedTodo);
            showToast("Item updated!");
        }
    }

    //Helper method to simplify showing a toast
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        return;
    }
}

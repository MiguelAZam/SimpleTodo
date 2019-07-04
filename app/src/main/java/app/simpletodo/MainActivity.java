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

    //Numberic code to identify the edit activity
    public final static int CREATE_REQUEST_CODE = 10;
    public final static int EDIT_REQUEST_CODE = 20;

    //Keys used for passing data between activities
    public final static String TITLE = "activityTitle";
    public final static String BUTTON = "buttonText";
    public final static String TODO = "todo";

    RecyclerView rv_todos;

    private static TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_todos = findViewById(R.id.rv_todos);

        setDatabaseObserver();

    }

    private void setRecyclerView(List<Todo> todoList){
        if(todoList != null){
            TodoAdapter adapter = new TodoAdapter(this, todoList);
            RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);

            rv_todos.setLayoutManager(mLayout);
            rv_todos.setItemAnimator(new DefaultItemAnimator());
            rv_todos.setAdapter(adapter);

            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    int position = viewHolder.getAdapterPosition();
                    todoViewModel.deleteTodo(todoViewModel.getTodo(position));
                    adapter.notifyItemRemoved(position);
                    showToast("Item deleted!");
                }
            });
            helper.attachToRecyclerView(rv_todos);
        }
    }

    public void setDatabaseObserver(){
        todoViewModel = new TodoViewModel(getApplication());
        todoViewModel.getTodos().observe(this, todos -> {
            setRecyclerView(todos);
        });
    }

    public void createTodo(View v){
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        intent.putExtra(TITLE, "Create Todo");
        intent.putExtra(BUTTON, "Create");
        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == CREATE_REQUEST_CODE){
            Todo createdTodo = (Todo) data.getExtras().getSerializable(TODO);
            todoViewModel.insertTodo(createdTodo);
            showToast("Item created!");
        } else if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE){
            Todo updatedTodo = (Todo) data.getExtras().getSerializable(TODO);
            todoViewModel.updateTodo(updatedTodo);
            showToast("Item updated!");
        }
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        return;
    }
}

package app.simpletodo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
    public final static String ITEM_TEXT = "itemText";
    public final static String ITEM_POSITION = "itemPosition";

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
        }
    }

    public void setDatabaseObserver(){
        todoViewModel = new TodoViewModel(getApplication());
        todoViewModel.getTodos().observe(this, todos -> {
            Log.d("Main Activity", "Getting Todos");
            setRecyclerView(todos);
        });
    }

    public void createTodo(View v){
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        intent.putExtra(TITLE, "Create Todo");
        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(resultCode == RESULT_OK && requestCode == CREATE_REQUEST_CODE){

        } else if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE){
            String updateItem = data.getExtras().getString(ITEM_TEXT);
            int position = data.getExtras().getInt(ITEM_POSITION);
            items.set(position, updateItem);
            itemsAdapter.notifyDataSetChanged();

            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
        }*/
    }
}

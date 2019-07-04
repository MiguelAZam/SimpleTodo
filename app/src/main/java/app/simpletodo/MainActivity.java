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
import app.simpletodo.Models.Todo;

public class MainActivity extends AppCompatActivity {

    //Numberic code to identify the edit activity
    public final static int CREATE_REQUEST_CODE = 10;
    public final static int EDIT_REQUEST_CODE = 20;

    //Keys used for passing data between activities
    public final static String ITEM_TEXT = "itemText";
    public final static String ITEM_POSITION = "itemPosition";

    RecyclerView rv_todos;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_todos = findViewById(R.id.rv_todos);

        List<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo("Clean dishes", "", 0, "25/06/2019", false));
        todoList.add(new Todo("Finish app", "", 1, "29/06/2019", true));
        todoList.add(new Todo("Finish assignment of spanish", "", 2, "04/07/2019", false));

        setRecyclerView(todoList);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE){
            String updateItem = data.getExtras().getString(ITEM_TEXT);
            int position = data.getExtras().getInt(ITEM_POSITION);
            items.set(position, updateItem);
            itemsAdapter.notifyDataSetChanged();

            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();
        }*/
    }
}

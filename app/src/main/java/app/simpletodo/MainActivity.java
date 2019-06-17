package app.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv_items;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_items = findViewById(R.id.lv_items);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lv_items.setAdapter(itemsAdapter);

        setupListViewListener();

    }

    public void onAddItem(View v){
        EditText et_new_item = findViewById(R.id.et_new_item);
        String newItem = et_new_item.getText().toString();
        itemsAdapter.add(newItem);
        et_new_item.setText("");

        writeItems();

        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }

    public void setupListViewListener(){
        lv_items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public File getDataFile(){
        return new File(getFilesDir(), "todo.txt");
    }

    public void readItems(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch(IOException e){
            Log.e("Main Activity", "Error reading file", e);
            items = new ArrayList<>();
        }

    }

    public void writeItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        } catch(IOException e){
            Log.e("Main Activity", "Error writing items", e);
        }

    }
}

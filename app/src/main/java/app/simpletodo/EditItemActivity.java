package app.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.simpletodo.Models.Todo;

import static app.simpletodo.MainActivity.BUTTON;
import static app.simpletodo.MainActivity.TITLE;

public class EditItemActivity extends AppCompatActivity {

    Button b_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE));
        b_action = findViewById(R.id.b_action);
        b_action.setText(getIntent().getStringExtra(BUTTON));
    }

    public void setTodo(Todo todo){

    }

    public Todo getTodo(){
        return null;
    }

    public void onSaveItem(View v){
        /*Intent intent = new Intent();
        intent.putExtra(ITEM_TEXT, et_item_text.getText().toString());
        intent.putExtra(ITEM_POSITION, position);
        setResult(RESULT_OK, intent);
        finish();*/
    }
}

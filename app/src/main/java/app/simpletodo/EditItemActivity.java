package app.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static app.simpletodo.MainActivity.ITEM_POSITION;
import static app.simpletodo.MainActivity.ITEM_TEXT;
import static app.simpletodo.MainActivity.TITLE;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String appTitle = getIntent().getStringExtra(TITLE);
        getSupportActionBar().setTitle(appTitle);
    }

    public void onSaveItem(View v){
        /*Intent intent = new Intent();
        intent.putExtra(ITEM_TEXT, et_item_text.getText().toString());
        intent.putExtra(ITEM_POSITION, position);
        setResult(RESULT_OK, intent);
        finish();*/
    }
}

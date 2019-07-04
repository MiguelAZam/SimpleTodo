package app.simpletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Method;
import java.util.Locale;

import app.simpletodo.Models.Todo;

import static app.simpletodo.MainActivity.BUTTON;
import static app.simpletodo.MainActivity.TITLE;
import static app.simpletodo.MainActivity.TODO;

public class EditItemActivity extends AppCompatActivity {

    //Views
    EditText et_title;
    EditText et_dueDate;
    Spinner s_priority;
    EditText et_description;
    Button b_action;

    //Calendar to pick date
    Calendar cal;

    //To-do to be updated
    Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Set activity title according to given action from main activity
        getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE));

        //Initialize views
        et_title = findViewById(R.id.et_title);
        et_dueDate = findViewById(R.id.et_dueDate);
        s_priority = findViewById(R.id.s_priority);
        et_description = findViewById(R.id.et_description);
        b_action = findViewById(R.id.b_action);

        //Set button text according to action to perform
        b_action.setText(getIntent().getStringExtra(BUTTON));

        //Set date picker
        setDateSelector();

        //Check if a to-do was given
        todo = (Todo) getIntent().getSerializableExtra(TODO);
        if(todo != null){
            setTodo(todo);
        }
    }

    //Method to update due date label according to selected date
    private void updateLabel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            et_dueDate.setText(sdf.format(cal.getTime()));
        }
    }

    //Method to set date picker
    public void setDateSelector(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Initialize calendar
            cal = Calendar.getInstance();
            //Create date picker dialog
            DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            };
            //set onClick listener when due date label is clicked to
            //launch date picker
            et_dueDate.setOnClickListener(v -> {
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                new DatePickerDialog(EditItemActivity.this, date, year, month, day).show();
            });
        }
    }

    //Method to fill label fields if to-do was given
    public void setTodo(Todo todo){
        et_title.setText(todo.getTitle());
        et_dueDate.setText(todo.getDueDate());
        s_priority.setSelection(todo.getPriority());
        et_description.setText(todo.getDescription());
    }

    //Helper method to convert spinner strings to integers
    private int priorityToInt(String priority){
        if(priority.equals("Low")){
            return 0;
        } else if(priority.equals("Medium")){
            return 1;
        } else if(priority.equals("High")){
            return 2;
        } else{
            return -1;
        }
    }

    //Method to obtain to-do from the information in fields
    public Todo getTodo(){
        //Get information
        String title = et_title.getText().toString();
        String description = et_description.getText().toString();
        String priority = s_priority.getSelectedItem().toString();
        String dueDate = et_dueDate.getText().toString();
        //If to-do is not null it means to-do is going to be updated
        if(todo != null){
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setPriority(priorityToInt(priority));
            todo.setDueDate(dueDate);
            return todo;
        }
        //Otherwise, to-do will be created
        return new Todo(title, description, priorityToInt(priority), dueDate);
    }

    //onClick method to send result to main activity
    public void onSaveItem(View v){
        Todo todo = getTodo();
        Intent intent = new Intent();
        intent.putExtra(TODO, todo);
        setResult(RESULT_OK, intent);
        finish();
    }
}

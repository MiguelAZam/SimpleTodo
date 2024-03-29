package app.simpletodo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.simpletodo.Database.TodoViewModel;
import app.simpletodo.EditItemActivity;
import app.simpletodo.Models.Todo;
import app.simpletodo.R;

import static app.simpletodo.MainActivity.BUTTON;
import static app.simpletodo.MainActivity.EDIT_REQUEST_CODE;
import static app.simpletodo.MainActivity.TITLE;
import static app.simpletodo.MainActivity.TODO;

//To-do adapter for recycler view and show a custom
//list view
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    //App context and list of todos
    private Context mContext;
    private List<Todo> todoList;

    public TodoAdapter(Context c, List<Todo> todoList){
        this.mContext = c;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    //Helper function to adapt to-do label color according to
    //priority
    private int getPriorityColor(int priority){
        switch (priority){
            case (0):
                return ContextCompat.getColor(mContext, R.color.sm_priority);
            case(1):
                return ContextCompat.getColor(mContext, R.color.md_priority);
            case(2):
                return ContextCompat.getColor(mContext, R.color.lg_priority);
            default:
                return 0;
        }
    }

    //Helper function to adapt done icon if the task is completed or not
    private int getTodoState(boolean isCompleted){
        return isCompleted ? R.drawable.done_green : R.drawable.done_grey;
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, int pos) {
        //Get to-do
        final Todo todo = todoList.get(pos);

        //Fill information
        holder.v_priority_label.setBackgroundColor(getPriorityColor(todo.getPriority()));
        holder.tv_title.setText(todo.getTitle());
        holder.tv_dueDate.setText(todo.getDueDate());
        holder.iv_done.setImageResource(getTodoState(todo.isCompleted()));

        //onClick listener to start intent of update information of to-do
        holder.card.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, EditItemActivity.class);
            //Activity title, button text & to-do to be updated
            intent.putExtra(TITLE, "Edit Todo");
            intent.putExtra(BUTTON, "Update");
            intent.putExtra(TODO, todo);
            //Start activity
            ((Activity) mContext).startActivityForResult(intent, EDIT_REQUEST_CODE);
        });

        //onLongClick listener to update to-do to toggle completion of to-do
        holder.card.setOnLongClickListener(view -> {
            todo.setCompleted(!todo.isCompleted());
            new TodoViewModel(((Activity) mContext).getApplication()).updateTodo(todo);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        View v_priority_label;
        TextView tv_title;
        TextView tv_dueDate;
        ImageView iv_done;

        TodoViewHolder(View view){
            super(view);
            //Initialize views by ids
            card = view.findViewById(R.id.cv_todo);
            v_priority_label = view.findViewById(R.id.v_priority_label);
            tv_title = view.findViewById(R.id.tv_title);
            tv_dueDate = view.findViewById(R.id.tv_dueDate);
            iv_done = view.findViewById(R.id.iv_done);
        }

    }

}

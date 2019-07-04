package app.simpletodo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.simpletodo.Models.Todo;
import app.simpletodo.R;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

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

    private int getTodoState(boolean isCompleted){
        return isCompleted ? R.drawable.done_green : R.drawable.done_grey;
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, int pos) {
        final Todo todo = todoList.get(pos);

        holder.v_priority_label.setBackgroundColor(getPriorityColor(todo.getPriority()));
        holder.tv_title.setText(todo.getTitle());
        holder.tv_dueDate.setText(todo.getDueDate());
        holder.iv_done.setImageResource(getTodoState(todo.isCompleted()));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{

        View v_priority_label;
        TextView tv_title;
        TextView tv_dueDate;
        ImageView iv_done;

        TodoViewHolder(View view){
            super(view);
            v_priority_label = view.findViewById(R.id.v_priority_label);
            tv_title = view.findViewById(R.id.tv_title);
            tv_dueDate = view.findViewById(R.id.tv_dueDate);
            iv_done = view.findViewById(R.id.iv_done);
        }

    }

}

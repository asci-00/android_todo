package com.example.todo.util;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.dto.Todo;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Todo.Response> itemList = null;
    private TransitionDrawable background;

    public ItemAdapter(ArrayList<Todo.Response> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_item, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo.Response todo = itemList.get(position);

        holder.itemText.setText(todo.getItem());
        holder.dateText.setText(todo.getDeadline() == null ? "No deadline" : todo.getDeadline().toString() );

        background = (TransitionDrawable) holder.layout.getBackground();

        if(itemList.get(position).getCompleted()) background.startTransition(0);
        else background.resetTransition();
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public class ViewHolder extends
        RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout layout;
        ImageView itemImage;
        TextView itemText;
        TextView dateText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_layout);
            itemText = itemView.findViewById(R.id.item_text);
            dateText = itemView.findViewById(R.id.date_text);
            itemImage = itemView.findViewById(R.id.item_image);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int idx = getAdapterPosition();
            final Todo.Response todo = itemList.get(idx);

            if(v.equals(layout)) {
                Log.i("ItemAdapter", String.format("click [%d] %s", idx, todo));
                todo.setCompleted(!todo.getCompleted());

                Todo.Request updateTodo = new Todo.Request();
                updateTodo.setCompleted(todo.getCompleted());
                toggleTodo(todo.getId(), updateTodo);

                notifyItemChanged(idx);
            }
        }
    }

    public void removeAt(int position, int id) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
        deleteTodo(id);
    }

    protected void deleteTodo(Integer id) { }
    protected void toggleTodo(Integer id, Todo.Request todo) { }
}

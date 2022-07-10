package com.example.todo.util;

import static com.example.todo.util.Service.service;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.dto.Todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Log.i("ItemAdapter", String.format("bind [%d] %s", position, todo));

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
        ImageButton deleteButton;
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
            deleteButton = itemView.findViewById(R.id.item_delete_btn);
            background = (TransitionDrawable) layout.getBackground();
            deleteButton.setOnClickListener(this);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int idx = getAdapterPosition();
            final Todo.Response todo = itemList.get(idx);

            if(v.equals(deleteButton)) {
                Log.i("ItemAdapter", String.format("delete [%d] %s", idx, todo));
                removeAt(idx, todo.getId());
            } else if(v.equals(layout)) {
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

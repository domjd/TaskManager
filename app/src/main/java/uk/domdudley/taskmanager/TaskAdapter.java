package uk.domdudley.taskmanager;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dom on 16/08/2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<Task> mDataset;
    private DBManager dbManager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox infoText;
        public ViewHolder(View v) {
            super(v);
            infoText = (CheckBox) v.findViewById(R.id.task_text);
        }
    }

    public TaskAdapter(List<Task> dataset){
        mDataset = dataset;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card,parent,false);
        ViewHolder vh = new ViewHolder(v);
        dbManager = new DBManager(v.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.infoText.setText(mDataset.get(position).getTaskName());
        holder.infoText.setChecked(mDataset.get(position).getDone());

        holder.infoText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dbManager.changeTaskStatus(mDataset.get(holder.getAdapterPosition()));
            }
        });
    }

    public void swapDataset(List<Task> dataset){
        mDataset.clear();
        mDataset.addAll(dataset);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {
        dbManager.removeTask(mDataset.get(position));
        mDataset.remove(position);
        notifyItemRemoved(position);
        Log.v("Array Size: ", Integer.toString(getItemCount()));
    }
}

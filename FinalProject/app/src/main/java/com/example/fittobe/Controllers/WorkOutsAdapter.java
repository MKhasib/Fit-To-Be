package com.example.fittobe.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittobe.Models.Exercise;
import com.example.fittobe.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkOutsAdapter extends RecyclerView.Adapter<WorkOutsAdapter.ViewHolder>{

    private List<Exercise> mExercises;
    private View.OnClickListener mOnWorkOutClickListener;
    private Context context;

    public WorkOutsAdapter(List<Exercise> exercises , Context context) {
        this.mExercises = exercises;
        this.context = context;
    }
    public void setItemClickListener(View.OnClickListener clickListener) {
        mOnWorkOutClickListener = clickListener;
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.workout_name)
        TextView mNameTextView;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnWorkOutClickListener);
            ButterKnife.bind(this, itemView);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View TaskView = inflater.inflate(R.layout.workout_card, parent, false);
        return new ViewHolder(TaskView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise e = mExercises.get(position);
        holder.mNameTextView.setText(e.getName());
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }
    public void setExercises(List<Exercise> exercises){
        this.mExercises = exercises;
    }

    public List<Exercise> getExercises(){
        return mExercises;
    }


}

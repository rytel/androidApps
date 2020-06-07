package com.example.kalkulatorocen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public RadioGroup mRadioGroup;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
            mRadioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, int position) {

        final ExampleItem currentItem = mExampleList.get(position);
        holder.mTextView.setText(currentItem.getmText());

        holder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.findViewById(R.id.option1) == group.findViewById(checkedId))
                    currentItem.setGrade(2);
                if (group.findViewById(R.id.option2) == group.findViewById(checkedId))
                    currentItem.setGrade(3);
                if (group.findViewById(R.id.option3) == group.findViewById(checkedId))
                    currentItem.setGrade(4);
                if (group.findViewById(R.id.option4) == group.findViewById(checkedId))
                    currentItem.setGrade(5);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}

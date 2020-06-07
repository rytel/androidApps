package com.example.app2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private List<ExampleItem> mExampleList;

    public ExampleAdapter(Context context, Cursor cursor, List<ExampleItem> exampleList) {
        mContext = context;
        mCursor = cursor;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        holder.exampleItem = new ExampleItem(
                mCursor.getLong(mCursor.getColumnIndex(DBHelper.ID)),
                mCursor.getString(mCursor.getColumnIndex(DBHelper.POLE1)),
                mCursor.getString(mCursor.getColumnIndex(DBHelper.POLE2))
        );
        holder.nameText.setText(holder.exampleItem.getPole1());
        holder.itemView.setTag(holder.exampleItem.getId());
        holder.nameText.setBackgroundColor(holder.exampleItem.isSelected() ? Color.GRAY : Color.WHITE);
        mExampleList.add(holder.exampleItem);

        holder.nameText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!holder.exampleItem.isSelected()) {
                    Uri uri = Uri.parse(holder.exampleItem.getPole2());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }
                return false;
            }
        });
        holder.nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.exampleItem.isSelected()) {
                    holder.exampleItem.setSelected(true);
                } else {
                    holder.exampleItem.setSelected(false);
                }
                holder.nameText.setBackgroundColor(holder.exampleItem.isSelected() ? Color.GRAY : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public ExampleItem exampleItem;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textView);
        }
    }

}

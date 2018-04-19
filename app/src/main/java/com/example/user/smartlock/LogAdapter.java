package com.example.user.smartlock;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {


    private Context context;
    private List<LogsDO> result = new ArrayList<>();

    public LogAdapter(CheckLog context, List<LogsDO> result)
    {
        this.context= (Context) context;
        this.result=result;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.listalllogs,null);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final LogsDO  user = result.get(position);
        holder.username.setText(user.getUserId());
        holder.timeofcreation.setText(user.getTimestamp());

      
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView username, timeofcreation;

        public ViewHolder(View itemView) {
            super(itemView);
            mView =itemView;

            username = (TextView) itemView.findViewById(R.id.username);
            timeofcreation = (TextView) itemView.findViewById(R.id.timeofcreation);
        }
    }
}

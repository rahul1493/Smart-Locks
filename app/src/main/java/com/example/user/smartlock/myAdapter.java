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

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {


    private Context context;
    private List<UserDetailsDO> result = new ArrayList<>();

    public myAdapter(AllUsers context, List<UserDetailsDO> result)
    {
        this.context= (Context) context;
        this.result=result;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.listallusers,null);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final UserDetailsDO  user = result.get(position);
        holder.username.setText(user.getUsername());
        holder.timeofcreation.setText(user.getTimeofcreation());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calling the intent SingleTask
                Intent singleTaskActivity = new Intent(context,SingleTask.class);
                singleTaskActivity.putExtra("Username",user.getUsername());
                singleTaskActivity.putExtra("TimeofCreation",user.getTimeofcreation());
                context.startActivity(singleTaskActivity);

            }
        });
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

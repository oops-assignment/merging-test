package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NAdapter extends RecyclerView.Adapter<com.example.myapp.NAdapter.MyHolder>
{

    List<Listdata> noteslist;
    private Context context;
    public  NAdapter(List<Listdata> noteslist,Context context)
    {
        this.context=context;
        this.noteslist=noteslist;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nitemv,viewGroup,false);

        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.myapp.NAdapter.MyHolder myHolder, int position) {
        Listdata data=noteslist.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.desc.setText(data.getDesc());
    }


    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,desc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.details);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listdata listdata=noteslist.get(getAdapterPosition());
                    Intent i=new Intent(context, EditN.class);
                    i.putExtra("id",listdata.id);
                    i.putExtra("title",listdata.title);
                    i.putExtra("desc",listdata.desc);
                    context.startActivity(i);
                }
            });

        }


    }
}


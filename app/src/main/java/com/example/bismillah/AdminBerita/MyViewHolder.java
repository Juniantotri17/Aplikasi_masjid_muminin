package com.example.bismillah.AdminBerita;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bismillah.R;

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    ImageView imageView1;
    TextView textView;
    TextView textView1;
    TextView textView2;
    View v;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image_single_view);
        textView=itemView.findViewById(R.id.textView_single_view);
        textView1=itemView.findViewById(R.id.textView_tgl);
//        textView2=itemView.findViewById(R.id.textView_desk);

        v=itemView;
    }
}


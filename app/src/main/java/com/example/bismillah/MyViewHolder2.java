package com.example.bismillah;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bismillah.R;

class MyViewHolder2 extends RecyclerView.ViewHolder {
    ImageView imageViewberita;
    ImageView imageView1;
    TextView textViewberita;
    TextView textView1berita;
    TextView textView2;
    View v;


    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        imageViewberita=itemView.findViewById(R.id.image_berita);
        textViewberita=itemView.findViewById(R.id.judul_berita);
        textView1berita=itemView.findViewById(R.id.tgl_berita);
//        textView2=itemView.findViewById(R.id.textView_desk);

        v=itemView;
    }
}
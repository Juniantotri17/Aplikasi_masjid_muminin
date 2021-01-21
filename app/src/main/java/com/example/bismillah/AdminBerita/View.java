package com.example.bismillah.AdminBerita;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bismillah.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class View extends AppCompatActivity {
    private ImageView imageView;
    TextView textView;
    TextView textView1;
    TextView textView2;
    ImageView btnDelete;

    DatabaseReference ref,DataRef;
    StorageReference StorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        btnDelete=findViewById(R.id.btnDelete);
        imageView=findViewById(R.id.image_single_view_Activity);
        textView=findViewById(R.id.textView_single_view_Activity);
        textView1=findViewById(R.id.textView_desk_act);
        textView2=findViewById(R.id.textView_tgl_act);
        ref= FirebaseDatabase.getInstance().getReference().child("Data");

        String Datakey=getIntent().getStringExtra("DataKey");
        DataRef=FirebaseDatabase.getInstance().getReference().child("Data").child(Datakey);
        StorageRef= FirebaseStorage.getInstance().getReference().child("Images").child(Datakey+".jpg");

        ref.child(Datakey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String Judul=dataSnapshot.child("Judul").getValue().toString();
                    String ImageURL=dataSnapshot.child("ImageURL").getValue().toString();
                    String Isi=dataSnapshot.child("Isi").getValue().toString();
                    String Tanggal=dataSnapshot.child("Tanggal").getValue().toString();

                    Picasso.get().load(ImageURL).into(imageView);
                    textView.setText(Judul);
                    textView1.setText(Isi);
                    textView2.setText(Tanggal);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDelete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        StorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(),AdminPage.class));
                            }
                        });
                    }
                });
            }
        });
    }
}

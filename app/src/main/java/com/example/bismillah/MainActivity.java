package com.example.bismillah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bismillah.AdminBerita.Data;
import com.example.bismillah.Qibla.Qibla;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private ImageView appbarSearch,appbarAccount;

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Data> options;
    FirebaseRecyclerAdapter<Data,MyViewHolder2> adapter;
    DatabaseReference Dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbarSearch  = findViewById(R.id.appbar_search);
        appbarAccount  = findViewById(R.id.appbar_account);
        appbarAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        appbarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Search Klik", Toast.LENGTH_LONG).show();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.navigation_jadwal:
                        startActivity(new Intent(getApplicationContext()
                                , Jadwal.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_keuangan:
                        startActivity(new Intent(getApplicationContext()
                                , Keuangan.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_Qibla:
                        startActivity(new Intent(getApplicationContext()
                                , Qibla.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navigation_home:
                        return true;
                }
                return false;
            }
        });

        Dataref = FirebaseDatabase.getInstance().getReference().child("Data");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData("");
    }

    private void LoadData(String data) {

        options = new FirebaseRecyclerOptions.Builder<Data>().setQuery(Dataref, Data.class).build();
        adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder2 holder, final int position, @NonNull Data model) {
                holder.textViewberita.setText(model.getJudul());
                holder.textView1berita.setText(model.getTanggal());
//            holder.textView2.setText(model.getIsi());


                Picasso.get().load(model.getImageURL()).into(holder.imageViewberita);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,ViewBerita.class);
                        intent.putExtra("DataKey",getRef(position).getKey());

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_berita, parent,false);
                return new MyViewHolder2(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()){
            moveTaskToBack(true);
            return;
        }else {
            Toast.makeText(getBaseContext(),"Tekan sekali lagi untu keluar",Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
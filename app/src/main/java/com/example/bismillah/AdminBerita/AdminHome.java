package com.example.bismillah.AdminBerita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bismillah.MainActivity;
import com.example.bismillah.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {
    Button btnLogout, btnBerita, btnKeuangan;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnBerita=findViewById(R.id.btn_berita);

        btnBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminPage.class);
                startActivity(intent);
            }
        });


        mAuth=FirebaseAuth.getInstance();
        btnLogout=findViewById(R.id.logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(AdminHome.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}


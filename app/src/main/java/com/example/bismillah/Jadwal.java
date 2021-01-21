package com.example.bismillah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.CalculationParameters;
import com.batoulapps.adhan.Coordinates;
import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import com.example.bismillah.Qibla.Qibla;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Jadwal extends AppCompatActivity {

    TextView Subuh,Dhuhur,Ashar,Maghrib,Isha,Hari;
    private float Latitude,Longitude;
    ProgressDialog progressDialog;
    FusedLocationProviderClient fusedLocationProviderClient;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_jadwal);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.navigation_jadwal:
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
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Hari = findViewById(R.id.hari);
        Hari.setText(currentdate);

        Subuh = findViewById(R.id.subuh_value);
        Dhuhur = findViewById(R.id.dhuhur_value);
        Ashar = findViewById(R.id.ashar_value);
        Maghrib = findViewById(R.id.maghrib_value);
        Isha = findViewById(R.id.isha_value);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        CheckinternetGPS();
    }
    private void CheckinternetGPS() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(this, "Nyalakan GPS anda", Toast.LENGTH_SHORT).show();
                } else {
                    GetLocation();
                }
            }
        }else{
            Toast.makeText(this,"No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void GetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Jadwal.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            ActivityCompat.requestPermissions(Jadwal.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        } else {
            JadwalShalat();
            //atas masih error
        }
        progressDialog.show();
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location !=null){
                    Geocoder geocoder = new Geocoder(Jadwal.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        Longitude = (float) addresses.get(0).getLongitude();
                        Latitude = (float) addresses.get(0).getLatitude();

                        JadwalShalat();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void JadwalShalat() {

        final DateComponents dateComponents = DateComponents.from(new Date());
        final Coordinates coordinates = new Coordinates(Latitude,Longitude);
        final CalculationParameters parameters = CalculationMethod.SINGAPORE.getParameters();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getDefault());

        PrayerTimes prayerTimes = new PrayerTimes(coordinates,dateComponents,parameters);

        Subuh.setText(Html.fromHtml("" + formatter.format(prayerTimes.fajr)));
        Dhuhur.setText(Html.fromHtml("" + formatter.format(prayerTimes.dhuhr)));
        Ashar.setText(Html.fromHtml("" + formatter.format(prayerTimes.asr)));
        Maghrib.setText(Html.fromHtml("" + formatter.format(prayerTimes.maghrib)));
        Isha.setText(Html.fromHtml("" + formatter.format(prayerTimes.isha)));
        progressDialog.dismiss();
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
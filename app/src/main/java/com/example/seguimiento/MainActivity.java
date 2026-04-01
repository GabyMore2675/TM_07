package com.example.seguimiento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "CicloVida";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate()");
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SegundaActividad.class);
        startActivity(intent);
    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, TerceraActividad3.class);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this, CuartaActividad4.class);
        startActivity(intent);
    }

    @Override protected void onStart()   {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override protected void onResume()  {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override protected void onPause()   {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override protected void onStop()    {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
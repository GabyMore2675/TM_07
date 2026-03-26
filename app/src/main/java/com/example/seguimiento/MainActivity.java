package com.example.seguimiento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Ciclo de Vida";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "En el evento onCreate()");

        //Button btnMostrar = findViewById(R.id.btnMostrar);

        //btnMostrar.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(MainActivity.this, SegundaActividad.class);
        //        startActivity(intent);
        //    }
        //});
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "En el evento onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "En el evento onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "En el evento onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "En el evento onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "En el evento onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "En el evento onDestroy()");
    }
}
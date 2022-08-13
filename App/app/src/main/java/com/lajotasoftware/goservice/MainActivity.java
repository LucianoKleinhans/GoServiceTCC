package com.lajotasoftware.goservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import com.lajotasoftware.goservice.R;

import android.view.View;

import com.lajotasoftware.goservice.Frames.Perfil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_login);
    }

    public void btn_main_to_perfil (View view){
        Intent it = new Intent(this, Perfil.class);
        startActivity(it);
    }
}
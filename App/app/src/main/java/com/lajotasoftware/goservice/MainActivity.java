package com.lajotasoftware.goservice;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lajotasoftware.goservice.Frames.Perfil;
import com.lajotasoftware.goservice.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_main_to_perfil (View view){
        Intent it = new Intent(this, Perfil.class);
        startActivity(it);
    }

    public void bt_main_to_telacadastros(View view){
        Intent it = new Intent();
        startActivity(it);
    }
}
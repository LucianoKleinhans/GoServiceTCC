package com.lajotasoftware.goservice.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lajotasoftware.goservice.R;

public class Perfil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_usuario);

    }

    public void btn_perfil_to_cad (View view){
        Intent it = new Intent(this, Cadastro.class);
        startActivity(it);
    }
}
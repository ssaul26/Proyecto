package com.example.proyecto;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;


public class LoginActivity extends AppCompatActivity {


    EditText txtUsuario, txtContraseña;
    TextView lblRegistrar;
    Button btnInegistrar;
    Connection con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtUsuario=(EditText)  findViewById(R.id.txtUsuario);
        txtContraseña=(EditText)  findViewById(R.id.txtUsuario);
        lblRegistrar=(TextView)  findViewById(R.id.lblRegistrar);
        btnInegistrar=(Button)  findViewById(R.id.btnIngresar);
        }
    }

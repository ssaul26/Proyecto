package com.example.proyecto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.Connection.ConnectionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginActivity extends AppCompatActivity {


    EditText usuario, clave;
    TextView lblregistrar;
    Button btningresar;
    Connection con;

public LoginActivity() {
    ConnectionBD instanceConnection = new ConnectionBD();
    Connection con = instanceConnection.connect();
}
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);

            usuario = (EditText) findViewById(R.id.txtUsuario);
            clave = (EditText) findViewById(R.id.txtContraseña);
            lblregistrar = (TextView) findViewById(R.id.lblRegistrar);
            btningresar = (Button) findViewById(R.id.btnIngresar);

            btningresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  new LoginActivity.login().execute();

                }
            });

            lblregistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent reg = new Intent(getApplicationContext(), RegistrarActivity.class);
                    startActivity(reg);



                }
            });
        }



        public class login extends AsyncTask<String, String, String> {
            String z = null;
            Boolean exito = false;


            @Override
            protected String doInBackground(String... strings) {
                if(con==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Verifique su conexion", Toast.LENGTH_SHORT).show();
                        }
                    });
                    z="En Conexion";
                }
                else {
                    try{
                        String sql=" Select FROM USUARIO WHERE usuario = '"+usuario.getText()+"AND CLAVE= '"+ clave.getText() +"'";
                        Statement stm=con.createStatement();
                        ResultSet rs= stm.executeQuery(sql);
                        if(rs.next()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Acceso Exitoso", Toast.LENGTH_SHORT).show();
                                    Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(menu);

                                }
                            });

                            usuario.setText(" ");
                            clave.setText(" ");
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Error en el usuario o contrasena", Toast.LENGTH_SHORT).show();
                                }
                            });

                            usuario.setText(" ");
                            clave.setText(" ");

                        }

                    }catch (Exception e){
                        exito=false;
                        Log.e("Error de Conexion", e.getMessage());
                    }
                }
                return z;
            }




            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }


        }

    }


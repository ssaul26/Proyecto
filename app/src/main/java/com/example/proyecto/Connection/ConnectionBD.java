package com.example.proyecto.Connection;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBD {

    private String ip = "192.168.1.74";

    private String usuario = "sa";

    private String password = "Sasuke343.";

    private String basedatos = "BASEDEDATOS";


    @SuppressLint("NewApi")
    public Connection connect() {
        Connection connection = null;
        String connectionURL = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.74;databaseName=BASEDEDATOS;user=sa;password=Sasuke343.;");
        } catch (Exception e) {
            Log.e("Error de conexion SQL:", e.getMessage());
        }

        return connection;
    }
}
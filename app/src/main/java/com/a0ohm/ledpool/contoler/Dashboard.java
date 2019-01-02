package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a0ohm.ledpool.HomeActivity;
import com.a0ohm.ledpool.R;
import com.a0ohm.ledpool.entidades.Equipo;
import com.a0ohm.ledpool.entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Activity de llegada despues de login donde muestras y sus bottones de navegacion
 */

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        if (m.contains("Response"))
        {
        setContentView(R.layout.activity_dashboard);}
        if (!m.contains("Response"))
        {
            Intent i = new Intent(Dashboard.this,HomeActivity.class);
            startActivity(i);
        }
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String mResponse = m.getString("Response", "");
        Usuario us = conver(mResponse);
        ((TextView)findViewById(R.id.saludos)).setText("bienvenido \n"+us.getNombre());
        ((Button)findViewById(R.id.control)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this,Control_activity.class);
                startActivity(i);
            }
        });
        ((Button)findViewById(R.id.editPerfil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Dashboard.this,edit.class);
                startActivity(in);

            }
        });
        ((Button)findViewById(R.id.invitados)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Dashboard.this,subUsuarioGestor.class);
                startActivity(i);


            }
        });
        ((Button)findViewById(R.id.Cerrarsesion)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences ma = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                ma.edit().clear().apply();
                Intent i = new Intent(Dashboard.this,HomeActivity.class);
                startActivity(i);
            }
        });



    }
    public static Usuario conver(String input)
    {
        try {
            JSONObject person = new JSONObject(input);
            String id = person.getString("idUsuarios");
            String name = person.getString("nombre");
            String mail = person.getString("mail");
            String rut = person.getString("rut");
            String Clave = person.getString("Clave");
            String rol = person.getString("rol");
            String telefono = person.getString("telefono");
            String codigo = person.getJSONObject("equipo").getString("codigo");
            String nluz = person.getJSONObject("equipo").getString("nluz");
            String StringLuz = person.getJSONObject("equipo").getString("STRINGLinght");
            String luzMAX = person.getJSONObject("equipo").getString("luzMAX");
            Equipo eq = new Equipo(codigo, nluz, luzMAX, StringLuz);
            Usuario usu = new Usuario(id, name, mail, rut, Clave, rol, telefono, eq);
            return usu;
    } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.a0ohm.ledpool.R;
import com.a0ohm.ledpool.entidades.Equipo;
import com.a0ohm.ledpool.entidades.subusuarios;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * activity de auth a los sub usuario
 */

public class loginSub extends AppCompatActivity {
    /**
     * variables de los modelos
     */
    Equipo eq;
    subusuarios usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sub);
    }

    /**
     *
     * Metodo de auth a sub usuario segun telefono y pass
     */
    public void authsub(View view)
    {
        EditText user = (EditText) findViewById(R.id.telefonosub);
        EditText pass = (EditText) findViewById(R.id.passwordSub);
        if (user.getText().length() >= 5)
        {
            if (pass.getText().length() >= 5)
            {
                dsf(user.getText().toString(),pass.getText().toString());

            }
            else {
                Toast.makeText(loginSub.this,"revise su clave",Toast.LENGTH_LONG).show();
            }
        }
        else{Toast.makeText(loginSub.this,"revise su Mail",Toast.LENGTH_LONG).show();}


    }

    /**
     * traer los datos de la bd con
     * @param Telefonos
     * @param Claves
     * de la tabla subusuarios
     */

    private void dsf(final String Telefonos, final String Claves)
    {
        Log.d("TAG", "dsf: "+Telefonos+" "+Claves);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://190.110.123.228/~asvingen/asuacs.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("TAG", "onResponse: "+response);
                        if (response.length() >=10) {
                            sharedResponse(response);

                            try {
                                JSONObject person = new JSONObject(response);
                                String idsubUsuarios = person.getString("idsubUsuarios");
                                String name = person.getString("nombre");
                                String IdUsuario = person.getString("idUsuario");
                                String Clave = person.getString("Clave");
                                String telefono = person.getString("telefono");
                                String codigo = person.getJSONObject("equipo").getString("codigo");
                                String nluz = person.getJSONObject("equipo").getString("nluz");
                                String StringLuz = person.getJSONObject("equipo").getString("STRINGLinght");
                                String luzMAX = person.getJSONObject("equipo").getString("luzMAX");
                                eq = new Equipo(codigo, nluz, luzMAX, StringLuz);
                                usu = new subusuarios(idsubUsuarios, name, IdUsuario, Clave, telefono, eq);

                            } catch (JSONException e) {
                                Toast.makeText(loginSub.this, "nose pudo cargar los datos", Toast.LENGTH_LONG).show();
                            }
                            if (Telefonos.equals(usu.getTelefono()) && Claves.equals(usu.getClave())) {
                                Intent i = new Intent(loginSub.this, ControlSub.class);
                                startActivity(i);
                            } else {
                                showToas();
                            }
                        }
                        else{showToas();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(loginSub.this,"nose pudo descargar la data",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("telefono",Telefonos);
                params.put("clave",Claves);
                return params;
            }
        };
// Add the request to the RequestQueue.

        queue.add(stringRequest);
    }

    private void showToas()
    {
        Toast.makeText(loginSub.this,"error de autentificacion", Toast.LENGTH_LONG).show();

    }

    /**
     * guardar la respuesta (Json) en en las shared preferenias de android
     * @param response String
     */

    private void sharedResponse(String response)
    {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }
}

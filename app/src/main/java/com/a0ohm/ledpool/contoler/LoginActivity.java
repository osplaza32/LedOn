package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.a0ohm.ledpool.HomeActivity;
import com.a0ohm.ledpool.R;
import com.a0ohm.ledpool.entidades.Equipo;
import com.a0ohm.ledpool.entidades.Usuario;
import com.a0ohm.ledpool.libowner.EmailValidator;
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
 * Activity de login
 */


public class LoginActivity extends AppCompatActivity {
    /**
     * Delaracion de modelos
     */
    Usuario usu;
    Equipo eq;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }
    /**
     * metodos de redireccionamiento de activity
     */
    public void goHome(View v)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    /**
     * metodos de boton de para la verificacion de los datos entregados
     */
    public void auth(View v)
    {
        EditText user = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        EmailValidator emailValidator = new EmailValidator();
        if (user.getText().length() >= 5 && emailValidator.validateEmail(user.getText().toString()))
        {
            if (pass.getText().length() >= 5)
            {
                /**
                 * metodos para escribir en la base de datos
                 * @params usuario(mail),password(clave)
                 */
                dsf(user.getText().toString(),pass.getText().toString());

            }
            else {
                Toast.makeText(LoginActivity.this,"revise su clave",Toast.LENGTH_LONG).show();
                }
        }
        else{Toast.makeText(LoginActivity.this,"revise su mail",Toast.LENGTH_LONG).show();}

    }
    /**
     * metodos para escribir en la base de datos
     * @params usuario(mail),password(clave)
     * method post
     * url
     */
    private void dsf(final String User, final String Pass)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://190.110.123.228/~asvingen/authusuario.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.length() >=10){
                        sharedResponse(response);
                        try {
                            JSONObject person = new JSONObject(response);
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
                             eq = new Equipo(codigo,nluz,luzMAX,StringLuz);
                             usu = new Usuario(id,name,mail,rut,Clave,rol,telefono,eq);

                        } catch (JSONException e)
                        {
                            Toast.makeText(LoginActivity.this,"nose pudo cargar los datos", Toast.LENGTH_LONG).show();

                        }

                        if (User.equals(usu.getMail()) && Pass.equals(usu.getClave()))
                        {
                        Intent i = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(i);
                        }
                        else
                        {
                            showToas();
                        }}
                        else{showToas();}



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(LoginActivity.this,"nose pudo descargar la data",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",User);
                params.put("password",Pass);
                return params;
            }
        };
// Add the request to the RequestQueue.

        queue.add(stringRequest);
    }

    private void showToas() {
        Toast.makeText(LoginActivity.this,"error de autentificacion", Toast.LENGTH_LONG).show();

    }
    /**
     * guardar json de la respuesta en las  shared preferencias
     */

    private void sharedResponse(String response) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }

    /**
     * ir a activity de recuperar passsword
     */
    public void goRescuePsw(View v)
    {
        Intent i = new Intent(this,rescue.class);
        startActivity(i);
    }




}







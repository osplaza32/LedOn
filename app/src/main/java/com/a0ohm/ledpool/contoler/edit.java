package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.a0ohm.ledpool.R;
import com.a0ohm.ledpool.entidades.Usuario;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.a0ohm.ledpool.contoler.Dashboard.conver;

public class edit extends AppCompatActivity {
    /**
     * Activity que genera la  ediccion de los usurios
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String mResponse = m.getString("Response", "");
        final Usuario us = conver(mResponse);
        ((EditText)findViewById(R.id.RutEdit)).setText(us.getRut());
        ((EditText)findViewById(R.id.TelefonoEdit)).setText(us.getTelefono());
        ((EditText)findViewById(R.id.emailreEdit)).setText(us.getMail());
        ((EditText)findViewById(R.id.nfocosEdit)).setText(us.getEquipo().getNluz());
        ((EditText)findViewById(R.id.passEdit)).setText(us.getClave());
        ((EditText)findViewById(R.id.nombreusuarioEdit)).setText(us.getNombre());
        ((Button)findViewById(R.id.editbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                RequestQueue queue = Volley.newRequestQueue(edit.this);
                final String url ="http://190.110.123.228/~asvingen/editar.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                sharedResponse(response);
                                Intent i = new Intent(edit.this,Dashboard.class);
                                startActivity(i);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id",us.getIdUsuarios());
                        params.put("idEquipo",us.getEquipo().getCodigo());
                        params.put("rut",((EditText)findViewById(R.id.RutEdit)).getText().toString());
                        params.put("telefono",((EditText)findViewById(R.id.TelefonoEdit)).getText().toString());
                        params.put("email",((EditText)findViewById(R.id.emailreEdit)).getText().toString());
                        params.put("nfocos",((EditText)findViewById(R.id.nfocosEdit)).getText().toString());
                        params.put("pass",((EditText)findViewById(R.id.passEdit)).getText().toString());
                        params.put("nombre",((EditText)findViewById(R.id.nombreusuarioEdit)).getText().toString());
                        return params;

                    }
                };
                queue.add(stringRequest);




            }
        });







    }

    private void sharedResponse(String response)
    {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }
}

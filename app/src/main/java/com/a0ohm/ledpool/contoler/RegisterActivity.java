package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.a0ohm.ledpool.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * ativity de registro
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * String de registro de error
     */
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    /**
     * botton de registro que toma las variables de los EditText
     */
    public void Register(View v)
    {
        final String email = ((EditText)findViewById(R.id.emailre)).getText().toString();
        final String rut = ((EditText)findViewById(R.id.Rut)).getText().toString();
        final String nombre = ((EditText)findViewById(R.id.nombreusuario)).getText().toString();
        final String telefono = ((EditText)findViewById(R.id.Telefono)).getText().toString();
        final String clave = ((EditText)findViewById(R.id.pass)).getText().toString();
        final String nfocos = ((EditText)findViewById(R.id.nfocos)).getText().toString();
        final String serial = ((EditText)findViewById(R.id.serial)).getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://190.110.123.228/~asvingen/register.php";
        /**
         * Escribir en la DB por su REST
         */

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    /**
                     *
                     * @param response ES LA ID DEL NUEVO CLIENTE
                     */
                    @Override
                    public void onResponse(String response)
                    {
                        /**
                         * ENVIO el id al ACTIVITY DE pregunta secreta
                         */
                        Intent intent = new Intent(RegisterActivity.this, Question.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("CLIENT_ID", response);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                test = "usuario no creado";
                showToas();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("RUT",rut);
                params.put("email",email);
                params.put("nombre",nombre);
                params.put("telefono",telefono);
                params.put("clave",clave);
                params.put("codigo",serial);
                params.put("Cantidad",nfocos);

                return params;


            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);

}

    private void showToas()
    {
        Toast.makeText(RegisterActivity.this,test,Toast.LENGTH_SHORT).show();

    }
}

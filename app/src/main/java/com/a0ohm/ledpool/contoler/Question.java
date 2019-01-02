package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.a0ohm.ledpool.HomeActivity;
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

public class Question extends AppCompatActivity {
    private String array_spinner[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String CLIENT_ID = "";
        Bundle b = getIntent().getExtras();
        if (b != null) {
            CLIENT_ID = b.getString("CLIENT_ID");
        }
        /**
         * generar las preguntas TODO:esto se puede cargar de una BD
         */
        final Spinner sp = (Spinner)findViewById(R.id.spinner);
        array_spinner = new String[3];
        array_spinner[0]="Lugar de nacimiento";
        array_spinner[1]="Nombre de tu mascota";
        array_spinner[2]="Ano de nacimiento";
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        sp.setAdapter(adapter);
        Button btn = (Button)findViewById(R.id.aceptbuttonpsw);
        final String finalCLIENT_ID = CLIENT_ID;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String elecction  = sp.getSelectedItem().toString();
                final String Respuesta = ((EditText)findViewById(R.id.respuesta)).getText().toString();
                if (Respuesta.length() >=3)
                {
                    RequestQueue queue = Volley.newRequestQueue(Question.this);
                    String url ="http://190.110.123.228/~asvingen/pregunta.php";
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "onResponse: "+response);
                            if (response.length() >3)
                            {
                            Intent a = new Intent(Question.this, HomeActivity.class);
                            startActivity(a);}
                            else
                            {Toast.makeText(Question.this,"sdf",Toast.LENGTH_SHORT).show();}

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Usuario",finalCLIENT_ID);
                            params.put("pregunta",elecction);
                            params.put("respuesta",Respuesta);
                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
                else
                    {
                        Toast.makeText(Question.this,"debe poner alguna respuesta",Toast.LENGTH_LONG).show();
                    }
            }
        });

    }
}

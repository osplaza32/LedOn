package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class rescue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue);

        ((Button)findViewById(R.id.Rescues)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperar();
            }
        });

    }

    public  void recuperar()
    {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://190.110.123.228/~asvingen/request.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent a = new Intent(rescue.this, LoginActivity.class);
                startActivity(a);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mail", ((EditText)findViewById(R.id.emailRescue)).getText().toString());
                params.put("rut", ((EditText)findViewById(R.id.rutRescue)).getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);

    }
}

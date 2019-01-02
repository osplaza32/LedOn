package com.a0ohm.ledpool.contoler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a0ohm.ledpool.R;
import com.a0ohm.ledpool.entidades.SubUsuario;
import com.a0ohm.ledpool.entidades.SubUsuariosComponentesUi;
import com.a0ohm.ledpool.entidades.Usuario;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.a0ohm.ledpool.contoler.Dashboard.conver;

public class subUsuarioGestor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_usuario_gestor);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String mResponse = m.getString("Response", "");
        Usuario us = conver(mResponse);

        final String id = us.getIdUsuarios();
        Log.d("TAG", "GetDB: ");
        Button bt1eliminar =(Button) findViewById(R.id.delete1);
        Button bt1add = (Button) findViewById(R.id.add1);
        EditText et1Tel = (EditText) findViewById(R.id.Phone1);
        EditText et1nomb = (EditText) findViewById(R.id.Nombre1);
        EditText et1pass = (EditText) findViewById(R.id.password1);
        final SubUsuariosComponentesUi row1  = new SubUsuariosComponentesUi(bt1add,bt1eliminar,et1Tel,et1nomb,et1pass);
        row1.getAgregar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDBelemnt(row1,id);
            }
        });

        Button bt2eliminar =(Button) findViewById(R.id.delete2);
        Button bt2add = (Button) findViewById(R.id.add2);
        EditText et2Tel = (EditText) findViewById(R.id.Phone2);
        EditText et2nomb = (EditText) findViewById(R.id.Nombre2);
        EditText et2pass = (EditText) findViewById(R.id.password2);
        final SubUsuariosComponentesUi row2 =new SubUsuariosComponentesUi(bt2add,bt2eliminar,et2Tel,et2nomb,et2pass);
        row2.getAgregar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDBelemnt(row2, id);
            }
        });



        Button bt3eliminar =(Button) findViewById(R.id.delete3);
        Button bt3add = (Button) findViewById(R.id.add3);
        EditText et3Tel = (EditText) findViewById(R.id.Phone3);
        EditText et3nomb = (EditText) findViewById(R.id.Nombre3);
        EditText et3pass = (EditText) findViewById(R.id.password3);
        final  SubUsuariosComponentesUi row3 = new SubUsuariosComponentesUi(bt3add,bt3eliminar,et3Tel,et3nomb,et3pass);
        row3.getAgregar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDBelemnt(row3, id);
            }
        });



        Button bt4eliminar =(Button) findViewById(R.id.delete4);
        Button bt4add = (Button) findViewById(R.id.add4);
        EditText et4Tel = (EditText) findViewById(R.id.Phone4);
        EditText et4nomb = (EditText) findViewById(R.id.Nombre4);
        EditText et4pass = (EditText) findViewById(R.id.password4);
        final SubUsuariosComponentesUi row4 = new SubUsuariosComponentesUi(bt4add,bt4eliminar,et4Tel,et4nomb,et4pass);
        row4.getAgregar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDBelemnt(row4, id);
            }
        });





        GetDB(id,row1,row2,row3,row4);
    }

    private void addDBelemnt(final SubUsuariosComponentesUi row, final String id)
    {
        if (row.getNombre().length() > 2 && row.getTelefono().length() >2)
        {
            generarPSW(row);
            final String tel = String.valueOf(row.getTelefono().getText());
            final String nombe = String.valueOf(row.getNombre().getText());
            final String clave = String.valueOf(row.getClave().getText());


            RequestQueue queue = Volley.newRequestQueue(subUsuarioGestor.this);
            String url ="http://190.110.123.228/~asvingen/registersub.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    Intent i = new Intent(subUsuarioGestor.this,Dashboard.class);
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
                    params.put("telefono",tel );
                    params.put("Nombre",nombe);
                    params.put("clave",clave);
                    params.put("id",id);
                    return params;

                }
            };
            queue.add(stringRequest);
        }


    }

    private void generarPSW(SubUsuariosComponentesUi row)
    {
        row.getClave().setText(generar());
    }

    private String generar()
    {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public void  GetDB(final String id, final SubUsuariosComponentesUi row1, final SubUsuariosComponentesUi row2, final SubUsuariosComponentesUi row3, final SubUsuariosComponentesUi row4)
    {
        Log.d("TAG", "GetDB: ");
        RequestQueue queue = Volley.newRequestQueue(subUsuarioGestor.this);
        String url ="http://190.110.123.228/~asvingen/getsub.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Log.d("TAG", "onResponse: "+response);
                if(!response.contains("Sin datos") )
                {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<SubUsuario>>(){}.getType();
                    List<SubUsuario> contactList = gson.fromJson(response, type);
                    show(contactList,row1,row2,row3,row4);
                }
                else
                {
                    Toast.makeText(subUsuarioGestor.this,"Sin Sub Usuario",Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id);
                return params;


            }
        };
        queue.add(stringRequest);

    }

    private void show(List<SubUsuario> contactList, SubUsuariosComponentesUi row1, SubUsuariosComponentesUi row2, SubUsuariosComponentesUi row3, SubUsuariosComponentesUi row4)
    {
        for (int i=0;i < contactList.size();i++)
        {
            String nombre = contactList.get(i).getNombre();
            String telefono = contactList.get(i).gettelefono();
            String clave = contactList.get(i).getClave();
            final String id = contactList.get(i).getIdsubUsuarios();
            switch (i)
            {
                case 0:
                    row1.getClave().setText(clave);
                    row1.getTelefono().setText(telefono);
                    row1.getNombre().setText(nombre);
                    row1.getAgregar().setBackground(ContextCompat.getDrawable(subUsuarioGestor.this,R.drawable.right));
                    row1.getAgregar().setEnabled(false);
                    row1.getEliminar().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletedb(id);
                        }
                    });
                    break;
                case 1:
                    row2.getClave().setText(clave);
                    row2.getTelefono().setText(telefono);
                    row2.getNombre().setText(nombre);
                    row2.getAgregar().setBackground(ContextCompat.getDrawable(subUsuarioGestor.this,R.drawable.right));
                    row2.getAgregar().setEnabled(false);
                    row2.getEliminar().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletedb(id);
                        }
                    });
                    break;
                case 2:
                    row3.getClave().setText(clave);
                    row3.getTelefono().setText(telefono);
                    row3.getNombre().setText(nombre);
                    row3.getAgregar().setBackground(ContextCompat.getDrawable(subUsuarioGestor.this,R.drawable.right));
                    row3.getAgregar().setEnabled(false);
                    row3.getEliminar().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletedb(id);
                        }
                    });
                    break;
                case 3:
                    row4.getClave().setText(clave);
                    row4.getTelefono().setText(telefono);
                    row4.getNombre().setText(nombre);
                    row4.getAgregar().setBackground(ContextCompat.getDrawable(subUsuarioGestor.this,R.drawable.right));
                    row4.getAgregar().setEnabled(false);
                    row4.getEliminar().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletedb(id);
                        }
                    });
                    break;}




        }

    }

    private void deletedb(final String id)
    {
        RequestQueue queue = Volley.newRequestQueue(subUsuarioGestor.this);
        String url ="http://190.110.123.228/~asvingen/deletesub.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Intent i = new Intent(subUsuarioGestor.this,Dashboard.class);
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

                params.put("id",id);
                return params;

            }
        };
        queue.add(stringRequest);


    }
}

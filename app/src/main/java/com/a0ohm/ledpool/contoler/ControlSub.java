package com.a0ohm.ledpool.contoler;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.a0ohm.ledpool.R;
import com.a0ohm.ledpool.entidades.Equipo;
import com.a0ohm.ledpool.entidades.Luces;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * clase de   control  de los led de los sub usuarios
 */

public class ControlSub extends AppCompatActivity {

    public  static ArrayList<Luces> obj = new ArrayList<Luces>();
    public  int getCounta() {
        return counta;
    }
    public void setCounta(int counta) {
        counta = counta;
    }
    private  int counta;

    public boolean isOpcion() {
        return Opcion;
    }

    public void setOpcion(boolean opcion) {
        Opcion = opcion;
    }

    private boolean Opcion = false;
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    private final String deafult = "000000000000000000000000000";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_control_sub2);
        rootLayout = (ViewGroup) findViewById(R.id.content);
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String mResponse = m.getString("Response", "");
        final subusuarios sub = convert(mResponse);
        ((TextView)findViewById(R.id.textViewName)).setText("Hola "+sub.getNombre());
        setId(sub.getEquipo().getCodigo());
        ToggleButton toggleAlarm = (ToggleButton) findViewById(R.id.interuptor);
        toggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    if (!isOpcion())
                    {
                        setOpcion(true);
                        String as = build(deafult,obj);
                        db(as,getId());
                    }
                    else
                    {
                        setOpcion(false);
                    }

                }
                else
                {
                    if (isOpcion())
                    {
                        setOpcion(false);
                        String as = build(deafult,obj);
                        db(as,getId());


                    }
                    else
                    {
                        setOpcion(false);}
                }

            }
        });



        String e = sub.getEquipo().getNluz();
        Integer ss = Integer.parseInt(e);
        createlamp(ss,((RelativeLayout)findViewById(R.id.content)));


    }
    private void createlamp(Integer nluz, RelativeLayout content)
    {
        obj.removeAll(obj);
        for (int i=1;i <= nluz;i++)
        {
            //setCounta(i);
            obj.add(generarLuz(i));
        }
        for (Luces l :obj)
        {
            content.removeView(l.getButton());
        }
        for (final Luces luces:obj)
        {
            content.addView(luces.getButton());
            luces.getButton().setOnTouchListener(new ControlSub.ChoiseTouchListner());
            luces.getButton().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (isOpcion())
                    {
                        if (luces.getEstado() == 2)
                        {
                            luces.setEstado(1);
                            String as = build(deafult,obj);
                            db(as,getId());
                            luces.setEstado(2);
                            Toast.makeText(ControlSub.this,"Cambia el color",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else{return false;}

                    }
                    else{return false;}
                }
            });
            luces.getButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOpcion())
                    {
                        if(luces.getEstado() == 0 || luces.getEstado() == 3)
                        {
                            luces.setEstado(2);
                            luces.getButton().setBackground(ContextCompat.getDrawable(ControlSub.this,R.drawable.ampolletaonsincapaluz));
                            String as = build(deafult,obj);
                            db(as,getId());
                            Toast.makeText(ControlSub.this,"Encendida",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            luces.setEstado(3);
                            luces.getButton().setBackground(ContextCompat.getDrawable(ControlSub.this,R.drawable.aponlleta_sin_capa));
                            String as = build(deafult,obj);
                            db(as,getId());
                            Toast.makeText(ControlSub.this,"Apagado",Toast.LENGTH_SHORT).show();


                        }

                    }

                }
            });

        }
    }

    private void db(final String stringLuz, final String Equipo)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://190.110.123.228/~asvingen/modificar.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("maquina",Equipo);
                params.put("luz",stringLuz);
                return params;

            }
        };
        queue.add(stringRequest);
    }

    private String build(String deafult, ArrayList<Luces> obj)
    {
        final char arr[]=deafult.toCharArray();
        for(Luces o : obj)
        {
            Integer posicion = o.getOrden();
            char ch = Character.forDigit(o.getEstado(), 10);
            arr[posicion-1] = ch;
        }
        if (isOpcion())
        {
            arr[arr.length-3] ='1';
        }
        else
        {
            arr[arr.length-3] ='0';
        }

        String str = String.valueOf(arr);
        Log.d("TAG", "build: "+str);
        return str;


    }

    private Luces generarLuz(int i)
    {
        Button b = new Button(ControlSub.this);
        b.setBackground(ContextCompat.getDrawable(ControlSub.this,R.drawable.aponlleta_sin_capa));
        b.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
        setCounta(i);
        b.setId(i);
        b.setText(Integer.toString(i));
        b.setY((float) doubleRandomInclusive(1050.0,0.0));
        b.setX((float) doubleRandomInclusive(0.0,0.0));
        Luces l = new Luces(b,i,0);
        return l;

    }
    public double doubleRandomInclusive(double max, double min)
    {
        double r = Math.random();
        if (r < 0.5) {
            return ((1 - Math.random()) * (max - min) + min);
        }
        return (Math.random() * (max - min) + min);
    }

    private class ChoiseTouchListner implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (!isOpcion()){
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;}
                    else
                    {
                        return false;

                    }
                case MotionEvent.ACTION_UP:
                    return false;

                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(!isOpcion()){
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        view.setLayoutParams(layoutParams);
                        break;}else{break;}
            }
            rootLayout.invalidate();
            return true;

        }
    }


    private subusuarios convert(String mResponse) {
        try {
            JSONObject person = new JSONObject(mResponse);
            String idsubUsuarios = person.getString("idsubUsuarios");
            String name = person.getString("nombre");
            String IdUsuario = person.getString("idUsuario");
            String Clave = person.getString("Clave");
            String telefono = person.getString("telefono");
            String codigo = person.getJSONObject("equipo").getString("codigo");
            String nluz = person.getJSONObject("equipo").getString("nluz");
            String StringLuz = person.getJSONObject("equipo").getString("STRINGLinght");
            String luzMAX = person.getJSONObject("equipo").getString("luzMAX");
            Equipo eq = new Equipo(codigo, nluz, luzMAX, StringLuz);
            subusuarios usu = new subusuarios(idsubUsuarios, name, IdUsuario, Clave, telefono, eq);
            return usu;
        } catch (JSONException e) {e.printStackTrace();}
        return null;
    }
}

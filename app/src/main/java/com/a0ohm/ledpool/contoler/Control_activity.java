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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.a0ohm.ledpool.R;
import com.a0ohm.ledpool.entidades.Luces;
import com.a0ohm.ledpool.entidades.Usuario;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Control_activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_control_user);
        rootLayout = (ViewGroup) findViewById(R.id.content);
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String mResponse = m.getString("Response", "");
        final Usuario us = Dashboard.conver(mResponse);
        setId(us.getEquipo().getCodigo());
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



        String e = us.getEquipo().getNluz();
        Integer ss = Integer.parseInt(e);
        createlamp(ss,((RelativeLayout)findViewById(R.id.content)),toggleAlarm);
    }
    private void createlamp(Integer nluz, RelativeLayout content, ToggleButton toggleAlarm)
    {
        Toast.makeText(Control_activity.this,obj.size()+" "+nluz,Toast.LENGTH_LONG).show();
        Integer a = obj.size();
        if(!a.equals(nluz))
        {

            obj.removeAll(obj);

        }
        if(!obj.isEmpty())
        {
            toggleAlarm.setChecked(true);
            DrawPreExistLamp(content);

        }
        else{

            DrawLamp(nluz,content);}
    }

    private void DrawPreExistLamp(RelativeLayout content)
    {
        ArrayList<Luces> Aux = new ArrayList<Luces>();
        for (Luces l :obj)
        {
            content.removeView(l.getButton());
        }
        for (Luces l :obj)
        {
            Aux.add(l);
        }
        obj.removeAll(obj);
        for (int i=1;i <= Aux.size();i++)
        {
            //setCounta(i);
            obj.add(generarLuzPre(i,Aux.get(i-1)));
        }
        for (Luces l :obj)
        {
            content.removeView(l.getButton());
        }
        for (final Luces luces:obj) {
            content.addView(luces.getButton());
            luces.getButton().setOnTouchListener(new ChoiseTouchListner());
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
                            Toast.makeText(Control_activity.this,"Cambia el color",Toast.LENGTH_SHORT).show();
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
                            luces.getButton().setBackground(ContextCompat.getDrawable(Control_activity.this,R.drawable.ampolletaonsincapaluz));
                            String as = build(deafult,obj);
                            db(as,getId());
                            Toast.makeText(Control_activity.this,"Encendida",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            luces.setEstado(3);
                            luces.getButton().setBackground(ContextCompat.getDrawable(Control_activity.this,R.drawable.aponlleta_sin_capa));
                            String as = build(deafult,obj);
                            db(as,getId());
                            Toast.makeText(Control_activity.this,"Apagado",Toast.LENGTH_SHORT).show();


                        }

                    }

                }
            });
        }


    }

    private Luces generarLuzPre(int i, Luces luces)
    {
        Button b = new Button(Control_activity.this);
        b.setBackground(luces.getButton().getBackground());
        b.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
        setCounta(i);
        b.setId(luces.getButton().getId());
        b.setText(luces.getButton().getText());
        b.setY(luces.getButton().getY());
        b.setX(luces.getButton().getX());
        Luces l = new Luces(b,i,luces.getEstado());
        return l;
    }

    private void DrawLamp(Integer nluz, RelativeLayout content)
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
            luces.getButton().setOnTouchListener(new ChoiseTouchListner());
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
                            Toast.makeText(Control_activity.this,"Cambia el color",Toast.LENGTH_SHORT).show();
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
                            luces.getButton().setBackground(ContextCompat.getDrawable(Control_activity.this,R.drawable.ampolletaonsincapaluz));
                            String as = build(deafult,obj);
                            db(as,getId());
                            Toast.makeText(Control_activity.this,"Encendida",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            luces.setEstado(3);
                            luces.getButton().setBackground(ContextCompat.getDrawable(Control_activity.this,R.drawable.aponlleta_sin_capa));
                            String as = build(deafult,obj);
                            db(as,getId());
                            Toast.makeText(Control_activity.this,"Apagado",Toast.LENGTH_SHORT).show();


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
        Button b = new Button(Control_activity.this);
        b.setBackground(ContextCompat.getDrawable(Control_activity.this,R.drawable.aponlleta_sin_capa));
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
}

package com.a0ohm.ledpool;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.a0ohm.ledpool.contoler.LoginActivity;
import com.a0ohm.ledpool.contoler.RegisterActivity;
import com.a0ohm.ledpool.contoler.loginSub;


/**

 * Esta clase define el punto de partida de la app la componen de 3 metodos de transacion a otros activity

 */

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);



    }
/**
 * metodos de redireccionamiento de activity
 */
    /**
     * metodos de redireccionamiento de activity login
     */

    public void goLogin(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    /**
     * metodos de redireccionamiento de activity registrar
     */
    public void goCreateAccount(View v)
    {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
    /**
     * metodos de redireccionamiento de activity login de los sub usuarios
     */

    public void goGuest(View view)
    {
        Intent i = new Intent(this,loginSub.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);

    }
}

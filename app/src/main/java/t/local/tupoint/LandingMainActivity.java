package t.local.tupoint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.crypto.NullCipher;

import t.local.tupoint.ui.login.LoginActivity;

public class LandingMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /*
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        */

        //Google Sign In

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        String serverClientId = getString(R.string.server_client_id); ;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();



        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //mGoogleSignInClient.signOut();



        try {
            if(isSign())
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                if(account.getPhotoUrl() == null)
                {

                }
                else
                {
                    URL myFileUrl = new URL(account.getPhotoUrl().toString());
                    HttpURLConnection conn =
                            (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    ImageView userPhoto = (ImageView) findViewById(R.id.userPhoto);
                    userPhoto.setImageBitmap(BitmapFactory.decodeStream(is));
                }

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }






        findViewById(R.id.sign_in_button).setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Anokis

    @Override
    public void onClick(View v) {
        Log.d("=TuPoint=>","Google Sign In");

        if(!isSign())
        {
            signIn();

        }
        else
        {
            try
            {
                Log.d("=TuPoint=>","Ya inicio Sesion");
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                Log.d("=TuPoint=>",account.getDisplayName() + account.getEmail() +account.getPhotoUrl());

                //Save in SharedPreferences
                SharedPreferences credentialsSP =
                        getSharedPreferences("credentials",
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor credentialsEditor = credentialsSP.edit();
                credentialsEditor.putString("getDisplayName",account.getDisplayName());
                credentialsEditor.putString("getEmail",account.getEmail());
                if(account.getPhotoUrl() == null)
                {

                }
                else
                {
                    credentialsEditor.putString("getPhotoUrl",account.getPhotoUrl().toString());
                }

                credentialsEditor.commit();
                Toast.makeText(getApplicationContext(),
                        "Login Sucefull" ,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LandingUserActivity.class);
                startActivity(intent);
            }
            catch (Exception e)
            {
                Log.d("=tuPoint=>",e.getMessage());
            }



        }

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 0) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Log.d("=TuPoint=>",account.getDisplayName() + account.getEmail() +account.getPhotoUrl());


            // Signed in successfully, show authenticated UI.
            //Save in SharedPreferences
            SharedPreferences credentialsSP =
                    getSharedPreferences("credentials",
                            Context.MODE_PRIVATE);
            SharedPreferences.Editor credentialsEditor = credentialsSP.edit();

            credentialsEditor.putString("getDisplayName",account.getDisplayName());
            credentialsEditor.putString("getEmail",account.getEmail());
            credentialsEditor.putString("getPhotoUrl",account.getPhotoUrl().toString());
            credentialsEditor.commit();
            Toast.makeText(getApplicationContext(),
                    "Login Sucefull" ,
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LandingUserActivity.class);
            startActivity(intent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("=TuPoint=>","signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(),
                    "Error en la sesion" ,
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);

        }
    }

    private boolean isSign()
    {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null)
        {
            return  false;
        }
        else
        {
            return true;
        }

    }

    public void SignOut(View view) {

        if(isSign())
        {
            mGoogleSignInClient.signOut();
            finish();
            startActivity(getIntent());
            Toast.makeText(getApplicationContext(),
                    "Sesion Cerrada" ,
                    Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "No hay sesion Activa" ,
                    Toast.LENGTH_SHORT).show();
        }
    }
}

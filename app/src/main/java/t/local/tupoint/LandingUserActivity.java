package t.local.tupoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LandingUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_user);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.landing_user, menu);

        Log.d("==TuPoint==>",LoadPreferences("getDisplayName"));
        Log.d("==TuPoint==>",LoadPreferences("getEmail"));
        Log.d("==TuPoint==>",LoadPreferences("getPhotoUrl"));

        //
        TextView userLongName = (TextView) findViewById(R.id.userName);
        userLongName.setText(LoadPreferences("getDisplayName"));

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(LoadPreferences("getEmail"));



        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL myFileUrl = new URL(LoadPreferences("getPhotoUrl"));
            HttpURLConnection conn =
                    (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            ImageView userPhoto = (ImageView) findViewById(R.id.imageView);
            userPhoto.setImageBitmap(BitmapFactory.decodeStream(is));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reservas) {
            Toast.makeText(getApplicationContext(),
                    "Reservas" ,
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ReservesUsersActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_cerca) {
            Toast.makeText(getApplicationContext(),
                    "Cerca De Mi" ,
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), NeighborhoodActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Methods
    public String LoadPreferences(String key)
    {
        SharedPreferences preferences =
                getSharedPreferences("credentials",
                        this.MODE_PRIVATE);
        return preferences.getString(key,"No Data");
    }


    public void LogOut(MenuItem item) {
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(getApplicationContext(), LandingMainActivity.class);
        startActivity(intent);

    }
}

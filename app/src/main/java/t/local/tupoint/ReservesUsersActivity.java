package t.local.tupoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import t.local.tupoint.adapters.RecyclerAdapterReservesUser;
import t.local.tupoint.adapters.RecyclerAdapterRestaurants;
import t.local.tupoint.config.WebServices;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Reserve;
import t.local.tupoint.models.Restaurant;

public class ReservesUsersActivity extends AppCompatActivity {

    List<Reserve> reserves;
    List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves_users);
        WebServices webServices = new WebServices();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServices.SpringBoot)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceRetrofit services = retrofit.create(InterfaceRetrofit.class);

        try {

             restaurants = services.ListRestaurants().execute().body();
             Log.d("=TuPoint=>","Rest Size: " + restaurants.size());
             reserves = services.FindByReserveByEmail(LoadPreferences("getEmail")).execute().body();
            Log.d("=TuPoint=>","Reserves Size: " + reserves.size());

            RecyclerView recycler = (RecyclerView) findViewById(R.id.rReservesUsers);
            recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            RecyclerAdapterReservesUser adapter = new RecyclerAdapterReservesUser((ArrayList)reserves,(ArrayList) restaurants);
            recycler.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void Reservar(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterReserveActivity.class);
        startActivity(intent);
    }

    public String LoadPreferences(String key)
    {
        SharedPreferences preferences =
                getSharedPreferences("credentials",
                        this.MODE_PRIVATE);
        return preferences.getString(key,"No Data");
    }
}

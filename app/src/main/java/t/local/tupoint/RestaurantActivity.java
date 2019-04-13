package t.local.tupoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import t.local.tupoint.adapters.RecyclerAdapterAdmins;
import t.local.tupoint.adapters.RecyclerAdapterRestaurants;
import t.local.tupoint.config.WebServices;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Admin;
import t.local.tupoint.models.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        WebServices webServices = new WebServices();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServices.SpringBoot)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceRetrofit services = retrofit.create(InterfaceRetrofit.class);

        services.ListRestaurants().enqueue(new retrofit2.Callback<List<Restaurant>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Restaurant>> call, retrofit2.Response<List<Restaurant>> response) {



                if(response.body() == null)
                {
                    Log.d("=TuPoint=>", "getUser - No data");
                }
                else
                {
                    List<Restaurant> objects = response.body();
                    Log.d("=TuPoint=>", "Admins: "+ objects.size());

                    RecyclerView recycler = (RecyclerView) findViewById(R.id.rAdmins);
                    recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    RecyclerAdapterRestaurants adapter = new RecyclerAdapterRestaurants((ArrayList)objects);
                    recycler.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<Restaurant>> call, Throwable t) {

                Log.d("=iTracking=>", t.toString());


            }
        });






    }
}

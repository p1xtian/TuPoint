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
import t.local.tupoint.config.WebServices;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Admin;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ArrayList<Admin> admins = new ArrayList<>();
        WebServices webServices = new WebServices();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServices.SpringBoot)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceRetrofit services = retrofit.create(InterfaceRetrofit.class);

        services.ListAdmins().enqueue(new retrofit2.Callback<List<Admin>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Admin>> call, retrofit2.Response<List<Admin>> response) {



                if(response.body() == null)
                {
                    Log.d("=TuPoint=>", "getUser - No data");
                }
                else
                {
                    List<Admin> objects = response.body();
                    Log.d("=TuPoint=>", "Admins: "+ objects.size());

                    RecyclerView recycler = (RecyclerView) findViewById(R.id.rAdmins);
                    recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    RecyclerAdapterAdmins adapter = new RecyclerAdapterAdmins((ArrayList)objects);
                    recycler.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<Admin>> call, Throwable t) {

                Log.d("=iTracking=>", t.toString());


            }
        });






    }
}

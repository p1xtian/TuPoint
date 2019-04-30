package t.local.tupoint;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import t.local.tupoint.config.WebServices;
import t.local.tupoint.helpers.HelperBase64;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Reserve;
import t.local.tupoint.models.Restaurant;

public class RegisterReserveActivity extends AppCompatActivity {

    Spinner sItems;
    EditText eT_date;
    EditText eT_time;
    EditText eT_comensales;
    List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reserve);

        String local = getIntent().getStringExtra("Local");

        Toast.makeText(getApplicationContext(),
                local,
                Toast.LENGTH_SHORT).show();

// Restaurants

        WebServices webServices = new WebServices();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(webServices.SpringBoot)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceRetrofit services = retrofit.create(InterfaceRetrofit.class);

        services.ListRestaurants().enqueue(new retrofit2.Callback<List<Restaurant>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Restaurant>> call, final retrofit2.Response<List<Restaurant>> response) {


                if (response.body() == null) {
                    Log.d("=TuPoint=>", "getUser - No data");
                } else {
                    List<Restaurant> objects = response.body();
                    List<String> spinnerArray = new ArrayList<String>();
                    restaurants = objects;

                    for (Restaurant rest : restaurants) {

                        if (!rest.getLatitud().equals("0")) {
                            spinnerArray.add(rest.getRazonsocial());
                        }

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            RegisterReserveActivity.this, android.R.layout.simple_spinner_item, spinnerArray);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sItems = (Spinner) findViewById(R.id.spi_razon);
                    sItems.setAdapter(adapter);

                    if (getIntent().getStringExtra("Local") != null) {
                        for (int i = 0; i < spinnerArray.size(); i++) {
                            if (spinnerArray.get(i).equals(getIntent().getStringExtra("Local"))) {
                                sItems.setSelection(i);
                            }
                        }
                    }


                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<Restaurant>> call, Throwable t) {

                Log.d("=TuPoint=>", t.toString());


            }
        });


        final Calendar calendarDate = Calendar.getInstance();

        eT_date = (EditText) findViewById(R.id.eT_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendarDate.set(Calendar.YEAR, year);
                calendarDate.set(Calendar.MONTH, monthOfYear);
                calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                eT_date.setText(sdf.format(calendarDate.getTime()));
            }

        };

        eT_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterReserveActivity.this, date, calendarDate
                        .get(Calendar.YEAR), calendarDate.get(Calendar.MONTH),
                        calendarDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Hora Minutos
        final Calendar calendarTime = Calendar.getInstance();

        eT_time = (EditText) findViewById(R.id.eT_time);
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eT_time.setText(hourOfDay + ":" + minute);
            }
        };

        eT_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(RegisterReserveActivity.this, time, calendarTime
                        .get(Calendar.HOUR), calendarTime.get(Calendar.MINUTE),
                        true).show();
            }
        });

        eT_comensales = (EditText) findViewById(R.id.eT_comensales);


    }

    public void Save(View view) {

        Boolean cancel = false;

        if (eT_date.getText().length() < 2) {
            eT_date.setError("Dato Requerido");
            cancel = true;
        }
        else
        {
            eT_date.setError(null);
        }

        if (eT_time.getText().length() < 2) {
            eT_time.setError("Dato Requerido");
            cancel = true;
        }
        else
        {
            eT_time.setError(null);
        }

        if (eT_comensales.getText().length() == 0) {
            eT_comensales.setError("Dato Requerido");
            cancel = true;
        }
        else
        {
            eT_comensales.setError(null);
        }

        if (cancel == false) {

            // Anokis

            WebServices webServices = new WebServices();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(webServices.SpringBoot)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            InterfaceRetrofit services = retrofit.create(InterfaceRetrofit.class);

            Reserve reserveToSave = new Reserve(
                     0,
                    LoadPreferences("getEmail"),
                    Calendar.getInstance().getTime().toString(),
            eT_date.getText() + " " + eT_time.getText(),
            FindRuc(sItems.getSelectedItem().toString()),
                    eT_comensales.getText().toString(),
                    "true"



            );



            services.SaveReserve(reserveToSave)
                    .
                            enqueue(new retrofit2.Callback<Reserve>() {


                                @Override
                                public void onResponse(Call<Reserve> call, Response<Reserve> response) {

                                    if(response.body() == null)
                                    {
                                        Log.d("=TuPoint=>", "getUser - No data");
                                        Toast.makeText(getApplicationContext(),
                                                "Server Error" ,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Reserve reserve = response.body();
                                        Log.d("=TuPoint=>", "Email Reserve: "+ reserve.getUserid());


                                        Toast.makeText(getApplicationContext(),
                                                "Grabado" ,
                                                Toast.LENGTH_SHORT).show();


                                        Intent intent = new Intent(getApplicationContext(), LandingUserActivity.class);
                                        startActivity(intent );

                                    }


                                }

                                @Override
                                public void onFailure(Call<Reserve> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),
                                            "Server Error" ,

                                            Toast.LENGTH_SHORT).show();
                                    Log.d("=TuPoint=>",t.toString());
                                }
                            });







        }
    }


    public String LoadPreferences(String key)
    {
        SharedPreferences preferences =
                getSharedPreferences("credentials",
                        this.MODE_PRIVATE);
        return preferences.getString(key,"No Data");
    }

    public void Exit(View view) {
    }


    public String FindRuc(String razonSocial)
    {
        String response = "";
        for (Restaurant rest : restaurants) {

            if (rest.razonsocial.equals(razonSocial))
            {
                response = rest.ruc;
            }

        }

        return response;
    }
}

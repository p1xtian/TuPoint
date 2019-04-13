package t.local.tupoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import t.local.tupoint.config.WebServices;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Admin;
import t.local.tupoint.models.Restaurant;

public class RegisterRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

    }

    public void Save(View view) {
        Toast.makeText(getApplicationContext(),
                "Validando" ,
                Toast.LENGTH_SHORT).show();

        //Declarando datos desde la vista
        final EditText eT_email = findViewById(R.id.eT_email);
        final EditText eT_password = findViewById(R.id.eT_password);
        final EditText eT_ruc = findViewById(R.id.eT_ruc);

        // Resetear errores.
        eT_email.setError(null);
        eT_password.setError(null);
        eT_ruc.setError(null);

        // Grabar valores ha validar
        String email = eT_email.getText().toString();
        String password = eT_password.getText().toString();
        String ruc = eT_ruc.getText().toString();

        Log.d("=TuPoint=>", email + " " + password + " " + ruc);

        boolean cancel = false;
        View focusView = null;

        // Validar Email
        if (TextUtils.isEmpty(email)) {
            eT_email.setError("Campo Necesario");
            //focusView = eT_email;
            cancel = true;
        }

        if (!isEmailValid(email)) {
            eT_email.setError("Formato De Correo Errado");
            focusView = eT_email;
            cancel = true;
        }

        // Validar Password
        if (!isPasswordValid(password)) {
            eT_password.setError("6 Digitos Minimo");
           //focusView = eT_password;
            cancel = true;
        }

        // Validar RUC
        if (TextUtils.isEmpty(ruc)) {
            eT_ruc.setError("Campo Necesario");
            //focusView = eT_email;
            cancel = true;
        }
        if (!isRucValid(ruc)) {
            eT_ruc.setError("RUC Errado");
            //focusView = eT_email;
            cancel = true;
        }

        if(cancel){
            Log.d("=TuPoint=>", "BAD:" + email + " " + password + " " + ruc);
        }
        else {
            Log.d("=TuPoint=>", "OK:" + email + " " + password + " " + ruc);

            //Uso del WEBSERVICES

            // Anokis

            WebServices webServices = new WebServices();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(webServices.SpringBoot)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            InterfaceRetrofit services = retrofit.create(InterfaceRetrofit.class);

            Restaurant restToSave =
                    new Restaurant(
                            0,
                            email,
                            password,
                            "",
                            "",
                            ruc,
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            1l );

            services.SaveRestaurant(restToSave)
                    .
                            enqueue(new retrofit2.Callback<Restaurant>() {


                                @Override
                                public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {

                                    if(response.body() == null)
                                    {
                                        Log.d("=TuPoint=>", "getUser - No data");
                                        Toast.makeText(getApplicationContext(),
                                                "Server Error" ,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Restaurant restaurant = response.body();
                                        Log.d("=TuPoint=>", "Email Attempt Login: "+ restaurant.getCorreo());


                                        Toast.makeText(getApplicationContext(),
                                                "Grabado" ,
                                                Toast.LENGTH_SHORT).show();

                                        eT_email.setText("");
                                        eT_password.setText("");
                                        eT_ruc.setText("");

                                    }


                                }

                                @Override
                                public void onFailure(Call<Restaurant> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),
                                            "Server Error" ,

                                            Toast.LENGTH_SHORT).show();
                                    Log.d("=TuPoint=>",t.toString());
                                }
                            });
        }


    }

    public void Exit(View view) {
        finish();
    }

    //Validaciones

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    private boolean isRucValid(String ruc) {
        //TODO: Replace this with your own logic

        return ruc.length() == 11;
    }

}

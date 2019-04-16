package t.local.tupoint;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import t.local.tupoint.config.WebServices;
import t.local.tupoint.helpers.HelperBase64;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Admin;
import t.local.tupoint.models.Restaurant;

public class RegisterRestaurantActivity extends AppCompatActivity {

    ImageView iV_logo;
    EditText eT_googlemaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

         iV_logo = findViewById(R.id.iVlogo);
         eT_googlemaps = findViewById(R.id.eT_googlemaps);

    }

    public void Save(View view) {
        Toast.makeText(getApplicationContext(),
                "Validando" ,
                Toast.LENGTH_SHORT).show();

        //Declarando datos desde la vista
        final EditText eT_email = findViewById(R.id.eT_email);
        final EditText eT_password = findViewById(R.id.eT_password);
        final EditText eT_ruc = findViewById(R.id.eT_ruc);
        final EditText eT_razonsocial = findViewById(R.id.eT_razon);
        final EditText eT_direccion = findViewById(R.id.eT_direccion);
        final EditText eT_telefono = findViewById(R.id.eT_telefono);
        final EditText eT_descripcion = findViewById(R.id.eT_descripcion);
        final EditText eT_mesas = findViewById(R.id.eT_mesas);
        final EditText eT_aforo = findViewById(R.id.eT_aforo);
        final Switch eT_garaje = findViewById(R.id.swGaraje);
        final Switch eT_terraza = findViewById(R.id.swTerraza);
        final Switch eT_aireacondicionado = findViewById(R.id.swAireAcondicionado);




        // Resetear errores.
        eT_email.setError(null);
        eT_password.setError(null);
        eT_ruc.setError(null);

        // Grabar valores ha validar
        String email = eT_email.getText().toString();
        String password = eT_password.getText().toString();
        String ruc = eT_ruc.getText().toString();
        String razonsocial = eT_razonsocial.getText().toString();
        String direccion = eT_direccion.getText().toString();
        String telefono = eT_telefono.getText().toString();
        String descripcion = eT_descripcion.getText().toString();
        String mesas = eT_mesas.getText().toString();
        final String aforo = eT_aforo.getText().toString();
        boolean garaje = eT_garaje.isChecked();
        boolean terraza = eT_terraza.isChecked();
        boolean aireacondicionado = eT_aireacondicionado.isChecked();
        String googlemaps = eT_googlemaps.getText().toString();
        String logo = "";


        //Logo

        //Photo
        HelperBase64 helperBase64 = new HelperBase64();


            logo = helperBase64.Encode(((BitmapDrawable)iV_logo.getDrawable()).getBitmap());

        Log.d("=TuPoint=>", email + " " + password + " " + ruc + " " + razonsocial);
        Log.d("=TuPoint=>", direccion + " " + telefono + " " + descripcion + " " + mesas);
        Log.d("=TuPoint=>", aforo + " " + garaje + " " + terraza + " " + aireacondicionado);
        Log.d("=TuPoint=>", googlemaps);
        Log.d("=TuPoint=>", logo);

        boolean cancel = false;
        View focusView = null;

        //Validaciones

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
            eT_password.setError("6 digitos minimo");
           //focusView = eT_password;
            cancel = true;
        }

        // Validar RUC
        if (TextUtils.isEmpty(ruc)) {
            eT_ruc.setError("Campo necesario");
            //focusView = eT_email;
            cancel = true;
        }

        if (!isRucValid(ruc)) {
            eT_ruc.setError("Minimo 11 digitos y debe empezar con 10 o 20 o 13");
            //focusView = eT_email;
            cancel = true;
        }

        //Valir Razon Social
        if(TextUtils.isEmpty(razonsocial))
        {
            eT_razonsocial.setError("Campo necesario");
            cancel = true;
        }

        if(razonsocial.length() >50)
        {
            eT_razonsocial.setError("Maximo 50 caracteres");
            cancel = true;
        }
//Validar Direccion
        if(TextUtils.isEmpty(direccion))
        {
            eT_direccion.setError("Campo necesario");
            cancel = true;
        }

        if(direccion.length() >150)
        {
            eT_razonsocial.setError("Maximo 150 caracteres");
            cancel = true;
        }
//Validar Telefono
        if(telefono.length() <=7)
        {
            eT_telefono.setError("Minimo 7 caracteres");
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
                            razonsocial,
                            "1",
                            direccion,
                            telefono,
                            "",
                            "",
                            logo,
                            descripcion,
                            Boolean.toString(garaje),
                            Boolean.toString(terraza),
                            mesas,
                            aforo,
                            Boolean.toString(aireacondicionado),
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

                                        //Limpiar
                                        eT_email.setText("");
                                        eT_password.setText("");
                                        eT_ruc.setText("");
                                        eT_razonsocial.setText("");
                                        eT_direccion.setText("");
                                        eT_telefono.setText("");
                                        eT_descripcion.setText("");
                                        eT_mesas.setText("");
                                        eT_aforo.setText("");
                                        eT_googlemaps.setText("");
                                        eT_garaje.setChecked(false);
                                        eT_terraza.setChecked(false);
                                        eT_aireacondicionado.setChecked(false);

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

        boolean valid = false;
        if (ruc.length() == 11) {
            if (
                    ruc.substring(0, 2).equals("10")
                            || ruc.substring(0, 2).equals("20")
                            || ruc.substring(0, 2).equals("13")) {

                valid = true;
            } else {
                valid = false;
            }
        } else {
            valid = false;
        }
        Log.d("=TuPoint=>", "Valid :" +valid);
        return valid;
    }

    public void LoadLogo(View view) {

       Intent intent = new Intent(Intent.ACTION_PICK,
               MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       intent.setType("image/");

       startActivityForResult(
               intent.createChooser(intent,
                       "Seleccione"),10);


    }


    //Retorno de Galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Logo
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                iV_logo.setImageURI(data.getData());
            }
        }

        //Mapa
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String returnedResult = data.getData().toString();

                eT_googlemaps.setText(returnedResult);
                Toast.makeText(getApplicationContext(),
                        "Retorno de Maps: " + returnedResult ,
                        Toast.LENGTH_LONG).show();
            }
        }


    }

    //Maps
    public void LoadMaps(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivityForResult(intent, 1);
    }



}

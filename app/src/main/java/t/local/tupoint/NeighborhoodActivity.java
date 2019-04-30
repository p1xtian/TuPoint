package t.local.tupoint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import t.local.tupoint.config.WebServices;
import t.local.tupoint.helpers.HelperBase64;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Restaurant;

public class NeighborhoodActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


//Anokis
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyC0hnzBdqF379K48bORs2TX701fB9HtezI");
        }

// Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                if(place.getLatLng() != null)
                {
                    double lat = place.getLatLng().latitude;
                    double lng = place.getLatLng().longitude;

                    Log.d("=TuPoint", "Place: " + place.getName() + ", " + place.getId());
                    Log.d("=TuPoint", "Lat: " + lat + ", Lng: " + lng);

                    LatLng position = new LatLng(lat, lng);
                    String title = place.getName();
                    //mMap.clear();

                    mMap.addMarker(new MarkerOptions().position(position).title(title).draggable(true));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,16));
                    Toast.makeText(getApplicationContext(),
                            title,
                            Toast.LENGTH_LONG).show();




                }
                else
                {
                    Log.d("=TuPoint", "Place: No LatLng ");

                }

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.d("=TuPoint", "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

// Setting a custom info window adapter for the google map

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

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



                if(response.body() == null)
                {
                    Log.d("=TuPoint=>", "getUser - No data");
                }
                else
                {
                    List<Restaurant> objects = response.body();
                    restaurants = objects;

                    Log.d("=TuPoint=>", "Restaurantes : " + objects.size());

                    for (int i = 0; i < objects.size() ; i++) {




                        TextView text = new TextView(getApplicationContext());
                        text.setText(objects.get(i).razonsocial);
                        IconGenerator generator = new IconGenerator(getApplicationContext());
                        //generator.setBackground(getDrawable(R.drawable.icon_rest));
                        generator.setStyle(IconGenerator.STYLE_ORANGE);
                        generator.setContentView(text);
                        Bitmap icon = generator.makeIcon();

                       try{
                           LatLng restPosition = new LatLng(
                                   Double.parseDouble(objects.get(i).getLatitud()),
                                   Double.parseDouble(objects.get(i).getLongitud()));

                           Marker marker = mMap.addMarker(new MarkerOptions()
                                   .position(restPosition)
                                   .icon(BitmapDescriptorFactory.fromBitmap(icon)));
                       }
                       catch (Exception e)
                       {

                       }



                    }

                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        // Use default InfoWindow frame
                        @Override
                        public View getInfoWindow(Marker arg0) {
                            return null;
                        }

                        // Defines the contents of the InfoWindow
                        @Override
                        public View getInfoContents(Marker arg0) {

// Getting view from the layout file info_window_layout
                            View v = getLayoutInflater().inflate(R.layout.info_windows, null);

// Getting the position from the marker
                            LatLng latLng = arg0.getPosition();

                            Restaurant restaurant = FoundRest(arg0.getPosition().latitude,arg0.getPosition().longitude);

// Getting reference to the TextView to set latitude
                            TextView tvLat = (TextView) v.findViewById(R.id.iWEmpresa);

// Getting reference to the TextView to set longitude
                            TextView tvLng = (TextView) v.findViewById(R.id.iWDireccion);

                            TextView iWDescripcion = (TextView) v.findViewById(R.id.iWDescripcion);

                            ImageView ivEmpresa = (ImageView) v.findViewById(R.id.iWLogo);

                            TextView iWGaraje = (TextView) v.findViewById(R.id.iWGaraje);
                            TextView iWTerraza = (TextView) v.findViewById(R.id.iWTerraza);
                            TextView iWAire = (TextView) v.findViewById(R.id.iWAire);



// Setting the latitude
                            tvLat.setText(restaurant.getRazonsocial());

// Setting the longitude
                            tvLng.setText(restaurant.getDireccion());


                            iWDescripcion.setText(restaurant.getDescripcion());

                            if(restaurant.getGaraje().equals("true"))
                            {
                                iWGaraje.setText("Garaje: SI");
                            }
                            else
                            {
                                iWGaraje.setText("Garaje: NO");
                            }

                            if(restaurant.getTerraza().equals("true"))
                            {
                                iWTerraza.setText("Terraza: SI");
                            }
                            else
                            {
                                iWTerraza.setText("Terraza: NO");
                            }

                            if(restaurant.getAireacondicionado().equals("true"))
                            {
                                iWAire.setText("Aire AC: SI");
                            }
                            else
                            {
                                iWAire.setText("Aire AC: NO");
                            }




                            HelperBase64 helperBase64 = new HelperBase64();
                            ivEmpresa.setImageBitmap(helperBase64.Decode(restaurant.getLogo()));

// Returning the view containing InfoWindow contents
                            return v;

                        }

                    });

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        public void onInfoWindowClick(final Marker marker) {
                            String[] items={"Reservar","Salir"};
                            AlertDialog.Builder itemDilog = new AlertDialog.Builder(NeighborhoodActivity.this);
                            itemDilog.setTitle("");
                            itemDilog.setCancelable(false);
                            itemDilog.setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch(which){
                                        case 0:{
                                            Reservar(marker);
                                        }break;
                                        case 1:{

                                        }break;
                                    }

                                }
                            });
                            itemDilog.show();

                        }
                    });



                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<Restaurant>> call, Throwable t) {

                Log.d("=TuPoint=>", t.toString());


            }
        });


        final double lat = -12.096230d;
        final double lng = -77.026294;
        LatLng position = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,16));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                /*
                String direccion = obtenerDireccion(marker.getPosition().latitude,
                        marker.getPosition().longitude);
                Toast.makeText(getApplicationContext(),
                        "Direccion:" + direccion,
                        Toast.LENGTH_SHORT).show();

                if(!direccion.contains("San Isidro"))
                {
                    Toast.makeText(getApplicationContext(),
                            "Direccion no permitida, debe pertenecer a San Isidro",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    confirmarUbicacion(marker,direccion);
                }


                */
                marker.showInfoWindow();
                return true;
            }


        });



            }





    public String obtenerDireccion(double lat, double lng)
    {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        String city = "";
        String state;
        String zip;
        String country;
        String direccion = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            zip = addresses.get(0).getPostalCode();
            country = addresses.get(0).getCountryName();
            direccion = addresses.get(0).getAddressLine(0);

            return direccion;

        } catch (IOException e) {
            Log.e("=TuPoint=>", e.getMessage());
            return "Error al obtener Direccion";
        }


    }

    private void confirmarUbicacion(final Marker marker, String direccion) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Es " + direccion + " la direccion correcta");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent data = new Intent();
                        String text =
                                marker.getPosition().latitude +":"+
                                        marker.getPosition().longitude
                                ;
//---set the data to pass back---
                        data.setData(Uri.parse(text));
                        setResult(RESULT_OK, data);
//---close the activity---
                        finish();

                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private Restaurant FoundRest(double lat, double lng) {


        for (int i = 0; i < restaurants.size(); i++) {

            if (
                    restaurants.get(i).getLatitud().equals(Double.toString(lat)) &&
                            restaurants.get(i).getLongitud().equals(Double.toString(lng))) {
                return restaurants.get(i);


            }
        }
        return null;
    }

    public void Reservar(Marker marker) {

        Restaurant restaurant = FoundRest(
                marker.getPosition().latitude,
                marker.getPosition().longitude);

        Toast.makeText(getApplicationContext(),
                "Reservar",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getBaseContext(), RegisterReserveActivity.class);
        intent.putExtra("Local", restaurant.getRazonsocial());
        startActivity(intent);


    }
}

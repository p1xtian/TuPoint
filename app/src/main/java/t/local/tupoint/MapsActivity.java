package t.local.tupoint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        Marker activeMarker = null;

        // Add a marker in Sydney and move the camera
        //-12.096230, -77.026294
        final double lat = -12.096230d;
        final double lng = -77.026294;
        LatLng position = new LatLng(lat, lng);
        String title = "Ubicar Restaurante";
        mMap.addMarker(new MarkerOptions().position(position).title("¿Tu Restaurante?").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,16));


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

                Toast.makeText(getApplicationContext(),
                        "¿Donde Esta Tu Restaurant?",
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onMarkerDrag(Marker marker) {


            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                /*
                Toast.makeText(getApplicationContext(),
                        "Geo Fijado--> Lat: " + marker.getPosition().latitude + " Lng " +
                                marker.getPosition().longitude,
                        Toast.LENGTH_SHORT).show();

*/
                String direccion = obtenerDireccion(marker.getPosition().latitude,
                        marker.getPosition().longitude);
                Toast.makeText(getApplicationContext(),
                        "Direccion:" + direccion,
                        Toast.LENGTH_SHORT).show();
                confirmarUbicacion(marker,direccion);

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
        alertDialogBuilder.setMessage("Es " + direccion + " la direccion corretcta");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent data = new Intent();
                        String text =
                                marker.getPosition().latitude +"|"+
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
}

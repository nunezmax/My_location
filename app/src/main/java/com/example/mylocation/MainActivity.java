/**************
 Aplicación MyLocation creada por nunezmax

 Consideraciones:

 El programa usa la biblioteca Osmdroid y se conecta a mapnik.openstreetmap.org para mostrar el mapa
 Tiene el MapView, el search y los marcadores de ubicación y de resultados de la busqueda funcionando
 El mapa aparece por defecto desde muy lejos y hay que acercarlo

 El search funciona con ciudades concretas, pero no autocompleta, si se pone por ejemplo
 "Madrid" o "London" debe funcionar y marcar el sitio.
 **************/


package com.example.mylocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


import java.io.IOException;
import java.util.List;





public class MainActivity extends AppCompatActivity{
    private MapView map = null;

    MyLocationNewOverlay locationOverlay;

    private EditText editText;

    private Button button;

    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_main);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
        map.getOverlays().add(locationOverlay);


        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        });


        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.search_button);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String query = editText.getText().toString();

                Log.d("Query", query);

                if(query != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            searchLocation(query);
                        }
                    });
                }

            }
        });



    }

    private void requestPermissionsIfNecessary(String[] permissions) {

        if(ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);

        } else {

            // permission already granted, can call geocoding method

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, call geocoding method
            } else {
                // permission denied
            }

        }
    }



    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        locationOverlay.enableMyLocation();


    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        locationOverlay.disableMyLocation();
    }

    private void searchLocation(String query) {

        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> addresses = geocoder.getFromLocationName(query, 1);

            if(addresses.size() > 0) {

                Address address = addresses.get(0);

                Marker marker = new Marker(map);


                // animate to location
                map.getController().animateTo(new GeoPoint(
                        address.getLatitude(), address.getLongitude()));

                GeoPoint geoPoint = new GeoPoint(address.getLatitude(), address.getLongitude());
                marker.setPosition(geoPoint);
                marker.setTitle(String.valueOf(geoPoint));

                map.getOverlays().add(marker);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


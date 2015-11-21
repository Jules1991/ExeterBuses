package website.julianrosser.exeterbuses;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        // UI settings
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Set info click listener
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("TAG", "LOGGGGGG");

                // Create new fragment and transaction
                Fragment newFragment = new StopInfoFragment();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                View v = getLayoutInflater().inflate(R.layout.demo_marker_info_layout, null);

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.map, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        // Set custom info view
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.demo_marker_info_layout, null);

                // Getting the position from the marker
                LatLng latLng = marker.getPosition();

                Button chooseThisStop = (Button) v.findViewById(R.id.info_button_open_fragment);
                chooseThisStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("TAG", "onCLickView");

                    }
                });

                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);

                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                // Setting the latitude
                tvLat.setText("Latitude:" + latLng.latitude);

                // Setting the longitude
                tvLng.setText("Longitude:" + latLng.longitude);

                // Returning the view containing InfoWindow contents
                return v;
            }
        });

        // 1 Canon Way marker
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.696893, -3.530342)).title("1 Canon Way")
                .title("1 Canon Way").snippet("Home to the famous cat, Danny.")).setAlpha(.8f);

        // Add bus stop markers
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.699926, -3.533140)).title("Bus Stop 1"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.697564, -3.530033)).title("Bus Stop 2")).setAlpha(.9f);
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.696392, -3.529814)).title("Bus Stop 3")).setAlpha(.8f);
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.696209, -3.531606)).title("Bus Stop 4")).setAlpha(.7f);
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.696650, -3.534964)).title("Bus Stop 5")).setAlpha(.6f);
    }

}

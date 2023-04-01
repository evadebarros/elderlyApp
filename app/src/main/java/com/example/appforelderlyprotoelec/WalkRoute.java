package com.example.appforelderlyprotoelec;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import java.util.ArrayList;

public class WalkRoute extends AppCompatActivity {
    private MapView mapView;
    private IMapController mapController;
    private RoadManager roadManager;
    private Road road;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_route);

        Configuration.getInstance().setUserAgentValue(getPackageName()); // Set a user agent string

        mapView = (MapView) findViewById(R.id.mapView_walk);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS); // Show zoom controls
        mapView.setMultiTouchControls(true);

        // Set the map center and zoom level to show Dublin
        mapController = mapView.getController();
        mapController.setZoom(13.0);
        mapController.setCenter(new GeoPoint(53.3498, -6.2603)); // Dublin, Ireland

        // Add markers for start and end points
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(new GeoPoint(53.3433, -6.2589)); // Trinity College
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(startMarker);

        Marker endMarker = new Marker(mapView);
        endMarker.setPosition(new GeoPoint(53.3394, -6.2717)); // St. Patrick's Cathedral
        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(endMarker);

        // Create walking route using OSRM road manager
        String osrmUrl = "http://router.project-osrm.org/route/v1/driving/";
        roadManager = new OSRMRoadManager(this, osrmUrl);
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(new GeoPoint(53.3433, -6.2589)); // Trinity College
        waypoints.add(new GeoPoint(53.3394, -6.2717)); // St. Patrick's Cathedral

        // Perform network operation on background thread using AsyncTask
        new AsyncTask<ArrayList<GeoPoint>, Void, Road>() {
            @Override
            protected Road doInBackground(ArrayList<GeoPoint>... params) {
                return roadManager.getRoad(params[0]);
            }

            @Override
            protected void onPostExecute(Road road) {
                super.onPostExecute(road);
                // Add route overlay to map
                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                mapView.getOverlays().add(roadOverlay);

                // Zoom to fit the route
                BoundingBox boundingBox = roadOverlay.getBounds();
                //mapController.zoomToBoundingBox(boundingBox, false);
                mapView.invalidate();
            }
        }.execute(waypoints);
    }
}

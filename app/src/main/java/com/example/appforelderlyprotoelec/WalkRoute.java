package com.example.appforelderlyprotoelec;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

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


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WalkRoute extends AppCompatActivity {
    private MapView mapView;
    private MapView secondMapView; // Declare second MapView
    private IMapController mapController;
    private int seconds=0;
    private boolean running;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private IMapController secondMapController; // Declare second IMapController
    private RoadManager roadManager,roadManager2;
    private Road road;
    private NestedScrollView ns;
    private ScrollView sv;
    private Button button1, button2;
    private double distance, distance2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_route);
        ns = (NestedScrollView)findViewById(R.id.scrollWalkNest);
        ns.setOnTouchListener((v, event) -> mapView.onTouchEvent(event));
        Configuration.getInstance().setUserAgentValue(getPackageName()); // Set a user agent string
        mapView = (MapView)findViewById(R.id.mapView_walk);

        button1=(Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


                View popupView = inflater.inflate(R.layout.popup_timer, null);
                findViewById(R.id.walking_route).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                Button button = (Button)popupView.findViewById(R.id.start_button);
                final TextView timeView= (TextView)popupView.findViewById(R.id.time);
                //running=true;
                button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  if (!running) {
                                                      running = true;
                                                      button.setBackgroundColor(getColor(R.color.red));
                                                      button.setText("Stop");
                                                      runTimer(timeView);
                                                  } else {
                                                      running = false;
                                                      String time = timeView.getText().toString();
                                                      button.setBackgroundColor(getColor(R.color.light_green));
                                                      button.setText("Completed in " + time +"!! Well Done");
                                                      button.setEnabled(false);
                                                      seconds=0;

                                                  }
                                              }
                                          });

                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.walking_route).setAlpha((float) 1.0);

                    }

                });




            }
        });

        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


                View popupView = inflater.inflate(R.layout.popup_timer, null);
                findViewById(R.id.walking_route).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                Button button = (Button)popupView.findViewById(R.id.start_button);
                final TextView timeView= (TextView)popupView.findViewById(R.id.time);
                //running=true;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!running) {
                            running = true;
                            button.setBackgroundColor(getColor(R.color.red));
                            button.setText("Stop");
                            runTimer(timeView);
                        } else {
                            running = false;
                            String time = timeView.getText().toString();
                            button.setBackgroundColor(getColor(R.color.light_green));
                            button.setText("Completed in " + time +"!! Well Done");
                            button.setEnabled(false);
                            seconds=0;

                        }
                    }
                });

                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.walking_route).setAlpha((float) 1.0);

                    }

                });




            }
        });
        mapView.setOnTouchListener((v, event) -> {
            ns.requestDisallowInterceptTouchEvent(true);
            return false;
        });


        mapController = mapView.getController();
        mapController.setZoom(16.0);
        mapController.setCenter(new GeoPoint(53.342087, -6.262247)); // Dublin, Ireland

        // Add markers for start and end points of second walking route
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(new GeoPoint(53.336494, -6.257290)); // Trinity College
        startMarker.setTitle("Start");
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(startMarker);
        Marker endMarker = new Marker(mapView);
        endMarker.setTitle("End");
        endMarker.setPosition(new GeoPoint(53.339768, -6.260495)); // St. Patrick's Cathedral
        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(endMarker);

        // Create second walking route using GraphHopper road manager
        String osrmUrl = "http://router.project-osrm.org/route/v1/walking/";
        roadManager = new OSRMRoadManager(this, osrmUrl);
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(new GeoPoint(53.336494, -6.257290)); // Trinity College
        waypoints.add(new GeoPoint(53.339768, -6.260495)); // Christ Church Cathedral

        // Perform network operation on background thread using AsyncTask
        new AsyncTask<ArrayList<GeoPoint>, Void, Road>() {
            @Override
            protected Road doInBackground(ArrayList<GeoPoint>... params) {
                return roadManager.getRoad(params[0]);
            }

            @Override
            protected void onPostExecute(Road road) {
                super.onPostExecute(road);
                // Add route overlay to first map
                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                mapView.getOverlays().add(roadOverlay);

                // Zoom to fit the route on first map
                BoundingBox boundingBox = roadOverlay.getBounds();
                mapView.zoomToBoundingBox(boundingBox, true, 50); // Set animate=true and padding=50
                // Get the distance of the route in meters
                double distance = road.mLength;
                setDistance(distance);

                // Display the distance of the route in a TextView or logcat
                System.out.println("Distance:"+getDistance()+"km");
                button1.setText("Distance:"+df.format(getDistance())+"km");
                //mapController.zoomToBoundingBox(boundingBox,true, 50); // Set animate=true and padding=50
                // Add route instructions to a TextView

            }
        }.execute(waypoints);

        // Set up second map for displaying alternate walking route
        secondMapView = (MapView)findViewById(R.id.mapView_walk2);
        secondMapView.setTileSource(TileSourceFactory.MAPNIK);
        secondMapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS); // Show zoom controls
        secondMapView.setMultiTouchControls(true);
        secondMapView.setOnTouchListener((v, event) -> {
            ns.requestDisallowInterceptTouchEvent(true);
            return false;
        });
        // Set the map center and zoom level to show Dublin
        secondMapController = secondMapView.getController();
        secondMapController.setZoom(16.0);
        secondMapController.setCenter(new GeoPoint(53.345469, -6.279360)); // Dublin, Ireland

        // Add markers for start and end points of second walking route
        Marker secondStartMarker = new Marker(secondMapView);
        secondStartMarker.setPosition(new GeoPoint(53.347226, -6.258170)); // Trinity College
        secondStartMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        secondStartMarker.setTitle("Start");
        secondMapView.getOverlays().add(secondStartMarker);
        Marker secondEndMarker = new Marker(secondMapView);
        secondEndMarker.setTitle("End");
        secondEndMarker.setPosition(new GeoPoint(53.347268, -6.291415)); // St. Patrick's Cathedral
        secondEndMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        secondMapView.getOverlays().add(secondEndMarker);

        // Create second walking route using GraphHopper road manager
        String osrmUrl2 = "http://router.project-osrm.org/route/v1/walking/";
        roadManager2 = new OSRMRoadManager(this, osrmUrl);
        ArrayList<GeoPoint> secondWaypoints = new ArrayList<GeoPoint>();
        secondWaypoints.add(new GeoPoint(53.347226, -6.258170)); // Trinity College
        secondWaypoints.add(new GeoPoint(53.347268, -6.291415)); // Christ Church Cathedral

        // Perform network operation on background thread using AsyncTask
        new AsyncTask<ArrayList<GeoPoint>, Void, Road>() {
            @Override
            protected Road doInBackground(ArrayList<GeoPoint>... params) {
                return roadManager2.getRoad(params[0]);
            }

            @Override
            protected void onPostExecute(Road road) {
                super.onPostExecute(road);
                // Add route overlay to second map
                Polyline secondRoadOverlay = RoadManager.buildRoadOverlay(road);
                secondMapView.getOverlays().add(secondRoadOverlay);

                // Zoom to fit the route on second map
                BoundingBox secondBoundingBox = secondRoadOverlay.getBounds();
                secondMapView.zoomToBoundingBox(secondBoundingBox, true, 50); // Set animate=true and padding=50
                double distance2 = road.mLength;

                setDistance2(distance2);
                // Display the distance of the route in a TextView or logcat
                System.out.println("Distance:"+getDistance2()+"km");
                button2.setText("Distance:"+df.format(getDistance2())+"km");

                //secondMapController.zoomToBoundingBox(secondBoundingBox, true, 50); // Set animate=true and padding=50


            }
        }.execute(secondWaypoints);



    }

    private void setDistance2(double distance2) {
        this.distance2=distance2;
    }

    private void setDistance(double distance) {
        this.distance=distance;
    }
    private double getDistance(){
        return distance;
    }

    private double getDistance2(){
        return distance2;
    }
    private void runTimer(TextView timeView) {

        // Get the text view.


        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
}


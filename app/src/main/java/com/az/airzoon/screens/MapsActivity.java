//package com.az.airzoon;
//
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private Boolean isFabOpen = false;
//    private FloatingActionButton fab;
//    private ImageView search, filter, fab1, fab2, fab3, fab4;
//    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
//    // private Toolbar supportActionBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//
//
//        initValues();
//        listeners();
//
//
//        // MyAnimation();
//
//    }
//
//    private void listeners() {
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Toast.makeText(getApplicationContext(),"welcome", Toast.LENGTH_SHORT).show();
//                animateFAB();
//            }
//        });
//        fab1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
//            }
//        });
//        fab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "welcome to button2", Toast.LENGTH_SHORT).show();
//            }
//        });
//        fab3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "button3", Toast.LENGTH_SHORT).show();
//            }
//        });
//        fab4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "button4", Toast.LENGTH_SHORT).show();
//            }
//        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "pressed search button", Toast.LENGTH_SHORT).show();
//            }
//        });
//        filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "pressed filter button", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void animateFAB() {
//        if (isFabOpen) {
//
//            fab.startAnimation(rotate_backward);
//            fab1.startAnimation(fab_close);
//            fab2.startAnimation(fab_close);
//            fab3.startAnimation(fab_close);
//            fab4.startAnimation(fab_close);
//            fab1.setClickable(true);
//            fab3.setClickable(true);
//            fab4.setClickable(true);
//            fab2.setClickable(true);
//            isFabOpen = false;
//            Log.d("Raj", "close");
//
//        } else {
//
//            fab.startAnimation(rotate_forward);
//            fab1.startAnimation(fab_open);
//            fab2.startAnimation(fab_open);
//            fab3.startAnimation(fab_open);
//            fab4.startAnimation(fab_open);
//            fab1.setClickable(true);
//            fab2.setClickable(true);
//            fab3.setClickable(true);
//            fab4.setClickable(true);
//
//            isFabOpen = true;
//            Log.d("Raj", "open");
//        }
//    }
//
//    private void initValues() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab1 = (ImageView) findViewById(R.id.fab1);
//        fab2 = (ImageView) findViewById(R.id.fab2);
//        fab3 = (ImageView) findViewById(R.id.fab3);
//        fab4 = (ImageView) findViewById(R.id.fab4);
//        search = (ImageView) findViewById(R.id.button_search_map);
//        filter = (ImageView) findViewById(R.id.button_filter_map);
//
//        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
//        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
//        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
//        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backword);
//        mapFragment.getMapAsync(this);
//    }
//
//
//
//   /* private void MyAnimation() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        fab = (FloatingActionButton)findViewById(R.id.fab);
//        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
//        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
//        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
//        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
//        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
//        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backword);
//        fab.setOnClickListener(this);
//        fab1.setOnClickListener(this);
//        fab2.setOnClickListener(this);
//    }*/
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(20.9374, 77.7796);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Amravati"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//    }
//
//   /* @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id){
//            case R.id.fab:
//
//                animateFAB();
//                break;
//            case R.id.fab1:
//
//                Log.d("Raj", "Fab 1");
//                break;
//            case R.id.fab2:
//
//                Log.d("Raj", "Fab 2");
//                break;
//    }
//}
//
//    private void animateFAB() {
//        if(isFabOpen){
//
//            fab.startAnimation(rotate_backward);
//            fab1.startAnimation(fab_close);
//            fab2.startAnimation(fab_close);
//            fab1.setClickable(false);
//            fab2.setClickable(false);
//            isFabOpen = false;
//            Log.d("Raj", "close");
//
//        } else {
//
//            fab.startAnimation(rotate_forward);
//            fab1.startAnimation(fab_open);
//            fab2.startAnimation(fab_open);
//            fab1.setClickable(true);
//            fab2.setClickable(true);
//            isFabOpen = true;
//            Log.d("Raj","open");
//
//        }
//    }*/
//
//   /* public void setSupportActionBar(Toolbar supportActionBar) {
//        this.supportActionBar = supportActionBar;
//    }*/
//}

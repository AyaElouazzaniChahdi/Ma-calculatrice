package ma.ensaf.myfirstproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        txtWelcome = findViewById(R.id.txtWelcome);


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Calculator");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(androidx.core.content.ContextCompat.getColor(this, android.R.color.white));


        drawerLayout.addDrawerListener(new androidx.drawerlayout.widget.DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@androidx.annotation.NonNull android.view.View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@androidx.annotation.NonNull android.view.View drawerView) {
                navigationView.setCheckedItem(R.id.nav_home);
            }

            @Override
            public void onDrawerClosed(@androidx.annotation.NonNull android.view.View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);


        String username = getIntent().getStringExtra("username");
        if (username != null && !username.trim().isEmpty()) {
            txtWelcome.setText(String.format("Hello, %s!", username));
        }


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_calc) {

            startActivity(new Intent(this, MainActivity2.class));

        } else if (id == R.id.nav_dict) {

            Toast.makeText(this, "Dictionnaire bientôt disponible", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {

            Toast.makeText(this, "Partage", Toast.LENGTH_SHORT).show();

        }

        else if (id == R.id.nav_login) {


            Intent intent = new Intent(HomeActivity.this, MainActivity.class);


            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
            return true;
        }

        else if (id == R.id.nav_logout) {


            finishAffinity();
            return true;
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
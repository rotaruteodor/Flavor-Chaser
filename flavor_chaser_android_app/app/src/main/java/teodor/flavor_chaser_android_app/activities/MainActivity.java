package teodor.flavor_chaser_android_app.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.fragments.EliquidCalculatorFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout mainFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        mainFrameLayout = findViewById(R.id.main_frame_layout);

        configureComponents();
    }

    private void configureComponents() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.open_navigation_drawer,
                R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.chatMenuItem:
                Toast.makeText(this, "CHAT PRESSED", Toast.LENGTH_LONG).show();
                break;
            case R.id.friendsMenuItem:
                Toast.makeText(this, "FRIENDS PRESSED", Toast.LENGTH_LONG).show();
                break;
            case R.id.groupsMenuItem:
                Toast.makeText(this, "Groups PRESSED", Toast.LENGTH_LONG).show();
                break;
            case R.id.liquidCalculatorMenuItem:
                setFragment(getEliquidCalculatorFragment());
                break;
            case R.id.recipesMenuItem:
                Toast.makeText(this, "recipes PRESSED", Toast.LENGTH_LONG).show();
                break;
            case R.id.flavorsMenuItem:
                Toast.makeText(this, "flavors PRESSED", Toast.LENGTH_LONG).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragmentToSet) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(mainFrameLayout.getId(), fragmentToSet)
                .commit();
    }

    public EliquidCalculatorFragment getEliquidCalculatorFragment() {
        return new EliquidCalculatorFragment();
    }

}
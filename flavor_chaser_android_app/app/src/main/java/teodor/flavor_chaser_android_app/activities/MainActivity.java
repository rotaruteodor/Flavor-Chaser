package teodor.flavor_chaser_android_app.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.databinding.ActivityLoginBinding;
import teodor.flavor_chaser_android_app.databinding.ActivityMainBinding;
import teodor.flavor_chaser_android_app.fragments.EliquidCalculatorFragment;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.CompanyApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;

//todo horizontal progress bar with actual progress
//todo animation for the 3 dots from the Loading your data... text

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeComponents();
    }

    private void initializeComponents() {

        configureComponents();
    }

    private void configureComponents() {
        configureDrawerLayout();
        getData(); // TODO Rename method?
    }


    private void getData() {
        Retrofit retrofit = RetrofitService.getRetrofit();
        FlavorApi flavorApi = retrofit.create(FlavorApi.class);
        CompanyApi companyApi = retrofit.create(CompanyApi.class);

        List<Observable<?>> requests = new ArrayList<>();

        requests.add(flavorApi.getAllFlavors());
        requests.add(companyApi.getAllCompanies());

        // TODO
        Observable.zip(requests, objects -> {

//                    objects[0]
//                    objects[1]

                    return new Object();
                })
                .subscribe(
                        o -> Log.e("DATA-LOAD-MAIN-ACTIVITY", "All data was loaded successfully"),
                        e -> Toast.makeText(getApplicationContext(), "ERROR! Data was not loaded", Toast.LENGTH_LONG) // TODO Custom toast + change message
                );
    }

    private void configureDrawerLayout() {
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.open_navigation_drawer,
                R.string.close_navigation_drawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.userProfileMenuItem:
                Toast.makeText(this, "PROFILE PRESSED", Toast.LENGTH_LONG).show();
                break;
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

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setFragment(Fragment fragmentToSet) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.mainFrameLayout.getId(), fragmentToSet)
                .commit();
    }

    public EliquidCalculatorFragment getEliquidCalculatorFragment() {
        return new EliquidCalculatorFragment();
    }

}
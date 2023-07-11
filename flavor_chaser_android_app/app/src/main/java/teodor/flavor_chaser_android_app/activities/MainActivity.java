package teodor.flavor_chaser_android_app.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import teodor.flavor_chaser_android_app.fragments.EliquidCalculatorFragment;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.CompanyApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;

//todo horizontal progress bar with actual progress
//todo animation for the 3 dots from the Loading your data... text

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout mainFrameLayout;
    private ProgressBar progressBar;
    private TextView textviewDataLoad;


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
        progressBar = findViewById(R.id.dataLoadProgressBar);
        textviewDataLoad = findViewById(R.id.textviewDataLoad);


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

        Retrofit retrofit = RetrofitService.getRetrofit();

        FlavorApi flavorApi = retrofit.create(FlavorApi.class);
        CompanyApi companyApi = retrofit.create(CompanyApi.class);

        List<Observable<?>> requests = new ArrayList<>();

        requests.add(flavorApi.getAllFlavors());
        requests.add(companyApi.getAllCompanies());

        Observable.zip(
                        requests,
                        new Function<Object[], Object>() {
                            @Override
                            public Object apply(Object[] objects) throws Exception {
                                // Objects[] is an array of combined results of completed requests
                                Log.e("OBJWHAT?", objects[0].toString());
                                // do something with those results and emit new event
                                return new Object();
                            }
                        })
                // After all requests had been performed the next observer will receive the Object, returned from Function
                .subscribe(
                        // Will be triggered if all requests will end successfully (4xx and 5xx also are successful requests too)
                        new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                //Do something on successful completion of all requests

                            }
                        },

                        // Will be triggered if any error during requests will happen
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                Log.e("OBJERROR", e.toString());
                            }
                        }
                );

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
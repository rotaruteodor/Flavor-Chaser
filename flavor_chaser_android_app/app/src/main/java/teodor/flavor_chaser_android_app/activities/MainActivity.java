package teodor.flavor_chaser_android_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.databinding.ActivityMainBinding;
import teodor.flavor_chaser_android_app.fragments.EliquidCalculatorFragment;
import teodor.flavor_chaser_android_app.models.Company;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.models.FlavorCategory;
import teodor.flavor_chaser_android_app.models.FlavorWarning;
import teodor.flavor_chaser_android_app.models.IngredientInStash;
import teodor.flavor_chaser_android_app.models.Rating;
import teodor.flavor_chaser_android_app.models.Recipe;
import teodor.flavor_chaser_android_app.models.RecipeFlavor;
import teodor.flavor_chaser_android_app.models.User;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;

// TODO horizontal progress bar with actual progress
// TODO animation for the 3 dots from the Loading your data... text

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private User user;
    private ArrayList<Company> companies;
    private ArrayList<Flavor> flavors;
    private ArrayList<FlavorCategory> flavorCategories;
    private ArrayList<FlavorWarning> flavorWarnings;
    private ArrayList<IngredientInStash> ingredientsInStash;
    private ArrayList<Rating> ratings;
    private ArrayList<Recipe> recipes;
    private ArrayList<RecipeFlavor> recipeFlavors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initializeComponents();
    }

    private void initializeComponents() {
        initializePassedData();
        configureComponents();
    }

    private void initializePassedData() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra(GeneralInfo.PASS_USER_REDIRECTACTIVITY_TO_MAINACTIVITY);
        companies = intent.getParcelableArrayListExtra(GeneralInfo.PASS_COMPANIES_REDIRECTACTIVITY_TO_MAINACTIVITY);
        flavors = intent.getParcelableArrayListExtra(GeneralInfo.PASS_FLAVORS_REDIRECTACTIVITY_TO_MAINACTIVITY);
        flavorCategories = intent.getParcelableArrayListExtra(GeneralInfo.PASS_FLAVORS_CATEGORIES_REDIRECTACTIVITY_TO_MAINACTIVITY);
        flavorWarnings = intent.getParcelableArrayListExtra(GeneralInfo.PASS_FLAVORS_WARNINGS_REDIRECTACTIVITY_TO_MAINACTIVITY);
        ingredientsInStash = intent.getParcelableArrayListExtra(GeneralInfo.PASS_INGREDIENTS_IN_STASH_REDIRECTACTIVITY_TO_MAINACTIVITY);
        ratings = intent.getParcelableArrayListExtra(GeneralInfo.PASS_RATINGS_REDIRECTACTIVITY_TO_MAINACTIVITY);
        recipes = intent.getParcelableArrayListExtra(GeneralInfo.PASS_RECIPES_REDIRECTACTIVITY_TO_MAINACTIVITY);
        recipeFlavors = intent.getParcelableArrayListExtra(GeneralInfo.PASS_RECIPE_FLAVORS_REDIRECTACTIVITY_TO_MAINACTIVITY);
    }

    private void configureComponents() {
        configureDrawerLayout();
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
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GeneralInfo.PASS_FLAVORS_MAINACTIVITY_TO_ELIQCALCFRAGMENT, flavors);
        bundle.putParcelableArrayList(GeneralInfo.PASS_INGREDIENTS_IN_STASH_MAINACTIVITY_TO_ELIQCALCFRAGMENT, ingredientsInStash);
        EliquidCalculatorFragment fragment = new EliquidCalculatorFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
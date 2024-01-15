package teodor.flavor_chaser_android_app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.databinding.ActivityRedirectBinding;
import teodor.flavor_chaser_android_app.models.Company;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.models.FlavorCategory;
import teodor.flavor_chaser_android_app.models.FlavorWarning;
import teodor.flavor_chaser_android_app.models.IngredientInStash;
import teodor.flavor_chaser_android_app.models.Rating;
import teodor.flavor_chaser_android_app.models.Recipe;
import teodor.flavor_chaser_android_app.models.RecipeFlavor;
import teodor.flavor_chaser_android_app.models.User;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.CompanyApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorCategoryApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorWarningApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.IngredientInStashApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.RatingApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.RecipeApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.RecipeFlavorApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.UserApi;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;

public class RedirectActivity extends AppCompatActivity {

    private ActivityRedirectBinding binding;
    private ActivityResultLauncher<Intent> openMainActivity;
    private ActivityResultLauncher<Intent> openLoginActivity;
    private SharedPreferences sharedPreferences;
    private Retrofit retrofit;
    private Disposable allDataLoadingDisposable;

    private User currentUser;
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
        binding = ActivityRedirectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initializeComponents();
    }

    private void initializeComponents() {
        initializeActivityLaunchers();
        sharedPreferences = getSharedPreferences(GeneralInfo.SHARED_PREFERENCES_FILENAME, MODE_PRIVATE);
        retrofit = RetrofitService.getInstance();
        configureComponents();
    }

    private void configureComponents() {
        String currentUserEmailAddress = sharedPreferences.getString(GeneralInfo.SHARED_PREFERENCES_USER_EMAIL_KEY, "");
        if (currentUserEmailAddress.isEmpty()) {
            launchLoginActivity();
        } else {
            launchActivityBasedOnCurrentUser(currentUserEmailAddress);
        }
    }

    private void launchActivityBasedOnCurrentUser(String emailAddress) {
        retrofit
                .create(UserApi.class)
                .getUserByEmail(emailAddress)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() == null) {
                            launchLoginActivity();
                        } else {
                             currentUser = response.body();
                            launchMainActivityWithAllData();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("FLAVOR-CHASER-ERROR", t.toString());
                        Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void initializeActivityLaunchers() {
        openMainActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {});
        openLoginActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {});
    }

    private void launchMainActivityWithAllData() {
        List<Observable<?>> requests = new ArrayList<>();
        requests.add(retrofit.create(CompanyApi.class).getAllCompanies());
        requests.add(retrofit.create(FlavorApi.class).getAllFlavors());
        requests.add(retrofit.create(FlavorCategoryApi.class).getAllFlavorsCategories());
        requests.add(retrofit.create(FlavorWarningApi.class).getAllFlavorsWarnings());
        requests.add(retrofit.create(IngredientInStashApi.class).getAllIngredientsInStashByUserId(currentUser.getId()));
        requests.add(retrofit.create(RatingApi.class).getAllRatings());
        requests.add(retrofit.create(RecipeApi.class).getAllRecipes());
        requests.add(retrofit.create(RecipeFlavorApi.class).getAllRecipeFlavors());

        // TODO Dispose on destroy
        allDataLoadingDisposable =
                Observable.zip(requests, objects -> {
                            // TODO Assign independent of their explicit index?
                            companies = (ArrayList<Company>) objects[0];
                            flavors = (ArrayList<Flavor>) objects[1];
                            flavorCategories = (ArrayList<FlavorCategory>) objects[2];
                            flavorWarnings = (ArrayList<FlavorWarning>) objects[3];
                            ingredientsInStash = (ArrayList<IngredientInStash>) objects[4];
                            ratings = (ArrayList<Rating>) objects[5];
                            recipes = (ArrayList<Recipe>) objects[6];
                            recipeFlavors = (ArrayList<RecipeFlavor>) objects[7];
                            return new Object();
                        })
                        .subscribe(
                                o -> {
                                    Log.e("FLAVOR-CHASER-DATA-LOADING", "All data was loaded successfully");
                                    launchMainActivity(currentUser,
                                            companies,
                                            flavors,
                                            flavorCategories,
                                            flavorWarnings,
                                            ingredientsInStash,
                                            ratings,
                                            recipes,
                                            recipeFlavors);
                                },
                                e -> Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_LONG)
                        );
    }

    private void launchMainActivity(
            User user,
            ArrayList<Company> companies,
            ArrayList<Flavor> flavors,
            ArrayList<FlavorCategory> flavorCategories,
            ArrayList<FlavorWarning> flavorWarnings,
            ArrayList<IngredientInStash> ingredientsInStash,
            ArrayList<Rating> ratings,
            ArrayList<Recipe> recipes,
            ArrayList<RecipeFlavor> recipeFlavors) {

        Intent intent = new Intent(RedirectActivity.this, MainActivity.class);
        intent.putExtra(GeneralInfo.PASS_USER_REDIRECTACTIVITY_TO_MAINACTIVITY, user);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_COMPANIES_REDIRECTACTIVITY_TO_MAINACTIVITY, companies);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_FLAVORS_REDIRECTACTIVITY_TO_MAINACTIVITY, flavors);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_FLAVORS_CATEGORIES_REDIRECTACTIVITY_TO_MAINACTIVITY, flavorCategories);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_FLAVORS_WARNINGS_REDIRECTACTIVITY_TO_MAINACTIVITY, flavorWarnings);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_INGREDIENTS_IN_STASH_REDIRECTACTIVITY_TO_MAINACTIVITY, ingredientsInStash);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_RATINGS_REDIRECTACTIVITY_TO_MAINACTIVITY, ratings);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_RECIPES_REDIRECTACTIVITY_TO_MAINACTIVITY, recipes);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_RECIPE_FLAVORS_REDIRECTACTIVITY_TO_MAINACTIVITY, recipeFlavors);
        openMainActivity.launch(intent);
        finish();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(RedirectActivity.this, LoginActivity.class);
        openLoginActivity.launch(intent);
        finish();
    }

}
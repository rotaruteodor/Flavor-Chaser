package teodor.flavor_chaser_android_app.fragments.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teodor.flavor_chaser_android_app.databinding.FragmentAdminDatabaseBinding;
import teodor.flavor_chaser_android_app.models.Company;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.models.FlavorCategory;
import teodor.flavor_chaser_android_app.models.FlavorWarning;
import teodor.flavor_chaser_android_app.models.IngredientInStash;
import teodor.flavor_chaser_android_app.models.Rating;
import teodor.flavor_chaser_android_app.models.User;
import teodor.flavor_chaser_android_app.models.enums.MainIngredientType;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.CompanyApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorCategoryApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorWarningApi;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.IngredientInStashApi;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;

public class AdminDatabaseFragment extends Fragment {
    // TODO Ingredients in stash (base, nicotine) should have vg,pg percentages maybe
    private FragmentAdminDatabaseBinding binding;

    private User user;
    private ArrayList<Company> companies = new ArrayList<>();
    private ArrayList<FlavorCategory> flavorCategories = new ArrayList<>();
    private ArrayList<FlavorWarning> flavorWarnings = new ArrayList<>();
    private ArrayList<Rating> ratings = new ArrayList<>();
    private ArrayList<Flavor> flavors = new ArrayList<>();
    private ArrayList<IngredientInStash> ingredientsInStash = new ArrayList<>();

    private Retrofit retrofit;
    private CompanyApi companyApi;
    private FlavorCategoryApi flavorCategoryApi;
    private FlavorWarningApi flavorWarningApi;
    private FlavorApi flavorApi;
    private IngredientInStashApi ingredientInStashApi;

    public AdminDatabaseFragment() {
    }

    public static AdminDatabaseFragment newInstance(String param1, String param2) {
        AdminDatabaseFragment fragment = new AdminDatabaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    // TODO

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeNonGraphicalComponents();
    }

    private void initializeNonGraphicalComponents() {
        retrofit = RetrofitService.getInstance();
        user = getArguments().getParcelable(GeneralInfo.PASS_USER_FROM_MAIN_ACTIVITY_TO_ADMINDATABASEFRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDatabaseBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        initializeGraphicalComponents(v);
        return v;
    }

    private void initializeGraphicalComponents(View v) {
        configureGraphicalComponents(v);
    }

    private void configureGraphicalComponents(View v) {
        binding.btnAddDataToDb.setOnClickListener(v1 -> {
            Toast.makeText(getContext(), "Registered", Toast.LENGTH_LONG).show();
            initializeLists();
            initializeRetrofitApis();
            saveEntitiesToDatabase();
        });
    }

    private void saveEntitiesToDatabase() {
        saveCompanies();
        saveFlavorCategories();
        saveFlavorWarnings();
        saveFlavors();
        saveIngredientsInStash();
    }

    private void saveIngredientsInStash() {
        for (int i = 0; i < ingredientsInStash.size(); i++) {
            int finalI = i;
            ingredientInStashApi.addIngredientInStash(ingredientsInStash.get(i)).enqueue(new Callback<IngredientInStash>() {
                @Override
                public void onResponse(Call<IngredientInStash> call, Response<IngredientInStash> response) {
                    Log.e("DATABASE-DATA-SAVING", "SUCCESS:" + response.body());
                    Log.e("DATABASE-DATA-SAVING", "SUCCESS CORRESPONDING:" + ingredientsInStash.get(finalI));
                    ingredientsInStash.get(finalI).setId(response.body().getId());
                }

                @Override
                public void onFailure(Call<IngredientInStash> call, Throwable t) {
                    Log.e("DATABASE-DATA-SAVING", "FAIL:" + t.getMessage());
                }
            });
        }
    }

    private void saveFlavors() {
        for (int i = 0; i < flavors.size(); i++) {
            int finalI = i;
            flavorApi.addFlavor(flavors.get(i)).enqueue(new Callback<Flavor>() {
                @Override
                public void onResponse(Call<Flavor> call, Response<Flavor> response) {
                    flavors.get(finalI).setId(response.body().getId());
                    Log.e("DATABASE-DATA-SAVING", "SUCCESS:" + response.body());
                }

                @Override
                public void onFailure(Call<Flavor> call, Throwable t) {
                    Log.e("DATABASE-DATA-SAVING", "FAIL:" + t.getMessage());
                }
            });
        }
    }

    private void saveFlavorWarnings() {
        for (int i = 0; i < flavorWarnings.size(); i++) {
            int finalI = i;
            flavorWarningApi.addFlavorWarning(flavorWarnings.get(i)).enqueue(new Callback<FlavorWarning>() {
                @Override
                public void onResponse(Call<FlavorWarning> call, Response<FlavorWarning> response) {
                    flavorWarnings.get(finalI).setId(response.body().getId());
                    Log.e("DATABASE-DATA-SAVING", "SUCCESS:" + response.body());
                }

                @Override
                public void onFailure(Call<FlavorWarning> call, Throwable t) {
                    Log.e("DATABASE-DATA-SAVING", "FAIL:" + t.getMessage());
                }
            });
        }
    }

    private void saveFlavorCategories() {
        for (int i = 0; i < flavorCategories.size(); i++) {
            int finalI = i;
            flavorCategoryApi.addFlavorCategory(flavorCategories.get(i)).enqueue(new Callback<FlavorCategory>() {
                @Override
                public void onResponse(Call<FlavorCategory> call, Response<FlavorCategory> response) {
                    flavorCategories.get(finalI).setId(response.body().getId());
                    Log.e("DATABASE-DATA-SAVING", "SUCCESS:" + response.body());
                }

                @Override
                public void onFailure(Call<FlavorCategory> call, Throwable t) {
                    Log.e("DATABASE-DATA-SAVING", "FAIL:" + t.getMessage());
                }
            });
        }
    }

    private void saveCompanies() {
        for (int i = 0; i < companies.size(); i++) {
            int finalI = i;
            companyApi.addCompany(companies.get(i)).enqueue(new Callback<Company>() {
                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    companies.get(finalI).setId(response.body().getId());
                    Log.e("DATABASE-DATA-SAVING", "SUCCESS:" + response.body());
                }

                @Override
                public void onFailure(Call<Company> call, Throwable t) {
                    Log.e("DATABASE-DATA-SAVING", "FAIL:" + t.getMessage());
                }
            });
        }
    }

    private void initializeRetrofitApis() {
        companyApi = retrofit.create(CompanyApi.class);
        flavorCategoryApi = retrofit.create(FlavorCategoryApi.class);
        flavorWarningApi = retrofit.create(FlavorWarningApi.class);
        flavorApi = retrofit.create(FlavorApi.class);
        ingredientInStashApi = retrofit.create(IngredientInStashApi.class);
    }

    private void initializeLists() {
        initializeCompanies();
        initializeFlavorCategories();
        initializeFlavorWarnings();
        initializeFlavors();
        initializeIngredientsInStash();
    }

    private void initializeCompanies() {
        companies.add(new Company("FA", "www.fa.com"));
        companies.add(new Company("CAP", "www.cap.com"));
    }

    private void initializeFlavorCategories() {
        flavorCategories.add(new FlavorCategory("Desert"));
        flavorCategories.add(new FlavorCategory("Fruit"));
        flavorCategories.add(new FlavorCategory("Tobacco"));
    }

    private void initializeFlavorWarnings() {
        flavorWarnings.add(new FlavorWarning("Contains bad substance1"));
        flavorWarnings.add(new FlavorWarning("Contains bad substance2"));
    }

    private void initializeFlavors() {
        Toast.makeText(getContext(), companies.get(0).toString(), Toast.LENGTH_LONG).show();
        flavors.add(new Flavor("Vanilla Bean Ice Cream", "very nice flavor", companies.get(0), flavorCategories.get(0), flavorWarnings.get(0), ratings));
        flavors.add(new Flavor("Strawberry", "very bad flavor", companies.get(1), flavorCategories.get(1), flavorWarnings.get(1), ratings));
    }

    private void initializeIngredientsInStash() {
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.BASE,
                "E-Potion Optimal 500ml",
                500d,
                500d,
                LocalDate.now(),
                BigDecimal.valueOf(38.99), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.BASE,
                "SteamOk Sweet 100ml",
                1500d,
                500d,
                LocalDate.now(),
                BigDecimal.valueOf(40), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.VG,
                "SteamOk VG 100ml",
                200d,
                100d,
                LocalDate.now(),
                BigDecimal.valueOf(13), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.VG,
                "E-Potion VG 250ml",
                250d,
                250d,
                LocalDate.now(),
                BigDecimal.valueOf(21.99), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.PG,
                "SteamOk PG 100ml",
                200d,
                100d,
                LocalDate.now(),
                BigDecimal.valueOf(16), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.PG,
                "E-Potion PG 250ml",
                250d,
                250d,
                LocalDate.now(),
                BigDecimal.valueOf(25.99), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.NICOTINE,
                "SteamOk 70/30",
                20d,
                10d,
                LocalDate.now(),
                BigDecimal.valueOf(17), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.NICOTINE,
                "Fusion 50/50",
                10d,
                10d,
                LocalDate.now(),
                BigDecimal.valueOf(17.99), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.FLAVOR,
                flavors.get(0).getName() + " - " + flavors.get(0).getCompany().getName(),
                flavors.get(0),
                10d,
                10d,
                LocalDate.now(),
                BigDecimal.valueOf(15), user));
        ingredientsInStash.add(new IngredientInStash(MainIngredientType.FLAVOR,
                flavors.get(1).getName() + " - " + flavors.get(1).getCompany().getName(),
                flavors.get(1),
                20d,
                10d,
                LocalDate.now(),
                BigDecimal.valueOf(20.99), user));
    }

}
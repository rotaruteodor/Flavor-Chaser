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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import teodor.flavor_chaser_android_app.databinding.ActivityRedirectBinding;
import teodor.flavor_chaser_android_app.models.User;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.UserApi;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;

public class RedirectActivity extends AppCompatActivity {

    //todo login and redirect logic

    private ActivityRedirectBinding binding;
    private ActivityResultLauncher<Intent> openMainActivity;
    private ActivityResultLauncher<Intent> openLoginActivity;
    private SharedPreferences sharedPreferences;

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
        configureComponents();
    }

    private void configureComponents() {
        String currentUserEmailAddress = sharedPreferences.getString(GeneralInfo.SHARED_PREFERENCES_USER_EMAIL_KEY, "");
        if (currentUserEmailAddress.isEmpty()) {
            launchLoginActivity();
        } else {
            getUserFromDatabase(currentUserEmailAddress);
        }
    }

    private void getUserFromDatabase(String emailAddress) {
        Retrofit retrofit = RetrofitService.getRetrofit();
        UserApi userApi = retrofit.create(UserApi.class);
        userApi.getUserByEmail(emailAddress).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() == null) {
//                    Toast.makeText(getApplicationContext(), "User with email " + emailAddress + " not found", Toast.LENGTH_LONG).show();
                    launchLoginActivity();
                } else {
                    User user = response.body();
                    // TODO launch main activity with logged in user
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeActivityLaunchers() {
        openMainActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                });

        openLoginActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(RedirectActivity.this, MainActivity.class);
        openMainActivity.launch(intent);
        finish();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(RedirectActivity.this, LoginActivity.class);
        openLoginActivity.launch(intent);
        finish();
    }

    // TODO General method for launching activity

}
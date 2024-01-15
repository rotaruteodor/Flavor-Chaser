package teodor.flavor_chaser_android_app.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.databinding.ActivityLoginBinding;
import teodor.flavor_chaser_android_app.models.Company;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.models.User;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.UserApi;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private ActivityResultLauncher<Intent> openMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeComponents();
    }

    private void initializeComponents() {
        openMainActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        });
        sharedPreferences = getSharedPreferences(GeneralInfo.SHARED_PREFERENCES_FILENAME, MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        configureComponents();
    }

    private void configureComponents() {
        binding.btnLogin.setOnClickListener(v -> {
            // TODO Check user
            String inputtedEmail = binding.tietLoginEmail.getText().toString();
            String inputtedPassword = binding.tietLoginPassword.getText().toString();

            if (areInputsValid()) {
                RetrofitService.getInstance()
                        .create(UserApi.class)
                        .getUserByCredentials(inputtedEmail, inputtedPassword)
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user = response.body();
                                if (user == null) {
                                    Toast.makeText(getApplicationContext(),
                                            R.string.user_credentials_not_found,
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    if (binding.switchRememberLoginCreditentials.isChecked()) {
                                        sharedPreferencesEditor.putString(GeneralInfo.SHARED_PREFERENCES_USER_EMAIL_KEY, inputtedEmail).apply();
                                    } else {
                                        sharedPreferencesEditor.remove(GeneralInfo.SHARED_PREFERENCES_USER_EMAIL_KEY).apply();
                                    }
                                    launchMainActivity(user, new ArrayList<>(), new ArrayList<>());
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e("FLAVOR-CHASER-ERROR", t.toString());
                                Toast.makeText(getApplicationContext(),
                                        R.string.server_error,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    private void launchMainActivity(
            User user,
            ArrayList<Flavor> flavors,
            ArrayList<Company> companies) {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(GeneralInfo.PASS_USER_REDIRECTACTIVITY_TO_MAINACTIVITY, user);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_FLAVORS_REDIRECTACTIVITY_TO_MAINACTIVITY, flavors);
        intent.putParcelableArrayListExtra(GeneralInfo.PASS_COMPANIES_REDIRECTACTIVITY_TO_MAINACTIVITY, companies);
        openMainActivity.launch(intent);
        finish();
    }

    private boolean areInputsValid() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.tietLoginEmail.getText().toString().trim()).matches()) {
            binding.tietLoginEmail.setError("Please enter a valid e-mail");
            binding.tietLoginEmail.requestFocus();
            return false;
        }

        if (binding.tietLoginPassword.getText().toString().isEmpty()) {
            binding.tietLoginPassword.setError("Field can't be empty");
            binding.tietLoginPassword.requestFocus();
            return false;
        }

        return true;
    }
}
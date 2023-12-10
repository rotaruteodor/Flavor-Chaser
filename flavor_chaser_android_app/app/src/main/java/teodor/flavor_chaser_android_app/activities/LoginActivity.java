package teodor.flavor_chaser_android_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import teodor.flavor_chaser_android_app.databinding.ActivityLoginBinding;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeComponents();
    }

    private void initializeComponents() {
        sharedPreferences = getSharedPreferences(GeneralInfo.SHARED_PREFERENCES_FILENAME, MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        configureComponents();
    }

    private void configureComponents() {
        binding.btnLogin.setOnClickListener(v -> {
            // TODO Check user

            String inputtedEmail = binding.tietLoginEmail.getText().toString();
            if (binding.switchRememberLoginCreditentials.isChecked()) {
                sharedPreferencesEditor.putString(GeneralInfo.SHARED_PREFERENCES_USER_EMAIL_KEY, inputtedEmail).apply();
            } else {
                sharedPreferencesEditor.remove(GeneralInfo.SHARED_PREFERENCES_USER_EMAIL_KEY).apply();
            }
        });
    }
}
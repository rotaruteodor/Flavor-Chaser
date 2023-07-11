package teodor.flavor_chaser_android_app.activities;

import static java.lang.Thread.sleep;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;

public class RedirectActivity extends AppCompatActivity {

    //todo login and redirect logic

    private ActivityResultLauncher<Intent> openMainActivity;

    public RedirectActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        initializeComponents();
    }

    private void initializeComponents() {
        openMainActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                });

        configureComponents();
    }

    private void configureComponents() {
        launchMainActivity();
    }

    private void launchMainActivity() {
        Intent intent = new Intent(RedirectActivity.this, MainActivity.class);
        openMainActivity.launch(intent);
        finish();
    }
}
package teodor.flavor_chaser_android_app.activities;

import static java.lang.Thread.sleep;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
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
import teodor.flavor_chaser_android_app.dtos.FlavorDto;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;

public class RedirectActivity extends AppCompatActivity {

    //todo horizontal progress bar with actual progress
    //todo animation for the 3 dots from the Loading your data... text
    private ProgressBar progressBar;
    private TextView textviewDataLoad;

    private ActivityResultLauncher<Intent> openMainActivity;

    private FlavorApi flavorApi;


    public RedirectActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        initializeComponents();
    }

    private void initializeComponents() {
        progressBar = findViewById(R.id.dataLoadProgressBar);
        textviewDataLoad = findViewById(R.id.textviewDataLoad);

        openMainActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                });

        flavorApi = RetrofitService.getRetrofit().create(FlavorApi.class);
        configureComponents();
    }

    private void configureComponents() {

        flavorApi.getAllFlavors()
                .enqueue(new Callback<List<FlavorDto>>() {
                    @Override
                    public void onResponse(Call<List<FlavorDto>> call,
                                           Response<List<FlavorDto>> response) {
                        Log.e("Flavors: ", response.body().toString());
                        progressBar.setVisibility(View.GONE);
                        launchMainActivity();
                    }

                    @Override
                    public void onFailure(Call<List<FlavorDto>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void launchMainActivity() {
        Intent intent = new Intent(RedirectActivity.this, MainActivity.class);
        openMainActivity.launch(intent);
        finish();
    }
}
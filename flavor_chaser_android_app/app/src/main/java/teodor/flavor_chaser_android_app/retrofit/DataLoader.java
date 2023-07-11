package teodor.flavor_chaser_android_app.retrofit;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;

public class DataLoader {

//
//    public static List<Flavor> flavors;
//
//
//    private void loadAllData() {
//        loadFlavors();
//    }
//
//    private static List<Flavor> loadFlavors() {
//
//        RetrofitService.getRetrofit()
//                .create(FlavorApi.class)
//                .getAllFlavors()
//                .enqueue(new Callback<List<Flavor>>() {
//                    @Override
//                    public void onResponse(Call<List<Flavor>> call,
//                                           Response<List<Flavor>> response) {
//                        Log.e("Flavors: ", response.body().toString());
//                        flavors = response.body();
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Flavor>> call, Throwable t) {
//                        Log.e("Flavors: ", t.toString());
//                    }
//                });
//    }
}

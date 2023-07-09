package teodor.flavor_chaser_android_app.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import teodor.flavor_chaser_android_app.PrivateInfo;

public class RetrofitService {

    private static Retrofit retrofit;

    private RetrofitService() {

    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            initializeRetrofit();
        }
        return retrofit;
    }


    private static void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(PrivateInfo.RETROFIT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
}

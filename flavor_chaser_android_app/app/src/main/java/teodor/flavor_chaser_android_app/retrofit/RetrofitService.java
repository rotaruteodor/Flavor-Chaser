package teodor.flavor_chaser_android_app.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;

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

//    TODO
//    private static OkHttpClient okClient() {
//        return new OkHttpClient.Builder()
//                .connectTimeout(1, TimeUnit.MINUTES)
//                .writeTimeout(1, TimeUnit.MINUTES)
//                .readTimeout(1, TimeUnit.MINUTES)
//                .build();
//    }

    private static void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.RETROFIT_BASE_URL)
//                .client(RetrofitService.okClient())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
}

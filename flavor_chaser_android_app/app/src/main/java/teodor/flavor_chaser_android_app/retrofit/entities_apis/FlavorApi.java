package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.retrofit.utils.GeneralInfo;

public interface FlavorApi {

    String FLAVORS_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/flavors";
    String FLAVORS_ID_URL = FLAVORS_MAIN_URL + "/{id}";
    String FLAVORS_NAMES_URL = FLAVORS_MAIN_URL + "/names";
    String FLAVORS_NAME_URL = FLAVORS_NAMES_URL + "/{name}";


    @GET(FLAVORS_MAIN_URL)
    Observable<List<Flavor>> getAllFlavors();

    @GET(FLAVORS_ID_URL)
    Call<Flavor> getFlavorById(Long id);

    @GET(FLAVORS_NAMES_URL)
    Call<List<String>> getAllFlavorNames();

//    @Headers("Accept: application/json")
    @POST(FLAVORS_MAIN_URL)
    Call<Flavor> addFlavor(@Body Flavor flavor);
}

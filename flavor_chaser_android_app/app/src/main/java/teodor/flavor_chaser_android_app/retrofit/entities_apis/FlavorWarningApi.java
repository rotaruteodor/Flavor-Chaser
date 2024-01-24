package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import teodor.flavor_chaser_android_app.models.FlavorWarning;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;


public interface FlavorWarningApi {

    String FLAVOR_WARNINGS_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/flavor_warnings";
    String FLAVOR_WARNINGS_ID_URL = FLAVOR_WARNINGS_MAIN_URL + "/{id}";

    @GET(FLAVOR_WARNINGS_MAIN_URL)
    Observable<List<FlavorWarning>> getAllFlavorsWarnings();

    @GET(FLAVOR_WARNINGS_ID_URL)
    Call<FlavorWarning> getFlavorWarningById(Long id);

    @POST(FLAVOR_WARNINGS_MAIN_URL)
    Call<FlavorWarning> addFlavorWarning(@Body FlavorWarning flavorWarning);

}

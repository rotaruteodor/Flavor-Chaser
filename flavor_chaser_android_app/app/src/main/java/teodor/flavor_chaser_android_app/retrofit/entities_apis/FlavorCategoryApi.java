package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import teodor.flavor_chaser_android_app.models.FlavorCategory;
import teodor.flavor_chaser_android_app.retrofit.utils.GeneralInfo;

public interface FlavorCategoryApi {

    String FLAVOR_CATEGORIES_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/flavor_categories";
    String FLAVOR_CATEGORIES_ID_URL = FLAVOR_CATEGORIES_MAIN_URL + "/{id}";

    @GET(FLAVOR_CATEGORIES_MAIN_URL)
    Observable<List<FlavorCategory>> getAllFlavorsCategories();

    @GET(FLAVOR_CATEGORIES_ID_URL)
    Call<FlavorCategory> getFlavorCategoryById(Long id);

    @POST(FLAVOR_CATEGORIES_MAIN_URL)
    Call<FlavorCategory> addFlavorCategory(@Body FlavorCategory flavorCategory);
}

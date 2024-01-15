package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import teodor.flavor_chaser_android_app.models.RecipeFlavor;
import teodor.flavor_chaser_android_app.retrofit.utils.GeneralInfo;

public interface RecipeFlavorApi {

    String RECIPE_FLAVORS_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/recipe_flavors";
    String RECIPE_FLAVORS_ID_URL = RECIPE_FLAVORS_MAIN_URL + "/{id}";

    @GET(RECIPE_FLAVORS_MAIN_URL)
    Observable<List<RecipeFlavor>> getAllRecipeFlavors();

    @GET(RECIPE_FLAVORS_ID_URL)
    Call<RecipeFlavor> getRecipeFlavorById(Long id);

    @POST(RECIPE_FLAVORS_MAIN_URL)
    Call<RecipeFlavor> addRecipeFlavor(@Body RecipeFlavor recipeFlavor);
}

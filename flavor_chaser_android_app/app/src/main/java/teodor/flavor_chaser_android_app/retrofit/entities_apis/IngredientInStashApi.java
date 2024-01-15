package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import teodor.flavor_chaser_android_app.models.IngredientInStash;
import teodor.flavor_chaser_android_app.retrofit.utils.GeneralInfo;

public interface IngredientInStashApi {

    String INGREDIENTS_IN_STASH_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/ingredients_in_stash";
    String INGREDIENTS_IN_STASH_ID_URL = INGREDIENTS_IN_STASH_MAIN_URL + "/{id}";
    String INGREDIENTS_IN_STASH_USER_URL = INGREDIENTS_IN_STASH_MAIN_URL + "/users/{userId}";

    @GET(INGREDIENTS_IN_STASH_MAIN_URL)
    Observable<List<IngredientInStash>> getAllIngredientsInStash();

    @GET(INGREDIENTS_IN_STASH_ID_URL)
    Call<IngredientInStash> getIngredientInStashById(Long id);

    @GET(INGREDIENTS_IN_STASH_USER_URL)
    Observable<List<IngredientInStash>> getAllIngredientsInStashByUserId(@Path("userId") Long userId);

    @POST(INGREDIENTS_IN_STASH_MAIN_URL)
    Call<IngredientInStash> addIngredientInStash(@Body IngredientInStash ingredientInStash);
}

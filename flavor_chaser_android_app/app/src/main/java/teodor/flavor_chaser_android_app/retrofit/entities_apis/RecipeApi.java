package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import teodor.flavor_chaser_android_app.models.Recipe;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;


public interface RecipeApi {

    String RECIPES_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/recipes";
    String RECIPES_ID_URL = RECIPES_MAIN_URL + "/{id}";

    @GET(RECIPES_MAIN_URL)
    Observable<List<Recipe>> getAllRecipes();

    @GET(RECIPES_ID_URL)
    Call<Recipe> getRecipeById();

    @POST(RECIPES_MAIN_URL)
    Call<Recipe> addRecipe(@Body Recipe recipe);
}

package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import teodor.flavor_chaser_android_app.dtos.RecipeDto;

public interface RecipeApi {

    String MAIN_URL = "/flavor-chaser-backend";
    String RECIPES_MAIN_URL = MAIN_URL + "/recipes";
    String RECIPES_ID_URL = RECIPES_MAIN_URL + "/{id}";

    @GET(RECIPES_MAIN_URL)
    Call<List<RecipeDto>> getAll();

    @GET(RECIPES_ID_URL)
    Call<RecipeDto> getById();

    @POST(RECIPES_MAIN_URL)
    Call<RecipeDto> add(@Body RecipeDto recipeDto);
}

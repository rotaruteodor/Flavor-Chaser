package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import teodor.flavor_chaser_android_app.models.Rating;
import teodor.flavor_chaser_android_app.retrofit.utils.GeneralInfo;

public interface RatingApi {

    String RATINGS_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/ratings";
    String RATINGS_ID_URL = RATINGS_MAIN_URL + "/{id}";

    @GET(RATINGS_MAIN_URL)
    Observable<List<Rating>> getAllRatings();

    @GET(RATINGS_ID_URL)
    Call<Rating> getRatingById(Long id);

//    @POST(RATINGS_MAIN_URL)
//    Call<Rating> addRating(@Body Rating rating);
}

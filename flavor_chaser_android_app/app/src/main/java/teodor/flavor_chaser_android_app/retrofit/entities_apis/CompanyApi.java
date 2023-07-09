package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import teodor.flavor_chaser_android_app.dtos.CompanyDto;

public interface CompanyApi {

    String MAIN_URL = "/flavor-chaser-backend";
    String COMPANIES_MAIN_URL = MAIN_URL + "/companies";
    String COMPANIES_ID_URL = COMPANIES_MAIN_URL + "/{id}";

    @GET(COMPANIES_MAIN_URL)
    Call<List<CompanyDto>> getAll();
}

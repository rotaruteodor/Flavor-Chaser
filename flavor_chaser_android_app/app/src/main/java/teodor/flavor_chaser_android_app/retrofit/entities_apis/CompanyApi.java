package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import teodor.flavor_chaser_android_app.models.Company;

public interface CompanyApi {

    String MAIN_URL = "/flavor-chaser-backend";
    String COMPANIES_MAIN_URL = MAIN_URL + "/companies";
    String COMPANIES_ID_URL = COMPANIES_MAIN_URL + "/{id}";

    @GET(COMPANIES_MAIN_URL)
    Observable<List<Company>> getAllCompanies();
}

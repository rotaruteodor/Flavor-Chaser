package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import teodor.flavor_chaser_android_app.models.Company;
import teodor.flavor_chaser_android_app.retrofit.utils.GeneralInfo;

public interface CompanyApi {

    String COMPANIES_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/companies";
    String COMPANIES_ID_URL = COMPANIES_MAIN_URL + "/{id}";

    @GET(COMPANIES_MAIN_URL)
    Observable<List<Company>> getAllCompanies();
}

package teodor.flavor_chaser_android_app.retrofit.entities_apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import teodor.flavor_chaser_android_app.models.User;
import teodor.flavor_chaser_android_app.retrofit.utils.GeneralInfo;

public interface UserApi {

    String USERS_MAIN_URL = GeneralInfo.MAIN_BACKEND_URL + "/users";
    String USERS_ID_URL = USERS_MAIN_URL + "/{id}";
    String USERS_FIND_BY_EMAIL_URL = USERS_MAIN_URL + "/findByEmailAddress";
    String USERS_CREDENTIALS_URL = USERS_MAIN_URL + "/findByCredentials";

    @GET(USERS_MAIN_URL)
    Call<List<User>> getAllUsers();

    @GET(USERS_ID_URL)
    Call<User> getUserById(@Query("id") Long id);

    @GET(USERS_FIND_BY_EMAIL_URL)
    Call<User> getUserByEmail(@Query("emailAddress") String emailAddress);

    @GET(USERS_CREDENTIALS_URL)
    Call<User> getUserByCredentials(@Query("emailAddress") String emailAddress, @Query("password") String password);

}

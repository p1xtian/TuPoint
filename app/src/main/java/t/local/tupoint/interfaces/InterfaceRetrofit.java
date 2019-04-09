package t.local.tupoint.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import t.local.tupoint.models.Admin;

public interface InterfaceRetrofit {

    //Save User
    @POST("registerAdmin")
    Call<Admin> SaveAdmin(@Body Admin admin);

}

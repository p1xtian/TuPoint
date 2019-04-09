package t.local.tupoint.interfaces;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.GET;
import t.local.tupoint.models.Admin;

public class InterfaceRetrofit {

    //Save User
    @POST("registrarAdmin")
    Call<Admin> SaveAdmin(@Body Admin admin);


}

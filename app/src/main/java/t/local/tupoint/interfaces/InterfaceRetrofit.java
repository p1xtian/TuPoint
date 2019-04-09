package t.local.tupoint.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import t.local.tupoint.models.Admin;

public interface InterfaceRetrofit {


    //List Admin

    @GET("admins")
    Call<List<Admin>> ListAdmins();


    //Save Admin
    @POST("registerAdmin")
    Call<Admin> SaveAdmin(@Body Admin admin);

}

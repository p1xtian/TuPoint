package t.local.tupoint.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import t.local.tupoint.models.Admin;
import t.local.tupoint.models.Restaurant;

public interface InterfaceRetrofit {


    //List Admin

    @GET("admins")
    Call<List<Admin>> ListAdmins();

    //List Admin

    @GET("restaurantes")
    Call<List<Restaurant>> ListRestaurants();
    //Save Admin
    @POST("registerRestaurant")
    Call<Restaurant> SaveRestaurant(@Body Restaurant restaurant);


    //Login
    @GET("admins/email/{email}}")
    Call<Admin> FindByEmail(@Path("email") String email);

    //Login
    @GET("admins/ruc/{ruc}}")
    Call<Admin> FindByRUC(@Path("ruc") String ruc);

    //Save Admin
    @POST("registerAdmin")
    Call<Admin> SaveAdmin(@Body Admin admin);

}

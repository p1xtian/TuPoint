package t.local.tupoint.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import t.local.tupoint.models.Admin;
import t.local.tupoint.models.Reserve;
import t.local.tupoint.models.Restaurant;

public interface InterfaceRetrofit {


    //List Admin

    @GET("admins")
    Call<List<Admin>> ListAdmins();

    //Listar Restaurantes
    @GET("restaurantes")
    Call<List<Restaurant>> ListRestaurants();

    //Grabar Resturante
    @POST("registerRestaurant")
    Call<Restaurant> SaveRestaurant(@Body Restaurant restaurant);

    //Iniciar Sesion Restaurante
    @GET("restaurantes/{email}/{password}")
    Call<Restaurant> Login(@Path("email") String email, @Path("password") String password);

    //Login
    @GET("admins/email/{email}}")
    Call<Admin> FindByEmail(@Path("email") String email);

    //FindByRUC
    @GET("admins/ruc/{ruc}}")
    Call<Admin> FindByRUC(@Path("ruc") String ruc);

    //Save Admin
    @POST("registerAdmin")
    Call<Admin> SaveAdmin(@Body Admin admin);


    @GET("reserves")
    Call<List<Reserve>> ListReserves();


    @POST("registerReserve")
    Call<Reserve> SaveReserve(@Body Reserve reserve);


    @GET("reserves/{email}")
    Call<List<Reserve>> FindByReserveByEmail(@Path("email") String email);

}

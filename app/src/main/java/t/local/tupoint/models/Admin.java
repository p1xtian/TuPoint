package t.local.tupoint.models;

import com.google.gson.annotations.SerializedName;

public class Admin {

    @SerializedName("codigo")
    public int Id;

    @SerializedName("correo")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("ruc")
    public String ruc;

    @SerializedName("fechadealta")
    public String dateRegister;

    @SerializedName("fechadebaja")
    public String dateDeactication ;

    @SerializedName("activo")
    public String active;

}

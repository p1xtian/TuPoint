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

    public Admin(int id, String email, String password, String ruc, String dateRegister, String dateDeactication, String active) {
        Id = id;
        this.email = email;
        this.password = password;
        this.ruc = ruc;
        this.dateRegister = dateRegister;
        this.dateDeactication = dateDeactication;
        this.active = active;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getDateDeactication() {
        return dateDeactication;
    }

    public void setDateDeactication(String dateDeactication) {
        this.dateDeactication = dateDeactication;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

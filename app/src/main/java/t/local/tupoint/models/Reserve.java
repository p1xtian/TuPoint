package t.local.tupoint.models;


import com.google.gson.annotations.SerializedName;

public class Reserve {

    @SerializedName("codigo")
    public int Id;

    @SerializedName("userid")
    public String userid;

    @SerializedName("fechadesolicitud")
    public String fechadesolicitud;

    @SerializedName("fechadereserva")
    public String fechadereserva;

    @SerializedName("ruc")
    public String ruc;

    @SerializedName("personas")
    public String personas;

    @SerializedName("activo")
    public String activo;

    public Reserve(int id, String userid, String fechadesolicitud, String fechadereserva, String ruc, String personas, String activo) {
        Id = id;
        this.userid = userid;
        this.fechadesolicitud = fechadesolicitud;
        this.fechadereserva = fechadereserva;
        this.ruc = ruc;
        this.personas = personas;
        this.activo = activo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFechadesolicitud() {
        return fechadesolicitud;
    }

    public void setFechadesolicitud(String fechadesolicitud) {
        this.fechadesolicitud = fechadesolicitud;
    }

    public String getFechadereserva() {
        return fechadereserva;
    }

    public void setFechadereserva(String fechadereserva) {
        this.fechadereserva = fechadereserva;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getPersonas() {
        return personas;
    }

    public void setPersonas(String personas) {
        this.personas = personas;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}

package t.local.tupoint.models;


import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("codigo")
    public int Id;

    @SerializedName("correo")
    public String correo;

    @SerializedName("password")
    public String password;

    @SerializedName("fechadealta")
    public String fechadealta;

    @SerializedName("fechadebaja")
    public String fechadebaja;

    @SerializedName("ruc")
    public String ruc;

    @SerializedName("razonsocial")
    public String razonsocial;

    @SerializedName("activo")
    public String activo;

    @SerializedName("direccion")
    public String direccion;

    @SerializedName("telefono")
    public String telefono;

    @SerializedName("latitud")
    public String latitud;

    @SerializedName("longitud")
    public String longitud;

    @SerializedName("logo")
    public String logo;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("garaje")
    public String garaje;

    @SerializedName("terraza")
    public String terraza;

    @SerializedName("mesas")
    public String mesas;

    @SerializedName("aforo")
    public String aforo;

    @SerializedName("aireacondicionado")
    public String aireacondicionado;

    @SerializedName("tipoId")
    public Long tipoId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechadealta() {
        return fechadealta;
    }

    public void setFechadealta(String fechadealta) {
        this.fechadealta = fechadealta;
    }

    public String getFechadebaja() {
        return fechadebaja;
    }

    public void setFechadebaja(String fechadebaja) {
        this.fechadebaja = fechadebaja;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGaraje() {
        return garaje;
    }

    public void setGaraje(String garaje) {
        this.garaje = garaje;
    }

    public String getTerraza() {
        return terraza;
    }

    public void setTerraza(String terraza) {
        this.terraza = terraza;
    }

    public String getMesas() {
        return mesas;
    }

    public void setMesas(String mesas) {
        this.mesas = mesas;
    }

    public String getAforo() {
        return aforo;
    }

    public void setAforo(String aforo) {
        this.aforo = aforo;
    }

    public String getAireacondicionado() {
        return aireacondicionado;
    }

    public void setAireacondicionado(String aireacondicionado) {
        this.aireacondicionado = aireacondicionado;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public Restaurant(int id, String correo, String password, String fechadealta, String fechadebaja, String ruc, String razonsocial, String activo, String direccion, String telefono, String latitud, String longitud, String logo, String descripcion, String garaje, String terraza, String mesas, String aforo, String aireacondicionado, Long tipoId) {
        Id = id;
        this.correo = correo;
        this.password = password;
        this.fechadealta = fechadealta;
        this.fechadebaja = fechadebaja;
        this.ruc = ruc;
        this.razonsocial = razonsocial;
        this.activo = activo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.logo = logo;
        this.descripcion = descripcion;
        this.garaje = garaje;
        this.terraza = terraza;
        this.mesas = mesas;
        this.aforo = aforo;
        this.aireacondicionado = aireacondicionado;
        this.tipoId = tipoId;
    }
}

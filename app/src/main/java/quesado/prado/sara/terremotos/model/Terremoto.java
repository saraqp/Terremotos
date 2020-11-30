package quesado.prado.sara.terremotos.model;

import java.io.Serializable;

public class Terremoto implements Serializable {
    double magnitud;
    String localizacion;
    long fechaenMilisegundos;
    String url;
    double latitud,longitud;

    public Terremoto(double magnitud, String localizacion, long fechaenMilisegundos,String url) {
        this.magnitud = magnitud;
        this.localizacion = localizacion;
        this.fechaenMilisegundos = fechaenMilisegundos;
        this.url=url;
    }

    public Terremoto(double magnitud, String localizacion, long fechaenMilisegundos, String url, double latitud, double longitud) {
        this.magnitud = magnitud;
        this.localizacion = localizacion;
        this.fechaenMilisegundos = fechaenMilisegundos;
        this.url = url;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(double magnitud) {
        this.magnitud = magnitud;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String licalizacion) {
        this.localizacion = licalizacion;
    }

    public long getFechaenMilisegundos() {
        return fechaenMilisegundos;
    }

    public void setFechaenMilisegundos(long fechaenMilisegundos) {
        this.fechaenMilisegundos = fechaenMilisegundos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}

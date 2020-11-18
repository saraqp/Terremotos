package quesado.prado.sara.terremotos.model;

public class Terremoto {
    double magnitud;
    String localizacion;
    long fechaenMilisegundos;
    String url;

    public Terremoto(double magnitud, String localizacion, long fechaenMilisegundos,String url) {
        this.magnitud = magnitud;
        this.localizacion = localizacion;
        this.fechaenMilisegundos = fechaenMilisegundos;
        this.url=url;
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
}

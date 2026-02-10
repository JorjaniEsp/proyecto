package Swing;

public class FiguraGeometrica {
    private double volumen;
    private double superficie;

    public FiguraGeometrica(double volumen, double superficie) {
        this.volumen = volumen;
        this.superficie = superficie;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

}

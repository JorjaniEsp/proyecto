package Swing;

public class Esfera extends FiguraGeometrica {
    private double radio;

    public Esfera(double radio) {
        super(0,0);
        this.radio = radio;
        this.setVolumen(calcularVolumen());
        this.setSuperficie(calcularSuperficie());
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }
    public double calcularVolumen(){
        return 1.333 * Math.PI * Math.pow(this.radio, 3.0);

    }
    public double calcularSuperficie(){
        return 4.0 * Math.PI * Math.pow(this.radio, 2.0);
    }
    public void Esfera(){

    }
}

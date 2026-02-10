package Swing;

public class Piramide extends FiguraGeometrica {
    private double base;
    private double altura;
    private double apotema;

    public Piramide(double base, double altura, double apotema) {
        super(0,0);
        this.base = base;
        this.altura = altura;
        this.apotema = apotema;
        this.setVolumen(calcularVolumen());
        this.setSuperficie(calcularSuperficie());
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getApotema() {
        return apotema;
    }

    public void setApotema(double apotema) {
        this.apotema = apotema;
    }
    public double calcularVolumen(){
        return (Math.pow(base, 2.0) * altura) / 3.0;


    }
    public double calcularSuperficie(){
       double areaBase = Math.pow(base, 2.0);
       double areaLado = 2.0 * base * apotema;

        return areaBase + areaLado;
    }
    public void Piramide(){

    }


}

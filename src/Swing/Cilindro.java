package Swing;

public class Cilindro extends FiguraGeometrica{
    private double radio;
    private double altura;

    public Cilindro(double radio, double altura) {
        super(0,0);
        this.radio = radio;
        this.altura = altura;
        this.setVolumen(calcularVolumen());
        this.setSuperficie(calcularSuperficie());
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
    public double calcularVolumen(){
        return Math.PI * altura * Math.pow(radio, 2.0);
    }
    public double calcularSuperficie(){
        double areaLadoA = 2.0 * Math.PI * radio * altura;
        double areaLadoB = 2.0 * Math.PI * Math.pow(radio, 2.0);

        return areaLadoA+areaLadoB;
    }
    public void Cilindro(){

    }


}

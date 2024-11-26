package com.example.calculoareacirculo;

public class CirculoModelo {

    //Interfaz para manejar el resultado y los errores
    public interface Callback {
        void cuandoEsteCalculadoElArea(double area);
        void cuandoHayaErrorDeRadioNegativo();
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    //Método que calcula el área del círculo
    public void calcularArea(double radio, Callback callback) {
        callback.cuandoEmpieceElCalculo();

        //Simulación de un cálculo largo
        try {
            Thread.sleep(2000);  // Simulamos un cálculo que tarda 2 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (radio < 0) {
            callback.cuandoHayaErrorDeRadioNegativo();
        } else {
            double area = Math.PI * Math.pow(radio, 2);
            callback.cuandoEsteCalculadoElArea(area);
        }

        callback.cuandoFinaliceElCalculo();
    }
}

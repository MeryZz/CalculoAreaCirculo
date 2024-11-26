package com.example.calculoareacirculo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CirculoViewModel extends AndroidViewModel {

    private final CirculoModelo circuloModelo;
    private final Executor executor;

    public MutableLiveData<Double> area = new MutableLiveData<>();
    public MutableLiveData<Boolean> calculando = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public CirculoViewModel(@NonNull Application application) {
        super(application);
        circuloModelo = new CirculoModelo();
        executor = Executors.newSingleThreadExecutor();
    }

    public void calcularArea(double radio) {
        executor.execute(() -> circuloModelo.calcularArea(radio, new CirculoModelo.Callback() {
            @Override
            public void cuandoEsteCalculadoElArea(double resultado) {
                area.postValue(resultado);
                error.postValue(null);
            }

            @Override
            public void cuandoHayaErrorDeRadioNegativo() {
                error.postValue("El radio no puede ser negativo.");
            }

            @Override
            public void cuandoEmpieceElCalculo() {
                calculando.postValue(true);
            }

            @Override
            public void cuandoFinaliceElCalculo() {
                calculando.postValue(false);
            }
        }));
    }
}
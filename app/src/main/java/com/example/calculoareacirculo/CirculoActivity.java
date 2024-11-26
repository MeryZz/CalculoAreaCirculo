package com.example.calculoareacirculo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CirculoActivity extends AppCompatActivity {

    private CirculoViewModel circuloViewModel;
    private EditText editTextRadio;
    private TextView textViewResultado;
    private TextView textViewError;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulo);

        //Inicializar las vistas
        editTextRadio = findViewById(R.id.editTextRadio);
        textViewResultado = findViewById(R.id.textViewResultado);
        textViewError = findViewById(R.id.textViewError);
        progressBar = findViewById(R.id.progressBar);

        //Inicializar ViewModel
        circuloViewModel = new ViewModelProvider(this).get(CirculoViewModel.class);

        //Observar cambios en los datos del ViewModel
        circuloViewModel.area.observe(this, area -> {
            if (area != null) {
                textViewResultado.setText(String.format("Área: %.2f", area)); //Muestra el resultado del cálculo del área
                textViewError.setVisibility(View.GONE); //Oculta el mensaje de error si no hay error
            }
        });

        circuloViewModel.error.observe(this, error -> {
            if (error != null) {
                textViewError.setText(error); //Muestra el mensaje de error
                textViewError.setVisibility(View.VISIBLE); //Lo hace visible
                textViewResultado.setVisibility(View.GONE); //Oculta el resultado si hay un error
            }
        });

        circuloViewModel.calculando.observe(this, calculando -> {
            if (calculando != null && calculando) {
                progressBar.setVisibility(View.VISIBLE); //Muestra el progressBar cuando se está calculando
                textViewResultado.setVisibility(View.GONE); //Oculta el resultado mientras se está calculando
                textViewError.setVisibility(View.GONE); //Oculta el mensaje de error mientras se está calculando
            } else {
                progressBar.setVisibility(View.GONE); //Oculta el progressBar cuando el cálculo termina
            }
        });

        //Manejo del botón de calcular
        Button buttonCalcular = findViewById(R.id.buttonCalcular);
        buttonCalcular.setOnClickListener(v -> {
            String radioText = editTextRadio.getText().toString();
            if (!radioText.isEmpty()) {
                try {
                    double radio = Double.parseDouble(radioText); //Convierte el texto a número
                    circuloViewModel.calcularArea(radio);  //Llama al ViewModel para calcular el área
                } catch (NumberFormatException e) {
                    textViewError.setText("Por favor, ingrese un número válido.");
                    textViewError.setVisibility(View.VISIBLE); //Muestra el mensaje de error si la entrada no ha sido válida
                }
            } else {
                textViewError.setText("El campo no puede estar vacío.");
                textViewError.setVisibility(View.VISIBLE);  //Muestra el mensaje si el campo está vacío
            }
        });
    }
}

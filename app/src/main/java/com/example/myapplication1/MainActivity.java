package com.example.myapplication1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button bdesc;
    private EditText precio, descuento, resultado;


    private void calculadora_descuento() {

        double p = Double.parseDouble(precio.getText().toString());
        double d = Double.parseDouble(descuento.getText().toString());


        String mensaje = "";

        if (d < 0) {
            mensaje = "Descuento inválido";
        }
        else if (d < 10) {
            mensaje = "Descuento bajo";
        }
        else if (d < 20) {
            mensaje = "Descuento medio";
        }
        else if (d < 30) {
            mensaje = "Descuento alto";
        }
        else if (d <= 100) {
            mensaje = "Súper descuento";
        }
        else {
            mensaje = "Descuento inválido";
        }


        double precioFinal = calc(p, d);

        // mostrar solamente dos decimales
        String formato = String.format("%.2f", precioFinal);


        resultado.setText("Precio final = $" + formato + " → " + mensaje);
    }

    // FUNCION (retorna un valor)
    private double calc(double precioBase, double descuentoPorc) {
        // Si el descuento no es válido, retorno el mismo precio
        if (descuentoPorc < 0 || descuentoPorc > 100) {
            return precioBase;
        }
        double rebaja = precioBase * (descuentoPorc / 100.0);
        return precioBase - rebaja;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        precio   = findViewById(R.id.precio
        );
        descuento = findViewById(R.id.desc);
        resultado  = findViewById(R.id.result);
        bdesc    = findViewById(R.id.button);


        bdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculadora_descuento();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
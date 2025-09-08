package com.example.conejossaltarines;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Actividad principal que maneja la interfaz de usuario.
 */
public class MainActivity extends AppCompatActivity {

    private final ImageView[] casillas = new ImageView[7];
    private TextView contadorMovimientos;
    private ConejoGameController controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlador = new ConejoGameController();
        inicializarVistas();
        actualizarTablero();
    }

    /** Configura las vistas y listeners de la interfaz. */
    private void inicializarVistas() {
        casillas[0] = findViewById(R.id.slot0);
        casillas[1] = findViewById(R.id.slot1);
        casillas[2] = findViewById(R.id.slot2);
        casillas[3] = findViewById(R.id.slot3);
        casillas[4] = findViewById(R.id.slot4);
        casillas[5] = findViewById(R.id.slot5);
        casillas[6] = findViewById(R.id.slot6);

        for (int i = 0; i < casillas.length; i++) {
            final int index = i;
            casillas[i].setOnClickListener(v -> manejarMovimiento(index));
        }

        contadorMovimientos = findViewById(R.id.tvMoves);
        Button reiniciar = findViewById(R.id.btnReset);
        reiniciar.setOnClickListener(v -> reiniciarJuego());
    }

    /** Maneja el toque sobre una casilla del tablero. */
    private void manejarMovimiento(int index) {
        if (controlador.moverConejo(index)) {
            actualizarTablero();
            if (controlador.verificarVictoria()) {
                mostrarDialogoVictoria();
            }
        } else {
            Toast.makeText(this, R.string.invalid_move, Toast.LENGTH_SHORT).show();
        }
    }

    /** Actualiza la representación gráfica del tablero. */
    private void actualizarTablero() {
        int[] tablero = controlador.obtenerTablero();
        for (int i = 0; i < tablero.length; i++) {
            int estado = tablero[i];
            if (estado == ConejoGameController.CONEJO_DERECHA) {
                casillas[i].setImageResource(R.drawable.conejo_derecha);
            } else if (estado == ConejoGameController.CONEJO_IZQUIERDA) {
                casillas[i].setImageResource(R.drawable.conejo_izquierda);
            } else {
                casillas[i].setImageResource(R.drawable.piedra);
            }
        }
        contadorMovimientos.setText(getString(R.string.moves_count, controlador.getMovimientos()));
    }

    /** Muestra un diálogo de victoria con opción de reiniciar. */
    private void mostrarDialogoVictoria() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.congrats)
                .setMessage(R.string.win_message)
                .setPositiveButton(R.string.restart, (d, w) -> reiniciarJuego())
                .setCancelable(false)
                .show();
    }

    /** Reinicia el juego a su estado inicial. */
    private void reiniciarJuego() {
        controlador.reiniciarJuego();
        actualizarTablero();
    }
}

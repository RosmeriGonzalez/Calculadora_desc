package com.example.conejossaltarines;

import java.util.Arrays;

/**
 * Controlador principal que gestiona los movimientos y el estado del juego.
 */
public class ConejoGameController {
    public static final int VACIO = 0;
    public static final int CONEJO_DERECHA = 1; // Conejo que se mueve hacia la derecha
    public static final int CONEJO_IZQUIERDA = 2; // Conejo que se mueve hacia la izquierda

    private int[] tablero;
    private int movimientos;

    public ConejoGameController() {
        reiniciarJuego();
    }

    /** Reinicia el tablero a la posición inicial. */
    public void reiniciarJuego() {
        tablero = new int[]{CONEJO_DERECHA, CONEJO_DERECHA, CONEJO_DERECHA,
                VACIO, CONEJO_IZQUIERDA, CONEJO_IZQUIERDA, CONEJO_IZQUIERDA};
        movimientos = 0;
    }

    /** Devuelve una copia del tablero actual. */
    public int[] obtenerTablero() {
        return tablero.clone();
    }

    /** Número de movimientos realizados. */
    public int getMovimientos() {
        return movimientos;
    }

    /** Verifica si el conejo en la posición origen puede moverse. */
    public boolean esMovimientoValido(int origen) {
        int conejo = tablero[origen];
        if (conejo == VACIO) {
            return false;
        }
        int direccion = conejo == CONEJO_DERECHA ? 1 : -1;
        int destino = origen + direccion;
        if (destino >= 0 && destino < tablero.length && tablero[destino] == VACIO) {
            return true;
        }
        destino = origen + 2 * direccion;
        return destino >= 0 && destino < tablero.length
                && tablero[origen + direccion] != VACIO
                && tablero[destino] == VACIO;
    }

    /** Intenta mover el conejo en la posición origen. */
    public boolean moverConejo(int origen) {
        if (!esMovimientoValido(origen)) {
            return false;
        }
        int conejo = tablero[origen];
        int direccion = conejo == CONEJO_DERECHA ? 1 : -1;
        int destino = origen + direccion;
        if (!(destino >= 0 && destino < tablero.length && tablero[destino] == VACIO)) {
            destino = origen + 2 * direccion;
        }
        tablero[destino] = conejo;
        tablero[origen] = VACIO;
        movimientos++;
        return true;
    }

    /** Comprueba si se alcanzó la configuración objetivo. */
    public boolean verificarVictoria() {
        int[] objetivo = {CONEJO_IZQUIERDA, CONEJO_IZQUIERDA, CONEJO_IZQUIERDA,
                VACIO, CONEJO_DERECHA, CONEJO_DERECHA, CONEJO_DERECHA};
        return Arrays.equals(tablero, objetivo);
    }
}

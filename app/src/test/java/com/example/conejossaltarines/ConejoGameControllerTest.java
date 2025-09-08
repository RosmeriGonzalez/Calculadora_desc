package com.example.conejossaltarines;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConejoGameControllerTest {

    @Test
    public void tableroInicialCorrecto() {
        ConejoGameController controlador = new ConejoGameController();
        int[] esperado = {
                ConejoGameController.CONEJO_DERECHA,
                ConejoGameController.CONEJO_DERECHA,
                ConejoGameController.CONEJO_DERECHA,
                ConejoGameController.VACIO,
                ConejoGameController.CONEJO_IZQUIERDA,
                ConejoGameController.CONEJO_IZQUIERDA,
                ConejoGameController.CONEJO_IZQUIERDA
        };
        assertArrayEquals(esperado, controlador.obtenerTablero());
    }

    @Test
    public void realizaMovimientoValido() {
        ConejoGameController controlador = new ConejoGameController();
        assertTrue(controlador.esMovimientoValido(2));
        assertTrue(controlador.moverConejo(2));
        int[] esperado = {
                ConejoGameController.CONEJO_DERECHA,
                ConejoGameController.CONEJO_DERECHA,
                ConejoGameController.VACIO,
                ConejoGameController.CONEJO_DERECHA,
                ConejoGameController.CONEJO_IZQUIERDA,
                ConejoGameController.CONEJO_IZQUIERDA,
                ConejoGameController.CONEJO_IZQUIERDA
        };
        assertArrayEquals(esperado, controlador.obtenerTablero());
    }

    @Test
    public void detectaVictoria() {
        ConejoGameController controlador = new ConejoGameController();
        int[] movimientos = {2, 3, 1, 4, 5, 2, 0, 3, 4, 6, 5, 3, 4, 2, 1, 3, 2};
        for (int m : movimientos) {
            controlador.moverConejo(m);
        }
        assertTrue(controlador.verificarVictoria());
    }
}

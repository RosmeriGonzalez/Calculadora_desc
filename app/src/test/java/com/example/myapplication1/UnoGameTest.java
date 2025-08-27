package com.example.myapplication1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Pruebas básicas para el juego de UNO.
 */
public class UnoGameTest {

    @Test
    public void playerStartsWithFiveCards() {
        UnoGame game = new UnoGame(UnoGame.AiMode.EASY);
        assertEquals(5, game.playerHandStrings().size());
    }

    @Test
    public void drawIncreasesHandSize() {
        UnoGame game = new UnoGame(UnoGame.AiMode.EASY);
        int before = game.playerHandStrings().size();
        game.playerDraw();
        assertEquals(before + 1, game.playerHandStrings().size());
    }
}


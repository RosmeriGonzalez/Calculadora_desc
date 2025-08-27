package com.example.myapplication1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Pruebas básicas para el juego de Tres en línea.
 */
public class TicTacToeGameTest {

    @Test
    public void boardStartsEmpty() {
        TicTacToeGame game = new TicTacToeGame(TicTacToeGame.AiMode.EASY);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(TicTacToeGame.Player.EMPTY, game.getCell(i, j));
            }
        }
    }

    @Test
    public void playerMoveMarksCell() {
        TicTacToeGame game = new TicTacToeGame(TicTacToeGame.AiMode.EASY);
        assertTrue(game.playerMove(0, 0));
        assertEquals(TicTacToeGame.Player.X, game.getCell(0, 0));
    }

    @Test
    public void detectsWinningRow() {
        TicTacToeGame game = new TicTacToeGame(TicTacToeGame.AiMode.EASY);
        game.setCellForTest(0, 0, TicTacToeGame.Player.X);
        game.setCellForTest(0, 1, TicTacToeGame.Player.X);
        game.setCellForTest(0, 2, TicTacToeGame.Player.X);
        assertEquals(TicTacToeGame.Player.X, game.checkWinner());
    }

    @Test
    public void twoPlayersCanMakeMoves() {
        TicTacToeGame game = new TicTacToeGame(TicTacToeGame.AiMode.TWO_PLAYERS);
        assertTrue(game.humanMove(TicTacToeGame.Player.X, 0, 0));
        assertTrue(game.humanMove(TicTacToeGame.Player.O, 0, 1));
        assertEquals(TicTacToeGame.Player.X, game.getCell(0, 0));
        assertEquals(TicTacToeGame.Player.O, game.getCell(0, 1));
    }
}


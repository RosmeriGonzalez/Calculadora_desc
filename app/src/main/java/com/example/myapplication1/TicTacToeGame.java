package com.example.myapplication1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Lógica del juego de Tic Tac Toe con tres niveles de dificultad.
 */
public class TicTacToeGame {

    public enum Difficulty { EASY, MEDIUM, HARD }

    private final Difficulty difficulty;
    private final char[] board = new char[9];
    private final Random random = new Random();

    public TicTacToeGame(Difficulty difficulty) {
        this.difficulty = difficulty;
        reset();
    }

    /** Reinicia el tablero. */
    public void reset() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    /** Obtiene una copia del tablero actual. */
    public char[] getBoard() {
        return board.clone();
    }

    /** Intenta realizar la jugada del jugador humano en la posición indicada. */
    public boolean humanMove(int index) {
        if (index < 0 || index >= board.length || board[index] != ' ') {
            return false;
        }
        board[index] = 'X';
        return true;
    }

    /** Realiza el movimiento de la CPU según la dificultad elegida. */
    public void cpuMove() {
        int move;
        switch (difficulty) {
            case MEDIUM:
                move = getMediumMove();
                break;
            case HARD:
                move = getBestMove();
                break;
            default:
                move = getRandomMove();
        }
        board[move] = 'O';
    }

    /** Movimiento aleatorio para la CPU. */
    private int getRandomMove() {
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                available.add(i);
            }
        }
        return available.get(random.nextInt(available.size()));
    }

    /** Movimiento medio: ganar si es posible, bloquear al jugador o elegir al azar. */
    private int getMediumMove() {
        int win = findWinningMove('O');
        if (win != -1) return win;
        int block = findWinningMove('X');
        if (block != -1) return block;
        return getRandomMove();
    }

    /** Encuentra un movimiento ganador para el jugador dado, o -1 si no existe. */
    private int findWinningMove(char player) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = player;
                if (checkWinnerInternal(board) == player) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }
        return -1;
    }

    /** Movimiento difícil mediante algoritmo minimax. */
    private int getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int score = minimax(false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    private int minimax(boolean maximizing) {
        char winner = checkWinnerInternal(board);
        if (winner == 'O') return 10;
        if (winner == 'X') return -10;
        if (isBoardFull()) return 0;

        int best = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char current = maximizing ? 'O' : 'X';
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = current;
                int score = minimax(!maximizing);
                board[i] = ' ';
                if (maximizing) {
                    best = Math.max(best, score);
                } else {
                    best = Math.min(best, score);
                }
            }
        }
        return best;
    }

    /** Verifica el estado actual del juego. */
    public char checkWinner() {
        char winner = checkWinnerInternal(board);
        if (winner != ' ') return winner;
        return isBoardFull() ? 'D' : ' ';
    }

    private char checkWinnerInternal(char[] b) {
        int[][] lines = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };
        for (int[] line : lines) {
            if (b[line[0]] != ' ' &&
                    b[line[0]] == b[line[1]] &&
                    b[line[1]] == b[line[2]]) {
                return b[line[0]];
            }
        }
        return ' ';
    }

    private boolean isBoardFull() {
        for (char c : board) {
            if (c == ' ') return false;
            }
        return true;
    }
}

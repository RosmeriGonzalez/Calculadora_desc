package com.example.myapplication1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Lógica de un juego de Tres en línea con tres niveles de dificultad para la CPU.
 */
public class TicTacToeGame {

    public enum Player { EMPTY, X, O }
    public enum AiMode { EASY, MEDIUM, HARD }

    private final Player[][] board = new Player[3][3];
    private AiMode aiMode;
    private final Random random = new Random();

    public TicTacToeGame(AiMode mode) {
        reset(mode);
    }

    /** Reinicia el tablero con el modo de dificultad indicado. */
    public void reset(AiMode mode) {
        this.aiMode = mode;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Player.EMPTY;
            }
        }
    }

    /** Obtiene el contenido de una celda. */
    public Player getCell(int row, int col) {
        return board[row][col];
    }

    /** Método de acceso para pruebas. */
    void setCellForTest(int row, int col, Player p) {
        board[row][col] = p;
    }

    /** Juega la ficha del usuario (X) si la celda está libre. */
    public boolean playerMove(int row, int col) {
        if (board[row][col] != Player.EMPTY || checkWinner() != null) {
            return false;
        }
        board[row][col] = Player.X;
        return true;
    }

    /** Turno de la CPU (O) según la dificultad elegida. */
    public void aiMove() {
        if (checkWinner() != null || isBoardFull()) {
            return;
        }
        int[] move;
        switch (aiMode) {
            case EASY:
                move = firstEmpty();
                break;
            case MEDIUM:
                move = randomEmpty();
                break;
            case HARD:
            default:
                move = bestMove();
                break;
        }
        if (move != null) {
            board[move[0]][move[1]] = Player.O;
        }
    }

    private int[] firstEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Player.EMPTY) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int[] randomEmpty() {
        List<int[]> empties = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Player.EMPTY) {
                    empties.add(new int[]{i, j});
                }
            }
        }
        if (empties.isEmpty()) {
            return null;
        }
        return empties.get(random.nextInt(empties.size()));
    }

    private int[] bestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] best = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Player.EMPTY) {
                    board[i][j] = Player.O;
                    int score = minimax(board, false);
                    board[i][j] = Player.EMPTY;
                    if (score > bestScore) {
                        bestScore = score;
                        best = new int[]{i, j};
                    }
                }
            }
        }
        return best;
    }

    private int minimax(Player[][] b, boolean maximizing) {
        Player winner = evaluateWinner(b);
        if (winner != null) {
            if (winner == Player.O) return 1;
            if (winner == Player.X) return -1;
        }
        if (boardFull(b)) return 0;

        int best = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b[i][j] == Player.EMPTY) {
                    b[i][j] = maximizing ? Player.O : Player.X;
                    int score = minimax(b, !maximizing);
                    b[i][j] = Player.EMPTY;
                    if (maximizing) {
                        best = Math.max(best, score);
                    } else {
                        best = Math.min(best, score);
                    }
                }
            }
        }
        return best;
    }

    private Player evaluateWinner(Player[][] b) {
        for (int i = 0; i < 3; i++) {
            if (b[i][0] != Player.EMPTY && b[i][0] == b[i][1] && b[i][1] == b[i][2]) {
                return b[i][0];
            }
            if (b[0][i] != Player.EMPTY && b[0][i] == b[1][i] && b[1][i] == b[2][i]) {
                return b[0][i];
            }
        }
        if (b[0][0] != Player.EMPTY && b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            return b[0][0];
        }
        if (b[0][2] != Player.EMPTY && b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            return b[0][2];
        }
        return null;
    }

    private boolean boardFull(Player[][] b) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b[i][j] == Player.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Determina si el tablero está lleno. */
    public boolean isBoardFull() {
        return boardFull(board);
    }

    /** Verifica si hay un ganador. */
    public Player checkWinner() {
        return evaluateWinner(board);
    }

    /** Mensaje apropiado para el estado final del juego. */
    public String getWinnerMessage() {
        Player winner = checkWinner();
        if (winner == Player.X) return "¡Ganaste!";
        if (winner == Player.O) return "La CPU ganó";
        if (isBoardFull()) return "Empate";
        return "";
    }
}


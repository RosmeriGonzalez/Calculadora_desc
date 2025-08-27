package com.example.myapplication1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Actividad principal que ejecuta un juego de Tres en línea.
 */
public class MainActivity extends AppCompatActivity {

    private TicTacToeGame game;
    private Button[][] cells = new Button[3][3];
    private TextView gameState;
    private Button btnEasy, btnMedium, btnHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gameState = findViewById(R.id.gameState);
        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);

        // Inicializa botones del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int resId = getResources().getIdentifier("cell" + i + j, "id", getPackageName());
                cells[i][j] = findViewById(resId);
                final int r = i, c = j;
                cells[i][j].setOnClickListener(v -> {
                    if (game == null) return;
                    if (game.playerMove(r, c)) {
                        updateBoard();
                        checkGame();
                        if (game.checkWinner() == null && !game.isBoardFull()) {
                            game.aiMove();
                            updateBoard();
                            checkGame();
                        }
                    }
                });
            }
        }

        btnEasy.setOnClickListener(v -> startGame(TicTacToeGame.AiMode.EASY));
        btnMedium.setOnClickListener(v -> startGame(TicTacToeGame.AiMode.MEDIUM));
        btnHard.setOnClickListener(v -> startGame(TicTacToeGame.AiMode.HARD));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startGame(TicTacToeGame.AiMode mode) {
        game = new TicTacToeGame(mode);
        updateBoard();
        gameState.setText("Tu turno");
    }

    private void updateBoard() {
        if (game == null) return;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeGame.Player p = game.getCell(i, j);
                String text = "";
                if (p == TicTacToeGame.Player.X) text = "X";
                if (p == TicTacToeGame.Player.O) text = "O";
                cells[i][j].setText(text);
            }
        }
    }

    private void checkGame() {
        if (game == null) return;
        TicTacToeGame.Player winner = game.checkWinner();
        if (winner != null || game.isBoardFull()) {
            gameState.setText(game.getWinnerMessage());
        }
    }
}


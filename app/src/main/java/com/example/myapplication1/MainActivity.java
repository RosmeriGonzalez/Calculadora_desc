package com.example.myapplication1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Actividad principal que muestra un juego de Tic Tac Toe
 * con tres niveles de dificultad para la CPU.
 */
public class MainActivity extends AppCompatActivity {

    private final Button[] cells = new Button[9];
    private Spinner difficultySpinner;
    private TextView statusText;
    private Button btnNewGame;
    private TicTacToeGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        difficultySpinner = findViewById(R.id.difficultySpinner);
        statusText = findViewById(R.id.statusText);
        btnNewGame = findViewById(R.id.btnNewGame);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Fácil", "Medio", "Difícil"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);

        for (int i = 0; i < 9; i++) {
            int resId = getResources().getIdentifier("cell" + i, "id", getPackageName());
            cells[i] = findViewById(resId);
            final int index = i;
            cells[i].setOnClickListener(v -> {
                if (game == null) return;
                if (game.humanMove(index)) {
                    updateBoard();
                    if (checkGameEnd()) return;
                    game.cpuMove();
                    updateBoard();
                    checkGameEnd();
                }
            });
        }

        btnNewGame.setOnClickListener(v -> startGame());

        startGame();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startGame() {
        TicTacToeGame.Difficulty diff = getSelectedDifficulty();
        game = new TicTacToeGame(diff);
        updateBoard();
        enableBoard(true);
        statusText.setText("Tu turno");
    }

    private TicTacToeGame.Difficulty getSelectedDifficulty() {
        String choice = (String) difficultySpinner.getSelectedItem();
        if ("Medio".equals(choice)) return TicTacToeGame.Difficulty.MEDIUM;
        if ("Difícil".equals(choice)) return TicTacToeGame.Difficulty.HARD;
        return TicTacToeGame.Difficulty.EASY;
    }

    private void updateBoard() {
        char[] b = game.getBoard();
        for (int i = 0; i < b.length; i++) {
            cells[i].setText(String.valueOf(b[i] == ' ' ? "" : b[i]));
        }
    }

    private boolean checkGameEnd() {
        char winner = game.checkWinner();
        if (winner == 'X') {
            statusText.setText("¡Ganaste!");
        } else if (winner == 'O') {
            statusText.setText("La CPU ganó");
        } else if (winner == 'D') {
            statusText.setText("Empate");
        } else {
            statusText.setText("Turno de la CPU");
            return false;
        }
        enableBoard(false);
        return true;
    }

    private void enableBoard(boolean enable) {
        for (Button b : cells) {
            b.setEnabled(enable);
        }
    }
}

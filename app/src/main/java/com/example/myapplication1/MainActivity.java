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
 * Actividad principal que ejecuta un juego básico de UNO.
 * Permite iniciar una partida en modo fácil (la CPU juega la primera carta válida)
 * o en modo aleatorio (la CPU elige una carta válida al azar).
 */
public class MainActivity extends AppCompatActivity {

    private Spinner playerHand;
    private TextView gameState;
    private Button btnPlay, btnDraw, btnEasy, btnRandom;
    private UnoGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        playerHand = findViewById(R.id.playerHand);
        gameState = findViewById(R.id.gameState);
        btnPlay = findViewById(R.id.btnPlay);
        btnDraw = findViewById(R.id.btnDraw);
        btnEasy = findViewById(R.id.btnEasy);
        btnRandom = findViewById(R.id.btnRandom);

        btnEasy.setOnClickListener(v -> startGame(false));
        btnRandom.setOnClickListener(v -> startGame(true));

        btnPlay.setOnClickListener(v -> {
            if (game == null) return;
            int index = playerHand.getSelectedItemPosition();
            if (game.playerPlay(index)) {
                afterPlayerAction();
            } else {
                gameState.setText("Carta superior: " + game.getTopCard() +
                        "\nNo puedes jugar esa carta" +
                        "\nCartas CPU: " + game.getAiHandSize());
            }
        });

        btnDraw.setOnClickListener(v -> {
            if (game == null) return;
            game.playerDraw();
            afterPlayerAction();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startGame(boolean randomAi) {
        game = new UnoGame(randomAi);
        updateViews();
        gameState.setText("Carta superior: " + game.getTopCard() +
                "\nCartas CPU: " + game.getAiHandSize());
    }

    private void afterPlayerAction() {
        updateViews();
        if (game.checkWinner()) {
            gameState.append("\n" + game.getWinnerMessage());
            return;
        }
        game.aiTurn();
        updateViews();
        if (game.checkWinner()) {
            gameState.append("\n" + game.getWinnerMessage());
        }
    }

    private void updateViews() {
        if (game == null) return;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                game.playerHandStrings());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerHand.setAdapter(adapter);
        gameState.setText("Carta superior: " + game.getTopCard() +
                "\nCartas CPU: " + game.getAiHandSize());
    }
}


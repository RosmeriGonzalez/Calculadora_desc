package com.example.myapplication1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Lógica de un juego muy simplificado de UNO.
 * Solo se utilizan cartas numéricas con cuatro colores.
 */
public class UnoGame {

    /** Colores disponibles en el mazo. */
    enum Color { ROJO, VERDE, AZUL, AMARILLO }

    /** Representa una carta con un color y un número. */
    static class Card {
        final Color color;
        final int number;

        Card(Color color, int number) {
            this.color = color;
            this.number = number;
        }

        boolean matches(Card other) {
            return color == other.color || number == other.number;
        }

        @Override
        public String toString() {
            return color.name().charAt(0) + String.valueOf(number);
        }
    }

    private final List<Card> deck = new ArrayList<>();
    private final List<Card> playerHand = new ArrayList<>();
    private final List<Card> aiHand = new ArrayList<>();
    private final Random random = new Random();
    private final boolean aiRandom;
    private Card topCard;

    public UnoGame(boolean aiRandom) {
        this.aiRandom = aiRandom;
        initDeck();
        Collections.shuffle(deck, random);
        for (int i = 0; i < 5; i++) {
            playerHand.add(draw());
            aiHand.add(draw());
        }
        topCard = draw();
    }

    /** Llena el mazo con cartas numéricas. */
    private void initDeck() {
        deck.clear();
        for (Color c : Color.values()) {
            for (int n = 0; n <= 9; n++) {
                deck.add(new Card(c, n));
            }
        }
    }

    /** Obtiene una carta del mazo. */
    private Card draw() {
        if (deck.isEmpty()) {
            initDeck();
            Collections.shuffle(deck, random);
        }
        return deck.remove(0);
    }

    public List<String> playerHandStrings() {
        List<String> list = new ArrayList<>();
        for (Card c : playerHand) {
            list.add(c.toString());
        }
        return list;
    }

    public String getTopCard() {
        return topCard.toString();
    }

    public int getAiHandSize() {
        return aiHand.size();
    }

    public void playerDraw() {
        playerHand.add(draw());
    }

    /**
     * El jugador intenta jugar una carta según el índice.
     * @return true si la jugada fue válida.
     */
    public boolean playerPlay(int index) {
        if (index < 0 || index >= playerHand.size()) {
            return false;
        }
        Card c = playerHand.get(index);
        if (c.matches(topCard)) {
            topCard = c;
            playerHand.remove(index);
            return true;
        }
        return false;
    }

    /** Turno de la CPU. */
    public void aiTurn() {
        List<Card> valid = new ArrayList<>();
        for (Card c : aiHand) {
            if (c.matches(topCard)) {
                valid.add(c);
            }
        }
        if (!valid.isEmpty()) {
            Card chosen = aiRandom ?
                    valid.get(random.nextInt(valid.size())) : valid.get(0);
            aiHand.remove(chosen);
            topCard = chosen;
        } else {
            aiHand.add(draw());
        }
    }

    public boolean checkWinner() {
        return playerHand.isEmpty() || aiHand.isEmpty();
    }

    public String getWinnerMessage() {
        if (playerHand.isEmpty()) {
            return "¡Ganaste!";
        }
        if (aiHand.isEmpty()) {
            return "La CPU ganó";
        }
        return "";
    }
}


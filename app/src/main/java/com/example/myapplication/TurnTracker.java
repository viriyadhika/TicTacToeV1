package com.example.myapplication;

class TurnTracker {
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    TurnTracker(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    void switchTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }

    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

}

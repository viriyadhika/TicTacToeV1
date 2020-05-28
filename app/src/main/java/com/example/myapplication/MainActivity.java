package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int RED_COLOR = Color.RED;
    public static final int BLUE_COLOR = Color.BLUE;
    public static final String DOUBLE_CLICK_MSG = "That Button is Clicked!";
    public static final String GAME_OVER_MSG = "Dude, the Game is Over -_-";

    private ArrayList<ArrayList<Button>> buttons;
    private boolean[][] buttonState;
    private boolean gameOver;

    private TurnTracker turnTracker;
    private TextView showTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = addAllButtons();
        showTurn = findViewById(R.id.showTurn);
        startNewGame();
    }

    public void startNewGame() {
        resetAllButtonColor();
        buttonState = new boolean[][]{
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
        gameOver = false;
        Player player1 = new Player(RED_COLOR);
        Player player2 = new Player(BLUE_COLOR);
        turnTracker = new TurnTracker(player1, player2);
        showTurn.setText(String.valueOf(turnTracker.getCurrentPlayer() + "'s Turn"));
    }

    public void resetButtonClick(View v) {
        startNewGame();
    }

    public void buttonClick(View v) {
        Player currentPlayer = turnTracker.getCurrentPlayer();
        try {
            int[] buttonPosition = getButtonPosition(v);

            updateButtonState(buttonPosition);
            currentPlayer.clickButton(v, buttonPosition);

            if (!currentPlayer.isWinning()) {
                turnTracker.switchTurn();
                showTurn.setText(String.valueOf(turnTracker.getCurrentPlayer() + "'s Turn"));
            } else {
                gameOver(currentPlayer);
            }
        } catch (DoubleClickException dce) {
            Toast.makeText(getApplicationContext(), DOUBLE_CLICK_MSG, Toast.LENGTH_SHORT).show();
        } catch (GameOverException goe) {
            Toast.makeText(getApplicationContext(), GAME_OVER_MSG, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateButtonState(int[] buttonPosition) throws DoubleClickException, GameOverException {

        if (buttonIsClicked(buttonPosition[0], buttonPosition[1])) {
            throw new DoubleClickException();
        }
        if (gameOver) {
            throw new GameOverException();
        }
        buttonState[buttonPosition[0]][buttonPosition[1]] = true;
    }

    public boolean buttonIsClicked(int row, int col) {
        return buttonState[row][col];
    }

    public ArrayList<ArrayList<Button>> addAllButtons() {
        ArrayList<ArrayList<Button>> finalList = new ArrayList<>();

        ArrayList<Button> topRow = new ArrayList<>();
        ArrayList<Button> midRow = new ArrayList<>();
        ArrayList<Button> bottomRow = new ArrayList<>();

        Button leftTop = findViewById(R.id.leftTop);
        Button centerTop = findViewById(R.id.centerTop);
        Button rightTop = findViewById(R.id.rightTop);
        topRow.add(leftTop);
        topRow.add(centerTop);
        topRow.add(rightTop);

        Button leftMid = findViewById(R.id.leftMid);
        Button centerMid = findViewById(R.id.centerMid);;
        Button rightMid = findViewById(R.id.rightMid);
        midRow.add(leftMid);
        midRow.add(centerMid);
        midRow.add(rightMid);

        Button leftBottom = findViewById(R.id.leftBottom);
        Button centerBottom = findViewById(R.id.centerBottom);
        Button rightBottom = findViewById(R.id.rightBottom);
        bottomRow.add(leftBottom);
        bottomRow.add(centerBottom);
        bottomRow.add(rightBottom);

        finalList.add(topRow);
        finalList.add(midRow);
        finalList.add(bottomRow);

        return finalList;
    }

    public void resetAllButtonColor() {
        for (ArrayList<Button> buttonRow: buttons) {
            for (Button button : buttonRow) {
                button.setBackgroundColor(Color.WHITE);
            }
        }
    }

    public int[] getButtonPosition(View v) {
        int[] pos = new int[2];
        int i;
        int j;

        for (i = 0; i < buttons.size(); i++) {
            for (j = 0; j < buttons.get(i).size(); j++) {
                if (buttons.get(i).get(j) == v) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }

        return pos;
    }

    public void gameOver(Player winner) {
        Toast.makeText(getApplicationContext(), winner + " Win!", Toast.LENGTH_LONG).show();
        gameOver = true;
    }

}

package edu.carleton.COMP2601.a1;

import java.util.ArrayList;

import static edu.carleton.COMP2601.a1.MainActivity.OMoves;
import static edu.carleton.COMP2601.a1.MainActivity.XMoves;
import static edu.carleton.COMP2601.a1.MainActivity.XOMoves;

/**
 * Created by Owner on 2017-01-26.
 */

//implement tic-tac-toe logic

public class Game {
    //access elements from mainactivity, make judgement about the move, send back to MainActivity
    private static int[][] diagonalSet = {{0, 4, 8}, {2, 4, 6}};
    private static int[][] horizontalSet = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    private static int[][] verticalSet = {{0, 3, 6}, {1, 4, 7}, {2, 5, 8}};

    //function checks if the game is over
    //if diagonal/horizontal/vertical matches or if all spots are filled
    public static int checkGameOver() { //(check this every time a move is played)
        if (checkSpots(diagonalSet, XMoves) || checkSpots(horizontalSet, XMoves) || checkSpots(verticalSet, XMoves)) {
            System.out.println("Player won this round");
            return 1;
        }
        if (checkSpots(diagonalSet, OMoves) || checkSpots(horizontalSet, OMoves) || checkSpots(verticalSet, OMoves)) {
            System.out.println("Computer won this round");
            return 2;
        }
        if (XOMoves.isEmpty()){
            System.out.println("No more moves to play, stalemate...");
            return 3;
        }
        System.out.println("No one won this round");
        return 0;
    }

    public static boolean checkSpots(int[][] set, ArrayList<Integer> moves) {
        boolean didWin;
        for (int x = 0; x < set.length; x++) {
            didWin = true;
            for (int y = 0; y < set[x].length; y++) {
                if (moves.indexOf(set[x][y]) == -1) {
                    didWin = false;
                }
            }
            if (didWin == true) {
                return true;
            }
        }
        return false; //no one won yet
    }
}

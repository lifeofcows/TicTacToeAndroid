package edu.carleton.COMP2601.a1;

/**
 * Created by Owner on 2017-01-26.
 */

//implement tic-tac-toe logic

public class Game {
    //access elements from mainactivity, make judgement about the move, send back to MainActivity
    int finished = 0;
    //function checks if the game is over
    //if diagonal/horizontal/vertical matches or if all spots are filled
    //returns: 0 if no game result yet, 1 if player won, 2 if computer won, or 3 if tie
    public int checkGameOver() { //(check this every time a move is played)
        checkDiagonals();
        checkHorizontals();
        checkVerticals();
        return 0;
    }

    public int checkDiagonals() { //check for both O and X

        //buttons 0, 4, 8 or 2, 4, 6
        //return 0 if n/a 1 if player won and 2 if computer won
        return 0;
    }

    public int checkHorizontals() { //check for both O and X
        //buttons 0, 1, 2 or 3, 4, 5 or 6, 7, 8
        //return 0 if n/a; 1 if player won and 2 if computer won
        return 0;
    }

    public int checkVerticals() { //check for both O and X
        //buttons 0, 3, 6 or 1, 4, 7 or 2, 5, 8
        //return 0 if n/a; 1 if player won and 2 if computer won
        return 0;
    }
}

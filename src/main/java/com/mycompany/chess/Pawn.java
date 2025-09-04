
package com.mycompany.chess;

public class Pawn extends Piece{

    //constructor
    public Pawn(int row, int col, String colour) {
        super(row, col, colour);
    }
    
    @Override
    public String getSymbol() { return colour.equals("white") ? "wP" : "bP"; }

    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        
        //dirction of move
        int direction = colour.equals("white") ? -1 : 1;  //White pawns move up the board (row decreases)
        int startRow = colour.equals("white") ? 6 : 1;    //Black pawns move down the board (row increases).
        
        //Move forward 1 square if empty.
        if (newCol == col && newRow == row + direction && board[newRow][newCol] == null) {
            return true;
        }
        
        //Move forward 2 squares only from start row, if both squares are empty.
        if (newCol == col && newRow == row + 2 * direction && row == startRow && board[newRow][newCol] == null
                && board[row + direction][col] == null) {
            return true;
        }
        
        //Move diagonally forward one column left or right.  Must land on a square with an opponent’s piece.
        if (Math.abs(newCol - col) == 1 && newRow == row + direction && board[newRow][newCol] != null
                && !board[newRow][newCol].getColour().equals(colour)) {
            return true;
        }
        return false;
        //(GeeksforGeeks, 2025),(Johns, 2024)
    }
    
}

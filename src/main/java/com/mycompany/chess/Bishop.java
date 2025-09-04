
package com.mycompany.chess;

public class Bishop extends Piece{

    //Constructor
    public Bishop(int row, int col, String colour) {
        super(row, col, colour);
    }    
    
    @Override
    public String getSymbol() { return colour.equals("white") ? "wB" : "bB"; }

    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        
        //Bishop moves diagonally
        if (Math.abs(newRow - row) != Math.abs(newCol - col)) return false;
        
        //moves through diagonal line throughout the game. if a square is occupied by own piece, the move is invalid.
        int rowStep = newRow > row ? 1 : -1;
        int colStep = newCol > col ? 1 : -1;
        int r = row + rowStep, c = col + colStep;
        while (r != newRow || c != newCol) {
            if (board[r][c] != null) return false;
            r += rowStep;
            c += colStep;
        }
        return true; //(GeeksforGeeks, 2025), (Johns, 2024)
    }
}


package com.mycompany.chess;

public class King extends Piece{

    //constructor
    public King(int row, int col, String colour) {
        super(row, col, colour);
    }
    
    @Override
    public String getSymbol() { return colour.equals("white") ? "wK" : "bK"; }

    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        //king moves one square in any direction
        int rowDiff = Math.abs(newRow - row);
        int colDiff = Math.abs(newCol - col);
        return rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0);
    } //(GeeksforGeeks, 2025), (Johns, 2024)
}

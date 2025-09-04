
package com.mycompany.chess;

public class Knight extends Piece{

    //Constructor
    public Knight(int row, int col, String colour) {
        super(row, col, colour);
    }
    
    @Override
    public String getSymbol() { return colour.equals("white") ? "wN" : "bN"; }

    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        
        //knight moves in L shape
        int rowDiff = Math.abs(newRow - row);
        int colDiff = Math.abs(newCol - col);
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
    //(GeeksforGeeks, 2025), (Johns, 2024)
    
}

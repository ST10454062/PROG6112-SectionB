
package com.mycompany.chess;

public class Rook extends Piece{

    //Constructor
    public Rook(int row, int col, String colour) {
        super(row, col, colour);
    }
    
    @Override
    public String getSymbol() { return colour.equals("white") ? "wR" : "bR"; }

    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        
        //moves only in straight lines. If neither row or column matches it is invalid
        if (newRow != row && newCol != col) return false;
        
        //determines Direction of move
        int rowStep = newRow == row ? 0 : (newRow > row ? 1 : -1);
        int colStep = newCol == col ? 0 : (newCol > col ? 1 : -1);
        
        //Stops before reaching destination, if a square is occupied the move is blocked and invalid
        int r = row + rowStep, c = col + colStep;
        while (r != newRow || c != newCol) {
            if (board[r][c] != null) return false;
            r += rowStep;
            c += colStep;
        }
        return true;
        //(GeeksforGeeks, 2025), (Johns, 2024)e2
    }
}

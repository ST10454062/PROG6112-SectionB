
package com.mycompany.chess;

public class Queen extends Piece{

    //constructor
    public Queen(int row, int col, String colour) {
        super(row, col, colour);
    }
    
    @Override
    public String getSymbol() { return colour.equals("white") ? "wQ" : "bQ"; }

    @Override
    public boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        
        //straight lines moves like a rook
        if (newRow == row || newCol == col) {
            int rowStep = newRow == row ? 0 : (newRow > row ? 1 : -1);
            int colStep = newCol == col ? 0 : (newCol > col ? 1 : -1);
            int r = row + rowStep, c = col + colStep;
            while (r != newRow || c != newCol) {
                if (board[r][c] != null) return false;
                r += rowStep;
                c += colStep;
            }
            return true;
            
            //diagonal moves like a bishop
        } else if (Math.abs(newRow - row) == Math.abs(newCol - col)) {
            int rowStep = newRow > row ? 1 : -1;
            int colStep = newCol > col ? 1 : -1;
            int r = row + rowStep, c = col + colStep;
            while (r != newRow || c != newCol) {
                if (board[r][c] != null) return false;
                r += rowStep;
                c += colStep;
            }
            return true;
        }
        return false;
    }
}


package com.mycompany.chess;

public abstract class Piece {
    protected int row, col;
    protected String colour;
    protected int newRow, newCol;

    public Piece(int row, int col, String colour) {
        this.row = row;
        this.col = col;
        this.colour = colour;
    }
    
    public String getColour(){
        return colour;
    }
    public abstract String getSymbol();

    public abstract boolean isValidMove(int newRow, int newCol, Piece[][] board);

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

}

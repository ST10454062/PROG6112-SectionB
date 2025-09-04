package com.mycompany.chess;

import java.util.Scanner;

public class Board {
    private final Piece[][] board;
    private String currentPlayer; 
    private final Scanner scanner;
    
    
    
    
    
    public Board() {
        board = new Piece[8][8];
        currentPlayer = "white"; 
        scanner = new Scanner(System.in);
        setupBoard();
    }
    
    private void setupBoard() {
        // Setup black pieces
        board[0][0] = new Rook(0, 0, "black");
        board[0][1] = new Knight(0, 1, "black");
        board[0][2] = new Bishop(0, 2, "black");
        board[0][3] = new Queen(0, 3, "black");
        board[0][4] = new King(0, 4, "black");
        board[0][5] = new Bishop(0, 5, "black");
        board[0][6] = new Knight(0, 6, "black");
        board[0][7] = new Rook(0, 7, "black");
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(1, col, "black");
        }

        // Setup white pieces
        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn(6, col, "white");
        }
        board[7][0] = new Rook(7, 0, "white");
        board[7][1] = new Knight(7, 1, "white");
        board[7][2] = new Bishop(7, 2, "white");
        board[7][3] = new Queen(7, 3, "white");
        board[7][4] = new King(7, 4, "white");
        board[7][5] = new Bishop(7, 5, "white");
        board[7][6] = new Knight(7, 6, "white");
        board[7][7] = new Rook(7, 7, "white");
    }
    
    
    private void printBoard() {
        System.out.println("\n   a  b  c  d  e  f  g  h "); //labels on board
        for (int row = 0; row < 8; row++) {  //labeled 8 to 1
            System.out.print((8 - row) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece == null) {
                    System.out.print(".  ");
                } else {
                    System.out.printf("%-3s", piece.getSymbol()); //makes sure of column alignment, each piece gets a width of 3
                }
            }
            System.out.println(" " + (8 - row));  //(Hector, 2015)
        }
        System.out.println("   a  b  c  d  e  f  g  h \n");       
    }
    
    private int[] parsePosition(String pos) {
        if (pos.length() != 2) return null; //valid square in algebraic notation is 2 char
        int col = pos.charAt(0) - 'a';  //takes input of first char e.g. a
                                        // subracts 'a' and then converts to 0-based indices. examples: a=0, b=1
        int row = 8 - (pos.charAt(1) - '0');   //takes input of second char e.g. 2
                                               //subracts from 8 e.g. '1'=7, '8'=0
        if (row < 0 || row > 7 || col < 0 || col > 7) return null;    //ensures position is in 8x8 board
        return new int[]{row, col};
    }                                  //(Oracle, 2014)

    private boolean isInCheck(String colour) {
        int kingRow = -1, kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.getColour().equals(colour)) {
                    kingRow = row;
                    kingCol = col;
                }
            }
        }

        String opponentColor = colour.equals("white") ? "black" : "white";
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColour().equals(opponentColor)) {
                    if (piece.isValidMove(kingRow, kingCol, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean makeMove(String move) {
        String[] parts = move.trim().split("\\s+to\\s+");
        if (parts.length != 2) {    //If not exactly 2 parts it rejects
            System.out.println("Invalid move format. Use 'e2 to e4'.");
            return false;
        }

        //converts positions to array indices like e2 is [6,4]
        int[] start = parsePosition(parts[0]);
        int[] end = parsePosition(parts[1]);
        if (start == null || end == null) {
            System.out.println("Invalid position. Use format like 'e2 to e4'.");
            return false;
        }

        //extracts the different move details. it retrieves the piece at the starting square
        int startRow = start[0], startCol = start[1];
        int endRow = end[0], endCol = end[1];
        Piece piece = board[startRow][startCol];

        
        //Corrects you if you select a piece that doesnt exist
        if (piece == null) {
            System.out.println("No piece at " + parts[0]);
            return false;
        }
        
        //making sure you move your own colour piece and not your opponents
        if (!piece.getColour().equals(currentPlayer)) {
            System.out.println("Not your piece! It's " + currentPlayer + "'s turn.");
            return false;
        }
        
        //Validates the pieces moves
        if (!piece.isValidMove(endRow, endCol, board)) {
            System.out.println("Invalid move for " + piece.getClass().getSimpleName());
            return false;
        }
        
        // rejects if the position is occupied by your own piece
        if (board[endRow][endCol] != null && board[endRow][endCol].getColour().equals(currentPlayer)) {
            System.out.println("Cannot capture your own piece!");
            return false;
        }

        
        //updates and saves the move
        Piece capturedPiece = board[endRow][endCol];
        board[endRow][endCol] = piece;
        board[startRow][startCol] = null;
        piece.setPosition(endRow, endCol);
        
        //if current player is in check after their move, it rejects and you try again
        if (isInCheck(currentPlayer)) {
            board[startRow][startCol] = piece;
            board[endRow][endCol] = capturedPiece;
            piece.setPosition(startRow, startCol);
            System.out.println("Move puts your king in check!");
            return false;   //(Johns, 2024)
        }

        //Switch players
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
        return true;
    }

    public void play() {
        while (true) {
            printBoard();
            System.out.println(currentPlayer + "'s turn. Enter move (e.g., 'e2 to e4') or 'quit' to exit:");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("quit")) break;
            if (makeMove(input)) {
                String opponent = currentPlayer.equals("white") ? "black" : "white";
                if (isInCheck(opponent)) {
                    System.out.println(opponent + " is in check!");
                }
            }
        }
        scanner.close();
    }
    
    
    
    //public method for unit testing
    public Piece[][] getBoard() { 
        return board; 
    }
    public Piece getPieceAt(int row, int col) { 
        return board[row][col]; 
    }
    public int[] parsePositionPublic(String pos) { 
        return parsePosition(pos); 
    }
    public boolean makeMovePublic(String move) { 
        return makeMove(move); 
    }
    public void setCurrentPlayerPublic(String colour) {
        currentPlayer = colour;
    }

}

package com.mycompany.chess;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(); // Initialize a new chess board before each test
    }

    @Test
    public void testParsePosition() {
        int[] pos = board.parsePositionPublic("e2");
        assertNotNull(pos);
        assertEquals(6, pos[0]); // Check that the row index for "e2" is correct
        assertEquals(4, pos[1]); // Check that the column index for "e2" is correct

        // Invalid positions
        assertNull(board.parsePositionPublic("z9"));
        assertNull(board.parsePositionPublic("a0"));
        assertNull(board.parsePositionPublic("a10"));
    }

    @Test
    public void testMovePawn() {
        // White pawn e2 to e4 (valid)
        assertTrue(board.makeMovePublic("e2 to e4"));

        Piece piece = board.getPieceAt(4, 4); // Verify pawn's new position on e4
        assertNotNull(piece);
        assertEquals("white", piece.getColour());
        assertEquals("wP", piece.getSymbol());

        // Trying to move a white pawn again immediately should fail
        assertFalse(board.makeMovePublic("d2 to d4"));
    }
    
    @Test
    public void testMoveRook() {
        board.setCurrentPlayerPublic("white");
        // Move pawn out of the way for rook
        assertTrue(board.makeMovePublic("a2 to a4"));

        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("a1 to a3")); // move rook vertical

        Piece rook = board.getPieceAt(5, 0); // Verify rook's position at a3
        assertNotNull(rook);
        assertEquals("wR", rook.getSymbol());

        board.setCurrentPlayerPublic("white");
        assertFalse(board.makeMovePublic("a3 to b4")); // illegal diagonal
    }
    
    @Test
    public void testMoveKnight() {
        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("b1 to c3")); // L-shape move

        Piece knight = board.getPieceAt(5, 2); // Verify knight's new position at c3
        assertNotNull(knight);
        assertEquals("wN", knight.getSymbol());

        board.setCurrentPlayerPublic("white");
        assertFalse(board.makeMovePublic("c3 to c4")); // illegal straight
    }
    
    @Test
    public void testMoveBishop() {
        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("d2 to d3")); // clear d3

        board.setCurrentPlayerPublic("black");
        assertTrue(board.makeMovePublic("a7 to a6")); // black moves

        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("e2 to e3")); // clear e3

        board.setCurrentPlayerPublic("black");
        assertTrue(board.makeMovePublic("b7 to b6")); // black moves

        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("c1 to f4")); // diagonal bishop move

        Piece bishop = board.getPieceAt(4, 5); // Verify bishop's new position at f4
        assertNotNull(bishop);
        assertEquals("wB", bishop.getSymbol());

        board.setCurrentPlayerPublic("white");
        assertFalse(board.makeMovePublic("f4 to f5")); // illegal straight
    }
    
    @Test
    public void testMoveQueen() {
        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("d2 to d4")); // Clear path
        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("e2 to e3")); // Clear path

        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("d1 to d3")); // straight move

        Piece queen = board.getPieceAt(5, 3); // Verify queen at d3
        assertNotNull(queen);
        assertEquals("wQ", queen.getSymbol());

        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("d3 to g6")); // diagonal

        queen = board.getPieceAt(2, 6); // Verify queen at g6
        assertNotNull(queen);
        assertEquals("wQ", queen.getSymbol());

        board.setCurrentPlayerPublic("white");
        assertFalse(board.makeMovePublic("g6 to h8")); // illegal knight-like move for queen
    }
    
    @Test
    public void testMoveKing() {
        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("e2 to e3")); // Free king's path
        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("d2 to d3"));

        board.setCurrentPlayerPublic("white");
        assertTrue(board.makeMovePublic("e1 to e2")); // one square move

        Piece king = board.getPieceAt(6, 4); // e2
        assertNotNull(king);
        assertEquals("wK", king.getSymbol());

        board.setCurrentPlayerPublic("white");
        assertFalse(board.makeMovePublic("e2 to e4")); // illegal two square move
    }

    @Test
    public void testInvalidMove() {
        // illegal move
        assertFalse(board.makeMovePublic("e2 to e1"));

        // trying to move into square of same colour piece
        assertFalse(board.makeMovePublic("d1 to e2")); // queen blocked by pawn
    }

    @Test
    public void testCapturePiece() {
        assertTrue(board.makeMovePublic("e2 to e4")); // white pawn
        assertTrue(board.makeMovePublic("d7 to d5")); // black pawn
        assertTrue(board.makeMovePublic("e4 to d5")); // white pawn captures black pawn

        Piece piece = board.getPieceAt(3, 3); // d5
        assertNotNull(piece);
        assertEquals("white", piece.getColour());
        assertEquals("wP", piece.getSymbol());
    }
    //(Bechtold et al., 2025)
}

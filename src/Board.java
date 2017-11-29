import java.util.Objects;
import java.util.Stack;

public class Board {

    private Square[] squares;

    public Board(Square[] squares) {
        this.squares = squares;
    }

    public Board() {
        this.squares = new Square[64];
        this.squares[0] = new Square(0, 0, new Piece("Rook", "White"));
        this.squares[1] = new Square(1, 0, new Piece("Knight", "White"));
        this.squares[2] = new Square(2, 0, new Piece("Bishop", "White"));
        this.squares[3] = new Square(3, 0, new Piece("King", "White"));
        this.squares[4] = new Square(4, 0, new Piece("Queen", "White"));
        this.squares[5] = new Square(5, 0, new Piece("Bishop", "White"));
        this.squares[6] = new Square(6, 0, new Piece("Knight", "White"));
        this.squares[7] = new Square(7, 0, new Piece("Rook", "White"));
        for (int i = 0; i < 8; i++) {
            this.squares[8 + i] = new Square(i, 1, new Piece("Pawn", "White"));
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                this.squares[16 + j + i * 8] = new Square(j, i + 2);
            }
        }
        for (int i = 0; i < 8; i++) {
            this.squares[48 + i] = new Square(i, 6, new Piece("Pawn", "Black"));
        }
        this.squares[56] = new Square(0, 7, new Piece("Rook", "Black"));
        this.squares[57] = new Square(1, 7, new Piece("Knight", "Black"));
        this.squares[58] = new Square(2, 7, new Piece("Bishop", "Black"));
        this.squares[59] = new Square(3, 7, new Piece("King", "Black"));
        this.squares[60] = new Square(4, 7, new Piece("Queen", "Black"));
        this.squares[61] = new Square(5, 7, new Piece("Bishop", "Black"));
        this.squares[62] = new Square(6, 7, new Piece("Knight", "Black"));
        this.squares[63] = new Square(7, 7, new Piece("Rook", "Black"));
    }

    public Square getSquare(int x, int y) {
        return this.squares[y * 8 + x];
    }

    public Square getSquare(int square) {
        return squares[square];
    }

    public Square[] getSquares() {
        return this.squares;
    }

    public void movePiece(int startPosition, int landingPosition) {

        Piece movingPiece = this.squares[startPosition].getPiece();

        //copying starting pos piece to landing position square

        if (Objects.equals(movingPiece.getType(), "Pawn") && Objects.equals(movingPiece.getColour(), "Black") && landingPosition < 8) {
            Piece queen = new Piece("Queen", "Black");
            this.squares[landingPosition].updatePiece(queen);
        } else if (Objects.equals(movingPiece.getType(), "Pawn") && Objects.equals(movingPiece.getColour(), "White") && landingPosition > 55) {
            Piece queen = new Piece("Queen", "White");
            this.squares[landingPosition].updatePiece(queen);
        } else {
            this.squares[landingPosition].updatePiece(this.squares[startPosition].getPiece());
        }
        //removing piece from starting square
        this.squares[startPosition].removePiece();
    }

    public Stack<Move> getMoves(String colour) {

        Stack<Move> tempStack;
        Stack<Move> completeStack = new Stack();

        for (int i = 0; i < 64; i++) {
            tempStack = this.squares[i].getMoves(this, colour);
            if (tempStack != null) {
                while (!tempStack.empty()) {
                    Move move = tempStack.pop();
                    completeStack.push(move);
                }
            }

        }

        return completeStack;
    }

    public Stack<Move> getNextMoves(String color, Move move) {

        //create a copy of board
        Square[] squares = new Square[64];
        for (int i = 0; i < squares.length; i++) {
            squares[i] = this.squares[i].copy();
        }
        Board boardCopy = new Board(squares);

        //for (int i=0; i<moves.size(); i++){
        //Move move = moves.pop();
        boardCopy.movePiece(move);

        //}

        Stack<Move> nextMoves = boardCopy.getMoves(color);
        //boardCopy.undoMove(move);

        return nextMoves;
    }

    public void movePiece(Move move) {

        int startPosition = move.getStartY() * 8 + move.getStartX();
        int landingPosition = move.getLandingY() * 8 + move.getLandingX();

        Piece movingPiece = this.squares[startPosition].getPiece();

        //copying starting pos piece to landing position square

        if (Objects.equals(movingPiece.getType(), "Pawn") && Objects.equals(movingPiece.getColour(), "Black") && landingPosition < 8) {
            Piece queen = new Piece("Queen", "Black");
            this.squares[landingPosition].updatePiece(queen);
        } else if (Objects.equals(movingPiece.getType(), "Pawn") && Objects.equals(movingPiece.getColour(), "White") && landingPosition > 55) {
            Piece queen = new Piece("Queen", "White");
            this.squares[landingPosition].updatePiece(queen);
        } else {
            this.squares[landingPosition].updatePiece(this.squares[startPosition].getPiece());
        }
        //removing piece from starting square
        this.squares[startPosition].removePiece();
    }

    public int getBoardScore(String color) {
        int score;
        int blackScore = 0;
        int whiteScore = 0;
        for (int i = 0; i < 64; i++) {
            Piece piece = squares[i].getPiece();
            if (squares[i].isOccupied()) {
                if (Objects.equals(piece.getColour(), "Black")) {
                    blackScore = blackScore + piece.getValue();
                } else {
                    whiteScore = whiteScore + piece.getValue();
                }
            }
        }

        if (Objects.equals(color, "Black")) {
            score = blackScore - whiteScore;
        } else {
            score = whiteScore - blackScore;
        }
        return score;
    }

    public Board copy(){
        Square[] squares = new Square[64];
        for (int j = 0; j < squares.length; j++) {
            squares[j] = this.getSquares()[j].copy();
        }
        return new Board(squares);
    }

    public void printBoaard() {
        for (int i = 0; i < 8; i++) {
            String line = "";
            for (int k = 0; k < 8; k++) {
                if (this.squares[i * 8 + k].isOccupied()) {
                    line = line + "# " + this.squares[i * 8 + k].getPiece().getColour().substring(0, 1) + this.squares[i * 8 + k].getPiece().getType().substring(0, 2) + " ";
                } else {
                    line = line + "#     ";
                }
            }
            line = line + "#";
            System.out.println("#=====#=====#=====#=====#=====#=====#=====#=====#");
            System.out.println(line);
        }
        System.out.println("#=====#=====#=====#=====#=====#=====#=====#=====#");
    }
}

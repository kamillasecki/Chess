public class Board {

    private Square[] squares;

    public Board(Square[] squares){
        this.squares = squares;
    }

    public Board(){
        squares = new Square[64];
        squares[0] = new Square(0,0,new Piece("Rook", "Black"));
        squares[1] = new Square(0,0,new Piece("Rook", "Black"));
        squares[2] = new Square(0,0,new Piece("Rook", "Black"));

    }

    public Square getSquare(int x, int y) {
        return squares[y*8 + x];
    }

    public Square getSquare(int square) {
        return squares[square];
    }

    public Square[] getSquares() {
        return squares;
    }
}

public class Board {

    private Square[] squares;

    public Board(Square[] squares){
        this.squares = squares;
    }

    public Board(){
        squares = new Square[64];
        squares[0] = new Square(0,0,new Piece("Rook", "Black"));

    }

    public Square getSquare(int x, int y) {
        return squares[y*8 + y];
    }
}

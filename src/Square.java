import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Objects;
import java.util.Stack;

class Square{
    public int x;
    public int y;
    public Piece piece;
    public boolean occupied;

    Square(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
        occupied = true;
    }

    Square(int x, int y){
        this.x = x;
        this.y = y;
        piece = null;
        occupied = false;
    }

    public int getXC(){
        return x;
    }

    public int getYC(){
        return y;
    }

    public boolean isOccupied () {
        return occupied;
    }

    public Piece getPiece(){
        return piece;
    }

    public void updatePiece (Piece newPiece){
        this.piece = newPiece;
        this.occupied = true;
    }

    public void removePiece () {
        this.piece = null;
        this.occupied = false;
    }

    public Stack<Move> getMoves(Board board, String movingColour) {

        Stack tmpMoves = new Stack();

        if(this.isOccupied() && Objects.equals(this.getPiece().getColour(), movingColour)) {

            if (Objects.equals(this.getPiece().getType(), "Knight")){
                tmpMoves = getKnightMoves(board);
            } else if (Objects.equals(this.getPiece().getType(), "Bishop")) {
                tmpMoves = getBishopMoves(board);
            } else if (Objects.equals(this.getPiece().getType(), "Pawn")) {
                if(Objects.equals(movingColour, "Black"))
                {
                    tmpMoves = getBlackPawnMoves(board);
                } else {
                    tmpMoves = getWhitePawnMoves(board);
                }
            } else if (Objects.equals(this.getPiece().getType(), "Rook")) {
                tmpMoves = getRookMoves(board);
            } else if (Objects.equals(this.getPiece().getType(), "Queen")) {
                tmpMoves = getQueenMoves(board);
            } else if (Objects.equals(this.getPiece().getType(), "King")) {
                tmpMoves = getKingMoves(board);
            }

            return tmpMoves;

        } else {
            return null;
        }
    }

    private Boolean checkSurroundingSquares(Board board, int x, int y) {
        Boolean possible = true;
        int kingsPosition = y*8+x;
        int[] kingsSurrounding;
        kingsSurrounding = new int[]{kingsPosition + 1,kingsPosition + 7,kingsPosition + 8,kingsPosition + 9,kingsPosition - 1,kingsPosition - 7,kingsPosition - 8,kingsPosition - 9};
         for (int i = 0; i<kingsSurrounding.length ;i++){
             if(kingsSurrounding[i] >= 0 &&
                     kingsSurrounding[i] < 64 &&
                     board.getSquare(kingsSurrounding[i]).isOccupied() &&
                     Objects.equals(board.getSquare(kingsSurrounding[i]).getPiece().getType(), "King") &&
                     Objects.equals(board.getSquare(kingsSurrounding[i]).getPiece().getColour(), "White")){
                 possible = false;
             }
         }

        return possible;
    }

    Stack<Move> getWhitePawnMoves(Board board) {
        Stack<Move> moves = new Stack<Move>();

        //moving 2 forward
        if (y == 1 && (!board.getSquare(x, y + 2).isOccupied()) && (!board.getSquare(x, y + 1).isOccupied())) {
            moves.push(new Move(x, y, x, y + 2,  this.piece.getColour() + this.piece.getType()));
        }
        //moving 1 forward
        if (y != 7) {  //checking if it is not the last row
            if ((!board.getSquare(x, y + 1).isOccupied())) {
                moves.push(new Move(x, y, x, y + 1, this.piece.getColour() + this.piece.getType()));
            }
            if (x != 7 && board.getSquare(x + 1, y + 1).isOccupied() && !Objects.equals(board.getSquare(x + 1, y + 1).getPiece().getColour(), this.piece.getColour())) {
                moves.push(new Move(x, y, x + 1, y + 1, this.piece.getColour() + this.piece.getType()));
            }

            if (x != 0 && board.getSquare(x - 1, y + 1).isOccupied() && !Objects.equals(board.getSquare(x - 1, y + 1).getPiece().getColour(), this.piece.getColour())) {
                moves.push(new Move(x, y, x - 1, y + 1, this.piece.getColour() + this.piece.getType()));
            }

        }
        return moves;
    }

    Stack<Move> getBlackPawnMoves(Board board) {
        Stack<Move> moves = new Stack<Move>();

        //moving 2 forward
        if (y == 6 && (!board.getSquare(x, y - 2).isOccupied()) && (!board.getSquare(x, y - 1).isOccupied())) {
            moves.push(new Move(x, y, x, y - 2,  this.piece.getColour() + this.piece.getType()));
        }
        //moving 1 forward
        if (y != 0) {  //checking if it is not the last row
            if ((!board.getSquare(x, y - 1).isOccupied())) {
                moves.push(new Move(x, y, x, y - 1, this.piece.getColour() + this.piece.getType()));
            }
            if (x != 7 && board.getSquare(x + 1, y - 1).isOccupied() && !Objects.equals(board.getSquare(x + 1, y - 1).getPiece().getColour(), this.piece.getColour())) {
                moves.push(new Move(x, y, x + 1, y - 1, this.piece.getColour() + this.piece.getType()));
            }

            if (x != 0 && board.getSquare(x - 1, y - 1).isOccupied() && !Objects.equals(board.getSquare(x - 1, y - 1).getPiece().getColour(), this.piece.getColour())) {
                moves.push(new Move(x, y, x - 1, y - 1, this.piece.getColour() + this.piece.getType()));
            }

        }
        return moves;
    }

    Stack<Move> getKingMoves(Board board) {
        Stack<Move> moves = new Stack<>();

        //moving up
        if (y != 0 && checkSurroundingSquares(board, x, y - 1)) {
            Move move = new Move(x, y, x, y - 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x, y - 1).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }
        //moving down
        if (y != 7 && checkSurroundingSquares(board, x, y + 1)) {
            Move move = new Move(x, y, x, y + 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x, y + 1).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x, y + 1).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }

        //moving left
        if (x != 0 && checkSurroundingSquares(board, x - 1, y)) {
            Move move = new Move(x, y, x - 1, y, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x - 1, y).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x - 1, y).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }

        //moving right
        if (x != 7 && checkSurroundingSquares(board, x + 1, y)) {
            Move move = new Move(x, y, x + 1, y, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x + 1, y).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x + 1, y).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }

        //moving down-right
        if (y != 7 && x != 7 && checkSurroundingSquares(board, x + 1, y + 1)) {
            Move move = new Move(x, y, x + 1, y + 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x + 1, y + 1).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x + 1, y + 1).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }

        //moving down-left
        if (y != 7 && x != 0 && checkSurroundingSquares(board, x - 1, y + 1)) {
            Move move = new Move(x, y, x - 1, y + 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x - 1, y + 1).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x - 1, y + 1).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }

        //moving up-right
        if (y != 0 && x != 7 && checkSurroundingSquares(board, x + 1, y - 1)) {
            Move move = new Move(x, y, x + 1, y - 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x + 1, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x + 1, y - 1).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }

        //moving up-left
        if (x != 0 && y != 0 && checkSurroundingSquares(board, x - 1, y - 1)) {
            Move move = new Move(x, y, x - 1, y - 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x - 1, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (!Objects.equals(board.getSquare(x - 1, y - 1).getPiece().getColour(), piece.getColour())) {
                    moves.push(move);
                }
            }
        }

        return moves;
    }

    Stack<Move> getQueenMoves(Board board) {
        Stack<Move> completeMoves = new Stack<Move>();
        Stack<Move> tmpMoves = new Stack<>();
        Move tmp;

        tmpMoves = getRookMoves(board);
        while (!tmpMoves.empty()) {
            tmp = tmpMoves.pop();
            completeMoves.push(tmp);
        }
        tmpMoves = getBishopMoves(board);
        while (!tmpMoves.empty()) {
            tmp = tmpMoves.pop();
            completeMoves.push(tmp);
        }
        return completeMoves;
    }

    Stack<Move> getRookMoves(Board board) {
        Stack<Move> moves = new Stack<Move>();

        for (int i = 1; i < 8; i++) {
            int tmpx = x + i;
            int tmpy = y;
            if ((tmpx > 7 || tmpx < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for (int j = 1; j < 8; j++) {
            int tmpx = x - j;
            int tmpy = y;
            if ((tmpx > 7 || tmpx < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for (int k = 1; k < 8; k++) {
            int tmpx = x;
            int tmpy = y + k;
            if ((tmpy > 7 || tmpy < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for (int l = 1; l < 8; l++) {
            int tmpx = x;
            int tmpy = y - l;
            if ((tmpy > 7 || tmpy < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }// end of get Rook Moves.
        return moves;
    }

    Stack<Move> getBishopMoves(Board board) {
        Stack<Move> moves = new Stack<Move>();

        for (int i = 1; i < 8; i++) {
            int tmpx = x + i;
            int tmpy = y + i;
            if ((tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }

        } // end of the first for Loop

        for (int i = 1; i < 8; i++) {
            int tmpx = x + i;
            int tmpy = y - i;
            if ((tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }
        } //end of second loop.

        for (int i = 1; i < 8; i++) {
            int tmpx = x - i;
            int tmpy = y + i;
            if ((tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }// end of the third loop

        for (int i = 1; i < 8; i++) {
            int tmpx = x - i;
            int tmpy = y - i;
            if ((tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
                break;
            } else {
                Move move = new Move(x, y, tmpx, tmpy, this.piece.getColour() + this.piece.getType());
                if (!board.getSquare(tmpx, tmpy).isOccupied()) {
                    moves.push(move);
                } else {
                    if (!Objects.equals(board.getSquare(tmpx, tmpy).getPiece().getColour(), this.piece.getColour())) {
                        moves.push(move);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }// end of the last loop
        return moves;
    }

    Stack<Move> getKnightMoves(Board board) {
        Stack<Move> moves = new Stack<Move>();
        Stack<Move> attackingMove = new Stack<Move>();
        Move m = new Move(x, y, x + 1, y + 2, this.piece.getColour() + this.piece.getType());
        moves.push(m);
        Move m2 = new Move(x, y, x + 1, y - 2, this.piece.getColour() + this.piece.getType());
        moves.push(m2);
        Move m3 = new Move(x, y, x - 1, y + 2, this.piece.getColour() + this.piece.getType());
        moves.push(m3);
        Move m4 = new Move(x, y, x - 1, y - 2, this.piece.getColour() + this.piece.getType());
        moves.push(m4);
        Move m5 = new Move(x, y, x + 2, y + 1, this.piece.getColour() + this.piece.getType());
        moves.push(m5);
        Move m6 = new Move(x, y, x + 2, y - 1, this.piece.getColour() + this.piece.getType());
        moves.push(m6);
        Move m7 = new Move(x, y, x - 2, y + 1, this.piece.getColour() + this.piece.getType());
        moves.push(m7);
        Move m8 = new Move(x, y, x - 2, y - 1, this.piece.getColour() + this.piece.getType());
        moves.push(m8);

        for (int i = 0; i < 8; i++) {
            Move tmpmove = moves.pop();
            if ((tmpmove.getLandingX() < 0) || (tmpmove.getLandingX() > 7) || (tmpmove.getLandingY()< 0) || (tmpmove.getLandingY() > 7)){

            } else if (board.getSquare(tmpmove.getLandingX(), tmpmove.getLandingY()).isOccupied()) {
                if ((!Objects.equals(board.getSquare(tmpmove.getLandingX(), tmpmove.getLandingY()).getPiece().getColour(), piece.getColour()))) {
                    attackingMove.push(tmpmove);
                }
            } else{
                attackingMove.push(tmpmove);
            }
        }
        return attackingMove;
    }

    public Square copy(){
        Square newSquare;
        if(this.isOccupied()){
            newSquare = new Square(this.getXC(), this.getYC(), this.getPiece().copy());
        } else {
            newSquare = new Square(this.getXC(), this.getYC());
        }
        return newSquare;
    }


}

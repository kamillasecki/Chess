import java.util.List;
import java.util.Objects;
import java.util.Stack;

class Square{
    public int x;
    public int y;
    public Piece piece;
    public boolean occupied;

    public Square(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
        occupied = true;
    }

    public Square(int x, int y){
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

    public void changeOccupied() {
        this.occupied = !this.occupied;
    }

    public Piece getPiece(){
        return piece;
    }

    private Boolean checkSurroundingSquares(Board board, int x, int y) {
        Boolean possible = true;
        int kingsPosition = y*8+x;
        int[] kingsSurrounding;
        kingsSurrounding = new int[]{kingsPosition + 1,kingsPosition + 7,kingsPosition + 8,kingsPosition + 9,kingsPosition - 1,kingsPosition - 7,kingsPosition - 8,kingsPosition - 9};
         for (int i = 0; i<kingsSurrounding.length ;i++){
             if(kingsSurrounding[i] >= 0 && kingsSurrounding[i] < 64 && board.getSquare(kingsSurrounding[i]).isOccupied() && Objects.equals(board.getSquare(kingsSurrounding[i]).getPiece().getType(), "King")){
                 possible = false;
             }
         }

        return possible;
    }

    Stack<Move> getPawnMoves(Board board) {
        Stack moves = new Stack();

        //moving 2 forward
        if (y == 1 && (!board.getSquare(x, y + 2).isOccupied()) && (!board.getSquare(x, y + 1).isOccupied())) {
            moves.push(new Move(x, y, x, y + 2,  this.piece.getColour() + this.piece.getType()));
        }
        //moving 1 forward
        if (y != 7) {  //checking if it is not the last row
            if ((!board.getSquare(x, y + 1).isOccupied())) {
                moves.push(new Move(x, y, x, y + 1, this.piece.getColour() + this.piece.getType()));
            }
            if (x != 7 && board.getSquare(x + 1, y + 1).isOccupied()) {
                moves.push(new Move(x, y, x + 1, y + 1, this.piece.getColour() + this.piece.getType()));
            }

            if (x != 0 && board.getSquare(x - 1, y + 1).isOccupied()) {
                moves.push(new Move(x, y, x - 1, y + 1, this.piece.getColour() + this.piece.getType()));
            }

        }
        return moves;
    }

    Stack getKingMoves(Board board) {
        Stack moves = new Stack();

        //moving up
        if (y != 0 && checkSurroundingSquares(board, x, y - 1)) {
            Move move = new Move(x, y, x, y - 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x, y - 1).getPiece().getColour() == piece.getColour()) {
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
                if (board.getSquare(x, y + 1).getPiece().getColour() == piece.getColour()) {
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
                if (board.getSquare(x - 1, y).getPiece().getColour() == piece.getColour()) {
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
                if (board.getSquare(x + 1, y).getPiece().getColour() == piece.getColour()) {
                    moves.push(move);
                }
            }
        }

        //moving up-left
        if (y != 0 && x != 0 && checkSurroundingSquares(board, x - 1, y - 1)) {
            Move move = new Move(x, y, x - 1, y - 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x - 1, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x - 1, y - 1).getPiece().getColour() == piece.getColour()) {
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
                if (board.getSquare(x - 1, y + 1).getPiece().getColour() == piece.getColour()) {
                    moves.push(move);
                }
            }
        }

        //moving up-righ
        if (y != 0 && x != 7 && checkSurroundingSquares(board, x + 1, y - 1)) {
            Move move = new Move(x, y, x + 1, y - 1, this.piece.getColour() + this.piece.getType());
            if (!board.getSquare(x + 1, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x + 1, y - 1).getPiece().getColour() == piece.getColour()) {
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
                if (board.getSquare(x - 1, y - 1).getPiece().getColour() == piece.getColour()) {
                    moves.push(move);
                }
            }
        }

        return moves;
    }

    Stack getQueenMoves(Board board) {
        Stack completeMoves = new Stack();
        Stack tmpMoves = new Stack();
        Move tmp;

        tmpMoves = getRookMoves(board);
        while (!tmpMoves.empty()) {
            tmp = (Move) tmpMoves.pop();
            completeMoves.push(tmp);
        }
        tmpMoves = getBishopMoves(board);
        while (!tmpMoves.empty()) {
            tmp = (Move) tmpMoves.pop();
            completeMoves.push(tmp);
        }
        return completeMoves;
    }


    Stack getRookMoves(Board board) {
        Stack moves = new Stack();

        for (int i = 1; i < 8; i++) {
            int tmpx = x + i;
            int tmpy = y;
            if (!(tmpx > 7 || tmpx < 0)) {
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
            break;
        }//end of the loop with x increasing and Y doing nothing...
        for (int j = 1; j < 8; j++) {
            int tmpx = x - j;
            int tmpy = y;
            if (!(tmpx > 7 || tmpx < 0)) {
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
            break;
        }//end of the loop with x increasing and Y doing nothing...
        for (int k = 1; k < 8; k++) {
            int tmpx = x;
            int tmpy = y + k;
            if (!(tmpy > 7 || tmpy < 0)) {
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
            break;
        }//end of the loop with x increasing and Y doing nothing...
        for (int l = 1; l < 8; l++) {
            int tmpx = x;
            int tmpy = y - l;
            if (!(tmpy > 7 || tmpy < 0)) {
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
            }//end of the loop with x increasing and Y doing nothing..
            break;
        }// end of get Rook Moves.
        return moves;
    }

    public Stack getBishopMoves(Board board) {
        Stack moves = new Stack();

        for (int i = 1; i < 8; i++) {
            int tmpx = x + i;
            int tmpy = y + i;
            if (!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
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

        for (int k = 1; k < 8; k++) {
            int tmpx = x + k;
            int tmpy = y - k;
            if (!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
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

        for (int l = 1; l < 8; l++) {
            int tmpx = x - l;
            int tmpy = y + l;
            if (!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
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

        for (int n = 1; n < 8; n++) {
            int tmpx = x - n;
            int tmpy = y - n;
            if (!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)) {
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



    public Stack getKnightMoves(Board board) {
        Stack moves = new Stack();
        Stack attackingMove = new Stack();
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
            Move tmpmove = (Move) moves.pop();
            if ((tmpmove.landingX < 0) || (tmpmove.landingX > 7) || (tmpmove.landingY< 0) || (tmpmove.landingY > 7)){

            } else if (board.getSquare(tmpmove.landingX, tmpmove.landingY).isOccupied()) {
                if ((!Objects.equals(board.getSquare(tmpmove.landingX, tmpmove.landingY).getPiece().getColour(), piece.getColour()))) {
                    attackingMove.push(tmpmove);
                }
            } else{
                attackingMove.push(tmpmove);
            }
        }
        return attackingMove;
    }


}

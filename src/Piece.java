import java.util.Stack;

class Piece {
    protected String colour;
    protected String type;
    protected int value;

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Piece(String type, String colour) {
        this.colour = colour;
        this.type = type;
        switch (type) {
            case "Pawn" : value = 1;
            break;
            case "Rook" : value = 5;
                break;
            case "Bishup" : value = 3;
                break;
            case "King" : value = 9;
                break;
            case "Queen" : value = 8;
                break;
            case "Knight" : value = 3;
                break;
        }
    }

    private Boolean checkSurroundingSquares(Board board, int x , int y){
        Boolean possible = false;
        if(
                board.getSquare(x+1,y).getPiece().type != "King" ||
                        board.getSquare(x-1,y).getPiece().type != "King" ||
                        board.getSquare(x+1,y).getPiece().type != "King" ||
                        board.getSquare(x,y+1).getPiece().type != "King" ||
                        board.getSquare(x,y-1).getPiece().type != "King" ||
                        board.getSquare(x+1,y-1).getPiece().type != "King" ||
                        board.getSquare(x+1,y+1).getPiece().type != "King" ||
                        board.getSquare(x-1,y-1).getPiece().type != "King" ||
                        board.getSquare(x-1,y+1).getPiece().type != "King"
                        ){
            possible = true;
        }
        return possible;
    }

    public Stack getPawnMoves(Board board, int x, int y) {
        Stack moves = new Stack();

        //moving 2 forward
        if (y == 1 && (!board.getSquare(x,y+2).isOccupied()) && (!board.getSquare(x,y+1).isOccupied())) {
            moves.push(new Move(x,y,x,y+2);
        }
        //moving 1 forward
        if(y!=7) {  //checking if it is not the last row
            if ((!board.getSquare(x,y+1).isOccupied())) {
                moves.push(new Move(x,y,x,y+1);
            }
            if (x!=7 && board.getSquare(x+1,y+1).isOccupied()){
                moves.push(new Move(x,y,x+1,y+1);
            }

            if (x!=0 && board.getSquare(x-1,y+1).isOccupied()){
                moves.push(new Move(x,y,x-1,y+1);
            }

        }
        return moves;
    }

    public Stack getKingMoves(Board board, int x, int y) {
        Stack moves = new Stack();

        //moving up
        if (y != 0 && checkSurroundingSquares(board, x, y - 1)) {
            Move move = new Move(x, y, x, y - 1);
            if (!board.getSquare(x, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x, y - 1).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }
        //moving down
        if (y != 7 && checkSurroundingSquares(board, x, y + 1)) {
            Move move = new Move(x, y, x, y + 1);
            if (!board.getSquare(x, y + 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x, y + 1).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }

        //moving left
        if (x != 0 && checkSurroundingSquares(board, x - 1, y)) {
            Move move = new Move(x, y, x - 1, y);
            if (!board.getSquare(x - 1, y).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x - 1, y).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }

        //moving right
        if (x != 7 && checkSurroundingSquares(board, x + 1, y)) {
            Move move = new Move(x, y, x + 1, y);
            if (!board.getSquare(x + 1, y).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x + 1, y).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }

        //moving up-left
        if (y != 0 && x != 0 && checkSurroundingSquares(board, x - 1, y - 1)) {
            Move move = new Move(x, y, x - 1, y - 1);
            if (!board.getSquare(x - 1, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x - 1, y - 1).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }

        //moving down-left
        if (y != 7 && x != 0 && checkSurroundingSquares(board, x - 1, y + 1)) {
            Move move = new Move(x, y, x - 1, y + 1);
            if (!board.getSquare(x - 1, y + 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x - 1y + 1).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }

        //moving up-righ
        if (y != 0 && x != 7 && checkSurroundingSquares(board, x + 1, y - 1)) {
            Move move = new Move(x, y, x + 1, y - 1);
            if (!board.getSquare(x + 1, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x + 1, y - 1).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }

        //moving up-left
        if (x != 0 && y != 0 && checkSurroundingSquares(board, x - 1, y - 1)) {
            Move move = new Move(x, y, x - 1, y - 1);
            if (!board.getSquare(x - 1, y - 1).isOccupied()) {
                moves.push(move);
            } else {
                if (board.getSquare(x - 1, y - 1).getPiece().getColour() == colour) {
                    moves.push(move);
                }
            }
        }

        return moves;
    }

    public Stack getQueenMoves(Board board, int x, int y) {
        Stack completeMoves = new Stack();
        Stack tmpMoves = new Stack();
        Move tmp;

        tmpMoves = getRookMoves(board, x, y);
        while(!tmpMoves.empty()){
            tmp = (Move)tmpMoves.pop();
            completeMoves.push(tmp);
        }
        tmpMoves = getBishopMoves(board, x, y);
        while(!tmpMoves.empty()){
            tmp = (Move)tmpMoves.pop();
            completeMoves.push(tmp);
        }
        return completeMoves;
    }

    /*
      Method to return all the squares that a Rook can move to. The Rook can either move in an x direction or
      in a y direction as long as there is nothing in the way and it can take its opponents piece but not its
      own piece. As seen in the below grid the Rook can either move in a horizontal direction (x changing value)
      or in a vertical movement (y changing direction)

                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
                                 |             |           |(x, y-N) |           |           |
                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
                                 |             |           |(x, y-2) |           |           |
                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
                                 |             |           |(x, y-1) |           |           |
                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
                                 | (x-N, y)    |(x-1, y)   | (x, y)  |(x+1, y)   |(x+N, y)   |
                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
                                 |             |           | (x, y+1)|           |           |
                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
                                 |             |           |(x, y+2) |           |           |
                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
                                 |             |           |(x, y+N) |           |           |
                                _|_____________|___________|_________|___________|___________|_
                                 |             |           |         |           |           |
    */
    private Stack getRookMoves(Board board, int x, int y) {
        Stack moves = new Stack();
        Move validM, validM2, validM3, validM4;
  /*
    There are four possible directions that the Rook can move to:
      - the x value is increasing
      - the x value is decreasing
      - the y value is increasing
      - the y value is decreasing

    Each of these movements should be catered for. The loop guard is set to incriment up to the maximun number of squares.
    On each iteration of the first loop we are adding the value of i to the current x coordinate.
    We make sure that the new potential square is going to be on the Board and if it is we create a new square and a new potential
    move (originating square, new square).If there are no pieces present on the potential square we simply add it to the Stack
    of potential moves.
    If there is a piece on the square we need to check if its an opponent piece. If it is an opponent piece its a valid move, but we
    must break out of the loop using the Java break keyword as we can't jump over the piece and search for squares. If its not
    an opponent piece we simply break out of the loop.

    This cycle needs to happen four times for each of the possible directions of the Rook.
  */
        for(int i=1;i < 8;i++){
            int tmpx = x+i;
            int tmpy = y;
            if(!(tmpx > 7 || tmpx < 0)){
                Move move = new Move(x,y,tmpx,tmpy);
                if(!board.getSquare(tmpx,tmpy).isOccupied()){
                    moves.push(move);
                }
                else{
                    if(board.getSquare(tmpx,tmpy).getPiece().getColour() != colour){
                        moves.push(move);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int j=1;j < 8;j++){
            int tmpx = x-j;
            int tmpy = y;
            if(!(tmpx > 7 || tmpx < 0)){
                Move move = new Move(x,y,tmpx,tmpy);
                if(!board.getSquare(tmpx,tmpy).isOccupied()){
                    moves.push(move);
                }
                else{
                    if(board.getSquare(tmpx,tmpy).getPiece().getColour() != colour){
                        moves.push(move);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int k=1;k < 8;k++){
            int tmpx = x;
            int tmpy = y+k;
            if(!(tmpy > 7 || tmpy < 0)){
                Move move = new Move(x,y,tmpx,tmpy);
                if(!board.getSquare(tmpx,tmpy).isOccupied()){
                    moves.push(move);
                }
                else{
                    if(board.getSquare(tmpx,tmpy).getPiece().getColour() != colour){
                        moves.push(move);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int l=1;l < 8;l++){
            int tmpx = x;
            int tmpy = y-l;
            if(!(tmpy > 7 || tmpy < 0)){
                Move move = new Move(x,y,tmpx,tmpy);
                if(!board.getSquare(tmpx,tmpy).isOccupied()){
                    moves.push(move);
                }
                else{
                    if(board.getSquare(tmpx,tmpy).getPiece().getColour() != colour){
                        moves.push(move);
                        break;
                    }
                    else{
                        break;
                    }
            }
        }//end of the loop with x increasing and Y doing nothing...
        return moves;
    }// end of get Rook Moves.

    /*
      Method to return all the squares that a Bishop can move to. As seen in the below grid, the Bishop
      can move in a diagonal moement. There are essentially four different directions from a single
      square that the Bishop can move along. The Bishop can move any distance along this diagonal
      as long as there is nothing in the way. The Bishop can also take an opponent piece but cannot take its
      own piece.


                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |
                                   |             |           |         |           |           |
                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |
                                   | (x-N, y-N)  |           |         |           |(x+N, y-N) |
                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |
                                   |             | (x-1, y-1)|         | (x+1, y-1)|           |
                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |
                                   |             |           | (x, y)  |           |           |
                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |
                                   |             |(x-1, y+1) |         | (x+1, y+1)|           |
                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |
                                   |(x-N, y+N)   |           |         |           |(x+N, y+N) |
                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |
                                   |             |           |         |           |           |
                                  _|_____________|___________|_________|___________|___________|_
                                   |             |           |         |           |           |

    */
    private Stack getBishopMoves(Board board, int x, int y) {
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Move validM, validM2, validM3, validM4;
  /*
    The Bishop can move along any diagonal until it hits an enemy piece or its own piece
    it cannot jump over its own piece. We need to use four different loops to go through the possible movements
    to identify possible squares to move to. The temporary squares, i.e. the values of x and y must change by the
    same amount on each iteration of each of the loops.

    If the new values of x and y are on the Board, we create a new square and a new move (from the original square to the new
    square). We then check if there is a piece present on the new square:
    - if not we add the move as a possible new move
    - if there is a piece we make sure that we can capture our opponents piece and we cannot take our own piece
      and then we break out of the loop

    This process is repeated for each of the other three possible diagonals that the Bishop can travel along.

  */
        for(int i=1;i < 8;i++){
            int tmpx = x+i;
            int tmpy = y+i;
            if(!(tmpx > 7 || tmpx < 0 || tmpy > 7 || tmpy < 0)){
                Square tmp = new Square(tmpx, tmpy, piece);
                validM = new Move(startingSquare, tmp);
                if(!piecePresent(tmp.getXC(),tmp.getYC())){
                    moves.push(validM);
                }
                else{
                    if(checkWhiteOponent(tmp.getXC(), tmp.getYC())){
                        moves.push(validM);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        } // end of the first for Loop
        for(int k=1;k < 8;k++){
            int tmpk = x+k;
            int tmpy2 = y-k;
            if(!(tmpk > 7 || tmpk < 0 || tmpy2 > 7 || tmpy2 < 0)){
                Square tmpK1 = new Square(tmpk, tmpy2, piece);
                validM2 = new Move(startingSquare, tmpK1);
                if(!piecePresent(tmpK1.getXC(),tmpK1.getYC())){
                    moves.push(validM2);
                }
                else{
                    if(checkWhiteOponent(tmpK1.getXC(), tmpK1.getYC())){
                        moves.push(validM2);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        } //end of second loop.
        for(int l=1;l < 8;l++){
            int tmpL2 = x-l;
            int tmpy3 = y+l;
            if(!(tmpL2 > 7 || tmpL2 < 0 || tmpy3 > 7 || tmpy3 < 0)){
                Square tmpLMov2 = new Square(tmpL2, tmpy3, piece);
                validM3 = new Move(startingSquare, tmpLMov2);
                if(!piecePresent(tmpLMov2.getXC(),tmpLMov2.getYC())){
                    moves.push(validM3);
                }
                else{
                    if(checkWhiteOponent(tmpLMov2.getXC(), tmpLMov2.getYC())){
                        moves.push(validM3);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }// end of the third loop
        for(int n=1;n < 8;n++){
            int tmpN2 = x-n;
            int tmpy4 = y-n;
            if(!(tmpN2 > 7 || tmpN2 < 0 || tmpy4 > 7 || tmpy4 < 0)){
                Square tmpNmov2 = new Square(tmpN2, tmpy4, piece);
                validM4 = new Move(startingSquare, tmpNmov2);
                if(!piecePresent(tmpNmov2.getXC(),tmpNmov2.getYC())){
                    moves.push(validM4);
                }
                else{
                    if(checkWhiteOponent(tmpNmov2.getXC(), tmpNmov2.getYC())){
                        moves.push(validM4);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }// end of the last loop
        return moves;
    }


    /*
        Method fo return all the squares that a Knight can attack. The knight is possibly the simplest piece
        to get possible movements from. The Knight can essentially move in an L direction from any square on the
        Board as long as the landing square is on the Board and we can take an opponents piece but not our own piece.
    */
    private Stack getKnightMoves(Board board, int x, int y) {
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Stack attackingMove = new Stack();
        Square s = new Square(x+1, y+2, piece);
        moves.push(s);
        Square s1 = new Square(x+1, y-2, piece);
        moves.push(s1);
        Square s2 = new Square(x-1, y+2, piece);
        moves.push(s2);
        Square s3 = new Square(x-1, y-2, piece);
        moves.push(s3);
        Square s4 = new Square(x+2, y+1, piece);
        moves.push(s4);
        Square s5 = new Square(x+2, y-1, piece);
        moves.push(s5);
        Square s6 = new Square(x-2, y+1, piece);
        moves.push(s6);
        Square s7 = new Square(x-2, y-1, piece);
        moves.push(s7);

        for(int i=0;i < 8;i++){
            Square tmp = (Square)moves.pop();
            Move tmpmove = new Move(startingSquare, tmp);
            if((tmp.getXC() < 0)||(tmp.getXC() > 7)||(tmp.getYC() < 0)||(tmp.getYC() > 7)){

            }
            else if(piecePresent(tmp.getXC(),tmp.getYC())){
                if(piece.contains("White")){
                    if(checkWhiteOponent(tmp.getXC(), tmp.getYC())){
                        attackingMove.push(tmpmove);
                    }
                }
            }
            else{
                attackingMove.push(tmpmove);
            }
        }
        return attackingMove;
    }

        return moves;
    }
}

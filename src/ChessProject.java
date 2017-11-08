import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Stack;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;

/*
	This class can be used as a starting point for creating your Chess game project. The only piece that
	has been coded is a white pawn...a lot done, more to do!
*/

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
    int startX;
    int startY;
    int initialX;
    int initialY;
    JPanel panels;
    JLabel pieces;
    Boolean win;
    String winner;
    Boolean white2Move;
    int moveCounter;
    AIAgent agent;
    Boolean agentwins;
    Stack temporary;
    boolean end;
    private int whiteKingX = 3;
    private int whiteKingY = 0;
    private int blackKingX = 3;
    private int blackKingY = 7;


    public ChessProject(){
        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, 0);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( square );

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray );
            else
                square.setBackground( i % 2 == 0 ? Color.gray : Color.white );
        }

        // Setting up the Initial Chess board.

        for (int i = 8; i < 16; i++) {
            pieces = new JLabel(new ImageIcon("WhitePawn.png"));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }
        pieces = new JLabel(new ImageIcon("WhiteRook.png"));
        panels = (JPanel) chessBoard.getComponent(0);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
        panels = (JPanel) chessBoard.getComponent(1);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
        panels = (JPanel) chessBoard.getComponent(6);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
        panels = (JPanel) chessBoard.getComponent(2);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
        panels = (JPanel) chessBoard.getComponent(5);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteKing.png"));
        panels = (JPanel) chessBoard.getComponent(3);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
        panels = (JPanel) chessBoard.getComponent(4);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("WhiteRook.png"));
        panels = (JPanel) chessBoard.getComponent(7);
        panels.add(pieces);
        for (int i = 48; i < 56; i++) {
            pieces = new JLabel(new ImageIcon("BlackPawn.png"));
            panels = (JPanel) chessBoard.getComponent(i);
            panels.add(pieces);
        }
        pieces = new JLabel(new ImageIcon("BlackRook.png"));
        panels = (JPanel) chessBoard.getComponent(56);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackKnight.png"));
        panels = (JPanel) chessBoard.getComponent(57);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackKnight.png"));
        panels = (JPanel) chessBoard.getComponent(62);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackBishup.png"));
        panels = (JPanel) chessBoard.getComponent(58);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackBishup.png"));
        panels = (JPanel) chessBoard.getComponent(61);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackKing.png"));
        panels = (JPanel) chessBoard.getComponent(59);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackQueen.png"));
        panels = (JPanel) chessBoard.getComponent(60);
        panels.add(pieces);
        pieces = new JLabel(new ImageIcon("BlackRook.png"));
        panels = (JPanel) chessBoard.getComponent(63);
        panels.add(pieces);

        moveCounter =0;
        white2Move = true;
        agent = new AIAgent();
        agentwins = false;
        temporary = new Stack();
    }

    private Stack getWhitePawnSquares(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Square dest1 = new Square(x, y+1, piece);
        Square dest2 = new Square(x, y+2, piece);
        Square dest3 = new Square(x+1, y+1, piece);
        Square dest4 = new Square(x-1, y+1, piece);

                if (y == 1 && (!piecePresent(x, y+2)) && (!piecePresent(x, y+1))) {
                    moves.push(new Move(startingSquare,dest2));
                }

                if (y!=7) {
                    if (!piecePresent(x, y+1)) {
                        moves.push(new Move(startingSquare,dest1));
                    }

                    if (x!=7 && piecePresent(x+1,y+1) && checkWhiteOponent(x+1,y+1)){
                        moves.push(new Move(startingSquare,dest3));
                    }

                    if (x!=0 && piecePresent(x-1,y+1) && checkWhiteOponent(x-1,y+1)){
                        moves.push(new Move(startingSquare,dest4));
                    }
                }
        return moves;
    }


    private Boolean checkSurroundingSquares(Square s){
        Boolean possible = false;
        int x = s.getXC()*75;
        int y = s.getYC()*75;
        if(!((getPieceName((x+75), y).contains("BlackKing"))||(getPieceName((x-75), y).contains("BlackKing"))||(getPieceName(x,(y+75)).contains("BlackKing"))||(getPieceName((x), (y-75)).contains("BlackKing"))||(getPieceName((x+75),(y+75)).contains("BlackKing"))||(getPieceName((x-75),(y+75)).contains("BlackKing"))||(getPieceName((x+75),(y-75)).contains("BlackKing"))||(getPieceName((x-75), (y-75)).contains("BlackKing")))){
            possible = true;
        }
        return possible;
    }


    /*
      The getKingSquares method takes as an input any coordinates from a square and returns a stack of all the possible
      valid moves that the WhiteKing can move to.

      So lets consider how the King can move. The King can essentially move in any direction as long as there is not another
      king in an adajacent square to were the king lands. Additionally, the King can only move one square at a time.

      To support this method we will also create a helper method called checkSurroundingSquares(Square s){, see above
      that returns a Boolean value to let us know if a supplied square will be adajacent to another square with a
      BlackKing present. Essentially if we consider that the board is a set of squares with coordinates for each square (x, y), this
      allows us to identify the possible squares that we should be investigating, see below;

                                           _|___________|_________|___________|_
                                            |           |         |           |
                                            |(x-1, y-1) |(x, y-1) |(x+1, y-1) |
                                           _|___________|_________|___________|_
                                            |           |         |           |
                                            |(x-1, y)   | (x, y)  |(x+1, y)   |
                                           _|___________|_________|___________|_
                                            |           |         |           |
                                            |(x-1, y+1) | (x, y+1)|(x+1, y+1) |
                                           _|___________|_________|___________|_
                                            |           |         |           |


      This shows us that for a single square with coordinates of (x, y) we need to check eight other potential squares.
      Remember we only need to check squares and contsruct moves if the movement (Piece on a Square --> a new Square) is
      going to be placing the piece back on the board, if we are not taking our own piece and if the resulting landing square
      is not adjacent to the enemy King.
    */
    private Stack getKingSquares(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();

        int goRight = x+1;
        int goLeft = x-1;
        int goDown = y+1;
        int goUp = y-1;

/*
  If we consider the grid above, we can create three different columes to check.
    - if x increases by one square, using the variable goRight (x+1)
    - if x decreases by one square, using the variable goLeft (x-1)
    - or if x stays the same.
*/
        Move move;
        Square up = new Square(x, goUp, piece);
        Square down = new Square(x, goDown, piece);
        Square left = new Square(goLeft, y, piece);
        Square right = new Square(goRight, y, piece);
        Square leftUp = new Square(goLeft, goUp, piece);
        Square leftDown = new Square(goLeft, goDown, piece);
        Square rightUp = new Square(goRight, goUp, piece);
        Square rightDown = new Square(goRight, goDown, piece);

        if(y!=0 && checkSurroundingSquares(up)){
            move = new Move(startingSquare, up);
            if (!piecePresent(x,goUp)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(x,goUp)){
                    moves.push(move);
                }
            }
        }

        if(y!=7 && checkSurroundingSquares(down)){
            move = new Move(startingSquare, down);
            if (!piecePresent(x,goDown)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(x,goDown)){
                    moves.push(move);
                }
            }
        }

        if(x!=0 && checkSurroundingSquares(left)){
            move = new Move(startingSquare, left);
            if (!piecePresent(goLeft, y)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(goLeft, y)){
                    moves.push(move);
                }
            }
        }

        if(x!=7 && checkSurroundingSquares(right)){
            move = new Move(startingSquare, right);
            if (!piecePresent(goRight,y)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(goRight,y)){
                    moves.push(move);
                }
            }
        }

        if(y!=0 && x!=0 && checkSurroundingSquares(leftUp)){
            move = new Move(startingSquare, leftUp);
            if (!piecePresent(goLeft,goUp)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(goLeft,goUp)){
                    moves.push(move);
                }
            }
        }

        if(y!=7 && x!=0 && checkSurroundingSquares(leftDown)){
            move = new Move(startingSquare, leftDown);
            if (!piecePresent(goLeft,goDown)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(goLeft,goDown)){
                    moves.push(move);
                }
            }
        }

        if(y!=0 && x!=7 && checkSurroundingSquares(rightUp)){
            move = new Move(startingSquare, rightUp);
            if (!piecePresent(goRight,goUp)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(goRight,goUp)){
                    moves.push(move);
                }
            }
        }

        if(y!=7 && x!=7 && checkSurroundingSquares(rightDown)){
            move = new Move(startingSquare, rightDown);
            if (!piecePresent(goRight,goDown)){
                moves.push(move);
            } else {
                if (checkWhiteOponent(goRight,goDown)){
                    moves.push(move);
                }
            }
        }


        return moves;
    }


    /*
        Method to return all the possible moves that a Queen can make
    */
    private Stack getQueenMoves(int x, int y, String piece){
        Stack completeMoves = new Stack();
        Stack tmpMoves = new Stack();
        Move tmp;
  /*
      The Queen is a pretty easy piece to figure out if you have completed the
      Bishop and the Rook movements. Either the Queen is going to move like a
      Bishop or its going to move like a Rook, so all we have to do is make a call to both of these
      methods.
  */
        tmpMoves = getRookMoves(x, y, piece);
        while(!tmpMoves.empty()){
            tmp = (Move)tmpMoves.pop();
            completeMoves.push(tmp);
        }
        tmpMoves = getBishopMoves(x, y, piece);
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
    private Stack getRookMoves(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
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
    We make sure that the new potential square is going to be on the board and if it is we create a new square and a new potential
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
        }//end of the loop with x increasing and Y doing nothing...
        for(int j=1;j < 8;j++){
            int tmpx1 = x-j;
            int tmpy1 = y;
            if(!(tmpx1 > 7 || tmpx1 < 0)){
                Square tmp2 = new Square(tmpx1, tmpy1, piece);
                validM2 = new Move(startingSquare, tmp2);
                if(!piecePresent(tmp2.getXC(),tmp2.getYC())){
                    moves.push(validM2);
                }
                else{
                    if(checkWhiteOponent(tmp2.getXC(), tmp2.getYC())){
                        moves.push(validM2);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int k=1;k < 8;k++){
            int tmpx3 = x;
            int tmpy3 = y+k;
            if(!(tmpy3 > 7 || tmpy3 < 0)){
                Square tmp3 = new Square(tmpx3, tmpy3, piece);
                validM3 = new Move(startingSquare, tmp3);
                if(!piecePresent(tmp3.getXC(),tmp3.getYC())){
                    moves.push(validM3);
                }
                else{
                    if(checkWhiteOponent(tmp3.getXC(), tmp3.getYC())){
                        moves.push(validM3);
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }//end of the loop with x increasing and Y doing nothing...
        for(int l=1;l < 8;l++){
            int tmpx4 = x;
            int tmpy4 = y-l;
            if(!(tmpy4 > 7 || tmpy4 < 0)){
                Square tmp4 = new Square(tmpx4, tmpy4, piece);
                validM4 = new Move(startingSquare, tmp4);
                if(!piecePresent(tmp4.getXC(),tmp4.getYC())){
                    moves.push(validM4);
                }
                else{
                    if(checkWhiteOponent(tmp4.getXC(), tmp4.getYC())){
                        moves.push(validM4);
                        break;
                    }
                    else{
                        break;
                    }
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
    private Stack getBishopMoves(int x, int y, String piece){
        Square startingSquare = new Square(x, y, piece);
        Stack moves = new Stack();
        Move validM, validM2, validM3, validM4;
  /*
    The Bishop can move along any diagonal until it hits an enemy piece or its own piece
    it cannot jump over its own piece. We need to use four different loops to go through the possible movements
    to identify possible squares to move to. The temporary squares, i.e. the values of x and y must change by the
    same amount on each iteration of each of the loops.

    If the new values of x and y are on the board, we create a new square and a new move (from the original square to the new
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
        board as long as the landing square is on the board and we can take an opponents piece but not our own piece.
    */
    private Stack getKnightMoves(int x, int y, String piece){
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


    /*
            Method to colour a stack of Squares
    */
    private void colorSquares(Stack squares){
        Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 3);
        while(!squares.empty()){
            Square s = (Square)squares.pop();
            int location = s.getXC() + ((s.getYC())*8);
            JPanel panel = (JPanel)chessBoard.getComponent(location);
            panel.setBorder(greenBorder);
        }
    }

    /*
        Method to get the landing square of a bunch of moves...
    */
    private void getLandingSquares(Stack found){
        Move tmp;
        Square landing;
        Stack squares = new Stack();
        while(!found.empty()){
            tmp = (Move)found.pop();
            landing = (Square)tmp.getLanding();
            squares.push(landing);
        }
        colorSquares(squares);
    }


    /*
      Method to find all the White Pieces.
    */
    private Stack findWhitePieces(){
        Stack squares = new Stack();
        String icon;
        int x;
        int y;
        String pieceName;
        for(int i=0;i < 600;i+=75){
            for(int j=0;j < 600;j+=75){
                y = i/75;
                x=j/75;
                Component tmp = chessBoard.findComponentAt(j, i);
                if(tmp instanceof JLabel){
                    chessPiece = (JLabel)tmp;
                    icon = chessPiece.getIcon().toString();
                    pieceName = icon.substring(0, (icon.length()-4));
                    if(pieceName.contains("White")){
                        Square stmp = new Square(x, y, pieceName);
                        squares.push(stmp);
                    }
                }
            }
        }
        return squares;
    }

    /*
        This method checks if there is a piece present on a particular square.
    */
    private Boolean piecePresent(int x, int y) {
        Component c = chessBoard.findComponentAt(x * 75, y * 75);
        if(c instanceof JPanel){
            if( ((JPanel) c).getComponentCount() == 1) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return true;
        }
    }

    /*
        This is a method to check if a piece is a Black piece.
    */
    private Boolean checkOpponent(int newX, int newY, String moving) {
        Boolean opponent;
        Component c1 = chessBoard.findComponentAt(newX * 75, newY * 75);
        JLabel awaitingPiece;
        if (c1 instanceof JPanel){
            awaitingPiece = (JLabel) ((JPanel) c1).getComponent(0);
        } else {
            awaitingPiece = (JLabel) c1;
        }
        String tmp1 = awaitingPiece.getIcon().toString();
        opponent = !(tmp1.contains(moving));
        if (opponent && tmp1.contains("King")) {
            JOptionPane.showMessageDialog(null, moving + " won!!");
            end = true;
        }
        return opponent;
    }

    private Boolean checkWhiteOponent(int newX, int newY){
        Boolean oponent;
        Component c1 = chessBoard.findComponentAt(newX*75, newY*75);
        JLabel awaitingPiece = (JLabel)c1;
        String tmp1 = awaitingPiece.getIcon().toString();
        if(((tmp1.contains("Black")))){
            oponent = true;
        }
        else{
            oponent = false;
        }
        return oponent;
    }


    private void resetBorders(){
        Border empty = BorderFactory.createEmptyBorder();
        for(int i=0;i < 64;i++){
            JPanel tmppanel = (JPanel)chessBoard.getComponent(i);
            tmppanel.setBorder(empty);
        }
    }

    /*
      The method printStack takes in a Stack of Moves and prints out all possible moves.
    */
    private void printStack(Stack input){
        Move m;
        Square s, l;
        while(!input.empty()){
            m = (Move)input.pop();
            s = (Square)m.getStart();
            l = (Square)m.getLanding();
            System.out.println("The possible move that was found is : ("+s.getXC()+" , "+s.getYC()+"), landing at ("+l.getXC()+" , "+l.getYC()+")");
        }
    }

    private void makeAIMove(){
    /*
      When the AI Agent decides on a move, a red border shows the square from where the move started and the
      landing square of the move.
    */
        resetBorders();
        layeredPane.validate();
        layeredPane.repaint();
        Stack white = findWhitePieces();
        Stack completeMoves = new Stack();
        Move tmp;
        while(!white.empty()){
            Square s = (Square)white.pop();
            String tmpString = s.getName();
            Stack tmpMoves = new Stack();
            Stack temporary = new Stack();
      /*
          We need to identify all the possible moves that can be made by the AI Opponent
      */
            if(tmpString.contains("Knight")){
                tmpMoves = getKnightMoves(s.getXC(), s.getYC(), s.getName());
            }
            else if(tmpString.contains("Bishop")){
                tmpMoves = getBishopMoves(s.getXC(), s.getYC(), s.getName());
            }
            else if(tmpString.contains("Pawn")){
                tmpMoves = getWhitePawnSquares(s.getXC(), s.getYC(), s.getName());
            }
            else if(tmpString.contains("Rook")){
                tmpMoves = getRookMoves(s.getXC(), s.getYC(), s.getName());
            }
            else if(tmpString.contains("Queen")){
                tmpMoves = getQueenMoves(s.getXC(), s.getYC(), s.getName());
            }
            else if(tmpString.contains("King")){
                tmpMoves = getKingSquares(s.getXC(), s.getYC(), s.getName());
            }

            while(!tmpMoves.empty()){
                tmp = (Move)tmpMoves.pop();
                completeMoves.push(tmp);
            }
        }
        temporary = (Stack)completeMoves.clone();
        getLandingSquares(temporary);
        printStack(temporary);
/*
  So now we should have a copy of all the possible moves to make in our Stack called completeMoves
*/
//        if(completeMoves.size() == 0){
///*
//      In Chess if you cannot make a valid move but you are not in Check this state is referred to
//      as a Stale Mate
//*/
//            JOptionPane.showMessageDialog(null, "Cogratulations, you have placed the AI component in a Stale Mate Position");
//            System.exit(0);
//
//        }
//        else{
    /*
      Okay, so we can make a move now. We have a stack of all possible moves and need to call the correct agent to select
      one of these moves. Lets print out the possible moves to the standard output to view what the options are for
      White. Later when you are finished the continuous assessment you don't need to have such information being printed
      out to the standard output.
    */
            System.out.println("=============================================================");
            Stack testing = new Stack();
            while(!completeMoves.empty()){
                Move tmpMove = (Move)completeMoves.pop();
                Square s1 = (Square)tmpMove.getStart();
                Square s2 = (Square)tmpMove.getLanding();
                System.out.println("The "+s1.getName()+" can move from ("+s1.getXC()+", "+s1.getYC()+") to the following square: ("+s2.getXC()+", "+s2.getYC()+")");
                testing.push(tmpMove);
            }
            System.out.println("=============================================================");
            Border redBorder = BorderFactory.createLineBorder(Color.RED, 3);
            Move selectedMove = agent.randomMove(testing);
            Square startingPoint = (Square)selectedMove.getStart();
            Square landingPoint = (Square)selectedMove.getLanding();
            int startX1 = (startingPoint.getXC()*75)+20;
            int startY1 = (startingPoint.getYC()*75)+20;
            int landingX1 = (landingPoint.getXC()*75)+20;
            int landingY1 = (landingPoint.getYC()*75)+20;
            System.out.println("-------- Move "+startingPoint.getName()+" ("+startingPoint.getXC()+", "+startingPoint.getYC()+") to ("+landingPoint.getXC()+", "+landingPoint.getYC()+")");

        Component c  = (JLabel)chessBoard.findComponentAt(startX1, startY1);
        Container parent = c.getParent();
        parent.remove(c);
        int panelID = (startingPoint.getYC() * 8)+startingPoint.getXC();
        panels = (JPanel)chessBoard.getComponent(panelID);
        panels.setBorder(redBorder);
        parent.validate();

        Component l = chessBoard.findComponentAt(landingX1, landingY1);
        if(l instanceof JLabel){
            Container parentlanding = l.getParent();
            JLabel awaitingName = (JLabel)l;
            String agentCaptured = awaitingName.getIcon().toString();
            if(agentCaptured.contains("King")){
                agentwins = true;
            }
            parentlanding.remove(l);
            parentlanding.validate();
            pieces = new JLabel( new ImageIcon(startingPoint.getName()+".png") );
            int landingPanelID = (landingPoint.getYC()*8)+landingPoint.getXC();
            panels = (JPanel)chessBoard.getComponent(landingPanelID);
            panels.add(pieces);
            panels.setBorder(redBorder);
            layeredPane.validate();
            layeredPane.repaint();

            if(agentwins){
                JOptionPane.showMessageDialog(null, "The AI Agent has won!");
                System.exit(0);
            }
        }
        else{
            pieces = new JLabel( new ImageIcon(startingPoint.getName()+".png") );
            int landingPanelID = (landingPoint.getYC()*8)+landingPoint.getXC();
            panels = (JPanel)chessBoard.getComponent(landingPanelID);
            panels.add(pieces);
            panels.setBorder(redBorder);
            layeredPane.validate();
            layeredPane.repaint();
        }
        white2Move = false;
   // }
}

    /*
      This is a method to check if a piece is a Black piece.
    */


    private String getPieceName(int x, int y){
        Component c1 = chessBoard.findComponentAt(x, y);
        if(c1 instanceof JPanel){
            return "empty";
        }
        else if(c1 instanceof JLabel){
            JLabel awaitingPiece = (JLabel)c1;
            String tmp1 = awaitingPiece.getIcon().toString();
            return tmp1;
        }
        else{
            return "empty";
        }
    }

    private Boolean checkField(int x, int y, String moving) {
        boolean validMove;
        if (piecePresent(x, y)) {
            validMove = checkOpponent(x, y, moving);
        } else {
            validMove = true;
        }
        return validMove;
    }

    private Boolean pieceMove(int x, int y){
        if((startX == x)&&(startY == y)){
            return false;
        }
        else{
            return true;
        }
    }
    private Boolean moveLikeRook(int movementX, int movementY, int landingX, int landingY, String movingColour) {
        boolean validMove;
        if ((movementX == 0 && movementY != 0) || (movementY == 0 && movementX != 0)) {
            boolean pieceFound = false;
            int vector;
            if (movementX == 0) {
                if (movementY > 0) {
                    vector = 1;
                } else {
                    vector = -1;
                }

                for (int i = 1; i < (Math.abs(movementY)); i++) {
                    if (piecePresent(landingX, landingY - (i * vector))) {
                        pieceFound = true;
                    }
                }
            } else {
                if (movementX > 0) {
                    vector = 1;
                } else {
                    vector = -1;
                }

                for (int i = 1; i < (Math.abs(movementX)); i++) {
                    if (piecePresent(landingX - (i * vector), landingY)) {
                        pieceFound = true;
                    }
                }
            }

            if (pieceFound) {
                validMove = false;
            } else {
                validMove = checkField(landingX, landingY, movingColour);
            }
        } else {
            validMove = false;
        }
        return validMove;
    }

    private Boolean moveLikeBishop(int movementX, int movementY, int landingX, int landingY, String movingColour) {
        boolean validMove;
        if (Math.abs(movementX) == Math.abs(movementY)) {
            boolean pieceFound = false;
            int vectorX;
            int vectorY;
            if (movementY > 0) {
                vectorY = 1;
            } else {
                vectorY = -1;
            }
            if (movementX > 0) {
                vectorX = 1;
            } else {
                vectorX = -1;
            }

            for (int i = 1; i < (Math.abs(movementY)); i++) {
                if (piecePresent(landingX - (i * vectorX), landingY - (i * vectorY))) {
                    pieceFound = true;
                }
            }

            if (pieceFound) {
                validMove = false;
            } else {
                validMove = checkField(landingX, landingY, movingColour);
            }
        } else {
            validMove = false;
        }
        return validMove;
    }
    /*
        This method is called when we press the Mouse. So we need to find out what piece we have
        selected. We may also not have selected a piece!
    */
    public void mousePressed(MouseEvent e){
        chessPiece = null;
        String name = getPieceName(e.getX(), e.getY());
        /*if(moveCounter == 0){
          makeAIMove();
        }*/
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel)
            return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel)c;
        initialX = e.getX();
        initialY = e.getY();
        startX = (e.getX()/75);
        startY = (e.getY()/75);

        if(name.contains("Knight")){

            getKnightMoves(startX, startY, name);
        }

        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    /*
       This method is used when the Mouse is released...we need to make sure the move was valid before
       putting the piece back on the board.
   */
    public void mouseReleased(MouseEvent e) {
        if (chessPiece == null || end) return;

        chessPiece.setVisible(false);
        Boolean success =false;
        Boolean promotion = false;
        Boolean progression = false;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());

        String tmp = chessPiece.getIcon().toString();
        String pieceName = tmp.substring(0, (tmp.length()-4));
        Boolean validMove = false;

        int landingX = (e.getX()/75);
        int landingY  = (e.getY()/75);
        int movementX = landingX - startX;
        int movementY = landingY - startY;
        String movingColour;
        if (pieceName.contains("White")) {
            movingColour = "White";
        } else {
            movingColour = "Black";
        }

//        int xMovement = Math.abs((e.getX()/75)-startX);
//        int yMovement = Math.abs((e.getY()/75)-startY);
        System.out.println("----------------------------------------------");
        System.out.println("The piece that is being moved is : "+pieceName);
        System.out.println("The starting coordinates are : "+"( "+startX+","+startY+")");
        System.out.println("The xMovement is : "+movementX);
        System.out.println("The yMovement is : "+movementY);
        System.out.println("The landing coordinates are : "+"( "+landingX+","+landingY+")");
        System.out.println("----------------------------------------------");

        /*
          We need a process to identify whos turn it is to make a move.
        */
        Boolean possible = false;

        if(white2Move){
            if(pieceName.contains("White")){
                possible = true;
            }
        }
        else{
            if(pieceName.contains("Black")){
                possible = true;
            }
        }
        if(possible){

            /*
        ==================================================
        ====== KING ======================================
        ==================================================
        */

                if (pieceName.contains("King")) {
                    if (movingColour.equals("Black")) {
                        validMove = Math.abs(whiteKingX - landingX) > 1 || Math.abs(whiteKingY - landingY) > 1;
                    } else {
                        validMove = Math.abs(blackKingX - landingX) > 1 || Math.abs(blackKingY - landingY) > 1;
                    }
                    if (validMove) {
                        validMove = (Math.abs(movementX) == 0 || Math.abs(movementX) == 1) && (Math.abs(movementY) == 0 || Math.abs(movementY) == 1) && (!piecePresent(landingX, landingY) || checkOpponent(landingX, landingY, movingColour));
                    }

                    if (validMove) {
                        if (movingColour.equals("Black")) {
                            blackKingX = landingX;
                            blackKingY = landingY;
                        } else {
                            whiteKingX = landingX;
                            whiteKingY = landingY;
                        }
                    }
                }

        /*
        ==================================================
        ====== KNIGHT ====================================
        ==================================================
        */

            if (pieceName.contains("Knight")) {
                if ((Math.abs(movementX) == 1 && Math.abs(movementY) == 2) || (Math.abs(movementY) == 1 && Math.abs(movementX) == 2)) {
                    if (piecePresent(landingX, landingY)) {
                        validMove = checkOpponent(landingX, landingY, movingColour);
                    } else {
                        validMove = true;
                    }
                }
            }


        /*
        ==================================================
        ====== BLACK PAWN ================================
        ==================================================
        */

            if (pieceName.equals("BlackPawn")) {
                if (startY == 6 && movementY == -2) {
                    validMove = (!piecePresent(landingX, landingY)) && (!piecePresent(landingX, landingY + 1));
                } else if ((movementX == 0) && (movementY == -1) && (!piecePresent(landingX, landingY))) {
                    validMove = true;
                } else if ((movementX == 1 || movementX == -1) && (movementY == -1) && piecePresent(landingX, landingY)) {
                    validMove = checkOpponent(landingX, landingY, movingColour);
                } else {
                    validMove = false;
                }
                success = landingY == 0;
            }


        /*
        ============================================
        ====== ROOK ================================
        ============================================
        */

            if (pieceName.contains("Rook")) {
                validMove = moveLikeRook(movementX, movementY, landingX, landingY, movingColour);
            }
        /*
        ============================================
        ====== BISHOP ==============================
        ============================================
        */

            if (pieceName.contains("Bishup")) {
                validMove = moveLikeBishop(movementX, movementY, landingX, landingY, movingColour);
            }
        } else {
            validMove = false;
        }

        if(!validMove){
            int location=0;
            if(startY ==0){
                location = startX;
            }
            else{
                location  = (startY*8)+startX;
            }
            String pieceLocation = pieceName+".png";
            pieces = new JLabel( new ImageIcon(pieceLocation) );
            panels = (JPanel)chessBoard.getComponent(location);
            panels.add(pieces);
        }
        else{ // this is the condition where we have a valid move and need to visually show it.
            if(white2Move){
                white2Move = false;
            }
            else{
                white2Move = true;
            }

            if(progression){
                int location = 0 + (e.getX()/75);
                if (c instanceof JLabel){
                    Container parent = c.getParent();
                    parent.remove(0);
                    pieces = new JLabel( new ImageIcon("BlackQueen.png") );
                    parent = (JPanel)chessBoard.getComponent(location);
                    parent.add(pieces);
                }
                if(winner !=null){
                    JOptionPane.showMessageDialog(null, winner);
                    System.exit(0);
                }
            }
            else if(success){
                //int location = 56 + (e.getX()/75);
                if (c instanceof JLabel){
                    Container parent = c.getParent();
                    parent.remove(0);
                    pieces = new JLabel(new ImageIcon(movingColour + "Queen.png"));
                    //parent = (JPanel)chessBoard.getComponent(location);
                    parent.add(pieces);
                }
                else{
                    Container parent = c.getParent();
                    pieces = new JLabel(new ImageIcon(movingColour + "Queen.png"));
                    //parent = (JPanel)chessBoard.getComponent(location);
                    parent.add(pieces);
                }
            }
            else{
                if (c instanceof JLabel){
                    Container parent = c.getParent();
                    parent.remove(0);
                    parent.add( chessPiece );
                }
                else {
                    Container parent = (Container)c;
                    parent.add( chessPiece );
                }
                chessPiece.setVisible(true);
                if(winner !=null){
                    JOptionPane.showMessageDialog(null, winner);
                    System.exit(0);
                }
            }
            makeAIMove();
        } // end of the else condition
    }// end of the mouseReleased event.

    public void mouseClicked(MouseEvent e) {

    }
    public void mouseMoved(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e) {

    }

    public void startGame(){
        System.out.println("Let the game begin");
    }
    /*
        Main method that gets the ball moving.
    */
    public static void main(String[] args) {
        ChessProject frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        Object[] options = {"Random Moves","Best Next Move","Based on Opponents Moves"};
        int n = JOptionPane.showOptionDialog(frame,"Lets play some Chess, choose your AI opponent","Introduction to AI Continuous Assessment", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
        System.out.println("The selected variable is : "+n);
        frame.makeAIMove();
    }
}

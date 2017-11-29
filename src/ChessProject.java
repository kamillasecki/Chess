import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Stack;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

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
    JPanel panels;
    JLabel pieces;
    Boolean win;
    String winner;
    Boolean white2Move;
    int moveCounter;
    AIAgent agent;
    Boolean agentwins;
    Stack<Move> temporary;
    boolean end;
    Board board;


    public ChessProject() {
        Dimension boardSize = new Dimension(600, 600);

        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess Board to the Layered Pane
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, 0);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
            else
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
        }

        // Setting up the Initial Chess Board.

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

        moveCounter = 0;
        white2Move = true;
        agent = new AIAgent();
        agentwins = false;
        temporary = new Stack<>();

        board = new Board();
    }


    /*
            Method to colour a stack of Squares
    */
    private void colorSquares(Stack<Square> squares) {
        Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 3);
        while (!squares.empty()) {
            Square s = squares.pop();
            int location = s.getXC() + ((s.getYC()) * 8);
            JPanel panel = (JPanel) chessBoard.getComponent(location);
            panel.setBorder(greenBorder);
        }
    }

    /*
        Method to get the landing square of a bunch of moves...
    */
    private void getLandingSquares(Stack<Move> found) {
        Move tmp;
        Square landing;
        Stack<Square> squares = new Stack<>();
        while (!found.empty()) {
            tmp = found.pop();
            landing = new Square(tmp.getLandingX(), tmp.getLandingY());
            squares.push(landing);
        }
        colorSquares(squares);
    }

    /*
      Method to find all the White Pieces.
    */
    private Stack<Square> findWhitePieces() {
        //     Board board = takeBoardSnapshot();
        Square[] squares = board.getSquares();
        Stack<Square> squaresStack = new Stack<>();

        for (int i = 0; i < 64; i++) {
            if (squares[i].isOccupied() && Objects.equals(squares[i].getPiece().getColour(), "White")) {
                squaresStack.push(squares[i]);
            }
        }

        return squaresStack;
    }

    private void resetBorders() {
        Border empty = BorderFactory.createEmptyBorder();
        for (int i = 0; i < 64; i++) {
            JPanel tmppanel = (JPanel) chessBoard.getComponent(i);
            tmppanel.setBorder(empty);
        }
    }

    /*
      The method printStack takes in a Stack of Moves and prints out all possible moves.
    */


    private void makeAIMove() {
    /*
      When the AI Agent decides on a move, a red border shows the square from where the move started and the
      landing square of the move.
    */

        //board = takeBoardSnapshot();
        resetBorders();
        layeredPane.validate();
        layeredPane.repaint();
        Stack<Square> white = findWhitePieces();
        Stack<Move> completeMoves = new Stack<>();
        Move tmp;
        while (!white.empty()) {
            Square s = white.pop();
            Stack tmpMoves = new Stack();
            tmpMoves = s.getMoves(board, "White");


            while (!tmpMoves.empty()) {
                tmp = (Move) tmpMoves.pop();
                completeMoves.push(tmp);
            }
        }

        //printing out all possible moves
        temporary = (Stack<Move>) completeMoves.clone();
        getLandingSquares(temporary);

        System.out.println("=============================================================");
        Stack<Move> testing = new Stack<Move>();
        while (!completeMoves.empty()) {
            Move tmpMove = completeMoves.pop();
            System.out.println("The " + tmpMove.getName() + " can move from (" + tmpMove.getStartX() + ", " + tmpMove.getStartY() + ") to the following square: (" + tmpMove.getLandingX() + ", " + tmpMove.getLandingY() + ")");
            testing.push(tmpMove);
        }
        System.out.println("=============================================================");
        Border redBorder = BorderFactory.createLineBorder(Color.RED, 3);

        // ========== SELECT THE AI MOVE ==============
        Move selectedMove = agent.nextBestMove(testing, board);
        int startXpix = (selectedMove.getStartX() * 75) + 20;
        int startYpix = (selectedMove.getStartY() * 75) + 20;
        int landingXpix = (selectedMove.getLandingX() * 75) + 20;
        int landingYpix = (selectedMove.getLandingY() * 75) + 20;

        // =========== PRINT OUT THE AI MOVE =============

        System.out.println("-------- Move " + selectedMove.getName() + " (" + selectedMove.getStartX() + ", " + selectedMove.getStartY() + ") to (" + selectedMove.getLandingX() + ", " + selectedMove.getLandingY() + ")");

        // =========== SHOW THE MOVE ON THE GUI ==============

        Component c = chessBoard.findComponentAt(startXpix, startYpix);
        Container parent = c.getParent();
        parent.remove(c);
        int panelID = (selectedMove.getStartY() * 8) + selectedMove.getStartX();
        panels = (JPanel) chessBoard.getComponent(panelID);
        panels.setBorder(redBorder);
        parent.validate();

        Component l = chessBoard.findComponentAt(landingXpix, landingYpix);
        if (l instanceof JLabel) {
            Container parentlanding = l.getParent();
            JLabel awaitingName = (JLabel) l;
            String agentCaptured = awaitingName.getIcon().toString();
            if (agentCaptured.contains("King")) {
                agentwins = true;
            }
            parentlanding.remove(l);
            parentlanding.validate();
            pieces = new JLabel(new ImageIcon(selectedMove.getName() + ".png"));
            int landingPanelID = (selectedMove.getLandingY() * 8) + selectedMove.getLandingX();
            panels = (JPanel) chessBoard.getComponent(landingPanelID);
            panels.add(pieces);
            panels.setBorder(redBorder);
            layeredPane.validate();
            layeredPane.repaint();

            if (agentwins) {
                JOptionPane.showMessageDialog(null, "The AI Agent has won!");
                System.exit(0);
            }
        } else {
            pieces = new JLabel(new ImageIcon(selectedMove.getName() + ".png"));
            int landingPanelID = (selectedMove.getLandingY() * 8) + selectedMove.getLandingX();
            panels = (JPanel) chessBoard.getComponent(landingPanelID);
            panels.add(pieces);
            panels.setBorder(redBorder);
            layeredPane.validate();
            layeredPane.repaint();
        }

        // ============ REFLECT THE MOVE NO BOARD INSTANCE ============

        int startPosition = selectedMove.getStartY() * 8 + selectedMove.getStartX();
        int landingPosition = selectedMove.getLandingY() * 8 + selectedMove.getLandingX();
        board.movePiece(startPosition, landingPosition);
        board.printBoaard();

        white2Move = false;

        // }
    }

    public void mousePressed(MouseEvent e) {
        chessPiece = null;

        /*if(moveCounter == 0){
          makeAIMove();
        }*/
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel)
            return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel) c;
        startX = (e.getX() / 75);
        startY = (e.getY() / 75);


        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);

    }

    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    public void mouseReleased(MouseEvent e) {
        if (chessPiece == null || end) return;

        chessPiece.setVisible(false);
        Boolean success = false;
        Boolean promotion = false;
        Boolean progression = false;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        String tmp = chessPiece.getIcon().toString();
        String pieceName = tmp.substring(0, (tmp.length() - 4));
        Boolean validMove = false;

        int landingX = (e.getX() / 75);
        int landingY = (e.getY() / 75);
        int movementX = landingX - startX;
        int movementY = landingY - startY;
        String movingColour;
        if (pieceName.contains("White")) {
            movingColour = "White";
        } else {
            movingColour = "Black";
        }

        Stack moves;
        Square s = board.getSquare(startX, startY);

        if (Objects.equals(movingColour, "Black")) {
            moves = s.getMoves(board, "Black");

            while (!moves.empty()) {

                Move m = (Move) moves.pop();
                if (m.getLandingX() == landingX && m.getLandingY() == landingY) {
                    validMove = true;
                }

            }

            System.out.println("----------------------------------------------");
            System.out.println("The piece that is being moved is : " + pieceName);
            System.out.println("The starting coordinates are : " + "( " + startX + "," + startY + ")");
            System.out.println("The xMovement is : " + movementX);
            System.out.println("The yMovement is : " + movementY);
            System.out.println("The landing coordinates are : " + "( " + landingX + "," + landingY + ")");
            System.out.println("----------------------------------------------");

        /*
          We need a process to identify whos turn it is to make a move.
        */
            Boolean possible = false;

            if (white2Move) {
                if (pieceName.contains("White")) {
                    possible = true;
                }
            } else {
                if (pieceName.contains("Black")) {
                    possible = true;
                }
            }
            if (possible) {
                if (!validMove) {
                    int location = 0;
                    if (startY == 0) {
                        location = startX;
                    } else {
                        location = (startY * 8) + startX;
                    }
                    String pieceLocation = pieceName + ".png";
                    pieces = new JLabel(new ImageIcon(pieceLocation));
                    panels = (JPanel) chessBoard.getComponent(location);
                    panels.add(pieces);
                } else { // this is the condition where we have a valid move and need to visually show it.

                    if (white2Move) {
                        white2Move = false;
                    } else {
                        white2Move = true;
                    }

                    if (Objects.equals(pieceName, "BlackPawn") && landingY == 0) { //checking if it is promotion move
                        int location = 0 + (e.getX() / 75);
                        if (c instanceof JLabel) {
                            Container parent = c.getParent();
                            parent.remove(0);
                            pieces = new JLabel(new ImageIcon("BlackQueen.png"));
                            parent = (JPanel) chessBoard.getComponent(location);
                            parent.add(pieces);
                            int startPosition = startY * 8 + startX;
                            int landingPosition = landingY * 8 + landingX;
                            board.movePiece(startPosition, landingPosition);
                        }
                        if (winner != null) {
                            JOptionPane.showMessageDialog(null, winner);
                            System.exit(0);
                        }
                    } else {
                        if (c instanceof JLabel) {
                            Container parent = c.getParent();
                            parent.remove(0);
                            parent.add(chessPiece);


                        } else {
                            Container parent = (Container) c;
                            parent.add(chessPiece);
                        }
                        chessPiece.setVisible(true);
                        if (winner != null) {
                            JOptionPane.showMessageDialog(null, winner);
                            System.exit(0);
                        }

                        int startPosition = startY * 8 + startX;
                        int landingPosition = landingY * 8 + landingX;
                        board.movePiece(startPosition, landingPosition);
                    }
                    board.printBoaard();
                    makeAIMove();
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void startGame() {
        System.out.println("Let the game begin");
    }

    /*
        Main method that gets the ball moving.
    */
    public static void main(String[] args) {
        ChessProject frame = new ChessProject();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Object[] options = {"Random Moves", "Best Next Move", "Based on Opponents Moves"};
        int n = JOptionPane.showOptionDialog(frame, "Lets play some Chess, choose your AI opponent", "Introduction to AI Continuous Assessment", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        System.out.println("The selected variable is : " + n);
        frame.makeAIMove();
    }
}

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.util.Objects;
//
///*
//    This class can be used as a starting point for creating your Chess game project. The only piece that
//	has been coded is a white pawn...a lot done, more to do!
//*/
//
//public class _old extends JFrame implements MouseListener, MouseMotionListener {
//    private JLayeredPane layeredPane;
//    private JPanel chessBoard;
//    private JLabel chessPiece;
//    private int xAdjustment;
//    private int yAdjustment;
//    private int startX;
//    private int startY;
//    private JPanel panels;
//    private JLabel pieces;
//    private int whiteKingX = 3;
//    private int whiteKingY = 0;
//    private int blackKingX = 3;
//    private int blackKingY = 7;
//    private boolean end = false;
//    private String turn = "White";
//
//
//    private _old() {
//        Dimension boardSize = new Dimension(600, 600);
//
//        //  Use a Layered Pane for this application
//        layeredPane = new JLayeredPane();
//        getContentPane().add(layeredPane);
//        layeredPane.setPreferredSize(boardSize);
//        layeredPane.addMouseListener(this);
//        layeredPane.addMouseMotionListener(this);
//
//        //Add a chess Board to the Layered Pane
//        chessBoard = new JPanel();
//        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
//        chessBoard.setLayout(new GridLayout(8, 8));
//        chessBoard.setPreferredSize(boardSize);
//        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
//
//        for (int i = 0; i < 64; i++) {
//            JPanel square = new JPanel(new BorderLayout());
//            chessBoard.add(square);
//
//            int row = (i / 8) % 2;
//            if (row == 0)
//                square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
//            else
//                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
//        }
//
//        // Setting up the Initial Chess Board.
//        for (int i = 8; i < 16; i++) {
//            pieces = new JLabel(new ImageIcon("WhitePawn.png"));
//            panels = (JPanel) chessBoard.getComponent(i);
//            panels.add(pieces);
//        }
//        pieces = new JLabel(new ImageIcon("WhiteRook.png"));
//        panels = (JPanel) chessBoard.getComponent(0);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
//        panels = (JPanel) chessBoard.getComponent(1);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
//        panels = (JPanel) chessBoard.getComponent(6);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
//        panels = (JPanel) chessBoard.getComponent(2);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
//        panels = (JPanel) chessBoard.getComponent(5);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("WhiteKing.png"));
//        panels = (JPanel) chessBoard.getComponent(3);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
//        panels = (JPanel) chessBoard.getComponent(4);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("WhiteRook.png"));
//        panels = (JPanel) chessBoard.getComponent(7);
//        panels.add(pieces);
//        for (int i = 48; i < 56; i++) {
//            pieces = new JLabel(new ImageIcon("BlackPawn.png"));
//            panels = (JPanel) chessBoard.getComponent(i);
//            panels.add(pieces);
//        }
//        pieces = new JLabel(new ImageIcon("BlackRook.png"));
//        panels = (JPanel) chessBoard.getComponent(56);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("BlackKnight.png"));
//        panels = (JPanel) chessBoard.getComponent(57);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("BlackKnight.png"));
//        panels = (JPanel) chessBoard.getComponent(62);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("BlackBishup.png"));
//        panels = (JPanel) chessBoard.getComponent(58);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("BlackBishup.png"));
//        panels = (JPanel) chessBoard.getComponent(61);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("BlackKing.png"));
//        panels = (JPanel) chessBoard.getComponent(59);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("BlackQueen.png"));
//        panels = (JPanel) chessBoard.getComponent(60);
//        panels.add(pieces);
//        pieces = new JLabel(new ImageIcon("BlackRook.png"));
//        panels = (JPanel) chessBoard.getComponent(63);
//        panels.add(pieces);
//    }
//
//    /*
//        Main method that gets the ball moving.
//    */
//    public static void main(String[] args) {
//        JFrame frame = new _old();
//        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        frame.pack();
//        frame.setResizable(true);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//
//	/*
//        This is a method to check if a piece is a Black piece.
//	*/
//
//    private Boolean piecePresent(int x, int y) {
//        Component c = chessBoard.findComponentAt(x * 75, y * 75);
//        return !(c instanceof JPanel);
//    }
//
//    private Boolean checkOpponent(int newX, int newY, String moving) {
//        Boolean opponent;
//        Component c1 = chessBoard.findComponentAt(newX * 75, newY * 75);
//        JLabel awaitingPiece = (JLabel) c1;
//        String tmp1 = awaitingPiece.getIcon().toString();
//        opponent = !(tmp1.contains(moving));
//        if (opponent && tmp1.contains("King")) {
//            JOptionPane.showMessageDialog(null, moving + " won!!");
//            end = true;
//        }
//        return opponent;
//    }
//
//    private Boolean checkField(int x, int y, String moving) {
//        boolean validMove;
//        if (piecePresent(x, y)) {
//            validMove = checkOpponent(x, y, moving);
//        } else {
//            validMove = true;
//        }
//        return validMove;
//    }
//
//    private void changeTurn() {
//        if (Objects.equals(turn, "White"))
//            turn = "Black";
//        else
//            turn = "White";
//    }
//
//    private Boolean moveLikeRook(int movementX, int movementY, int landingX, int landingY, String movingColour) {
//        boolean validMove;
//        if ((movementX == 0 && movementY != 0) || (movementY == 0 && movementX != 0)) {
//            boolean pieceFound = false;
//            int vector;
//            if (movementX == 0) {
//                if (movementY > 0) {
//                    vector = 1;
//                } else {
//                    vector = -1;
//                }
//
//                for (int i = 1; i < (Math.abs(movementY)); i++) {
//                    if (piecePresent(landingX, landingY - (i * vector))) {
//                        pieceFound = true;
//                    }
//                }
//            } else {
//                if (movementX > 0) {
//                    vector = 1;
//                } else {
//                    vector = -1;
//                }
//
//                for (int i = 1; i < (Math.abs(movementX)); i++) {
//                    if (piecePresent(landingX - (i * vector), landingY)) {
//                        pieceFound = true;
//                    }
//                }
//            }
//
//            if (pieceFound) {
//                validMove = false;
//            } else {
//                validMove = checkField(landingX, landingY, movingColour);
//            }
//        } else {
//            validMove = false;
//        }
//        return validMove;
//    }
//
//    private Boolean moveLikeBishop(int movementX, int movementY, int landingX, int landingY, String movingColour) {
//        boolean validMove;
//        if (Math.abs(movementX) == Math.abs(movementY)) {
//            boolean pieceFound = false;
//            int vectorX;
//            int vectorY;
//            if (movementY > 0) {
//                vectorY = 1;
//            } else {
//                vectorY = -1;
//            }
//            if (movementX > 0) {
//                vectorX = 1;
//            } else {
//                vectorX = -1;
//            }
//
//            for (int i = 1; i < (Math.abs(movementY)); i++) {
//                if (piecePresent(landingX - (i * vectorX), landingY - (i * vectorY))) {
//                    pieceFound = true;
//                }
//            }
//
//            if (pieceFound) {
//                validMove = false;
//            } else {
//                validMove = checkField(landingX, landingY, movingColour);
//            }
//        } else {
//            validMove = false;
//        }
//        return validMove;
//    }
//
//    /*
//        This method is called when we press the Mouse. So we need to find out what piece we have
//		selected. We may also not have selected a piece!
//	*/
//    public void mousePressed(MouseEvent e) {
//        if (end) return;
//        chessPiece = null;
//        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
//        if (c instanceof JPanel)
//            return;
//
//        Point parentLocation = c.getParent().getLocation();
//        xAdjustment = parentLocation.x - e.getX();
//        yAdjustment = parentLocation.y - e.getY();
//        chessPiece = (JLabel) c;
//        startX = (e.getX() / 75);
//        startY = (e.getY() / 75);
//        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
//        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
//        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
//    }
//
//    public void mouseDragged(MouseEvent me) {
//        if (chessPiece == null || end) return;
//        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
//    }
//
//
//    public void mouseReleased(MouseEvent e) {
//        if (chessPiece == null || end) return;
//
//        chessPiece.setVisible(false);
//        Boolean success = false;
//        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
//        String tmp = chessPiece.getIcon().toString();
//        String pieceName = tmp.substring(0, (tmp.length() - 4));
//        Boolean validMove = false;
//        int landingX = (e.getX() / 75);
//        int landingY = (e.getY() / 75);
//        int movementX = landingX - startX;
//        int movementY = landingY - startY;
//        String movingColour;
//        if (pieceName.contains("White")) {
//            movingColour = "White";
//        } else {
//            movingColour = "Black";
//        }
//        if (Objects.equals(movingColour, turn)) {
//
////check if mouse released within the Board boundaries and if not restore the chess piece
//            if ((e.getX() < 0) || (e.getY() < 0) || (e.getX() > 8 * 75) || (e.getY() > 8 * 75)) {
//                int location;
//                if (startY == 0) {
//                    location = startX;
//                } else {
//                    location = (startY * 8) + startX;
//                }
//                String pieceLocation = pieceName + ".png";
//                pieces = new JLabel(new ImageIcon(pieceLocation));
//                panels = (JPanel) chessBoard.getComponent(location);
//                panels.add(pieces);
//                return;
//            }
//
//            if (pieceName.contains("Queen")) {
//                if (Math.abs(movementX) == Math.abs(movementY)) {
//                    validMove = moveLikeBishop(movementX, movementY, landingX, landingY, movingColour);
//                } else if ((movementX == 0 && movementY != 0) || (movementY == 0 && movementX != 0)) {
//                    validMove = moveLikeRook(movementX, movementY, landingX, landingY, movingColour);
//                } else {
//                    validMove = false;
//                }
//            }
//
//
//        /*
//        ==================================================
//        ====== KING ======================================
//        ==================================================
//        */
//
//            if (pieceName.contains("King")) {
//                if (movingColour.equals("Black")) {
//                    validMove = Math.abs(whiteKingX - landingX) > 1 || Math.abs(whiteKingY - landingY) > 1;
//                } else {
//                    validMove = Math.abs(blackKingX - landingX) > 1 || Math.abs(blackKingY - landingY) > 1;
//                }
//                if (validMove) {
//                    validMove = (Math.abs(movementX) == 0 || Math.abs(movementX) == 1) && (Math.abs(movementY) == 0 || Math.abs(movementY) == 1) && (!piecePresent(landingX, landingY) || checkOpponent(landingX, landingY, movingColour));
//                }
//
//                if (validMove) {
//                    if (movingColour.equals("Black")) {
//                        blackKingX = landingX;
//                        blackKingY = landingY;
//                    } else {
//                        whiteKingX = landingX;
//                        whiteKingY = landingY;
//                    }
//                }
//            }
//
//        /*
//        ==================================================
//        ====== KNIGHT ====================================
//        ==================================================
//        */
//
//            if (pieceName.contains("Knight")) {
//                if ((Math.abs(movementX) == 1 && Math.abs(movementY) == 2) || (Math.abs(movementY) == 1 && Math.abs(movementX) == 2)) {
//                    if (piecePresent(landingX, landingY)) {
//                        validMove = checkOpponent(landingX, landingY, movingColour);
//                    } else {
//                        validMove = true;
//                    }
//                }
//            }
//
//        /*
//        ==================================================
//        ====== WHITE PAWN ================================
//        ==================================================
//        */
//
//            if (pieceName.equals("BlackPawn")) {
//                if (startY == 6 && movementY == -2) {
//                    validMove = (!piecePresent(landingX, landingY)) && (!piecePresent(landingX, landingY + 1));
//                } else if ((movementX == 0) && (movementY == -1) && (!piecePresent(landingX, landingY))) {
//                    validMove = true;
//                } else if ((movementX == 1 || movementX == -1) && (movementY == -1) && piecePresent(landingX, landingY)) {
//                    validMove = checkOpponent(landingX, landingY, movingColour);
//                } else {
//                    validMove = false;
//                }
//                success = landingY == 0;
//            }
//
//        /*
//        ==================================================
//        ====== WHITE PAWN ================================
//        ==================================================
//        */
//
//            if (pieceName.equals("WhitePawn")) {
//                if (startY == 1 && movementY == 2) {
//                    validMove = (!piecePresent(landingX, landingY)) && (!piecePresent(landingX, landingY - 1));
//                } else if ((movementX == 0) && (movementY == 1) && (!piecePresent(landingX, landingY))) {
//                    validMove = true;
//                } else if ((movementX == 1 || movementX == -1) && (movementY == 1) && piecePresent(landingX, landingY)) {
//                    validMove = checkOpponent(landingX, landingY, movingColour);
//                } else {
//                    validMove = false;
//                }
//                success = landingY == 7;
//            }
//
//        /*
//        ============================================
//        ====== ROOK ================================
//        ============================================
//        */
//
//            if (pieceName.contains("Rook")) {
//                validMove = moveLikeRook(movementX, movementY, landingX, landingY, movingColour);
//            }
//        /*
//        ============================================
//        ====== BISHOP ==============================
//        ============================================
//        */
//
//            if (pieceName.contains("Bishup")) {
//                validMove = moveLikeBishop(movementX, movementY, landingX, landingY, movingColour);
//            }
//        } else {
//            validMove = false;
//        }
//
//        if (!validMove) {
//            int location;
//            if (startY == 0) {
//                location = startX;
//            } else {
//                location = (startY * 8) + startX;
//            }
//            String pieceLocation = pieceName + ".png";
//            pieces = new JLabel(new ImageIcon(pieceLocation));
//            panels = (JPanel) chessBoard.getComponent(location);
//            panels.add(pieces);
//        } else {
//            if (success) {
//                if (c instanceof JLabel) {
//                    Container parent = c.getParent();
//                    chessPiece = new JLabel(new ImageIcon(movingColour + "Queen.png"));
//                    parent.remove(0);
//                    parent.add(chessPiece);
//                } else {
//                    Container parent = c.getParent();
//                    chessPiece = new JLabel(new ImageIcon(movingColour + "Queen.png"));
//                    parent.add(chessPiece);
//                }
//            } else {
//                if (c instanceof JLabel) {
//                    Container parent = c.getParent();
//                    parent.remove(0);
//                    parent.add(chessPiece);
//                } else {
//                    Container parent = (Container) c;
//                    parent.add(chessPiece);
//                }
//                chessPiece.setVisible(true);
//            }
//            changeTurn();
//        }
//
//        System.out.println("-----------------------------------------------");
//        System.out.println("Moved piece: " + pieceName);
//        System.out.println("Starting position: ( " + startX + ", " + startY + " )");
//        System.out.println("Move offset: ( " + movementX + ", " + movementY + " )");
//        System.out.println("Landing coordinates are: ( " + e.getX() + ", " + e.getY() + " )");
//        System.out.println("Move was: " + (validMove ? "VALID" : "INVALID"));
//        System.out.println("-----------------------------------------------");
//
//    }
//
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    public void mouseMoved(MouseEvent e) {
//    }
//
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    public void mouseExited(MouseEvent e) {
//
//    }
//}
//
//

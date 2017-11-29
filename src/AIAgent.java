import java.util.*;

public class AIAgent {
    Random rand;
    double boardValueWeight = 0.3;
    double movesWeight = 0.7;

    public AIAgent() {
        rand = new Random();
    }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a rondom number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

    public Move randomMove(Stack possibilities) {

        int moveID = rand.nextInt(possibilities.size());
        System.out.println("Agent randomly selected move : " + moveID);
        for (int i = 1; i < (possibilities.size() - (moveID)); i++) {
            possibilities.pop();
        }
        Move selectedMove = (Move) possibilities.pop();
        return selectedMove;
    }

    public Move nextBestMove(Stack<Move> possibilities, Board board) {
        int playerMovesCount;
        int aiMovesCount;
        int boardValue;
        double[] scores = new double[possibilities.size()];
        double maxScore =0;
        int moveNumber=0;

        Stack<Move> backupStack;
        backupStack = (Stack<Move>) possibilities.clone();

        //Simulate all moves
        for (int i = 0; i < backupStack.size(); i++) {
            Board boardCopy = board.copy();

            Move testMove = possibilities.pop();
            Stack<Move> playerMoves = boardCopy.getNextMoves("Black", testMove);

            Stack<Move> aiMoves = boardCopy.getNextMoves("White", testMove);
            playerMovesCount = playerMoves.size();
            aiMovesCount = aiMoves.size();

            System.out.println("If AI would make move from (" + testMove.getStartX() + "," + testMove.getStartY() + ") to position (" + testMove.getLandingX() + "," + testMove.getLandingY() + ", player would have " + playerMovesCount + " moves, and AI would have: " + aiMovesCount + " moves");

            boardCopy.movePiece(testMove);
            boardValue = boardCopy.getBoardScore("White");

            scores[i] =  0.1*(aiMovesCount - playerMovesCount) + boardValue;
            System.out.println("Score for this move: " + scores[i] + " (board value: " + boardValue + ")");
        }
        //for each output check number of opponent moves and figures left

        for (int i=0; i<scores.length;i++){
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                moveNumber = i+1;
            }
        }

        Move selectedMove;
        for (int i = 1; i < moveNumber; i++) {
            selectedMove = (Move) backupStack.pop();
            System.out.println("Removing move " +  i + " : (" + selectedMove.getStartX() + "," + selectedMove.getStartY() + ") to position (" + selectedMove.getLandingX() + "," + selectedMove.getLandingY());

        }
        selectedMove = (Move) backupStack.pop();

        return selectedMove;
    }
//
    // public Move twoLevelsDeep(Stack possibilities){
    //Move selectedMove = new Move();
    //return selectedMove;
    // }
}

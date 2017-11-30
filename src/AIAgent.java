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
        double[] scores = new double[possibilities.size()];
        double maxScore = Double.POSITIVE_INFINITY;
        int moveNumber=0;

        Stack<Move> backupStack = (Stack<Move>) possibilities.clone();

        //Simulate all moves
        for (int i = 0; i < backupStack.size(); i++) {
            Move testMove = possibilities.pop();
            scores[i] =  getScore(board,testMove,"White");
        }

        //find the move highest score
        for (int i=0; i<scores.length;i++){
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                moveNumber = i+1;
            }
        }

        //travers to the move with highest score
        Move selectedMove;
        for (int i = 1; i < moveNumber; i++) {
            backupStack.pop();
        }
        selectedMove = backupStack.pop();

        return selectedMove;
    }

     public Move twoLevelsDeep(Stack<Move> possibilities, Board board){
         double playerMaxScore;
         double minScore = Double.POSITIVE_INFINITY;
         int moveNumber=0;
         double[] scores = new double[possibilities.size()];

         Stack<Move> backupStack;
         backupStack = (Stack<Move>) possibilities.clone();

         //Simulate all moves
         //for each Ai move:
         for (int i = 0; i < backupStack.size(); i++) {
             //grab the move
             Move testMove = possibilities.pop();
             System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
             System.out.println("For AI move (" + testMove.getStartX() + "," + testMove.getStartY() + ") to (" + testMove.getLandingX() + "," + testMove.getLandingY() +")");
             System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
             //make a copy of the board for the purpose of simulation
             Board boardCopy = board.copy();
             //proceed the simulation of the AI move
             boardCopy.movePiece(testMove);
             //get all player'm moves
             Stack<Move> playerMoves = boardCopy.getMoves("Black");

             Stack<Move> playerMovesBackup = (Stack<Move>) playerMoves.clone();

             //reset players max score
             playerMaxScore = Double.NEGATIVE_INFINITY;
             //get all scores of player moves
             for (int j = 0; j < playerMovesBackup.size(); j++) {

                 Move playerMove = playerMoves.pop();

                 //get score for player's move and if it is a new highest remember the value. There is no need to remember the move number
                 //because we wont be making players move, just want to check which move will player make most likely and what will be a score of this move
                 //later on we want to choose a lowest score of the player's highests,to make the decision on AI move.
                 double playerScores =  getScore(boardCopy,playerMove,"Black");
                 if (playerScores > playerMaxScore) {
                     playerMaxScore = playerScores;
                     scores[i] = playerMaxScore;

                 }
             }
         }

         //find the move lowest score of player's highests, in order to make decision which move should AI make
         for (int k=0; k<scores.length;k++){
             double currScore = scores[k];
             if (currScore < minScore) {
                 minScore = currScore;
                 moveNumber = k+1;
             }
         }


         //travers to the move with highest score
         Move selectedMove;
         for (int i = 1; i < moveNumber; i++) {
             backupStack.pop();
         }
         selectedMove = backupStack.pop();

         return selectedMove;
     }

     public double getScore(Board board, Move move, String colour) {
         double score;
         int playerMovesCount;
         int aiMovesCount;
         int boardValue;
         Board boardCopy = board.copy();

         if (Objects.equals(colour, "Black"))
         {
             Stack<Move> playerMoves = boardCopy.getNextMoves("Black", move);
             Stack<Move> aiMoves = boardCopy.getNextMoves("White", move);

             boardCopy.movePiece(move);
             playerMovesCount = playerMoves.size();
             aiMovesCount = aiMoves.size();

             boardValue = boardCopy.getBoardScore("Black");
             score =  0.1*(playerMovesCount - aiMovesCount) + boardValue;
             System.out.println("Player move (" + move.getStartX() + "," + move.getStartY() + ") to (" + move.getLandingX() + "," + move.getLandingY() +"). Score: " + score );
         } else {
             Stack<Move> playerMoves = boardCopy.getNextMoves("Black", move);
             Stack<Move> aiMoves = boardCopy.getNextMoves("White", move);

             boardCopy.movePiece(move);
             playerMovesCount = playerMoves.size();
             aiMovesCount = aiMoves.size();

             boardValue = boardCopy.getBoardScore("White");
             score =  0.1*(aiMovesCount - playerMovesCount) + boardValue;
             System.out.println("AI move (" + move.getStartX() + "," + move.getStartY() + ") to (" + move.getLandingX() + "," + move.getLandingY() +"). Score: " + score );
         }

         return score;
     }


}

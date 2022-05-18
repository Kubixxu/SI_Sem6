
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;


public class AiPlayer implements CheckersPlayer {
    private Board gameBoard;
    //private Game game;
    private Color color;
    public final int DEPTH = 6;
    @Override
    public Pawn[][] move() {
        return minimaxAlphaBeta(gameBoard, DEPTH, color, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getKey();
    }
    @Override
    public Pawn[][] moveRandomly() {
        Random randomGen = new Random();
        ArrayList<Pawn[][]> moves = gameBoard.allPossibleMoves(color);
        return moves.get(randomGen.nextInt(0, moves.size()));
    }
    private double evaluateBoard(Board gameBoard) {
        Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        return gameBoard.countElements(gameBoard.getBoard(), color) - gameBoard.countElements(gameBoard.getBoard(), enemyColor);
    }

    /*private double evaluateBoard(Board gameBoard) {
        Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        return gameBoard.countElements(gameBoard.getBoard(), color) - gameBoard.countElements(gameBoard.getBoard(), enemyColor) +
                2.0 * (gameBoard.countQueens(color) - gameBoard.countQueens(enemyColor));
    }*/

    private double evaluateBoard2(Board gameBoard) {
        Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        int [] pawnsInZonesPlr = numberOfPawnsInZones(color);
        int [] pawnsInZonesEnm = numberOfPawnsInZones(enemyColor);
        return pawnsInZonesPlr[0] + pawnsInZonesPlr[1] * 2.0 + pawnsInZonesPlr[2] * 4.0 - pawnsInZonesEnm[0] - pawnsInZonesEnm[1] * 2.0 - pawnsInZonesEnm[2] * 4.0;
    }

    private int [] numberOfPawnsInZones(Color color) {
        int numOfPawns = 0;
        int [] numOfPawnsByZone = {0,0,0};
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < gameBoard.getBoard()[0].length; y++) {
                if(gameBoard.getBoard()[x][y] != Pawn.EMPTY) {
                    Color pawnColor = gameBoard.getBoard()[x][y] == Pawn.WHITE || gameBoard.getBoard()[x][y] == Pawn.WHITE_QUEEN ? Color.WHITE : Color.BLACK;
                    if (pawnColor == color)
                        numOfPawnsByZone[0] = numOfPawnsByZone[0] + 1;
                }
            }
        }
        for (int x = 3; x < 6; x++) {
            for (int y = 0; y < gameBoard.getBoard()[0].length; y++) {
                if(gameBoard.getBoard()[x][y] != Pawn.EMPTY) {
                    Color pawnColor = gameBoard.getBoard()[x][y] == Pawn.WHITE || gameBoard.getBoard()[x][y] == Pawn.WHITE_QUEEN ? Color.WHITE : Color.BLACK;
                    if (pawnColor == color)
                        numOfPawnsByZone[1] = numOfPawnsByZone[1] + 1;
                }
            }
        }
        for (int x = 6; x < 8; x++) {
            for (int y = 0; y < gameBoard.getBoard()[0].length; y++) {
                if(gameBoard.getBoard()[x][y] != Pawn.EMPTY) {
                    Color pawnColor = gameBoard.getBoard()[x][y] == Pawn.WHITE || gameBoard.getBoard()[x][y] == Pawn.WHITE_QUEEN ? Color.WHITE : Color.BLACK;
                    if (pawnColor == color)
                        numOfPawnsByZone[1] = numOfPawnsByZone[1] + 1;
                }
            }
        }
        if (color == Color.WHITE) {
            int [] revArray = new int[3];
            revArray[0] = numOfPawnsByZone[2];
            revArray[1] = numOfPawnsByZone[1];
            revArray[2] = numOfPawnsByZone[0];
            return revArray;
        }
        return numOfPawnsByZone;
    }

    public AiPlayer(Board board, Color color) {
        gameBoard = board;
        this.color = color;
    }
    private AbstractMap.SimpleEntry<Pawn[][], Double> minimax(Board currentBoard, int depth, Color currPlayerColor) {
        ArrayList<Pawn[][]> possibleMoves = currentBoard.allPossibleMoves(currPlayerColor);
        if (depth == 0 || possibleMoves.isEmpty()) {
            return new AbstractMap.SimpleEntry<>(currentBoard.getBoard(), evaluateBoard(currentBoard));
        }
        if (currPlayerColor == color) {
            double maxEval = Double.NEGATIVE_INFINITY;
            Pawn[][] bestMove = null;
            for (Pawn[][] move : currentBoard.allPossibleMoves(currPlayerColor)) {
                AbstractMap.SimpleEntry<Pawn[][], Double> evaluation = minimax(new Board(move), depth - 1, currPlayerColor == Color.WHITE ? Color.BLACK : Color.WHITE);
                maxEval = Math.max(maxEval, evaluation.getValue());
                if (maxEval == evaluation.getValue()) {
                    bestMove = move;
                }
            }
            return new AbstractMap.SimpleEntry<>(bestMove, maxEval);
        }
        else {
            double minEval = Double.POSITIVE_INFINITY;
            Pawn[][] bestMove = null;
            for (Pawn[][] move : currentBoard.allPossibleMoves(currPlayerColor)) {
                AbstractMap.SimpleEntry<Pawn[][], Double> evaluation = minimax(new Board(move), depth - 1, currPlayerColor == Color.WHITE ? Color.BLACK : Color.WHITE);
                minEval = Math.min(minEval, evaluation.getValue());
                if (minEval == evaluation.getValue()) {
                    bestMove = move;
                }
            }
            return new AbstractMap.SimpleEntry<>(bestMove, minEval);
        }
    }

    private AbstractMap.SimpleEntry<Pawn[][], Double> minimaxAlphaBeta(Board currentBoard, int depth, Color currPlayerColor, Double alpha, Double beta) {
        ArrayList<Pawn[][]> possibleMoves = currentBoard.allPossibleMoves(currPlayerColor);
        if (depth == 0 || possibleMoves.isEmpty()) {
            return new AbstractMap.SimpleEntry<>(currentBoard.getBoard(), evaluateBoard(currentBoard));
        }
        if (currPlayerColor == color) {
            double maxEval = Double.NEGATIVE_INFINITY;
            Pawn[][] bestMove = null;
            for (Pawn[][] move : currentBoard.allPossibleMoves(currPlayerColor)) {
                AbstractMap.SimpleEntry<Pawn[][], Double> evaluation = minimaxAlphaBeta(new Board(move), depth - 1, currPlayerColor == Color.WHITE ? Color.BLACK : Color.WHITE, alpha, beta);
                maxEval = Math.max(maxEval, evaluation.getValue());
                if (maxEval == evaluation.getValue()) {
                    bestMove = move;
                }
                alpha = Math.max(alpha, maxEval);
                if (alpha >= beta) {
                    return new AbstractMap.SimpleEntry<>(bestMove, maxEval);
                }

            }
            return new AbstractMap.SimpleEntry<>(bestMove, maxEval);
        }
        else {
            double minEval = Double.POSITIVE_INFINITY;
            Pawn[][] bestMove = null;
            for (Pawn[][] move : currentBoard.allPossibleMoves(currPlayerColor)) {
                AbstractMap.SimpleEntry<Pawn[][], Double> evaluation = minimaxAlphaBeta(new Board(move), depth - 1, currPlayerColor == Color.WHITE ? Color.BLACK : Color.WHITE, alpha, beta);
                minEval = Math.min(minEval, evaluation.getValue());
                if (minEval == evaluation.getValue()) {
                    bestMove = move;
                }
                beta = Math.min(beta, minEval);
                if (beta <= alpha) {
                    return new AbstractMap.SimpleEntry<>(bestMove, minEval);
                }
            }
            return new AbstractMap.SimpleEntry<>(bestMove, minEval);
        }
    }
}

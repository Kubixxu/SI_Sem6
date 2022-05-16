import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;

public class AiPlayer implements CheckersPlayer {
    private Board gameBoard;
    private Color color;
    public final int DEPTH = 6;
    @Override
    public AbstractMap.SimpleEntry<Pawn[][], Pawn> move() {
        return new AbstractMap.SimpleEntry<>(minimax(gameBoard, DEPTH, color).getKey(), gameBoard.getBoard()[0][0]);
    }

    private double evaluateBoard(Board gameBoard) {
        Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        return gameBoard.countElements(gameBoard.getBoard(), color) - gameBoard.countElements(gameBoard.getBoard(), enemyColor);
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
}

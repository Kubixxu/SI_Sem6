import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;

public class AiPlayer implements CheckersPlayer {
    private Board gameBoard;
    //private Game game;
    private Color color;
    public final int DEPTH = 6;
    @Override
    public AbstractMap.SimpleEntry<Pawn[][], Pawn> move() {
        return new AbstractMap.SimpleEntry<>(minimaxAlphaBeta(gameBoard, DEPTH, color, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getKey(), gameBoard.getBoard()[0][0]);
    }
    @Override
    public AbstractMap.SimpleEntry<Pawn[][], Pawn> moveRandomly() {
        Random randomGen = new Random();
        ArrayList<Pawn[][]> moves = gameBoard.allPossibleMoves(color);
        return new AbstractMap.SimpleEntry<>(moves.get(randomGen.nextInt(0, moves.size())), gameBoard.getBoard()[0][0]);
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

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private CheckersPlayer whitePlayer;
    private CheckersPlayer blackPlayer;
    private Board gameBoard;
    public final int MAX_QUEEN_MOVES = 15;
    public void playGame() {
        boolean isGameOver = false;
        Color winner = null;
        Color playersTurn = Color.WHITE;
        int queenMoves = 0;
        while(!isGameOver) {
            gameBoard.printBoard();
            ArrayList<Pawn[][]> possibleMoves = gameBoard.allPossibleMoves(playersTurn);
            if(possibleMoves.isEmpty()) {
                winner = playersTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
                isGameOver = true;
            } else if (playersTurn == Color.WHITE) {
                SimpleEntry<Pawn[][], Pawn> move = whitePlayer.move();
                Pawn[][] playersMove = move.getKey();
                if(checkIfMoveIsPossible(playersMove, possibleMoves)) {
                    gameBoard.move(playersMove);
                    playersTurn = Color.BLACK;
                }
                if(move.getValue() == Pawn.WHITE_QUEEN) {
                    queenMoves++;
                } else {
                    queenMoves = 0;
                }
            } else {
                SimpleEntry<Pawn[][], Pawn> move = blackPlayer.move();
                Pawn[][] playersMove = move.getKey();
                if(checkIfMoveIsPossible(playersMove, possibleMoves)) {
                    gameBoard.move(playersMove);
                    playersTurn = Color.WHITE;
                }
                if(move.getValue() == Pawn.BLACK_QUEEN) {
                    queenMoves++;
                } else {
                    queenMoves = 0;
                }
            }
            if(queenMoves == MAX_QUEEN_MOVES) {
                isGameOver = true;
            }
        }
        if (winner == null) {
            System.out.println("It's draw!");
        }
        else {
            switch (winner) {
                case WHITE -> System.out.println("White player has won!");
                case BLACK -> System.out.println("Black player has won!");
            }
        }
    }
    private boolean checkIfMoveIsPossible(Pawn[][] playersMove, ArrayList<Pawn[][]> possibleMoves) {
        for(Pawn[][] move : possibleMoves) {
            if (Arrays.deepEquals(playersMove, move)) {
                return true;
            }
        }
        return false;
    }
}

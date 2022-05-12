import java.awt.*;
import java.util.ArrayList;

public class Board {
    private PlayersTurn playersTurn;
    private Pawn[][] board;
    public static final int MAX_X_CORD = 7;
    public static final int MAX_Y_CORD = 7;
    public void move(Pawn[][] board) {
        this.board = board;
        playersTurn = playersTurn == PlayersTurn.BLACK ? PlayersTurn.WHITE : PlayersTurn.BLACK;
    }
    public int countElements(Pawn[][] board, Color color) {
        int pawnNum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (color == Color.BLACK && (board[i][j] == Pawn.BLACK || board[i][j] == Pawn.BLACK_QUEEN)) {
                    pawnNum++;
                } else if (color == Color.WHITE && (board[i][j] == Pawn.WHITE || board[i][j] == Pawn.WHITE_QUEEN)) {
                    pawnNum++;
                }
            }
        }
        return pawnNum;
    }
    public ArrayList<Pawn[][]> allPossibleMoves(Color color) {
        ArrayList<Pawn[][]> possibleMoves = new ArrayList<>();
        Color enemyColor = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        int currentEnemyState = countElements(board, enemyColor);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (color == Color.BLACK && (board[i][j] == Pawn.BLACK || board[i][j] == Pawn.BLACK_QUEEN)) {
                    ArrayList<Pawn[][]> pawnPossibleMoves = possibleMoves(new Point(i, j));
                    for (Pawn[][] boardState : pawnPossibleMoves) {
                        if(countElements(boardState, enemyColor) < currentEnemyState) {
                            possibleMoves.clear();
                            possibleMoves.add(boardState);
                            currentEnemyState = countElements(boardState, enemyColor);
                        } else if (countElements(boardState, enemyColor) == currentEnemyState) {
                            possibleMoves.add(boardState);
                        }
                    }
                } else if (color == Color.WHITE && (board[i][j] == Pawn.WHITE || board[i][j] == Pawn.WHITE_QUEEN)) {
                    ArrayList<Pawn[][]> pawnPossibleMoves = possibleMoves(new Point(i, j));
                    for (Pawn[][] boardState : pawnPossibleMoves) {
                        if(countElements(boardState, enemyColor) < currentEnemyState) {
                            possibleMoves.clear();
                            possibleMoves.add(boardState);
                            currentEnemyState = countElements(boardState, enemyColor);
                        } else if (countElements(boardState, enemyColor) == currentEnemyState) {
                            possibleMoves.add(boardState);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }
    private ArrayList<Pawn[][]> possibleBeatings(Point coordinates) {
        Pawn type = board[coordinates.x][coordinates.y];
        Color plrColor = type == Pawn.BLACK || type == Pawn.BLACK_QUEEN ? Color.BLACK : Color.WHITE;
        if(type == Pawn.BLACK) {
            if(coordinates.y > 1 && coordinates.x < MAX_X_CORD - 1 && (board[coordinates.x + 1][coordinates.y - 1] == Pawn.WHITE ||
                    board[coordinates.x + 1][coordinates.y - 1] == Pawn.WHITE_QUEEN) && board[coordinates.x + 2][coordinates.y - 2] == Pawn.EMPTY) {

                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y - 1] = Pawn.BLACK;

            } else if(coordinates.y < MAX_Y_CORD - 1 && coordinates.x < MAX_X_CORD - 1 && (board[coordinates.x + 1][coordinates.y + 1] == Pawn.WHITE ||
                    board[coordinates.x + 1][coordinates.y + 1] == Pawn.WHITE_QUEEN) && board[coordinates.x + 2][coordinates.y + 2] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y + 1] = Pawn.BLACK;

            } else if(coordinates.y < MAX_Y_CORD - 1 && coordinates.x > 1 && (board[coordinates.x - 1][coordinates.y + 1] == Pawn.WHITE ||
                    board[coordinates.x - 1][coordinates.y + 1] == Pawn.WHITE_QUEEN) && board[coordinates.x - 2][coordinates.y + 2] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y + 1] = Pawn.BLACK;

            }
        }
        else if (type == Pawn.WHITE) {
            if(coordinates.y != 0 && coordinates.x != MAX_X_CORD && board[coordinates.x - 1][coordinates.y - 1] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y - 1] = Pawn.WHITE;

            } else if(coordinates.y != MAX_Y_CORD && coordinates.x != MAX_X_CORD && board[coordinates.x - 1][coordinates.y + 1] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y + 1] = Pawn.WHITE;

            }
        }
        return null;
    }
    public ArrayList<Pawn[][]> possibleMoves(Point coordinates) {
        ArrayList<Pawn[][]> possibleMoves = new ArrayList<>();
        if(coordinates.x > MAX_X_CORD || coordinates.x < 0) {
            throw new IllegalArgumentException(coordinates.x + " must be in the range of 0-" + MAX_X_CORD);
        }
        if(coordinates.y > MAX_Y_CORD || coordinates.y < 0) {
            throw new IllegalArgumentException(coordinates.y + " must be in the range of 0-" + MAX_Y_CORD);
        }
        Pawn color = board[coordinates.x][coordinates.y];
        if(color == Pawn.BLACK) {
            if(coordinates.y != 0 && coordinates.x != MAX_X_CORD && board[coordinates.x + 1][coordinates.y - 1] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y - 1] = Pawn.BLACK;
                possibleMoves.add(move);
            } else if(coordinates.y != MAX_Y_CORD && coordinates.x != MAX_X_CORD && board[coordinates.x + 1][coordinates.y + 1] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y + 1] = Pawn.BLACK;
                possibleMoves.add(move);
            }
        }
        else if (color == Pawn.WHITE) {
            if(coordinates.y != 0 && coordinates.x != MAX_X_CORD && board[coordinates.x - 1][coordinates.y - 1] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y - 1] = Pawn.WHITE;
                possibleMoves.add(move);
            } else if(coordinates.y != MAX_Y_CORD && coordinates.x != MAX_X_CORD && board[coordinates.x - 1][coordinates.y + 1] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y + 1] = Pawn.WHITE;
                possibleMoves.add(move);
            }
        } else if(color == Pawn.BLACK_QUEEN || color == Pawn.WHITE_QUEEN) {
            int startRowUp = coordinates.x++;
            int startRowDown = coordinates.x--;
            int startColumnUp = coordinates.y++;
            int startColumnDown = coordinates.y--;
            while (startRowUp <= MAX_X_CORD && board[startRowUp][coordinates.y] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowUp][coordinates.y] = color;
                possibleMoves.add(move);
                startRowUp++;
            }
            while (startRowDown >= 0 && board[startRowDown][coordinates.y] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowDown][coordinates.y] = color;
                possibleMoves.add(move);
                startRowUp--;
            }
            while (startColumnUp <= MAX_Y_CORD && board[coordinates.x][startColumnUp] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x][startColumnUp] = color;
                possibleMoves.add(move);
                startColumnUp++;
            }
            while (startColumnDown >= 0 && board[coordinates.x][startColumnDown] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x][startColumnDown] = color;
                possibleMoves.add(move);
                startColumnDown--;
            }
            startRowUp = coordinates.x++;
            startRowDown = coordinates.x--;
            startColumnUp = coordinates.y++;
            startColumnDown = coordinates.y--;
            while (startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && board[startRowUp][startColumnUp] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowUp][startColumnUp] = color;
                possibleMoves.add(move);
                startRowUp++;
                startColumnUp++;
            }
            startRowUp = coordinates.x++;
            startColumnUp = coordinates.y++;
            while (startRowUp <= MAX_X_CORD && startColumnDown >= 0 && board[startRowUp][startColumnDown] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowUp][startColumnDown] = color;
                possibleMoves.add(move);
                startRowUp++;
                startColumnDown--;
            }
            startRowUp = coordinates.x++;
            startColumnDown = coordinates.y--;
            while (startRowDown >= 0 && startColumnDown >= 0 && board[startRowDown][startColumnDown] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowDown][startColumnDown] = color;
                possibleMoves.add(move);
                startRowDown--;
                startColumnDown--;
            }
            startRowDown = coordinates.x--;
            startColumnDown = coordinates.y--;
            while (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && board[startRowDown][startColumnUp] == Pawn.EMPTY) {
                Pawn[][] move = board.clone();
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowDown][startColumnUp] = color;
                possibleMoves.add(move);
                startRowDown--;
                startColumnUp++;
            }
        }
        return possibleMoves;
    }
}

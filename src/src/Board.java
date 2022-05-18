import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Pawn[][] board;
    public static final int MAX_X_CORD = 7;
    public static final int MAX_Y_CORD = 7;
    public void move(Pawn[][] board) {
        this.board = board;
    }
    public void printBoard() {
        System.out.println("  A B C D E F G H ");
        for (int i = 0; i < board.length; i++) {
            //System.out.println();
            System.out.print((i + 1) + " ");
            for (int j = 0; j < board[0].length; j++) {
                switch (board[i][j]) {
                    case BLACK -> System.out.print("b ");
                    case WHITE -> System.out.print("w ");
                    case BLACK_QUEEN -> System.out.print("B ");
                    case WHITE_QUEEN -> System.out.print("W ");
                    case EMPTY -> System.out.print("* ");
                }
            }
            System.out.println();
        }
    }
    public Board(Pawn[][] brd) {
        board = brd;
    }
    public Board() {
        board = new Pawn[MAX_X_CORD + 1][MAX_Y_CORD + 1];
        for (int i = 0; i <= MAX_X_CORD; i++) {
            for (int j = 0; j <= MAX_Y_CORD; j++) {
                if (i <= 2) {
                    if (i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0) {
                        board[i][j] = Pawn.BLACK;
                    } else {
                        board[i][j] = Pawn.EMPTY;
                    }
                } else if (i >= 5) {
                    if (i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0) {
                        board[i][j] = Pawn.WHITE;
                    } else {
                        board[i][j] = Pawn.EMPTY;
                    }
                } else {
                    board[i][j] = Pawn.EMPTY;
                }
            }
        }
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

    public Pawn[][] getBoardCopy() {
        Pawn[][] nv = new Pawn[board.length][board[0].length];
        for (int i = 0; i < nv.length; i++)
            nv[i] = Arrays.copyOf(board[i], board[i].length);
        return nv;
    }
    public Pawn[][] getBoard() {
        return board;
    }
    public Pawn[][] getBoardCopy(Pawn[][] board) {
        Pawn[][] nv = new Pawn[board.length][board[0].length];
        for (int i = 0; i < nv.length; i++)
            nv[i] = Arrays.copyOf(board[i], board[i].length);
        return nv;
    }
    public int countQueens(Color color) {
        Pawn pawnToLookFor = color == Color.WHITE ? Pawn.WHITE_QUEEN : Pawn.BLACK_QUEEN;
        int queenNum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == pawnToLookFor) {
                    queenNum++;
                }
            }
        }
        return queenNum;
    }

    public boolean queensAlone() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != Pawn.BLACK_QUEEN && board[i][j] != Pawn.WHITE_QUEEN && board[i][j] != Pawn.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<Pawn[][]> possibleBeatings(Point coordinates, Pawn[][] board) {
        Pawn type = board[coordinates.x][coordinates.y];
        Color plrColor = type == Pawn.BLACK || type == Pawn.BLACK_QUEEN ? Color.BLACK : Color.WHITE;
        ArrayList<Pawn[][]> beatingsToChooseFrom = new ArrayList<>();
        if(type == Pawn.BLACK) {
            if(coordinates.y > 1 && coordinates.x < MAX_X_CORD - 1 && (board[coordinates.x + 1][coordinates.y - 1] == Pawn.WHITE ||
                    board[coordinates.x + 1][coordinates.y - 1] == Pawn.WHITE_QUEEN) && board[coordinates.x + 2][coordinates.y - 2] == Pawn.EMPTY) {

                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y - 1] = Pawn.EMPTY;
                if(coordinates.x + 2 != MAX_X_CORD)
                    move[coordinates.x + 2][coordinates.y - 2] = Pawn.BLACK;
                else
                    move[coordinates.x + 2][coordinates.y - 2] = Pawn.BLACK_QUEEN;
                //move[coordinates.x + 2][coordinates.y - 2] = Pawn.BLACK;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x + 2, coordinates.y - 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            } if(coordinates.y < MAX_Y_CORD - 1 && coordinates.x < MAX_X_CORD - 1 && (board[coordinates.x + 1][coordinates.y + 1] == Pawn.WHITE ||
                    board[coordinates.x + 1][coordinates.y + 1] == Pawn.WHITE_QUEEN) && board[coordinates.x + 2][coordinates.y + 2] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y + 1] = Pawn.EMPTY;
                if(coordinates.x + 2 != MAX_X_CORD)
                    move[coordinates.x + 2][coordinates.y + 2] = Pawn.BLACK;
                else
                    move[coordinates.x + 2][coordinates.y + 2] = Pawn.BLACK_QUEEN;
                //move[coordinates.x + 2][coordinates.y + 2] = Pawn.BLACK;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x + 2, coordinates.y + 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            } if(coordinates.y < MAX_Y_CORD - 1 && coordinates.x > 1 && (board[coordinates.x - 1][coordinates.y + 1] == Pawn.WHITE ||
                    board[coordinates.x - 1][coordinates.y + 1] == Pawn.WHITE_QUEEN) && board[coordinates.x - 2][coordinates.y + 2] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y + 1] = Pawn.EMPTY;
                move[coordinates.x - 2][coordinates.y + 2] = Pawn.BLACK;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x - 2, coordinates.y + 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            } if(coordinates.y > 1 && coordinates.x > 1 && (board[coordinates.x - 1][coordinates.y - 1] == Pawn.WHITE ||
                    board[coordinates.x - 1][coordinates.y - 1] == Pawn.WHITE_QUEEN) && board[coordinates.x - 2][coordinates.y - 2] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y - 1] = Pawn.EMPTY;
                move[coordinates.x - 2][coordinates.y - 2] = Pawn.BLACK;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x - 2, coordinates.y - 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            }
            if (beatingsToChooseFrom.isEmpty()) {
                return null;
            } else {
                ArrayList<Pawn[][]> bestBeatings = new ArrayList<>();
                bestBeatings.add(beatingsToChooseFrom.get(0));
                for (int i = 1; i < beatingsToChooseFrom.size(); i++ ) {
                    if(countElements(bestBeatings.get(0), Color.WHITE) == countElements(beatingsToChooseFrom.get(i), Color.WHITE)) {
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    } else if (countElements(bestBeatings.get(0), Color.WHITE) > countElements(beatingsToChooseFrom.get(i), Color.WHITE)) {
                        bestBeatings.clear();
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    }
                }
                return bestBeatings;
            }

        }
        else if (type == Pawn.WHITE) {
            if(coordinates.y > 1 && coordinates.x < MAX_X_CORD - 1 && (board[coordinates.x + 1][coordinates.y - 1] == Pawn.BLACK ||
                    board[coordinates.x + 1][coordinates.y - 1] == Pawn.BLACK_QUEEN) && board[coordinates.x + 2][coordinates.y - 2] == Pawn.EMPTY) {

                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y - 1] = Pawn.EMPTY;
                move[coordinates.x + 2][coordinates.y - 2] = Pawn.WHITE;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x + 2, coordinates.y - 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            } else if(coordinates.y < MAX_Y_CORD - 1 && coordinates.x < MAX_X_CORD - 1 && (board[coordinates.x + 1][coordinates.y + 1] == Pawn.BLACK ||
                    board[coordinates.x + 1][coordinates.y + 1] == Pawn.BLACK_QUEEN) && board[coordinates.x + 2][coordinates.y + 2] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y + 1] = Pawn.EMPTY;
                move[coordinates.x + 2][coordinates.y + 2] = Pawn.WHITE;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x + 2, coordinates.y + 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            } else if(coordinates.y < MAX_Y_CORD - 1 && coordinates.x > 1 && (board[coordinates.x - 1][coordinates.y + 1] == Pawn.BLACK ||
                    board[coordinates.x - 1][coordinates.y + 1] == Pawn.BLACK_QUEEN) && board[coordinates.x - 2][coordinates.y + 2] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y + 1] = Pawn.EMPTY;
                if(coordinates.x - 2 != 0)
                    move[coordinates.x - 2][coordinates.y + 2] = Pawn.WHITE;
                else
                    move[coordinates.x - 2][coordinates.y + 2] = Pawn.WHITE_QUEEN;
                //move[coordinates.x - 2][coordinates.y + 2] = Pawn.WHITE;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x - 2, coordinates.y + 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            } else if(coordinates.y > 1 && coordinates.x > 1 && (board[coordinates.x - 1][coordinates.y - 1] == Pawn.BLACK ||
                    board[coordinates.x - 1][coordinates.y - 1] == Pawn.BLACK_QUEEN) && board[coordinates.x - 2][coordinates.y - 2] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x - 1][coordinates.y - 1] = Pawn.EMPTY;
                if(coordinates.x - 2 != 0)
                    move[coordinates.x - 2][coordinates.y - 2] = Pawn.WHITE;
                else
                    move[coordinates.x - 2][coordinates.y - 2] = Pawn.WHITE_QUEEN;
                //move[coordinates.x - 2][coordinates.y - 2] = Pawn.WHITE;
                ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x - 2, coordinates.y - 2), move);
                if(possibleBeatingsFurth != null) {
                    beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                } else {
                    beatingsToChooseFrom.add(move);
                }

            }
            if (beatingsToChooseFrom.isEmpty()) {
                return null;
            } else {
                ArrayList<Pawn[][]> bestBeatings = new ArrayList<>();
                bestBeatings.add(beatingsToChooseFrom.get(0));
                for (int i = 1; i < beatingsToChooseFrom.size(); i++ ) {
                    if(countElements(bestBeatings.get(0), Color.BLACK) == countElements(beatingsToChooseFrom.get(i), Color.BLACK)) {
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    } else if (countElements(bestBeatings.get(0), Color.BLACK) > countElements(beatingsToChooseFrom.get(i), Color.BLACK)) {
                        bestBeatings.clear();
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    }
                }
                return bestBeatings;
            }
        } else if (type == Pawn.BLACK_QUEEN) {
            int startRowUp = coordinates.x + 1;
            int startRowDown = coordinates.x - 1;
            int startColumnUp = coordinates.y + 1;
            int startColumnDown = coordinates.y - 1;
            while (startRowUp <= MAX_X_CORD && board[startRowUp][coordinates.y] == Pawn.EMPTY) {
                startRowUp++;
            }
            //System.out.println(startRowUp <= MAX_X_CORD);
            if (startRowUp <= MAX_X_CORD && (board[startRowUp][coordinates.y] == Pawn.WHITE || board[startRowUp][coordinates.y] == Pawn.WHITE_QUEEN)) {
                int cordX = startRowUp;
                int cordY = coordinates.y;
                startRowUp++;
                while (startRowUp <= MAX_X_CORD && board[startRowUp][coordinates.y] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowUp][coordinates.y] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowUp, coordinates.y), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowUp++;
                }
            }
            //System.out.println(coordinates.x);
            //System.out.println(startRowDown);
            while (startRowDown >= 0 && board[startRowDown][coordinates.y] == Pawn.EMPTY) {
                startRowDown--;
            }
            if (startRowDown >= 0 && (board[startRowDown][coordinates.y] == Pawn.WHITE || board[startRowDown][coordinates.y] == Pawn.WHITE_QUEEN)) {
                int cordX = startRowDown;
                int cordY = coordinates.y;
                startRowDown--;
                while (startRowDown >= 0 && board[startRowDown][coordinates.y] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowDown][coordinates.y] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowDown, coordinates.y), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowDown--;
                }
            }
            while (startColumnUp <= MAX_Y_CORD && board[coordinates.x][startColumnUp] == Pawn.EMPTY) {
                startColumnUp++;
            }
            if(startColumnUp <= MAX_Y_CORD && (board[coordinates.x][startColumnUp] == Pawn.WHITE || board[coordinates.x][startColumnUp] == Pawn.WHITE_QUEEN)) {
                int cordX = coordinates.x;
                int cordY = startColumnUp;
                startColumnUp++;
                while (startColumnUp <= MAX_Y_CORD && board[coordinates.x][startColumnUp] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[coordinates.x][startColumnUp] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x, startColumnUp), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startColumnUp++;
                }
            }
            while (startColumnDown >= 0 && board[coordinates.x][startColumnDown] == Pawn.EMPTY) {
                startColumnDown--;
            }
            if(startColumnDown >= 0 && (board[coordinates.x][startColumnDown] == Pawn.WHITE || board[coordinates.x][startColumnDown] == Pawn.WHITE_QUEEN)) {
                startColumnDown--;
                int cordX = coordinates.x;
                int cordY = startColumnDown;
                while (startColumnDown >= 0 && board[coordinates.x][startColumnDown] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[coordinates.x][startColumnDown] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x, startColumnDown), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startColumnDown--;
                }
            }
            startRowUp = coordinates.x + 1;
            startRowDown = coordinates.x - 1;
            startColumnUp = coordinates.y + 1;
            startColumnDown = coordinates.y - 1;
            while (startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && board[startRowUp][startColumnUp] == Pawn.EMPTY) {
                startRowUp++;
                startColumnUp++;
            }
            if(startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && (board[startRowUp][startColumnUp] == Pawn.WHITE || board[startRowUp][startColumnUp] == Pawn.WHITE_QUEEN)) {
                int cordX = startRowUp;
                int cordY = startColumnUp;
                startRowUp++;
                startColumnUp++;
                while (startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && board[startRowUp][startColumnUp] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowUp][startColumnUp] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowUp, startColumnUp), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowUp++;
                    startColumnUp++;
                }
            }
            startRowUp = coordinates.x + 1;
            startColumnUp = coordinates.y + 1;
            while (startRowUp <= MAX_X_CORD && startColumnDown >= 0 && board[startRowUp][startColumnDown] == Pawn.EMPTY) {
                startRowUp++;
                startColumnDown--;
            }
            if(startRowUp <= MAX_X_CORD && startColumnDown >= 0 && (board[startRowUp][startColumnDown] == Pawn.WHITE || board[startRowUp][startColumnDown] == Pawn.WHITE_QUEEN)) {
                int cordX = startRowUp;
                int cordY = startColumnDown;
                startRowUp++;
                startColumnDown--;
                while (startRowUp <= MAX_X_CORD && startColumnDown >= 0 && board[startRowUp][startColumnDown] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowUp][startColumnDown] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowUp, startColumnDown), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowUp++;
                    startColumnDown--;
                }
            }
            startRowUp = coordinates.x + 1;
            startColumnDown = coordinates.y - 1;
            while (startRowDown >= 0 && startColumnDown >= 0 && board[startRowDown][startColumnDown] == Pawn.EMPTY) {
                startRowDown--;
                startColumnDown--;
            }
            if (startRowDown >= 0 && startColumnDown >= 0 && (board[startRowDown][startColumnDown] == Pawn.WHITE || board[startRowDown][startColumnDown] == Pawn.WHITE_QUEEN)) {
                int cordX = startRowDown;
                int cordY = startColumnDown;
                startRowDown--;
                startColumnDown--;
                while (startRowDown >= 0 && startColumnDown >= 0 && board[startRowDown][startColumnDown] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowDown][startColumnDown] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowDown, startColumnDown), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowDown--;
                    startColumnDown--;
                }
            }
            startRowDown = coordinates.x - 1;
            startColumnDown = coordinates.y - 1;
            //System.out.println(startRowDown >= 0);
            while (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && board[startRowDown][startColumnUp] == Pawn.EMPTY) {
                startRowDown--;
                startColumnUp++;
            }
            if (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && (board[startRowDown][startColumnUp] == Pawn.WHITE || board[startRowDown][startColumnUp] == Pawn.WHITE_QUEEN)) {
                int cordX = startRowDown;
                int cordY = startColumnUp;
                startRowDown--;
                startColumnUp++;
                while (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && board[startRowDown][startColumnUp] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowDown][startColumnUp] = Pawn.BLACK_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowDown, startColumnUp), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowDown--;
                    startColumnUp++;
                }
            }
            if (beatingsToChooseFrom.isEmpty()) {
                return null;
            } else {
                ArrayList<Pawn[][]> bestBeatings = new ArrayList<>();
                bestBeatings.add(beatingsToChooseFrom.get(0));
                for (int i = 1; i < beatingsToChooseFrom.size(); i++ ) {
                    if(countElements(bestBeatings.get(0), Color.WHITE) == countElements(beatingsToChooseFrom.get(i), Color.WHITE)) {
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    } else if (countElements(bestBeatings.get(0), Color.WHITE) > countElements(beatingsToChooseFrom.get(i), Color.WHITE)) {
                        bestBeatings.clear();
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    }
                }
                return bestBeatings;
            }

        } else if (type == Pawn.WHITE_QUEEN) {
            int startRowUp = coordinates.x + 1;
            int startRowDown = coordinates.x - 1;
            int startColumnUp = coordinates.y + 1;
            int startColumnDown = coordinates.y - 1;
            while (startRowUp <= MAX_X_CORD && board[startRowUp][coordinates.y] == Pawn.EMPTY) {
                startRowUp++;
            }
            if (startRowUp <= MAX_X_CORD && (board[startRowUp][coordinates.y] == Pawn.BLACK || board[startRowUp][coordinates.y] == Pawn.BLACK_QUEEN)) {
                int cordX = startRowUp;
                int cordY = coordinates.y;
                startRowUp++;
                while (startRowUp <= MAX_X_CORD && board[startRowUp][coordinates.y] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowUp][coordinates.y] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowUp, coordinates.y), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowUp++;
                }
            }
            while (startRowDown >= 0 && board[startRowDown][coordinates.y] == Pawn.EMPTY) {
                startRowDown--;
            }
            if (startRowDown >= 0 && (board[startRowDown][coordinates.y] == Pawn.BLACK || board[startRowDown][coordinates.y] == Pawn.BLACK_QUEEN)) {
                int cordX = startRowDown;
                int cordY = coordinates.y;
                startRowDown--;
                while (startRowDown >= 0 && board[startRowDown][coordinates.y] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowDown][coordinates.y] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowDown, coordinates.y), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowDown--;
                }
            }
            while (startColumnUp <= MAX_Y_CORD && board[coordinates.x][startColumnUp] == Pawn.EMPTY) {
                startColumnUp++;
            }
            if(startColumnUp <= MAX_Y_CORD && (board[coordinates.x][startColumnUp] == Pawn.BLACK || board[coordinates.x][startColumnUp] == Pawn.BLACK_QUEEN)) {
                int cordX = coordinates.x;
                int cordY = startColumnUp;
                startColumnUp++;
                while (startColumnUp <= MAX_Y_CORD && board[coordinates.x][startColumnUp] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[coordinates.x][startColumnUp] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x, startColumnUp), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startColumnUp++;
                }
            }
            while (startColumnDown >= 0 && board[coordinates.x][startColumnDown] == Pawn.EMPTY) {
                startColumnDown--;
            }
            if(startColumnDown >= 0 && (board[coordinates.x][startColumnDown] == Pawn.BLACK || board[coordinates.x][startColumnDown] == Pawn.BLACK_QUEEN)) {
                int cordX = coordinates.x;
                int cordY = startColumnDown;
                startColumnDown--;
                while (startColumnDown >= 0 && board[coordinates.x][startColumnDown] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[coordinates.x][startColumnDown] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(coordinates.x, startColumnDown), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startColumnDown--;
                }
            }
            startRowUp = coordinates.x + 1;
            startRowDown = coordinates.x - 1;
            startColumnUp = coordinates.y + 1;
            startColumnDown = coordinates.y - 1;
            while (startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && board[startRowUp][startColumnUp] == Pawn.EMPTY) {
                startRowUp++;
                startColumnUp++;
            }
            if(startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && (board[startRowUp][startColumnUp] == Pawn.BLACK || board[startRowUp][startColumnUp] == Pawn.BLACK_QUEEN)) {
                int cordX = startRowUp;
                int cordY = startColumnUp;
                startRowUp++;
                startColumnUp++;
                while (startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && board[startRowUp][startColumnUp] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowUp][startColumnUp] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowUp, startColumnUp), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowUp++;
                    startColumnUp++;
                }
            }
            startRowUp = coordinates.x+1;
            startColumnUp = coordinates.y+1;
            while (startRowUp <= MAX_X_CORD && startColumnDown >= 0 && board[startRowUp][startColumnDown] == Pawn.EMPTY) {
                startRowUp++;
                startColumnDown--;
            }
            if(startRowUp <= MAX_X_CORD && startColumnDown >= 0 && (board[startRowUp][startColumnDown] == Pawn.BLACK || board[startRowUp][startColumnDown] == Pawn.BLACK_QUEEN)) {
                int cordX = startRowUp;
                int cordY = startColumnDown;
                startRowUp++;
                startColumnDown--;
                while (startRowUp <= MAX_X_CORD && startColumnDown >= 0 && board[startRowUp][startColumnDown] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowUp][startColumnDown] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowUp, startColumnDown), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowUp++;
                    startColumnDown--;
                }
            }
            startRowUp = coordinates.x + 1;
            startColumnDown = coordinates.y - 1;
            while (startRowDown >= 0 && startColumnDown >= 0 && board[startRowDown][startColumnDown] == Pawn.EMPTY) {
                startRowDown--;
                startColumnDown--;
            }
            if (startRowDown >= 0 && startColumnDown >= 0 && (board[startRowDown][startColumnDown] == Pawn.BLACK || board[startRowDown][startColumnDown] == Pawn.BLACK_QUEEN)) {
                int cordX = startRowDown;
                int cordY = startColumnDown;
                startRowDown--;
                startColumnDown--;
                while (startRowDown >= 0 && startColumnDown >= 0 && board[startRowDown][startColumnDown] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowDown][startColumnDown] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowDown, startColumnDown), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowDown--;
                    startColumnDown--;
                }
            }
            startRowDown = coordinates.x - 1;
            startColumnDown = coordinates.y - 1;
            while (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && board[startRowDown][startColumnUp] == Pawn.EMPTY) {
                startRowDown--;
                startColumnUp++;
            }
            if (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && (board[startRowDown][startColumnUp] == Pawn.BLACK || board[startRowDown][startColumnUp] == Pawn.BLACK_QUEEN)) {
                int cordX = startRowDown;
                int cordY = startColumnUp;
                startRowDown--;
                startColumnUp++;
                while (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && board[startRowDown][startColumnUp] == Pawn.EMPTY) {
                    Pawn[][] move = getBoardCopy(board);
                    move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                    move[cordX][cordY] = Pawn.EMPTY;
                    move[startRowDown][startColumnUp] = Pawn.WHITE_QUEEN;
                    ArrayList<Pawn[][]> possibleBeatingsFurth = possibleBeatings(new Point(startRowDown, startColumnUp), move);
                    if(possibleBeatingsFurth != null) {
                        beatingsToChooseFrom.addAll(possibleBeatingsFurth);
                    } else {
                        beatingsToChooseFrom.add(move);
                    }
                    startRowDown--;
                    startColumnUp++;
                }
            }
            if (beatingsToChooseFrom.isEmpty()) {
                return null;
            } else {
                ArrayList<Pawn[][]> bestBeatings = new ArrayList<>();
                bestBeatings.add(beatingsToChooseFrom.get(0));
                for (int i = 1; i < beatingsToChooseFrom.size(); i++ ) {
                    if(countElements(bestBeatings.get(0), Color.BLACK) == countElements(beatingsToChooseFrom.get(i), Color.BLACK)) {
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    } else if (countElements(bestBeatings.get(0), Color.BLACK) > countElements(beatingsToChooseFrom.get(i), Color.BLACK)) {
                        bestBeatings.clear();
                        bestBeatings.add(beatingsToChooseFrom.get(i));
                    }
                }
                return bestBeatings;
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
        ArrayList<Pawn[][]> possibleBeatings = possibleBeatings(coordinates, this.board);
        if (possibleBeatings != null) {
            return possibleBeatings;
        }
        if(color == Pawn.BLACK) {
            if(coordinates.y != 0 && coordinates.x != MAX_X_CORD && board[coordinates.x + 1][coordinates.y - 1] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                if(coordinates.x + 1 != MAX_X_CORD)
                    move[coordinates.x + 1][coordinates.y - 1] = Pawn.BLACK;
                else
                    move[coordinates.x + 1][coordinates.y - 1] = Pawn.BLACK_QUEEN;
                possibleMoves.add(move);
            }
            if(coordinates.y != MAX_Y_CORD && coordinates.x != MAX_X_CORD && board[coordinates.x + 1][coordinates.y + 1] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x + 1][coordinates.y + 1] = Pawn.BLACK;
                possibleMoves.add(move);
            }
        }
        else if (color == Pawn.WHITE) {
            if(coordinates.y != 0 && coordinates.x != 0 && board[coordinates.x - 1][coordinates.y - 1] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                if(coordinates.x - 1 != 0)
                    move[coordinates.x - 1][coordinates.y - 1] = Pawn.WHITE;
                else
                    move[coordinates.x - 1][coordinates.y - 1] = Pawn.WHITE_QUEEN;
                possibleMoves.add(move);
            }
            if(coordinates.y != MAX_Y_CORD && coordinates.x != 0 && board[coordinates.x - 1][coordinates.y + 1] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                if(coordinates.x - 1 != 0)
                    move[coordinates.x - 1][coordinates.y + 1] = Pawn.WHITE;
                else
                    move[coordinates.x - 1][coordinates.y + 1] = Pawn.WHITE_QUEEN;
                //move[coordinates.x - 1][coordinates.y + 1] = Pawn.WHITE;
                possibleMoves.add(move);
            }
        } else if(color == Pawn.BLACK_QUEEN || color == Pawn.WHITE_QUEEN) {
            int startRowUp = coordinates.x + 1;
            int startRowDown = coordinates.x - 1;
            int startColumnUp = coordinates.y + 1;
            int startColumnDown = coordinates.y - 1;
            while (startRowUp <= MAX_X_CORD && board[startRowUp][coordinates.y] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowUp][coordinates.y] = color;
                possibleMoves.add(move);
                startRowUp++;
            }
            while (startRowDown >= 0 && board[startRowDown][coordinates.y] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowDown][coordinates.y] = color;
                possibleMoves.add(move);
                startRowDown--;
            }
            while (startColumnUp <= MAX_Y_CORD && board[coordinates.x][startColumnUp] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x][startColumnUp] = color;
                possibleMoves.add(move);
                startColumnUp++;
            }
            while (startColumnDown >= 0 && board[coordinates.x][startColumnDown] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[coordinates.x][startColumnDown] = color;
                possibleMoves.add(move);
                startColumnDown--;
            }
            startRowUp = coordinates.x+1;
            startRowDown = coordinates.x-1;
            startColumnUp = coordinates.y+1;
            startColumnDown = coordinates.y-1;
            while (startRowUp <= MAX_X_CORD && startColumnUp <= MAX_Y_CORD && board[startRowUp][startColumnUp] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowUp][startColumnUp] = color;
                possibleMoves.add(move);
                startRowUp++;
                startColumnUp++;
            }
            startRowUp = coordinates.x+1;
            startColumnUp = coordinates.y+1;
            while (startRowUp <= MAX_X_CORD && startColumnDown >= 0 && board[startRowUp][startColumnDown] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowUp][startColumnDown] = color;
                possibleMoves.add(move);
                startRowUp++;
                startColumnDown--;
            }
            startRowUp = coordinates.x+1;
            startColumnDown = coordinates.y-1;
            while (startRowDown >= 0 && startColumnDown >= 0 && board[startRowDown][startColumnDown] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
                move[coordinates.x][coordinates.y] = Pawn.EMPTY;
                move[startRowDown][startColumnDown] = color;
                possibleMoves.add(move);
                startRowDown--;
                startColumnDown--;
            }
            startRowDown = coordinates.x-1;
            startColumnDown = coordinates.y-1;
            while (startRowDown >= 0 && startColumnUp <= MAX_Y_CORD && board[startRowDown][startColumnUp] == Pawn.EMPTY) {
                Pawn[][] move = getBoardCopy(board);
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

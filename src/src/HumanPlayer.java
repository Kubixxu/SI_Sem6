import java.awt.*;
import java.util.AbstractMap;
import java.util.Scanner;

public class HumanPlayer implements CheckersPlayer {
    public HumanPlayer(Board board) {
        gameBoard = board;
    }
    private Board gameBoard;
    @Override
    public AbstractMap.SimpleEntry<Pawn[][], Pawn> move() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give the pawn that you want to move");
        String pawnsCords = scanner.nextLine();
        while(!isInputValid(pawnsCords)) {
            System.out.println("Please give valid coordinates");
            pawnsCords = scanner.nextLine();
        }
        System.out.println("Give the end coordinates");
        String endCords = scanner.nextLine();
        while(!isInputValid(endCords)) {
            System.out.println("Please give valid coordinates");
            endCords = scanner.nextLine();
        }
        System.out.println("Give coordinates of pawns that you beaten, separated by ;");
        String pawnsBeated = scanner.nextLine();
        String[] singleCords = pawnsBeated.split(";");
        if (singleCords.length == 1 && singleCords[0].equals("")) {
            singleCords = new String[0];
        }
        while(!areCordsValid(singleCords)) {
            System.out.println("Please give valid coordinates");
            pawnsBeated = scanner.nextLine();
            singleCords = pawnsBeated.split(";");
        }

        Pawn[][] boardCopy = gameBoard.getBoardCopy();
        Pawn chosenPawn = boardCopy[mapStringCordsToPoint(pawnsCords).x][mapStringCordsToPoint(pawnsCords).y];
        boardCopy[mapStringCordsToPoint(pawnsCords).x][mapStringCordsToPoint(pawnsCords).y] = Pawn.EMPTY;
        boardCopy[mapStringCordsToPoint(endCords).x][mapStringCordsToPoint(endCords).y] = chosenPawn;
        for (String cord : singleCords) {
            boardCopy[mapStringCordsToPoint(cord).x][mapStringCordsToPoint(cord).y] = Pawn.EMPTY;
        }
        return new AbstractMap.SimpleEntry<>(boardCopy, chosenPawn);
    }
    private Point mapStringCordsToPoint(String pawnsCord) {
        int xCord;
        int yCord;
        xCord = Character.getNumericValue(pawnsCord.charAt(1)) - 1;
        yCord = pawnsCord.charAt(0) - 65;
        //System.out.println(xCord);
        //System.out.println(yCord);
        return new Point(xCord, yCord);
    }
    private boolean areCordsValid(String[] cordArray) {

        for (String cord : cordArray) {
            if(!isInputValid(cord)) {
                return false;
            }
        }
        return true;
    }
    private boolean isInputValid(String pawnsCord) {
        boolean isYCordValid = pawnsCord.charAt(0) == 'A' || pawnsCord.charAt(0) == 'B' || pawnsCord.charAt(0) == 'C' || pawnsCord.charAt(0) == 'D'
                || pawnsCord.charAt(0) == 'E' || pawnsCord.charAt(0) == 'F' || pawnsCord.charAt(0) == 'G' || pawnsCord.charAt(0) == 'H';
        boolean isXCordValid = Character.getNumericValue(pawnsCord.charAt(1)) < 9 && Character.getNumericValue(pawnsCord.charAt(1)) > 0;
        return isYCordValid && isXCordValid;
    }
}

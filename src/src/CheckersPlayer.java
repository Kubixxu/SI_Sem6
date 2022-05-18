import java.util.AbstractMap.SimpleEntry;

public interface CheckersPlayer {
    public Pawn[][] move();
    public Pawn[][] moveRandomly();
}

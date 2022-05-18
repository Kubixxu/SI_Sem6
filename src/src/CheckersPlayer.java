import java.util.AbstractMap.SimpleEntry;

public interface CheckersPlayer {
    public SimpleEntry<Pawn[][], Pawn> move();
    public SimpleEntry<Pawn[][], Pawn> moveRandomly();
}

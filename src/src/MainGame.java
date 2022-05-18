public class MainGame {
    public static void main(String[] args) {
        /*Board board = new Board();
        CheckersPlayer humanPlayer1 = new HumanPlayer(board);
        CheckersPlayer humanPlayer2 = new HumanPlayer(board);
        Game game = new Game(board, humanPlayer1, humanPlayer2);
        game.playGame();*/
        Board board = new Board();
        CheckersPlayer humanPlayer = new HumanPlayer(board, Color.WHITE);
        CheckersPlayer aiPlayer = new AiPlayer(board, Color.BLACK);
        Game game = new Game(board, humanPlayer, aiPlayer);
        game.playGame();
    }
}

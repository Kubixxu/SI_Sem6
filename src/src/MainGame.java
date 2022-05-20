public class MainGame {
    public static void main(String[] args) {
        /*Board board = new Board();
        CheckersPlayer humanPlayer1 = new HumanPlayer(board, Color.WHITE);
        CheckersPlayer aiPlayer1 = new AiPlayer(board, Color.BLACK);
        Game game = new Game(board, humanPlayer1, aiPlayer1);
        game.playGame();*/
        Board board = new Board();
        CheckersPlayer aiPlayer1 = new AiPlayer(board, Color.WHITE);
        CheckersPlayer aiPlayer2 = new AiPlayer2(board, Color.BLACK);
        Game game = new Game(board, aiPlayer1, aiPlayer2);
        long startTime = System.nanoTime();
        game.playGame();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Game lasted " + duration + " milliseconds");

    }
}

import java.io.IOException;
import java.util.Scanner;

public class CodenamesApplication {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("What board size would you like to play? The standard is 5 5");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        initializeBoard(rows, cols);
    }

    public static void initializeBoard(int rows, int cols) throws IOException {
        Board board = new Board(rows, cols);
        Gameplay gameplay = new Gameplay(board);
        gameplay.startGamePlay();
    }
}

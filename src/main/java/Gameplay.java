import java.util.List;
import java.util.Scanner;

import org.apache.commons.collections4.ListUtils;

import static java.lang.System.exit;

public class Gameplay {

    private static final Scanner scanner = new Scanner(System.in);
    private final Board board;
    private Integer redWordsRemaining = 9;
    private Integer blueWordsRemaining = 8;
    private String currentTeamTurn = "R";

    public Gameplay(Board board) {
        this.board = board;
    }

    public void startGamePlay() {
        board.spymasterView();

        System.out.println("\nRed spymaster, please enter your clue followed by number of words: " );
        String clue = scanner.next();
        int numWords = scanner.nextInt();

        if (numWords > redWordsRemaining) {
            System.out.println("\nThere are only " + redWordsRemaining + " words remaining. Please re-enter the number of words.");
            numWords = scanner.nextInt();
        }

        playTheRound(clue, numWords);
    }

    public void playTheRound(String clue, int numGuesses) {

        while (numGuesses > 0 && redWordsRemaining > 0 && blueWordsRemaining > 0) {
            updateBoard();
            if ("B".equals(currentTeamTurn)) {
                System.out.println("\nBlue player, enter your guess: ");
            } else {
                System.out.println("\nRed player, enter your guess: ");
            }

            String guess = scanner.next();
            Card guessedWordCard = errorCheckingGuess(guess);
            guessedWordCard.setGuessed(true);

            if ("R".equals(guessedWordCard.getColor())) {
                redWordsRemaining -= 1;
                if ("B".equals(currentTeamTurn)) {
                    updateBoard();
                    System.out.println("\nOh no! You guessed a red word. Your turn is over.");
                    switchTeams();
                } else {
                    numGuesses -= 1;
                    System.out.println("\nNice guess! " + numGuesses + " remaining.");
                    continue;
                }
            }

            if ("X".equals(guessedWordCard.getColor())) {
                if ("B".equals(currentTeamTurn)) {
                    System.out.println("Red team automatically wins!!!");
                } else if ("R".equals(currentTeamTurn)){
                    System.out.println("Blue team automatically wins!!!");
                }
                exit(0);
            }

            if ("B".equals(guessedWordCard.getColor())) {
                blueWordsRemaining -= 1;
                if ("R".equals(currentTeamTurn)) {
                    updateBoard();
                    System.out.println("\nOh no! You guessed a blue word. Your turn is over.");
                    switchTeams();
                } else {
                    numGuesses -= 1;
                    System.out.println("\nNice guess! " + numGuesses + " remaining.");
                    continue;
                }
            }

            if ("T".equals(guessedWordCard.getColor())) {
                updateBoard();
                System.out.println("\nYou guessed a tan word. Your turn is over.");
                switchTeams();
            }
        }
        if (blueWordsRemaining == 0) {
            System.out.println("Blue team wins!");
        } else if (redWordsRemaining == 0) {
            System.out.println("Red team wins!");
        }
        switchTeams();
    }

    public void switchTeams() {
        String clue;
        int numWords;

        if ("R".equals(currentTeamTurn)) {
            board.spymasterView();
            System.out.println("\nBlue spymaster, please enter your clue followed by number of words: " );
            currentTeamTurn = "B";
            clue = scanner.next();
            numWords = scanner.nextInt();

            if (numWords > blueWordsRemaining) {
                System.out.println("\nThere are only " + blueWordsRemaining + " words remaining. Please re-enter the number of words.");
                numWords = scanner.nextInt();
            }

        } else {
            board.spymasterView();
            System.out.println("\nRed spymaster, please enter your clue followed by number of words: " );
            currentTeamTurn = "R";
            clue = scanner.next();
            numWords = scanner.nextInt();

            if (numWords > redWordsRemaining) {
                System.out.println("\nThere are only " + redWordsRemaining + " words remaining. Please re-enter the number of words.");
                numWords = scanner.nextInt();
            }
        }
        playTheRound(clue, numWords);
    }

    public Card errorCheckingGuess(String guess) {
        Card card = findCardFromWord(guess);
        while (card == null) {
            System.out.println("\nThis word does not exist in the game board. Please select a valid word.");
            guess = scanner.next();
            card = findCardFromWord(guess);
        }

        if (card.isGuessed()) {
            System.out.println("\nThis word has already been guessed. Please choose another word.");
            guess = scanner.next();
            card = findCardFromWord(guess);
        }
        return card;
    }

    public Card findCardFromWord(String word) {
        for (Card card : board.cards) {
            if (card.getWord().equalsIgnoreCase(word)) {
                return card;
            }
        }
        return null;
    }

    public void updateBoard() {
        List<List<Card>> cardList = ListUtils.partition(board.cards, 5);
        for (List list : cardList) {
            System.out.print("[");
            for (Object card : list) {
                Card cardObj = (Card) card;
                if (cardObj.isGuessed()) {
                    System.out.format(" %-12s", cardObj.getWord() + "(" + cardObj.getColor() + ")");
                } else {
                    System.out.format(" %-12s", cardObj.getWord() + " ");
                }
            }
            System.out.print("]\n");
        }
    }
}

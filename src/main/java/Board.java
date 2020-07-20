import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;

public class Board {

    private static final String WORDS = "src/main/resources/words";
    public final List<Card> cards;
    private List<String> cardColors = new ArrayList<>();

    public Board(int rows, int cols) throws IOException {
        this.cards = new ArrayList<>();
        getCardColor(rows, cols);
        List<String> words = getWords();

        for (int i = 0; i < rows*cols; i++) {
            String cardColor = cardColors.get(i);
            String word = words.get(i);

            cards.add(new Card(cardColor, word, false));
        }
    }

    private List<String> getWords() throws IOException {
        List<String> list = Files.readAllLines(Paths.get(WORDS));

        Collections.shuffle(list);
        return list.stream()
            .limit(25)
            .collect(Collectors.toList());
    }

    private void getCardColor(int rows, int cols) {
        putColorInList((int) (0.28 * rows * cols), "T");
        putColorInList((int) (0.04 * rows * cols), "X");
        putColorInList((int) (0.32 * rows * cols), "B");
        putColorInList((int) (0.36 * rows * cols), "R");
        Collections.shuffle(cardColors);
    }

    private void putColorInList(int numCards, String color) {
        for (int i = 0; i < numCards; i++) {
            cardColors.add(color);
        }
    }

    public void spymasterView() {
        List<List<Card>> cardList = ListUtils.partition(cards, 5);
        for (List list : cardList) {
            System.out.print("[");
            for (Object card : list) {
                Card cardObj = (Card) card;
                System.out.format(" %-15s", cardObj.getWord() + "(" + cardObj.getColor() + ")" + " ");
            }
            System.out.print("]\n");
        }
    }
}

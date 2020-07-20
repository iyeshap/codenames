public class Card {
    private String color;
    private String word;
    private boolean guessed;

    public Card(String color, String word, boolean guessed) {
        this.color = color;
        this.word = word;
        this.guessed = guessed;
    }

    public String getColor() {
        return color;
    }

    public String getWord() {
        return word;
    }

    public boolean isGuessed() {
        return guessed;
    }

    public void setGuessed(boolean guessed) {
        this.guessed = guessed;
    }
}

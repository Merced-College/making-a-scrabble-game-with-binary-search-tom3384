public class Word implements Comparable<Word> {
    // Stores word as aa string. Private means it cannot be accesssed outside of the class.
    private String word;

    // This is the constructor. 
    public Word(String word) {
        this.word = word;
    }

    // Getter method for word 
    public String getWord() {
        return word;        
    }

    // allows for word comparisons
    public int compareTo(Word otherWord) {
        return this.word.compareToIgnoreCase(otherWord.getWord());
    }

    
    // output the word when Word objects are printed
    public String toString() {
        return word;
    }
}
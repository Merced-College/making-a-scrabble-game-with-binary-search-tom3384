// Jeremiah Tenn, Tom Nguyen
// 10/11/2024

// Our improvement: Allow the user to exchange one of their letters.

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class ScrabbleGame {

    // create arraylist with type Word
    public static ArrayList<Word> wordList = new ArrayList<Word>();
    public static Random random = new Random();
    
    // binary search method written by Tom and Jeremiah
    public static void search(Word word) {
        int found = 0;
        int low = 2;
        int high = wordList.size() - 1; 
        while (low <= high) {
            int mid = (low + high) / 2; 
            
            // if word is the middle word return since found
            if (wordList.get(mid).compareTo(word) == 0) {
                found = 1;
                break;
            }
            
            // if word is greater than middle word ignore left half
            if (wordList.get(mid).compareTo(word) < 0) {
                low = mid + 1;
            } 

            // If the word is smaller (meaning the letters in the word come beforehand), ignore right half. 
            else {
                high = mid - 1;
            }
        }
        
        // If word found return 1        
        if (found == 1) {
            System.out.println("Valid word, good job!");
        }

        // Word not found and returns 0. 
        else {
            System.out.println("Sorry, not a valid word.");
        }
    }

    // Done by Jeremiah. Tests to see if the word the user inputted maatches the 4 random letters given to them. 
    public static void testWord(String userWord, char[] list) {
        // if the word has more than 4 letters then word is impossible
        if (userWord.length() > 4) {
            System.out.println("Too much letters! You can't use that word.");
        }
        
        // iterate through the word and look at each letter
        for (int i = 0; i < userWord.length(); ++i) {
            // letter that will be counted
            char letter = userWord.charAt(i);
            
            // amount of the letter in the word and in hand
            int letterCountWord = 0;
            int letterCountHand = 0;
            
            // If the char matches the letter, then add +1 to letterCountWord. 
            for (int j = 0; j < userWord.length(); ++j) {
                if (userWord.charAt(j) == letter) {
                    letterCountWord += 1;
                }
            }

            // If the char at list[j] matches the letter, then add +1 to letterCountHand.
            for (int j = 0; j < list.length; ++j) {
                if (list[j] == letter) {
                    letterCountHand += 1;
                }
            }
            // if the word has more letters then the player has then it is invalid
            if (letterCountWord > letterCountHand) {
                System.out.println("Not enough of each letter in hand");
                break;
            }
        }
    }
    
    // Loads the file with the scrabble words. Prints an error if the file is not found. Done by Tom.
    private static void loadWords(String filename) {
        // uses try for better error handling
        try (Scanner input = new Scanner(new File(filename))) {
            // interates through the whole file
            while (input.hasNextLine()) {
                // makes a string for each word
                String word = input.nextLine().toLowerCase(); 
                // adds a Word object for the word into the wordList
                wordList.add(new Word(word));
            }
        } catch (FileNotFoundException e) {     // aka what to do if the file is not found
            // prints out not found message if not found
            System.out.println("Error: File not found.");
            // prints out java error info
            e.printStackTrace();
        }
    }

    // Generates 4 random letters. Done by Tom and Jeremiah. 
    private static char[] generateRandomLetters() {
        char[] letters = new char[4];
        // The loop runs 4 times, so it will create 4 random letters. To make more random letters, increase the loop count (i<4). 
        // 26 letters in the alphabet, so adding a random integer up to 26 will cover a to z. For example, nextInt(26) will generate numbers in the range 0 to 26 both inclusive.
        for (int i = 0; i < 4; i++) {
            char randomLetter = (char) ('a' + random.nextInt(26)); // 'a' to 'z'
            //letters+=randomLetter;
            letters[i] = randomLetter;
        }
        return letters; 
    }

    // gets word from the user written by Jeremiah
    public static String getWord() {
        // creates a scanner 
        Scanner scnr = new Scanner(System.in);
        // lets user input word
        String userWord = scnr.nextLine();
        // returns the user inputted word
        return userWord;
    }    

    // Our improvement: Allow the user to exchange one of their letters.
    // Done by Tom and Jeremiah, lets user exchange letter with a new random letter if they want to. 
    public static void exchangeLetter(char[] letters) {
        // creates a scanner 
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter letter to exchange for random one:");
        // gets a lowercase char input
        char letterToExchange = scnr.nextLine().toLowerCase().charAt(0);

        // searches for the character that it supposed to be changed 
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == letterToExchange) {
                 // Generate new random letter from 'a' to 'z'
                letters[i] = (char) ('a' + random.nextInt(26));
                // prints out the change 
                System.out.println("Exchanged " + letterToExchange + " for " + letters[i] + "." );
                break;
            }
        }     
    }

    // written by Jeremiah and Tom
    public static void main(String[] args) {
        // Loads the scrabble words from the text file. 
        loadWords("CollinsScrabbleWords_2019.txt");
        // Generates 4 random letters from a to z. 
        char[] randomLetters = generateRandomLetters();
        Scanner scnr = new Scanner(System.in);
        
        System.out.println("Your Letters:");
        for (int i = 0; i < randomLetters.length; ++i) {
            System.out.print(randomLetters[i] + " ");
        }            
        System.out.print("\n");


        // lets user input if they want to exchange a letter. If yes, exchangeLetter asks them what letter they want exchanged.
        System.out.println("Would you like to exchange a letter for a new random letter? (y/n)");
        String input = scnr.nextLine();
        // If user inputed y for yes, they want to exchange a letter. 
        if (input.equals("y")) {
            exchangeLetter(randomLetters);
        }
            
        for (int i = 0; i < randomLetters.length; ++i) {
            System.out.print(randomLetters[i] + " ");
        }
        System.out.print("\n");

        // Asks the user to write a word using the 4 letters they have. 
        System.out.println("Enter a word from the letters you have.");
        String word = getWord();

        testWord(word, randomLetters);

        // Uses binary search to see if the word that the user inputted is a valid word in the CollinsScrabbleWords_2019.txt file that we are reading from. 
        search(new Word(word));
        scnr.close();
    }
}

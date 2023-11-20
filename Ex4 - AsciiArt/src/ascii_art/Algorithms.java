package ascii_art;

import java.util.*;

/**
 * Algorithms class containing two method that solves two different algo problems
 * @author Tamuz Gitler
 */
public class Algorithms {

    //================ private constants ==============

    public static final int A_ASCII_VALUE = 97;
    public static final int NOT_UNIQUE_REP = 0;

    //================ public methods =================

    /**
     * Finds the duplicated value in a given array
     * @param numList array in length n+1 that all of its values are in range 1-n
     * @return duplicated value
     */
    public static int findDuplicate(int[] numList){
        int advOnce = numList[0]; //advances on numList one once each round
        int advTwice = numList[numList[0]];  //advances on numList twice each round
        int advOnceFromBeggining = 0; //advances on numList one once each round for second loop
        /* advancing advOnce and advTwice in numList until they hit the same node*/
        while(advOnce != advTwice){
            advOnce = numList[advOnce];
            advTwice = numList[numList[advTwice]];
        }

        /*advancing advOnce and advOnceFromBeggining together util they hit the duplicated node*/
        while (advOnce != advOnceFromBeggining){
            advOnce = numList[advOnce];
            advOnceFromBeggining = numList[advOnceFromBeggining];
        }
        return advOnce; // returns the duplicate
    }

    /**
     * This function translates each word to its morse representations and counts the number of
     * unique morse representations
     * @param words list of words to translate to morce code
     * @return number of unique morse representations
     */
    public static int uniqueMorseRepresentations(String[] words){
        /* inits array for translating letter to morse code*/
        String[] morseTranslation = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-", //a-k
        ".-..","--","-.","---",".--.","--.-",".-.","...","-", //l-t
                "..-","...-",".--","-..-","-.--","--.."}; //u-z

        Set<String> morceSet = new HashSet<>();
        int counter = NOT_UNIQUE_REP;
        /* iterates over words, for each words translates it to morce code and adds it to morceSet,
        * and updates counter if its not in morceSet already.*/
        for (String word: words) {
            String morisWord = "";
            for (int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                morisWord += morseTranslation[c - A_ASCII_VALUE]; //this way a will be mapped to 0 and so on...
            }
            if (! morceSet.contains(morisWord)){
                morceSet.add(morisWord);
                counter ++;
            }
        }
        return counter;
    }
//
    public static void main(String[] args) {
        int[] array = {1, 3, 2, 6, 5, 4, 4};
        System.out.println(findDuplicate(array));
    }

}

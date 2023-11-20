package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;
z
/**
 * Shell class in charge on managing and executing all commands given by the user.
 * It is initiated by Driver class
 * @author Tamuz Gitler
 */
public class Shell {

    //================ private constants ==============

    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int MAX_MIN_CHAR_VALUE = 1;
    private static final int SET_CHARINROW_MULT = 2;

    /* default settings */
    private static final String FONT_NAME = "Courier New";
    private static final String OUTPUT_FILENAME = "out.html";
    private static final String INITIAL_CHARS_RANGE = "0-9";

    /* For user input usage */
    private static final String CHARS = "chars";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String RES = "res";
    private static final String CONSOLE = "console";
    private static final String RENDER = "render";
    private static final String CMD_EXIT = "exit";
    private static final String ALL = "all";
    private static final String SPACE = "space";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String PREFIX = ">>> ";
    private static final char HYPHEN = '-';
    private static final char SPACE_CHAR = ' ';
    private static final char UNARY = '~';

    /* Exception messages*/
    public static final String ADD_EXCEPTION_MSG = "ERROR: Invalid add command";
    public static final String REMOVE_EXCEPTION_MSG = "ERROR: Invalid remove command";
    private static final String WIDTH_CHANGE_MSG = "Width set to ";
    public static final String RES_CHANGE_EXCEPTION_MSG = "ERROR: Char in row out of bounds";
    private static final String ILLEGAL_INPUT = "Error: Illegal input, try again";
    private static final String TRY_AGAIN = ", try again";
    private static final String RENDER_ERROR = "Error: Empty charSet, unable to render";

    //================ fields =========================

    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private final Image img;
    private Set<Character> charSet = new HashSet<>();
    private final BrightnessImgCharMatcher charMatcher;
    private AsciiOutput output;

    //================ constructor ====================

    /**
     * Constructor
     * @param image to convert
     * @throws IOException when given invalid input
     */
    public Shell(Image image) throws IOException {
        this.img = image;
        this.addChars(INITIAL_CHARS_RANGE);
        this.minCharsInRow = Math.max(MAX_MIN_CHAR_VALUE, img.getWidth()/img.getHeight());
        this.maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        this.charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        this.charMatcher = new BrightnessImgCharMatcher(this.img, FONT_NAME);
        this.output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
    }

    //================ public methods =================

    /**
     * translates the given commands from user input and executes them
     */
    public void run(){
//        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
//        String userInput = "";  // Read user input
//        while(! userInput.toLowerCase().equals(EXIT)){ //TODO maybe use given code on campus
//            System.out.print(PREFIX);
//            userInput = scanner.nextLine().trim();
//            String[] translatedInput = userInput.split(BY_SPACE);
//            executeCommand(translatedInput);
//        }
        //
        Scanner scanner = new Scanner(System.in);
        System.out.print(PREFIX);
        String cmd = scanner.nextLine().trim();
        String[] words = cmd.split("\\s+");
        while(!words[0].toLowerCase().equals(CMD_EXIT)) {
            if(!words[0].equals("")) {
                String param = "";
                if(words.length > 1) {
                    param = words[1];
                }
            }
            executeCommand(words); //handles input
            System.out.print(PREFIX);
            cmd = scanner.nextLine().trim();
            words = cmd.split("\\s+");
        }
    }


    //================ private methods ================
    /**
     * executes the given command and prints informative error message when given invalid input
     * / invalid command
     * @param translatedInput array that contains the user command to be executed
     */
    private void executeCommand(String[] translatedInput) {
        switch (translatedInput[0]){
            case CHARS:
                this.showChars();
                break;
            case ADD:
                try{
                    addChars(translatedInput[1]);
                }
                catch (Exception exception){
                    System.out.println(exception.getMessage() + TRY_AGAIN);
                }
                break;
            case REMOVE:
                try{
                    removeChars(translatedInput[1]);
                }
                catch (Exception exception){
                    System.out.println(exception.getMessage() + TRY_AGAIN);
                }
                break;
            case RES:
                try{
                    resChange(translatedInput[1]);
                    System.out.println(WIDTH_CHANGE_MSG + this.charsInRow);
                }
                catch (ArithmeticException exception){
                    System.out.println(exception.getMessage());
                }
                break;
            case CONSOLE:
                console();
                break;
            case RENDER:
                render();
                break;
            default:
                System.out.println(ILLEGAL_INPUT);
                break;
        }
    }

    /**
     * prints charSet for the user
     */
    private void showChars(){
        charSet.stream().sorted().forEach(c-> System.out.print(c + " "));
        System.out.println();
    }

    /**
     * parses the translated command for adding and removing chars from charSet.
     * @param param given command
     * @return parsed command
     */
    private static char[] parseCharRange(String param) {
        if (paramNotValid(param)){
            return null;
        }
        char[] res = new char[2];
        if(param.length() == 1){
            res[0] = param.charAt(0);
            res[1] = param.charAt(0); //only 1 char in param
        }
        else if(param.equals(ALL)){
            res[0] = SPACE_CHAR;
            res[1] = UNARY;
        }
        else if(param.equals(SPACE)){
            res[0] = SPACE_CHAR;
            res[1] = SPACE_CHAR;
        }
        else if(param.charAt(1) == HYPHEN){ // hyphen is '-'
            if(param.charAt(0) < param.charAt(2)){
                res[0] = param.charAt(0);
                res[1] = param.charAt(2);
            }
            else{
                res[0] = param.charAt(2);
                res[1] = param.charAt(0);
            }
        }
        return res;
    }

    /**
     * checks if command is valid or not.
     * @param param given command
     * @return true if command is legal, else false
     */
    private static boolean paramNotValid(String param) {
        return param.length() != 1 && !param.equals(ALL) && !param.equals(SPACE) && param.charAt(1) != HYPHEN;
    }

    /**
     * executes add command.
     * It adds the wanted chars to charSet
     * @param s chars to add
     * @throws IOException when given an illegal string to add to charSet
     */
    private void addChars(String s) throws IOException {
        char[] range = parseCharRange(s);
        if(range != null){
            Stream.iterate(range[0], c -> c <= range[1], c -> (char)((int)c+1)).forEach(charSet::add);
        }
        else {
            throw new IOException(ADD_EXCEPTION_MSG);
        }
    }

    /**
     * executes remove command.
     * It removes the wanted chars to charSet
     * @param s chars to remove
     * @throws IOException when given an illegal string to remove to charSet
     */
    private void removeChars(String s) throws IOException{
        char[] range = parseCharRange(s);
        if(range != null){
            Stream.iterate(range[0], c -> c <= range[1], c -> (char)((int)c+1)).forEach(charSet::remove);
        }
        else {
            throw new IOException(REMOVE_EXCEPTION_MSG);
        }
    }

    /**
     * changes charsInRow as the user ordered.
     * @param s command
     * @throws ArithmeticException when charsInRow has exceeded boundaries.
     */
    private void resChange(String s) throws ArithmeticException { //TODO should i throw exception in this case or just print?
        switch (s) {
            case DOWN:
                if (this.charsInRow == this.minCharsInRow) {
                    throw new ArithmeticException(RES_CHANGE_EXCEPTION_MSG);
                } else {
                    this.charsInRow /= SET_CHARINROW_MULT;
                    break;
                }
            case UP:
                if (this.charsInRow == this.maxCharsInRow) {
                    throw new ArithmeticException(RES_CHANGE_EXCEPTION_MSG);
                } else {
                    this.charsInRow *= SET_CHARINROW_MULT;
                    break;
                }
            default:
                throw new ArithmeticException(ILLEGAL_INPUT);
        }
    }

    /**
     * changes the output to console.
     */
    private void console(){
        this.output = new ConsoleAsciiOutput();
    }

    /**
     * renders the image to output.
     */
    private void render(){
        if( this.charSet.isEmpty()) {
            System.out.println(RENDER_ERROR);
            return;
        }
        else{
            Character[] charsArray = new Character[this.charSet.size()];
            this.charSet.toArray(charsArray);
            char[][] chars = charMatcher.chooseChars(this.charsInRow,  charsArray);
            this.output.output(chars);
        }
    }
}

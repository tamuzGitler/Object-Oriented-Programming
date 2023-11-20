package ascii_art.img_to_char;

import image.Image;
import java.awt.*;
import java.util.HashMap;


/**
 * BrightnessImgCharMatcher class gets a picture and an image and is able to
 * converts an image to Ascii from given charSet
 * @author Tamuz Gitler
 */
public class BrightnessImgCharMatcher {

    //================ private constant =========================

    private static final int PIXELS = 16;
    private static final int TOTAL_PIXELS = PIXELS * PIXELS;
    private static final int MAXIMAL_RGB = 255;
    private static final float RED_CONVERTER = 0.2126f;
    private static final float GREEN_CONVERTER = 0.7152f;
    private static final float BLUE_CONVERTER = 0.0722f;

    //================ fields =========================

    private final Image image;
    private final String font;
    private final HashMap<Image, Float> cache = new HashMap<>();


    //================ constructor ====================

    /**
     * Constructor
     * @param image to convert
     * @param font of chars
     */
    public BrightnessImgCharMatcher(Image image, String font) {
        this.image = image;
        this.font = font;
    }

    //================ public methods =================

    /**
     * converts an image to Ascii from given charSet
     * first part - split image to sub images
     * second part - calculate average brightness for every sub image
     * third part - replace every sub image --> char
     * @param numCharsInRow number of chars in each row of the ASCII image
     * @param charSet       array containing chars to build the ASCII image from
     * @return array containing the brightness values of the image
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {

        BrightChar[] brightnessOfChars = getBrightnessOfChars(charSet);
        sortArrayByBrightness(brightnessOfChars); //sorts brightnessOfChars
        normalizeBrightness(brightnessOfChars);

        return convertImageToAscii( numCharsInRow, brightnessOfChars);
    }

    //================ private methods ================

    /**
     * calculates the brightness of each char in charSet
     * @param charSet a set of chars
     * @return an array of char brightness
     */
    private   BrightChar[] getBrightnessOfChars(Character[] charSet) {
        BrightChar[] brightnessOfChars = new BrightChar[charSet.length];
        for (int i = 0; i < charSet.length; i++) {
            boolean[][] booleanArr = CharRenderer.getImg(charSet[i], PIXELS, font);
            int counterOfTrueValues = calculateTrueValueCounter(booleanArr);
            brightnessOfChars[i] = new BrightChar(charSet[i],counterOfTrueValues / (float)TOTAL_PIXELS);
        }
        return brightnessOfChars;
    }

    /**
     * sorts the BrightChar array by their brightness
     * @param brightnessOfChars array of BrightChar
     */
    private void sortArrayByBrightness(BrightChar[] brightnessOfChars){
        int n = brightnessOfChars.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (brightnessOfChars[j].compareTo(brightnessOfChars[j+1]) == 1)
                {
                    // swap arr[j+1] and arr[j]
                    BrightChar temp = brightnessOfChars[j];
                    brightnessOfChars[j] = brightnessOfChars[j+1];
                    brightnessOfChars[j+1] = temp;
                }
    }

    /**
     * calculates the number of truth values in a boolean array
     * @param booleanArr array of boolean
     * @return number of truth values in booleanArr
     */
    private static int calculateTrueValueCounter(boolean[][] booleanArr) {
        int counterOfTrueValues = 0;
        for (int row = 0; row < booleanArr.length; row++) {
            for (int col = 0; col < booleanArr[0].length; col++) {
                if (booleanArr[row][col]) {
                    counterOfTrueValues++;
                }
            }
        }
        return counterOfTrueValues;
    }

    /**
     * normalizes the brightness of brightnessOfChars
     * @param brightnessOfChars array of brightness of chars to normalize
     * @return array of normalized brightness
     */
    private void normalizeBrightness(BrightChar[] brightnessOfChars ){

        float minBrightness = brightnessOfChars[0].getBrightness();
        float maxBrightness = brightnessOfChars[brightnessOfChars.length-1].getBrightness();
        for (int i = 0; i < brightnessOfChars.length; i++) {
            brightnessOfChars[i].setBrightness((brightnessOfChars[i].getBrightness() - minBrightness) / (maxBrightness - minBrightness));
        }
    }

    /**
     * calculates the average brightness of a given sub image
     * @param subImage sub image of this.image
     * @return average brightness of sub image
     */
    private  float calculateAverageBrightness(Image subImage){
        float brightness = 0;
        for (Color pixel: subImage.pixels()){
            brightness += convertPixelToGreyShade(pixel);
        }
        float averageBrightness = (brightness / (subImage.getWidth() * subImage.getHeight()) / MAXIMAL_RGB);
        return averageBrightness;

    }

    /**
     * converts a given pixel to grey shades
     * @param pixel pixel to convert
     * @return grey shade number
     */
    private static float convertPixelToGreyShade(Color pixel){
        return (pixel.getRed() * RED_CONVERTER + pixel.getGreen() * GREEN_CONVERTER + pixel.getBlue() * BLUE_CONVERTER);
    }

    /**
     * converts image to ascii by converting sub images
     * @param numCharsInRow number of chars in each row of the ASCII image
     * @param brightnessOfChars array of BrightChar
     * @return ascii array of image
     */
    private char[][] convertImageToAscii(int numCharsInRow, BrightChar[] brightnessOfChars){
        int pixels = this.image.getWidth() / numCharsInRow;
        char[][] asciiArt = new char[this.image.getHeight()/pixels]
                [this.image.getWidth()/pixels];
        int i = 0;
        for (Image subImage : this.image.squareSubImagesOfSize(pixels)){
            float averageBrightness = getAverageBrightness(subImage);
            //get the closest char to averageBrightness
            char closestChar = findClosestChar(brightnessOfChars,
                    averageBrightness);
            addCharToAsciiArt(numCharsInRow, asciiArt, i, closestChar);   //adds char to array
            i++;
        }
        return asciiArt;
    }

    /**
     * computes averageBrightness, from cache if already computed or calculates it
     * @param subImage to calculate its average brightness
     * @return average brightness of sub Image
     */
    private float getAverageBrightness(Image subImage) {
        float averageBrightness;
        if ( cache.containsKey(subImage)){
            averageBrightness = this.cache.get(subImage);
        }
        else {
            averageBrightness = calculateAverageBrightness(subImage);
            this.cache.put(subImage, averageBrightness);
        }
        return averageBrightness;
    }

    /**
     * adds closestChar to asciiArt
     * @param numCharsInRow number of chars in each row of the ASCII image
     * @param asciiArt of image
     * @param i counter
     * @param closestChar to add
     */
    private void addCharToAsciiArt(int numCharsInRow, char[][] asciiArt, int i, char closestChar) {
        int row = i / numCharsInRow;
        int col = i % numCharsInRow;
        asciiArt[row][col] = closestChar;
    }

    /**
     * helper function that finds the closest char brightness to the averageBrightness
     * @param brightnessOfChars array of brightness of charSet
     * @param averageBrightness average brightness of sub image
     * @return index of the closest char
     */
    private char findClosestChar(BrightChar[] brightnessOfChars,
                                 float averageBrightness){
        float distance = Float.MAX_VALUE;
        int closetBrightnessIndex = -1;
        for(int i = 0; i < brightnessOfChars.length; i++){
            float curDistance = Math.abs(brightnessOfChars[i].getBrightness() - averageBrightness);
            if(curDistance < distance){
                closetBrightnessIndex = i;
                distance = curDistance;
            }
        }
        return brightnessOfChars[closetBrightnessIndex].getCharacter();
    }
}

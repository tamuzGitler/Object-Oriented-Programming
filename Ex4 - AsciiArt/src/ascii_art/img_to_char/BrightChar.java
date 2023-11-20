package ascii_art.img_to_char;

/**
 * BrighterChar contains for each char the character and its brightness
 * and in this way i can compare two characters by their brightness
 * @author Tamuz Gitler
 */
public class BrightChar implements  Comparable<BrightChar>{

    //================ fields =========================

    private final Character character;
    private float brightness;

    //================ constructor ====================

    /**
     * Constructor
     * @param character char
     * @param brightness of char
     */
    public BrightChar(Character character, float brightness){
        this.character =character;
        this.brightness = brightness;
    }

    //================ public methods =================

    /**
     * getter of character
     * @return character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * getter of brightness
     * @return brightness
     */
    public float getBrightness() {
        return this.brightness;
    }

    /**
     * setter of brightness
     * @param newBrightness
     */
    public void setBrightness(float newBrightness){
        this.brightness = newBrightness;
    }

    /**
     * compares two characters by their brightness
     * @param other BrightChar
     * @return indictaor of comparator
     */
    @Override
    public int compareTo(BrightChar other) {
        if (this.brightness == other.brightness)
            return 0;
        if (this.brightness > other.brightness)
            return 1;
        return -1;
    }

}

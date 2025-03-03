package image_char_matching;

import java.util.*;

/**
 * The SubImgCharMatcher class is designed for mapping characters to their perceived brightness levels,
 * enabling the selection of characters that best match the brightness of a segment in an image.
 * This functionality is particularly useful in the context of creating ASCII art representations of images.
 */
public class SubImgCharMatcher {
    private final TreeMap<Double, TreeSet<Character>> treeMap;
    private TreeMap<Double, TreeSet<Character>> relativeTreeMap;
    private boolean needToUpdate;

    /**
     * Constructs a SubImgCharMatcher object with a specified set of characters.
     * Each character's brightness is calculated and stored for future matching.
     *
     * @param charSet An array of characters to be used for matching.
     */
    public SubImgCharMatcher(char[] charSet){
        treeMap = new TreeMap<>();
        for (char c: charSet){
            double curCharBrightness = charBrightness(c);
            putNewChar(c, curCharBrightness);
        }
        relativeTreeMap = new TreeMap<>();
        needToUpdate = true;
    }
    /**
     * Adds a new character to the TreeMap, associating it with its brightness.
     * If the brightness level already exists, the character is added to the existing set.
     *
     * @param c The character to add.
     * @param curCharBrightness The brightness of the character.
     */
    private void putNewChar(char c, double curCharBrightness) {
        if (treeMap.containsKey(curCharBrightness))
            treeMap.get(curCharBrightness).add(c);
        else {
            TreeSet<Character> brightnessSet = new TreeSet<>();
            brightnessSet.add(c);
            treeMap.put(curCharBrightness,brightnessSet);
        }
    }
    private void updateValues(){
        if (treeMap.size()>=2){
            double minVal = treeMap.firstKey();
            double maxVal = treeMap.lastKey();
            TreeSet<Double> keysToUpdate = new TreeSet<>(treeMap.keySet());
            relativeTreeMap = new TreeMap<>();
            for (Double key : keysToUpdate) {
                Double newValue = relativeBrightness(key, minVal, maxVal);
                relativeTreeMap.put(newValue, treeMap.get(key));
                if (!newValue.equals(key)){
                    relativeTreeMap.remove(key);
                }
            }
        }
    }
    private static double relativeBrightness(double key,double first, double last){
        return (key - first) / (last - first);
    }
    private static int countTrueBooleans(boolean[][] array) {
        int count = 0;
        for (boolean[] booleans : array) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    count++;
                }
            }
        }
        return count;
    }
    private double charBrightness(char c){
        boolean[][] charBoolArray = CharConverter.convertToBoolArray(c);
        int numOfTrue = countTrueBooleans(charBoolArray);
        return (double) numOfTrue
                /(charBoolArray.length*charBoolArray.length);
    }
    /**
     * Selects the character that best matches a given brightness level by finding the closest match
     * in the TreeMap of brightness to characters.
     *
     * @param brightness The target brightness level.
     * @return The character that best matches the target brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        if (needToUpdate){
            updateValues();
            needToUpdate = false;
        }
        var ceilingEntry = relativeTreeMap.ceilingEntry(brightness);
        double cielDiff = ceilingEntry != null ? Math.abs(ceilingEntry.getKey() - brightness) :
                Double.POSITIVE_INFINITY;
        var floorEntry = relativeTreeMap.floorEntry(brightness);
        double floorDiff = floorEntry != null ? Math.abs(floorEntry.getKey() - brightness) :
                Double.POSITIVE_INFINITY;
        // Determine the closest character based on brightness differences
        char closestChar;
        if (cielDiff == floorDiff) {
            // If both differences are equal, choose the smaller character
            closestChar = (char) Math.min(ceilingEntry.getValue().first(), floorEntry.getValue().first());
        } else {
            // Otherwise, choose the character from the closer brightness entry
            closestChar =cielDiff<floorDiff ? ceilingEntry.getValue().first():floorEntry.getValue().first();
        }
        return closestChar;
    }
    /**
     * Adds a new character to the brightness mapping. If the character's brightness level already exists,
     * the character is added to the set of characters for that brightness. Otherwise, a new entry is created.
     *
     * @param c The character to add.
     */
    public void addChar(char c){
        double curCharBrightness = charBrightness(c);
        putNewChar(c, curCharBrightness);
        needToUpdate = true;
    }
    /**
     * Removes a character from the brightness mapping. If the character is the only one at its brightness
     * level, the entire entry is removed. Otherwise, just the character is removed from its set.
     *
     * @param c The character to remove.
     */
    public void removeChar(char c){
        double curCharBrightness = charBrightness(c);
        if (treeMap.containsKey(curCharBrightness)){
            safelyRemove(c, curCharBrightness);
        }
    }
    private void safelyRemove(char c, double curCharBrightness) {
        if (treeMap.get(curCharBrightness).size()>1) {
            treeMap.get(curCharBrightness).remove(c);
        }
        else treeMap.remove(curCharBrightness);
    }
}
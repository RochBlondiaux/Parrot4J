package me.rochblondiaux.parrot4j.ardrone2.video;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class ImageSlice {

    private final MacroBlock[] blocks;

    public ImageSlice(int macroBlockCount) {
        blocks = new MacroBlock[macroBlockCount];

        for (int index = 0; index < macroBlockCount; index++) {
            blocks[index] = new MacroBlock();
        }
    }

    public MacroBlock[] blocks() {
        return blocks;
    }
}

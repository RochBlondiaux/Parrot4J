package me.rochblondiaux.parrot4j.ardrone2.video;

/**
 * Parrot4J
 * 09/12/2022
 *
 * @author Roch Blondiaux (Kiwix).
 */
public class MacroBlock {

    private final short[][] blocks;

    public MacroBlock() {
        blocks = new short[6][];

        for (int index = 0; index < 6; index++)
            blocks[index] = new short[64];
    }

    public short[][] blocks() {
        return blocks;
    }
}

package me.rochblondiaux.parrot4j.listeners;

import java.awt.image.BufferedImage;

public interface VideoDataListener {
    void onVideoData(BufferedImage droneImage);
}

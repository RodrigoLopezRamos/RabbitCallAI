package com.analia.media.util;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtil {

    public static BufferedImage resizeImage(BufferedImage originalImage, ImageSize imageSize) throws IOException {
        BufferedImage resizedImage = Scalr.resize(originalImage, Method.ULTRA_QUALITY, Mode.AUTOMATIC, imageSize.getX(), imageSize.getY());
        return resizedImage;
    }

    public static double calculateAspectRatioFit(int srcWidth, int srcHeight, int destWidth, int destHeight) throws IOException {
        float ratio = Math.min(((float) destWidth / (float) srcWidth), ((float) destHeight / (float) srcHeight));
        return ratio;
    }

    public enum ImageSize {

        THUMB(240, 240), SMALL(725, 725), MEDIUM(1200, 1200), LARGE(1920, 1920);

        private int x;
        private int y;

        ImageSize(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}

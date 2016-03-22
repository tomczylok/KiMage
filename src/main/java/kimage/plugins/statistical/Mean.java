package kimage.plugins.statistical;

import kimage.image.Image;
import kimage.plugin.Plugin;
import kimage.plugin.extras.Attributes;

/**
 * @author Krzysztof
 */
public class Mean extends Plugin {
    int maskSize = 3;

    @Override
    public void process(Image imgIn, Image imgOut) {
        if (getAttribute(Attributes.SIZE) != null) {
            maskSize = (Integer) getAttribute(Attributes.SIZE);
        } else {
            setAttribute(Attributes.SIZE, maskSize);
        }

        final int width = imgIn.getWidth();
        final int height = imgIn.getHeight();
        final int outputPixels[] = new int[width * height];

        int squareMaskSize = maskSize * maskSize;
        int alpha[] = new int[squareMaskSize];
        int red[] = new int[squareMaskSize];
        int green[] = new int[squareMaskSize];
        int blue[] = new int[squareMaskSize];

        /**
         * Median Filter operation
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int count = 0;
                int halfMaskSize = maskSize / 2;
                int xMin = x - halfMaskSize;
                int xMax = x + halfMaskSize;
                int yMin = y - halfMaskSize;
                int yMax = y + halfMaskSize;
                for (int r = yMin; r <= yMax; r++) {
                    for (int c = xMin; c <= xMax; c++) {
                        if (r >= 0 && r < height && c >= 0 && c < width) {
                            final int argb = imgIn.getRGB(c, r);
                            alpha[count] = (argb >> 24) & 0xff;
                            red[count] = (argb >> 16) & 0xff;
                            green[count] = (argb >> 8) & 0xff;
                            blue[count] = (argb) & 0xFF;
                            count++;
                        }
                    }
                }

                /**
                 * save median value in outputPixels array
                 */
                final int index = 0;
                final int p = (alpha[index] << 24) | (mean(red, count) << 16) | (mean(green, count) << 8) | mean(blue, count);
                outputPixels[x + y * width] = p;
            }
        }
        /**
         * Write the output pixels to the image pixels
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                imgOut.setRGB(x, y, outputPixels[x + y * width]);
            }
        }
    }

    private int mean(int[] t, int c) {
        double ret = 0;
        for (int i = 0; i < c; ++i)
            ret += t[i];
        return (int) Math.round(ret / c);
    }

}
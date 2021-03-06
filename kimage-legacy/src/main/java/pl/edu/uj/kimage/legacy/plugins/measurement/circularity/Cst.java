package pl.edu.uj.kimage.legacy.plugins.measurement.circularity;

import pl.edu.uj.kimage.legacy.image.Image;
import pl.edu.uj.kimage.legacy.plugin.measurement.ShapeMeasurement;

/**
 * @author Marcin Chołoniewski
 */
public class Cst extends ShapeMeasurement {


    @Override
    public void process(Image imgIn, Image imgOut) {
        final long area = getArray(imgIn);
        final double perimeter = getPerimeter(imgIn);

        final double circ = (4.0 * Math.PI * (double) area) / (perimeter * perimeter);
        setAttribute("area", area);
        setAttribute("perimeter", perimeter);
        setAttribute("circularity", round(circ, 4));
    }

}

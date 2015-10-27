import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by drapek on 26.10.15.
 */

public class RandomPointsGenerator {

    double minX, minY, maxX, maxY;
    public Point2D [] randomizedPoints;

    public RandomPointsGenerator(double minX, double minY, double maxX, double maxY, int howManyGenerate) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        randomizedPoints = new Point2D[howManyGenerate];
        randomPoints();
    }

    public Point2D[] getRandomizedPoints() {
        return randomizedPoints;
    }

    public void randomPoints(){
        Random rnd = new Random();
        for(int i = 0; i < randomizedPoints.length; i++) {
            randomizedPoints[i] = new Point2D.Double();
            double x = rnd.nextDouble() * (maxX - minX) + minX;
            double y = rnd.nextDouble() * (maxY - minY) + minY;
            randomizedPoints[i].setLocation(x, y);
        }
    }

    @Override
    public String toString() {
        StringBuilder strBld = new StringBuilder();
        int i = 1;
        for(Point2D each : randomizedPoints) {
            strBld.append("Point ").append(i).append(": (").append(each.getX()).append(", ").append(each.getY()).append(") \n");
            i++;
        }

        return strBld.toString();
    }

    public static void main( String [] args) {
        RandomPointsGenerator testPointGenerator = new RandomPointsGenerator(0, 0, 1, 1, 20);
        System.out.println(testPointGenerator);
    }
}

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by drapek on 01.11.15.
 */
public class PointSlopeFactor implements Comparable <PointSlopeFactor> {
    private Point2D point;
    private double slopeFactor;

    public PointSlopeFactor (Point2D thisPoint, Point2D xyCoordianteCenter) {
        point = thisPoint;
        slopeFactor = calculateSlopeFactor(xyCoordianteCenter);
    }

    private double calculateSlopeFactor( Point2D xyCoordianteCenter) {
        double referenceCoordinateX = point.getX() - xyCoordianteCenter.getX();
        double referenceCoordinateY = point.getY() - xyCoordianteCenter.getY();

        double d = Math.abs(referenceCoordinateX) + Math.abs(referenceCoordinateY);

        if( referenceCoordinateX >= 0 && referenceCoordinateY >= 0) {
            return referenceCoordinateY / d;
        }
        else if (referenceCoordinateX < 0 && referenceCoordinateY >= 0) {
            return 2 - (referenceCoordinateY / d);
        }
        else if (referenceCoordinateX < 0 && referenceCoordinateY < 0) {
            return 2 + (Math.abs(referenceCoordinateY) / d);
        }
        else if (referenceCoordinateX >= 0 && referenceCoordinateY < 0) {
            return 4 - (Math.abs(referenceCoordinateY) / d);
        }
        return -1;
    }

    public Point2D getPoint() {
        return point;
    }

    @Override
    public int compareTo(PointSlopeFactor o) {
        double resultIndDouble = this.slopeFactor - o.slopeFactor;

        if(resultIndDouble > 0)
            return 1;
        else if (resultIndDouble < 0 )
            return -1;
        else
            return 1;
    }

    public static void main(String [] args) {
        ArrayList <PointSlopeFactor> testPointSlope = new ArrayList<>();
        Point2D.Double center = new Point2D.Double(0,0);

        testPointSlope.add(new PointSlopeFactor(new Point2D.Double(-5, -1), center));
        testPointSlope.add(new PointSlopeFactor(new Point2D.Double(4, -4), center));
        testPointSlope.add(new PointSlopeFactor(new Point2D.Double(2, 4), center));
        testPointSlope.add(new PointSlopeFactor(new Point2D.Double(-3, 2), center));
        testPointSlope.add(new PointSlopeFactor(new Point2D.Double(4, 1), center));

        Collections.sort(testPointSlope);

        for(PointSlopeFactor each : testPointSlope) {
            System.out.println("    Pkt: (" + each.getPoint().getX() + ", " + each.getPoint().getY() + ")    d = " + each.slopeFactor );
        }
    }
}

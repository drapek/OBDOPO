import ProjectExceptions.ToFewPointsToFindConvexHullException;

import java.awt.geom.Point2D;

/**
 * Created by drapek on 29.10.15.
 */
public class GrahamScan {
    private Point2D [] pointsCollection;

    /**
         @param inputArr must have at less 3 Points2D object
         @return null if inputArr has less than 3 points, because
         it is imposible to find convex hull
     */
    public GrahamScan(Point2D [] inputArr) throws ToFewPointsToFindConvexHullException {
        if (inputArr.length > 2 ) {
            pointsCollection = inputArr;
        }
        else {
            throw new ToFewPointsToFindConvexHullException();
        }
    }


    public static void main(String [] args) {

        Point2D [] testPoints = new Point2D[3];
        testPoints[0] = new Point2D.Double(2, 3);
        testPoints[1] = new Point2D.Double(12, 4);
        try {
            GrahamScan testGrahamScan = new GrahamScan(testPoints);
            System.out.println(testGrahamScan);
        } catch (ToFewPointsToFindConvexHullException ex) {
            System.out.println("W podanej przez ciebie tablicy jest mniej niż 3 punkty, przez co nie można znaleźć otoczki wypukłej!");
            ex.printStackTrace();
        }


    }
}

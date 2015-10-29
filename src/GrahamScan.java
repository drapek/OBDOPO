import ProjectExceptions.ToFewPointsToFindConvexHullException;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by drapek on 29.10.15.
 */
public class GrahamScan {
    private ArrayList <Point2D> pointsCollection;

    /**
         @param inputArr must have at less 3 Points2D object
         @throws ToFewPointsToFindConvexHullException exception if inputArr has less than 3 points
     */
    public GrahamScan(Point2D [] inputArr) throws ToFewPointsToFindConvexHullException {
        if (inputArr.length > 2 ) {
            pointsCollection = new ArrayList<Point2D>(Arrays.asList(inputArr));
        }
        else {
            throw new ToFewPointsToFindConvexHullException();
        }
    }

    /**
     * @return tops points of found polygon
     * //TODO add proper exception
     * @throws someException if something goes wrong
     */
    public Point2D [] findPolygon() {

        Point2D minimalYPoint = pullOutMinimalYXpoint();

        //TODO return array of polygon tops
        return null;
    }

    /**
     * It's find minimal point and delete it from points collection
     * @return the minimal point on Y axis, and if there are more than 1 on this axis than
     * it takes this with smaller X parameter.
     */
    private Point2D pullOutMinimalYXpoint() {
        Point2D minimalXYpoint = pointsCollection.get(0);


        for(int i = 1; i < pointsCollection.size(); i++ ) {

            Point2D actualProcessedPoint = pointsCollection.get(i);

            if(actualProcessedPoint.getY() < minimalXYpoint.getY()) {
                minimalXYpoint = actualProcessedPoint;
            }
            else if(actualProcessedPoint.getY() == minimalXYpoint.getY()) {
                if(actualProcessedPoint.getX() < minimalXYpoint.getX())
                    minimalXYpoint = actualProcessedPoint;
            }
        }
        pointsCollection.remove(minimalXYpoint);
        return minimalXYpoint;
    }

    /**
     *  Sort pointsCollection by polar angle in counterclockwise order with respect to inReferenceTo parameter.
     * @param inReferenceTo is point whereby the rest willby sorted
     */
    private void sortPointsByPolarAngle(Point2D inReferenceTo) {
        //TODO add algoritm here, and may make seperate metod to calculate the angles.
    }

    private void printPointCollection() {
        System.out.println("Aktualny zestaw punktów w GrahmScan: ");
        for(Point2D elem: pointsCollection)
            System.out.println("    X:" + elem.getX() + "   Y:" + elem.getY());
    }

    public static void main(String [] args) {

        Point2D [] testPoints = new Point2D[5];
        testPoints[0] = new Point2D.Double(2, 3);
        testPoints[1] = new Point2D.Double(12, 4);
        testPoints[2] = new Point2D.Double(8, 2);
        testPoints[3] = new Point2D.Double(9, 3);
        testPoints[4] = new Point2D.Double(0, 53);

        try {
            GrahamScan testGrahamScan = new GrahamScan(testPoints);
            System.out.println(testGrahamScan);

            testGrahamScan.printPointCollection();
            Point2D minimalY = testGrahamScan.pullOutMinimalYXpoint();
            System.out.println("Minimalny pkt: (" + minimalY.getX() + ", " + minimalY.getY() + ")");
            testGrahamScan.printPointCollection();

        } catch (ToFewPointsToFindConvexHullException ex) {
            System.out.println("W podanej przez ciebie tablicy jest mniej niż 3 punkty, przez co nie można znaleźć otoczki wypukłej!");
            ex.printStackTrace();
        }




    }
}

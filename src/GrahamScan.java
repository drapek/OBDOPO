import ProjectExceptions.ToFewPointsToFindConvexHullException;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by drapek on 29.10.15.
 */
public class GrahamScan {
    private ArrayList <Point2D> pointsCollection;
    private ArrayList <Point2D> convexHull;
    private Point2D minimalYPoint;

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
     */
    public ArrayList <Point2D> findHull() {

        minimalYPoint = pullOutMinimalYXpoint();
        sortPointsByPolarAngle(pointsCollection);
        convexHull = new ArrayList<>();
        grahamScanAlgorithm();

        return convexHull;
    }

    private void grahamScanAlgorithm() {
        convexHull.add(minimalYPoint);
        convexHull.add(pointsCollection.get(0));
        convexHull.add(pointsCollection.get(1));

        for( int i = 2; i < pointsCollection.size(); i++) {
            while( calculateDetBySarrusMethod(convexHull.get(convexHull.size() - 2),
                                              convexHull.get(convexHull.size() - 1),
                                              pointsCollection.get(i))
                    < 0){
                //so if the third element is at the right side of vector [first, second]
                convexHull.remove(convexHull.size() - 1);
            }
            convexHull.add(pointsCollection.get(i));
        }
    }

    private double calculateDetBySarrusMethod(Point2D a, Point2D b, Point2D c) {
        return a.getX() * b.getY() + a.getY() * c.getX() + b.getX() * c.getY()
                - b.getY() * c.getX() - a.getX() * c.getY() - a.getY() * b.getX();
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
     */
    private void sortPointsByPolarAngle(ArrayList <Point2D> listToSort) {

        ArrayList <PointSlopeFactor> listToSortBySlopeFactor = new ArrayList <>();
        for( Point2D each : listToSort) {
            listToSortBySlopeFactor.add(new PointSlopeFactor(each, minimalYPoint));
        }

        Collections.sort(listToSortBySlopeFactor);

        pointsCollection.clear();
        for( PointSlopeFactor each : listToSortBySlopeFactor)
            pointsCollection.add(each.getPoint());


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

        Point2D [] testPoints2 = new Point2D[7];
        testPoints2[0] = new Point2D.Double(5, 7);
        testPoints2[1] = new Point2D.Double(0, 5);
        testPoints2[2] = new Point2D.Double(3, 8);
        testPoints2[3] = new Point2D.Double(9, 10);
        testPoints2[4] = new Point2D.Double(7, 5);
        testPoints2[5] = new Point2D.Double(10, 2);
        testPoints2[6] = new Point2D.Double(1, 1);

        try {
            System.out.println();
            System.out.println();
            GrahamScan testGrahamScan = new GrahamScan(testPoints2);
            testGrahamScan.printPointCollection();

            System.out.println("#####Znalezione pkt otoczki to: ##########");
            ArrayList <Point2D> findedElements = testGrahamScan.findHull();
            for( Point2D each : findedElements) {
                System.out.println("    (" + each.getX() + ", " + each.getY() + ")");
            }


        } catch (ToFewPointsToFindConvexHullException ex) {
            System.out.println("W podanej przez ciebie tablicy jest mniej niż 3 punkty, przez co nie można znaleźć otoczki wypukłej!");
            ex.printStackTrace();
        }






    }
}

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by drapek on 25.11.15.
 */
public class StaticPointChecker {
    public static boolean isInsideConvexHull(ArrayList <Point2D> convexHull, Point2D point) {
        //dodaj pierwszy element na koniec, tak aby powstała pętla, bo inaczej nie działa dobrze znajdywanie pkt
        convexHull.add(convexHull.get(0));
        if( checkIfExistInBoundaryOfConvexHull(convexHull,point))
            return true;

        ArrayList<Point2D[]> pointPairsOnTheSameYAxis = findSectionsOnTheSameYAxis(convexHull, point);

        if( pointPairsOnTheSameYAxis.isEmpty())
            return false; //because there is Sections to compare, so it's obvious that this is on Y axis that didn't cut polygon

        int nmbOfCuttedSections = howManySectionYRayIsCutting(pointPairsOnTheSameYAxis, point);

        //if the number isn't odd means that point is outside
        if( nmbOfCuttedSections % 2 == 0)
            return false;
        else
            return true;

    }

    private static ArrayList <Point2D []> findSectionsOnTheSameYAxis(ArrayList <Point2D> convexHull, Point2D point) {

        ArrayList <Point2D[]> result = new ArrayList<>();
        for(int i = 0; i < convexHull.size() - 2; i++) {
            if(isThisNumberBetweenLimits(point.getY(), convexHull.get(i).getY(), convexHull.get(i + 1).getY())) {
                Point2D [] tmp = new Point2D[2];
                tmp[0] = convexHull.get(i);
                tmp[1] = convexHull.get(i + 1);
                result.add(tmp);
            }
        }
        return result;
    }
    
    
    /**
     *  Parameters firstLimit and secondLimits not must be sorted.
     * @param nmb
     * @param firstLimit
     * @param secondLimit
     */
    private static boolean isThisNumberBetweenLimits(double nmb, double firstLimit, double secondLimit) {
        double min, max;
        if( firstLimit > secondLimit ) {
            max = firstLimit;
            min = secondLimit;
        }
        else {
            max = secondLimit;
            min = firstLimit;
        }

        if( nmb <= max && nmb >= min)
            return true;
        else
            return false;
    }

    private static boolean checkIfExistInBoundaryOfConvexHull(ArrayList <Point2D> convexHull, Point2D point) {
        for(Point2D onePoint : convexHull) {
            if(onePoint.equals(point))
                return true;
        }
        return false;
    }

    /**
     * says how many of given section is ray (from test point) cutting
     * @return
     */
    private static int howManySectionYRayIsCutting(ArrayList<Point2D[]> sections, Point2D testPoint) {
        int result = 0;
        for( Point2D [] section : sections) {
            if( isCuttingThisSection(testPoint, section))
                result++;
        }
        return result;
    }

    private static boolean isCuttingThisSection(Point2D testPoint, Point2D[] section) {
        //calculate linear function y = factorA * x + factorB
        double factorA;
        double factorB;

        if( section[0].getX() - section[1].getX() == 0) {
            //szczególny przypadek, wtedy mamy prostą pionową, dlatego wystarczy sprawdzić czy leży po lewej stronie od pkt
            if( section[0].getX() <= testPoint.getX() )
                return true;
            else
                return false;
        }
        else
        {
            factorA = (section[0].getY() - section[1].getY())/(section[0].getX() - section[1].getX());
            factorB = section[0].getY() - factorA * section[0].getX();

            //szukam x, dla podanej wysokości y (wziętej z półprostej testPoint)
            double linearX = (testPoint.getY() - factorB) / factorA;
            //sprawdzam czy znaleziony x, jest po lewej stornie pkt testPoint
            if( linearX <= testPoint.getX())
                return true;
            else
                return false;
        }
    }



    public static void main(String [] args) {
        ArrayList<Point2D> nmbs = new ArrayList<>();
        nmbs.add(new Point2D.Double(2, 2));
        nmbs.add(new Point2D.Double(3, 1));
        nmbs.add(new Point2D.Double(2, 4));
        nmbs.add(new Point2D.Double(6, 0));
        nmbs.add(new Point2D.Double(8, 22));


        System.out.println("############test equalation function ###########");
        System.out.println("(2,2) jest w array list (powinno być true) : " + checkIfExistInBoundaryOfConvexHull(nmbs, new Point2D.Double(2, 2)));
        System.out.println("(3,1) jest w array list (powinno być true) : " + checkIfExistInBoundaryOfConvexHull(nmbs, new Point2D.Double(3, 1)));
        System.out.println("(6,0) jest w array list (powinno być true) : " + checkIfExistInBoundaryOfConvexHull(nmbs, new Point2D.Double(6, 0)));
        System.out.println("(2,0) jest w array list (powinno być false) : " + checkIfExistInBoundaryOfConvexHull(nmbs, new Point2D.Double(2, 0)));

        System.out.println("/n/n");
        System.out.println("###########test isCuttingThisSection#############");
        Point2D [] tmp = new Point2D[2];
        tmp[0] = new Point2D.Double(1, 1);
        tmp[1] = new Point2D.Double(1, 8);

        System.out.println("Pkt (2, 5) jest po prawej stronie prostej x=1: " + isCuttingThisSection(new Point2D.Double(2, 5), tmp)+ " [powinno: true]");
        System.out.println("Pkt (0, 4) jest po prawej stronie prostej x=1: " + isCuttingThisSection(new Point2D.Double(0, 4), tmp)+ " [powinno: false]");
        System.out.println("Pkt (1, 5) jest po prawej stronie prostej x=1: " + isCuttingThisSection(new Point2D.Double(1, 5), tmp)+ " [powinno: true]");
        System.out.println("Pkt (0.99999, 3) jest po prawej stronie prostej x=1: " + isCuttingThisSection(new Point2D.Double(0.99999, 3), tmp)+ " [powinno: false]");
        System.out.println("Pkt (1.0000001, 14) jest po prawej stronie prostej x=1: " + isCuttingThisSection(new Point2D.Double(1.0000001, 14), tmp)+ " [powinno: true]");

        System.out.println("#Test dla odcinka normalnego A(2,3) B(34, 123)");
        tmp[0] = new Point2D.Double(2, 3);
        tmp[1] = new Point2D.Double(34, 123);

        System.out.println("Półprosta idąca na lewo od pkt (3.75, -4.5) (należący do odcinka AB) przecina odcinek AB? : " + isCuttingThisSection(new Point2D.Double(3.75, -4.5), tmp)+ " [powinno: true]");
        System.out.println("Półprosta idąca na lewo od pkt (5, 14.25) (należący do odcinka AB) przecina odcinek AB? : " + isCuttingThisSection(new Point2D.Double(5, 14.25), tmp)+ " [powinno: true]");
        System.out.println("Półprosta idąca na lewo od pkt (5, 12.25) przecina odcinek AB? : " + isCuttingThisSection(new Point2D.Double(5, 12.25), tmp)+ " [powinno: true]");
        System.out.println("Półprosta idąca na lewo od pkt (10, 12.25) przecina odcinek AB? : " + isCuttingThisSection(new Point2D.Double(10, 34), tmp)+ " [powinno: false]");
    }
}

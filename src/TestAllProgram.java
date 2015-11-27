import ProjectExceptions.ToFewPointsToFindConvexHullException;
import ProjectExceptions.ToFewPointsToMakePlygon;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by drapek on 27.11.15.
 */
public class TestAllProgram {
    private static final int MIN_NMB_RND_PNT = 10;
    private static final int MULT_PER_STEP = 10;
    private static final int ADD_PER_STEP = 20;
    private static final int STEP_NMBRS = 20;


    public static void main(String [] args) {
        Point2D [] triangle = new Point2D[10];

        triangle[0] = new Point2D.Double(0, 7.5);
        triangle[1] = new Point2D.Double(5, 4);
        triangle[2] = new Point2D.Double(-2, 3);//wierzchołek trójkąta
        triangle[3] = new Point2D.Double(1, 7);
        triangle[4] = new Point2D.Double(11, 4);
        triangle[5] = new Point2D.Double(18, 3);//wierzchołek 2 trójkąta
        triangle[6] = new Point2D.Double(2, 11);
        triangle[7] = new Point2D.Double(3, 5);
        triangle[8] = new Point2D.Double(4, 6);
        triangle[9] = new Point2D.Double(4, 18);//wierzchołek 3 trójkąta

        try {
            GrahamScan grahScan = new GrahamScan(triangle);
            ArrayList <Point2D> convexHull =  grahScan.findHull();

            double realAreaOfTriangle = 0.5 * 20 * 15;
            System.out.println("Rzeczywista wielkość pola obliczonego trójkąta to: " + realAreaOfTriangle);
            System.out.println("Znalezione punkty otoczki: ");
            for(Point2D oneHullPoint : convexHull)
                System.out.println("\t("+ oneHullPoint.getX() +","+ oneHullPoint.getY() +")");

            System.out.println("Mierzenie pola metodą Monte Carlo:\n" +
                               "\"Il. próbek\";\"Pole Monte\";\"odchył od pola rzeczywistego\";\"czas [ns]\"");

            for( int i = 0; i < STEP_NMBRS; i++) {
                CalculateAreaMonteCarlo calcArea = new CalculateAreaMonteCarlo(convexHull);
                int nmbSamples = (i ==0 ? MIN_NMB_RND_PNT : MIN_NMB_RND_PNT * i * MULT_PER_STEP);

                calcArea.setNumerOfSamples(nmbSamples);

                long startTime = System.nanoTime();
                double result = calcArea.calculateArea();
                long estimatedTime = System.nanoTime() - startTime;

                System.out.println(nmbSamples + ";" + result  + ";" + Math.abs(result - realAreaOfTriangle) + ";" + estimatedTime );
            }


        } catch(ToFewPointsToFindConvexHullException e) {
            System.out.println("Podany zestaw ma zbyt mało punktów by znaleźć w nim otoczkę!");
        } catch (ToFewPointsToMakePlygon e) {
            System.out.println("Znaleziona otoczka ma niepoprawną ilość punktów!");
        }




    }
}

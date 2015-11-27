import ProjectExceptions.ToFewPointsToMakePlygon;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by drapek on 01.11.15.
 */
public class CalculateAreaMonteCarlo {
    private ArrayList <Point2D> fieldBoundaryPoints;

    private Point2D minPointOuterRectangle;
    private Point2D maxPointOuterRectangle;
    private Random rnd = new Random();

    private int randomSamplesNumber = 200;
    private int hitsNumber;


    public CalculateAreaMonteCarlo(ArrayList <Point2D> fieldBoundaryPoints) throws ToFewPointsToMakePlygon {
        if( fieldBoundaryPoints.size() != 0)
            this.fieldBoundaryPoints = fieldBoundaryPoints;
        else throw new ToFewPointsToMakePlygon();

        minPointOuterRectangle = findMinimalPointLimitingFigure();
        maxPointOuterRectangle = findMaximalPointLimitingFigure();
    }

    public void setNumerOfSamples(int naturalNumber) {
        if( naturalNumber < 1)
            System.out.println("Nie mogłem zmienić liczby powtórzeń losowań próbek na liczbę ujemną!");
        else
            randomSamplesNumber = naturalNumber;
    }

    public double calculateArea() {
        hitsNumber = 0;

        for(int i = 0; i < randomSamplesNumber; i++) {
            if(StaticPointChecker.isInsideConvexHull(fieldBoundaryPoints, randomPoint()) )
                hitsNumber++;
        }

        double outerRectangleArea = (maxPointOuterRectangle.getX() - minPointOuterRectangle.getX()) * (maxPointOuterRectangle.getY() - minPointOuterRectangle.getY());
        return (double) hitsNumber / (double) randomSamplesNumber * outerRectangleArea;
    }



    private Point2D randomPoint() {
        double randX = rnd.nextDouble()*(maxPointOuterRectangle.getX() - minPointOuterRectangle.getX()) + minPointOuterRectangle.getX();
        double randY = rnd.nextDouble()*(maxPointOuterRectangle.getY() - minPointOuterRectangle.getY()) + minPointOuterRectangle.getY();

        return new Point2D.Double(randX, randY);

    }

    private Point2D findMinimalPointLimitingFigure() {
        double minX = fieldBoundaryPoints.get(0).getX();
        double minY = fieldBoundaryPoints.get(0).getY();;
        for( Point2D each : fieldBoundaryPoints) {
            if( each.getX() < minX )
                minX = each.getX();
            if( each.getY() < minY )
                minY = each.getY();
        }

        return new Point2D.Double(minX, minY);
    }

    private Point2D findMaximalPointLimitingFigure() {
        double maxX = fieldBoundaryPoints.get(0).getX();
        double maxY = fieldBoundaryPoints.get(0).getY();;
        for( Point2D each : fieldBoundaryPoints) {
            if( each.getX() > maxX )
                maxX = each.getX();
            if( each.getY() > maxY )
                maxY = each.getY();
        }

        return new Point2D.Double(maxX, maxY);
    }

    public static void main(String [] args) {
        ArrayList <Point2D> testsPoints = new ArrayList<>();
        testsPoints.add(new Point2D.Double(2, 2));
        testsPoints.add(new Point2D.Double(8, 3));
        testsPoints.add(new Point2D.Double(9, 7));
        testsPoints.add(new Point2D.Double(6, 11));
        testsPoints.add(new Point2D.Double(3, 9));
        testsPoints.add(new Point2D.Double(1, 6));

        try {
            CalculateAreaMonteCarlo testCalculateArea = new CalculateAreaMonteCarlo(testsPoints);

            System.out.println("##########Test isInsideFigure#########");

            System.out.println("(5, 4) jest w figurze: " + StaticPointChecker.isInsideConvexHull(testsPoints, new Point2D.Double(5, 4))); //powinno dać true
            System.out.println("(9, 2,5) jest w figurze: " + StaticPointChecker.isInsideConvexHull(testsPoints, new Point2D.Double(9, 2.5))); //powinno dać false
            System.out.println("(1, 3) jest w figurze: " + StaticPointChecker.isInsideConvexHull(testsPoints, new Point2D.Double(1, 3))); //powinno dać false
            System.out.println("(2, 10) jest w figurze: " + StaticPointChecker.isInsideConvexHull(testsPoints, new Point2D.Double(2, 10))); //powinno dać false

            System.out.println("##########Test liczenia pola powierzchni#########");
            System.out.println("Pole podanje figury to: " + testCalculateArea.calculateArea());

        } catch (ToFewPointsToMakePlygon toFewPointsToMakePlygon) {
            toFewPointsToMakePlygon.printStackTrace();
        }


    }
}

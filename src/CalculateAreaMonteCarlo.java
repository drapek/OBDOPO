import ProjectExceptions.ToFewPointsToMakePlygon;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by drapek on 01.11.15.
 */
public class CalculateAreaMonteCarlo {
    ArrayList <Point2D> fieldBoundaryPoints;

    Point2D minPointOuterRectangle;
    Point2D maxPointOuterRectangle;
    Random rnd = new Random();

    int randomSamplesNumber = 200;
    int hitsNumber;


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
            if(isInsideFigure(randomPoint()))
                hitsNumber++;
        }

        double outerRectangleArea = (maxPointOuterRectangle.getX() - minPointOuterRectangle.getX()) * (maxPointOuterRectangle.getY() - minPointOuterRectangle.getY());
        return (double) hitsNumber / (double) randomSamplesNumber * outerRectangleArea;
    }

    private boolean isInsideFigure(Point2D check) {
        for (int i = 0, j = fieldBoundaryPoints.size() - 1; i < fieldBoundaryPoints.size(); j = i++) {
            if ((fieldBoundaryPoints.get(i).getY() > check.getY()) != (fieldBoundaryPoints.get(j).getY() > check.getY()) &&
                    (check.getX() < (fieldBoundaryPoints.get(j).getX() - fieldBoundaryPoints.get(i).getX()) * (check.getY() - fieldBoundaryPoints.get(i).getY()) / (fieldBoundaryPoints.get(j).getY() - fieldBoundaryPoints.get(i).getY()) + fieldBoundaryPoints.get(i).getX())) {
                return true;
            }
        }
        return false;
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
        testsPoints.add(new Point2D.Double(1, 1));
        testsPoints.add(new Point2D.Double(10, 2));
        testsPoints.add(new Point2D.Double(9, 10));
        testsPoints.add(new Point2D.Double(3, 8));
        testsPoints.add(new Point2D.Double(0, 5));

        try {
            CalculateAreaMonteCarlo testCalculateArea = new CalculateAreaMonteCarlo(testsPoints);

            System.out.println("##########Test isInsideFigure#########");
            System.out.println("(7, 5) jest w figurze: " + testCalculateArea.isInsideFigure(new Point2D.Double(7, 5)));
            System.out.println("(10, 11) jest w figurze: " + testCalculateArea.isInsideFigure(new Point2D.Double(10, 11)));

            System.out.println("##########Test liczenia pola powierzchni#########");
            System.out.println("Pole podanje figury to: " + testCalculateArea.calculateArea());

        } catch (ToFewPointsToMakePlygon toFewPointsToMakePlygon) {
            toFewPointsToMakePlygon.printStackTrace();
        }


    }
}

import com.sun.media.sound.InvalidFormatException;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by drapek on 27.10.15.
 */
public class ReadPointsFromFile {
    FileReader readedFile;

    public ReadPointsFromFile(String path) throws IOException {
        readedFile = new FileReader(path);
    }

    public Point2D [] readPoints() throws InvalidFormatException, NumberFormatException, IOException {
        BufferedReader reader = new BufferedReader(readedFile);
        String line;
        ArrayList <Point2D> readedPoints = new ArrayList<>();

        while( (line = reader.readLine() ) != null ) {
            String [] coordinatsXY = line.split(" ");

            if(coordinatsXY.length != 2 )
                throw new InvalidFormatException();
            else {
                //check if readed strings are numbers
                if(coordinatsXY[0].matches(".*\\d.*") && coordinatsXY[1].matches(".*\\d.*")) {
                    double x = Double.parseDouble(coordinatsXY[0]);
                    double y = Double.parseDouble(coordinatsXY[1]);
                    Point2D.Double tmpPoint = new Point2D.Double();
                    tmpPoint.setLocation(x, y);
                    readedPoints.add(tmpPoint);

                } else
                    throw new InvalidFormatException();

            }

        }

        Point2D [] allReadedPoints = new Point2D[readedPoints.size()];
        allReadedPoints = readedPoints.toArray(allReadedPoints);

        return allReadedPoints;
    }

    public static void main(String [] args) {
        /* good file example */
        System.out.println("######Przypadek dla dobrego pliku#########");
        Point2D [] points = null;
        try {
            ReadPointsFromFile testRead = new ReadPointsFromFile("./TestFiles/points_goodFormat.txt");
            points = testRead.readPoints();

        } catch (InvalidFormatException ex) {
            System.out.println("Użyłeś złego formatu we wprowadzanym pliku!");
        } catch (IOException ex) {
            System.out.println("Nie moge otworzyć pliku! Sprawdź czy podana ścieżka jest poprawna!");
        }

        if( points != null)
            for(Point2D each : points)
                System.out.println("    x:" + each.getX() + "   y:" + each.getY());

        /* wrong file example */
        System.out.println("######Przypadek dla złego pliku#########");
        points = null;

        try {
            ReadPointsFromFile testRead = new ReadPointsFromFile("./TestFiles/points_wrongFormat.txt");
            points = testRead.readPoints();

        } catch (InvalidFormatException ex) {
            System.out.println("Użyłeś złego formatu we wprowadzanym pliku!");
        } catch (IOException ex) {
            System.out.println("Nie moge otworzyć pliku! Sprawdź czy podana ścieżka jest poprawna!");
        } catch (NumberFormatException ex) {
            System.out.println("Gdzieś zamiast liczby wstawiłeś litere!");
        }

        if( points != null)
            for(Point2D each : points) {
                System.out.println("    x:" + each.getX() + "   y:" + each.getY());
            }
        else
            System.out.println("Nie wczytano punktów, przepraszamy :(");
    }
}

package sp1r0s.nqueen.util;

import sp1r0s.nqueen.model.Chessboard;
import sp1r0s.nqueen.model.Coordinates;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

public final class Utilities {

    public static Coordinates getMostCentralCoordinate(List<Coordinates> coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            throw new IllegalStateException("List should not be undefined or empty");
        }
        Coordinates mostCentralCoordinate = coordinates.get(0);
        for (Coordinates coordinate : coordinates) {
            if (Math.abs(coordinate.getX() - coordinate.getY()) < Math.abs(mostCentralCoordinate.getX() - mostCentralCoordinate.getY())) {
                mostCentralCoordinate = coordinate;
            }
        }
        return mostCentralCoordinate;
    }

    public static void printChessboard(final Chessboard chessboard) {
        final int numberOfRows = chessboard.getNumberOfRows();
        final int numberOfColumns = chessboard.getNumberOfColumns();
        final String indexesSpace = calculateIndexSpace(numberOfColumns);
        final String fileName = numberOfColumns + "x" + numberOfColumns + ".txt";
        final Set<Coordinates> queensLocation = chessboard.getQueensLocation();

        try {

            Files.deleteIfExists(new File(fileName).toPath());

            final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            writer.print(indexesSpace + indexesSpace + " 0");
            for (int columnIndex = 1; columnIndex < numberOfColumns; columnIndex++) {
                writer.print(indexesSpace.substring(String.valueOf(columnIndex).length() - 1) + columnIndex);
            }
            writer.println();
            for (int i = 0; i < numberOfRows; i++) {
                writer.print(indexesSpace.substring(String.valueOf(i).length()) + i + "|");
                for (int j = 0; j < numberOfColumns; j++) {
                    writer.print(indexesSpace);
                    if (queensLocation.contains(new Coordinates(i, j))) {
                        writer.print("Q");
                    } else {
                        writer.print("*");
                    }
                }
                writer.println(indexesSpace + "|");
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String calculateIndexSpace(final int numberOfColums) {
        int numberOfDigits = String.valueOf(numberOfColums).length();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfDigits; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

}
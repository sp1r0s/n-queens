package sp1r0s.nqueen.util;

import sp1r0s.nqueen.model.Chessboard;
import sp1r0s.nqueen.model.Coordinates;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
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

    public static void showChessboard(final Chessboard chessboard) {
        final JFrame frame = getGui(chessboard);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                synchronized (Utilities.class) {
                    frame.setVisible(false);
                    Utilities.class.notify();
                }
            }
        });
        final Thread thread = new Thread() {
            public void run() {
                synchronized(Utilities.class) {
                    while (frame.isVisible()) {
                        try {
                            Utilities.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static JFrame getGui(final Chessboard chessboard) {
        final int rows = chessboard.getNumberOfRows();
        final int columns = chessboard.getNumberOfColumns();
        final JPanel container = new JPanel(new BorderLayout(3, 3));
        final JButton[][] chessBoardSquares = new JButton[rows][columns];
        final JPanel chessBoard = new JPanel(new GridLayout(0, columns + 1));
        final Set<Coordinates> queensLocation = chessboard.getQueensLocation();

        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                final JButton squareButton = new JButton();
                squareButton.setMargin(new Insets(0,0,0,0));
                ImageIcon icon;
                if (queensLocation.contains(new Coordinates(i,j))) {
                    final BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = image.createGraphics();
                    g.setColor(Color.RED);
                    g.fillRect(0, 0, 64, 64);
                    g.dispose();
                    icon = new ImageIcon(image);
                } else {
                    icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                }

                squareButton.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    squareButton.setBackground(Color.WHITE);
                } else {
                    squareButton.setBackground(Color.BLACK);
                }
                chessBoardSquares[j][i] = squareButton;
            }
        }

        chessBoard.add(new JLabel(""));
        for (int i = 0; i < rows; i++) {
            chessBoard.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j == 0) {
                    chessBoard.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
                }
                chessBoard.add(chessBoardSquares[j][i]);
            }
        }

        chessBoard.setBorder(new LineBorder(Color.BLACK));
        container.add(chessBoard);

        final JFrame jFrame = new JFrame("Chessboard");
        final JScrollPane jScrollPane = new JScrollPane(container);
        //todo: this kind of works for many squares, however it doesn't work great
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jFrame.add(jScrollPane);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setLocationByPlatform(true);
        jFrame.pack();
        jFrame.setVisible(true);
        return jFrame;
    }
}
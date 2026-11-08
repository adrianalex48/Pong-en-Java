package mx.unison.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("Pong");
        f.setResizable(false);

        GameModel model = new GameModel();
        Board board = new Board(model);
        GameController controller = new GameController(model, board);

        setupKeyBindings(board, model, controller);

        JPanel p = new JPanel(new BorderLayout());
        p.add(board, BorderLayout.CENTER);

        f.setContentPane(p);

        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stop();
                f.dispose();
                System.exit(0);
            }
        });

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        controller.start();
    }

    private static void setupKeyBindings(JComponent component, GameModel model, GameController controller) {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("pressed LEFT"), "leftPressed");
        inputMap.put(KeyStroke.getKeyStroke("released LEFT"), "leftReleased");
        inputMap.put(KeyStroke.getKeyStroke("pressed RIGHT"), "rightPressed");
        inputMap.put(KeyStroke.getKeyStroke("released RIGHT"), "rightReleased");
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "spacePressed");
        inputMap.put(KeyStroke.getKeyStroke("R"), "rPressed");

        actionMap.put("leftPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setMovingLeft(true);
            }
        });

        actionMap.put("leftReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setMovingLeft(false);
            }
        });

        actionMap.put("rightPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setMovingRight(true);
            }
        });

        actionMap.put("rightReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setMovingRight(false);
            }
        });

        actionMap.put("spacePressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.pause();
            }
        });

        actionMap.put("rPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resume();
            }
        });
    }
}
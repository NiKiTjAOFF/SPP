package EngineTester;

import renderEngine.DisplayManager;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public static final int CANVAS_ID = 0;
    public static final int SPAWN_POINT_X_LABEL_ID = 1;
    private static final String SPAWN_POINT_X_LABEL_CONTENT = "SpawnPoint X:";
    public GUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 229, 180));
        this.setLayout(null);

        Canvas canvas = new Canvas();
        canvas.setSize(DisplayManager.WIDTH, DisplayManager.HEIGHT);
        canvas.setLocation(0, this.getHeight() - canvas.getHeight());

        JLabel spawnPointXLabel = createLabel(SPAWN_POINT_X_LABEL_CONTENT, 0, 0);


        this.add(canvas);
        this.add(spawnPointXLabel);

        this.setVisible(true);
    }

    private static JLabel createLabel(String labelContent, int x, int y) {
        JLabel label = new JLabel(labelContent);
        FontMetrics labelMetrics = label.getFontMetrics(label.getFont());
        int spawnPointXLabelWidth = labelMetrics.stringWidth(label.getText());
        int spawnPointXLabelHeight = labelMetrics.getAscent() + labelMetrics.getDescent();
        label.setSize(spawnPointXLabelWidth, spawnPointXLabelHeight);
        label.setLocation(x, y);
        return label;
    }
}

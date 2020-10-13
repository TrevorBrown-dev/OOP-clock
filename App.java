import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        int WIDTH = 600;
        int HEIGHT = 600;

        JFrame frame = new JFrame();
        // Shape dimensions
        Clock shape = new Clock(0, 0, 600);

        ShapeIcon icon = new ShapeIcon(shape, WIDTH, HEIGHT);

        final JLabel label = new JLabel(icon);
        label.setOpaque(true);
        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(WIDTH + 200, HEIGHT + 200);
        frame.setVisible(true);
        final int DELAY = 100;

        Timer t = new Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shape.setMinute(1);
                label.repaint();
            }
        });
        t.start();

    }
}

class Clock implements Moveable {
    private int x;
    private int y;
    private int width;

    private int hAngle;
    private int mAngle;
    private int sAngle;

    public Clock(int x, int y, int width) {
        mAngle = 0;
        this.x = x;
        this.y = y;
        this.width = width;
    }

    public void setMinute(int minute) {
        this.mAngle += minute;
    }

    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g2) {
        Ellipse2D.Double clock = new Ellipse2D.Double(x, y, width, width);
        Point2D.Double endMinutes = new Point2D.Double(300 + (width * 0.5) * Math.cos(Math.toRadians(mAngle)),
                300 + (width * 0.5) * Math.sin(Math.toRadians(mAngle)));

        g2.setColor(Color.decode("#00AA00"));
        g2.fill(clock);
        g2.draw(clock);

        Shape minutes = new Line2D.Double(new Point(width / 2, width / 2), endMinutes);

        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.decode("#AA0000"));

        // Rotate the clock hand;
        // g2.fill(minutes);
        g2.draw(minutes);

    }
}

class ShapeIcon implements Icon {
    private int width;
    private int height;
    private Moveable shape;

    public ShapeIcon(Moveable s, int w, int h) {
        shape = s;
        width = w;
        height = h;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        shape.draw(g2);

    }
}
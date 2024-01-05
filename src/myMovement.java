import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class myMovement extends JPanel {
    private static final Color Background = Color.WHITE;
    private int panelWidth;
    private int panelHeight;
    public List<ColorShape> shapes = new ArrayList<>();


    public myMovement() {

        setBounds(10,100,960,550);
        setBackground(Color.green);
        setBackground(Background);

        MyMouse myMouse = new MyMouse();
        addMouseListener(myMouse);
        addMouseMotionListener(myMouse);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // do JPanel house-keeping painting
        super.paintComponent(g);

        // create smooth curves
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // iterate through the shapes list, drawing each shape
        for (ColorShape colorShape : shapes) {
            colorShape.draw(g2);
        }
    }

    // size the JPanel appropriately
    @Override
    public Dimension getPreferredSize() {
        Dimension prefSize = super.getPreferredSize();
        if (isPreferredSizeSet()) {
            return prefSize;
        }
        int w = Math.max(prefSize.width, panelWidth);
        int h = Math.max(prefSize.height, panelHeight);
        return new Dimension(w, h);
    }

    // combination mouse listener and mouse motion listener
    private class MyMouse extends MouseAdapter {
        private ColorShape colorShape; // the current color shape that we're dragging
        private Point p; // previous location of the color shape

        @Override
        public void mousePressed(MouseEvent e) {
            // iterate *backward* through the list
            for (int i = shapes.size() - 1; i >= 0; i--) {
                // get the shape in the list
                ColorShape colorShape = shapes.get(i);
                // if it contains the current point, we've got it!
                if (colorShape.contains(e.getPoint())) {
                    // set the mouse adapter colorShape field with this shape
                    this.colorShape = colorShape;
                    // set the current point, p
                    this.p = e.getPoint();

                    //NIEPOTRZEBNE

                    // *remove* the shape from the list
                    shapes.remove(colorShape);

                    // re-add it so it is now the last shape in the list
                    shapes.add(colorShape);

                    // KONIEC NIEPOTRZEBNYCH

                    // draw all shapes
                    repaint();
                    return;  // we're done
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            moveShape(e); // move the dragged shape
            colorShape = null;  // and release the reference to it
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            moveShape(e); // move the dragged shape
        }

        private void moveShape(MouseEvent e) {
            // if we are not currently dragging a shape
            if (colorShape == null) {
                return;  // get out of here
            }

            // otherwise translate this shape using the 2 points
            colorShape.translate(p, e.getPoint());
            repaint();

            // re-set the current point
            p = e.getPoint();
        }
    }


}

class ColorShape {
    public static final int WIDTH = 100;
    private Color color;
    private int x;
    private int y;

    char character;

    // path 2d objects are Shape objects that can be translated
    // easily using an affine transform
    private Path2D path;

    public ColorShape(Color color, int x, int y) {
        super();
        this.color = color;
        this.x = x;
        this.y = y;

        // create an ellipse (circle) and make a Path2D object with it
        Shape shape = new Rectangle2D.Double(x - WIDTH / 2, y - WIDTH / 2, WIDTH, WIDTH);
        path = new Path2D.Double(shape);
    }

    // test if the point is contained by this shape
    public boolean contains(Point p) {
        return path.contains(p);
    }

    // draw our shape
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(path);
        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(character),x - WIDTH / 2, y - WIDTH / 2);
        g2.draw(path);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public Color getColor() {
        return color;
    }

    // the magic of affine transforms
    public void translate(Point p0, Point p1) {
        int tx = p1.x - p0.x;
        int ty = p1.y - p0.y;
        path.transform(AffineTransform.getTranslateInstance(tx, ty));
    }

}
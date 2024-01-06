import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;

public class myMovement extends JPanel {
    private static final Color Background = Color.WHITE;
    private int panelWidth;
    private int panelHeight;
    public List<ColorShape> movableLetters = new ArrayList<>();
    public List<AnswerRectangle> answerRectangles = new ArrayList<>();

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
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // antyaliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // rysowanie kwadratów oraz liter
        for (AnswerRectangle rect : answerRectangles) {
            rect.draw(g2);
        }

        for (ColorShape colorShape : movableLetters) {
            colorShape.draw(g2);
        }

    }


    private class MyMouse extends MouseAdapter {
        private ColorShape colorShape; // aktualna litera, którą ruszamy
        private Point p; // poprzednie położenie litery

        @Override
        public void mousePressed(MouseEvent e) {
            for (int i = movableLetters.size() - 1; i >= 0; i--) {
                // wzięcie używanej litery
                ColorShape colorShape = movableLetters.get(i);
                if (colorShape.contains(e.getPoint())) {
                    this.colorShape = colorShape;
                    this.p = e.getPoint();

                    // aktualna litera na wierzchu
                    movableLetters.remove(colorShape);
                    movableLetters.add(colorShape);

                    repaint();
                    return;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            moveShape(e); // ruch literą
            colorShape = null;  // usunięcie referencji
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            moveShape(e); // ruch literą
        }

        private void moveShape(MouseEvent e) {
            // jeśli nie ruszamy literą - wyjście
            if (colorShape == null) {
                return;
            }

            // jeśli ruszamy - translate i aktualizacja X oraz Y
            colorShape.setX(e.getX());
            colorShape.setY(e.getY());
            colorShape.translate(p, e.getPoint());

            for (int i=0;i<answerRectangles.size(); ++i) {
                colorShape.intersecting(answerRectangles.get(i).rect);
                System.out.println(i + " rect = " + colorShape.placedCorrectly);
                //  System.out.println(answerRectangles.get(i).rect.x + " " + answerRectangles.get(i).rect.y + " " + answerRectangles.get(i).rect.width + " " + answerRectangles.get(i).rect.height + " ");
                System.out.println(colorShape.getX() + " " + colorShape.getY() + " ");
            }


           // System.out.println(colorShape.getX() + " " + colorShape.getY());
           // drawString(String.valueOf(colorShape.character), colorShape.gettx(p, e.getPoint()), colorShape.gettx(p, e.getPoint()));
            repaint();

            // reset
            p = e.getPoint();

        }
    }

}



class ColorShape {
    private Color color;
    private int x;
    private int y;
    public boolean placedCorrectly;
    public String character;
    private Path2D path;
    private Shape shape;
    private Rectangle intersectionZone;

    public ColorShape(Color color, String character) {
        super();
        this.color = color;
        this.character = character;
        this.placedCorrectly = false;

        // tworzenie litery
        TextLayout textTl = new TextLayout(character, new Font("Helvetica", Font.BOLD, 92),new FontRenderContext(null, false, false));
        AffineTransform textAt = new AffineTransform();
        textAt.translate(0, (float)textTl.getBounds().getHeight());
        shape = textTl.getOutline(textAt);
        intersectionZone = new Rectangle(x, y, 25, 25);
        path = new Path2D.Double(shape);
    }

    // czy punkt zawarty jest w literze
    public boolean contains(Point p) {
        return path.contains(p);
    }

    // sprawdzenie czy kształty się przecinają
    public void intersecting(Rectangle rectangle){
        if(this.intersectionZone.intersects(rectangle.x, rectangle.y, rectangle.width, rectangle.height)) {
            this.placedCorrectly = true;
        }
        else this.placedCorrectly = false;
    }

    // rysuj
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(path);
        g2.setColor(Color.BLACK);
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


    // przekształcenie afiniczne (C)jak to działa to ja nie wiem)
    public void translate(Point p0, Point p1) {
        int tx = p1.x - p0.x;
        int ty = p1.y - p0.y;
        path.transform(AffineTransform.getTranslateInstance(tx, ty));
        intersectionZone.x = getX();
        intersectionZone.y = getY();
    }
}

class AnswerRectangle{
    private int x;
    private int y;
    private int SIZE = 90;
    private Path2D path;
    public Rectangle rect;
    public AnswerRectangle(int x, int y){
        this.x = x;
        this.y = y;

        rect = new Rectangle(x, y ,SIZE, SIZE);
        path = new Path2D.Double(rect);

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fill(path);
        g2.setColor(Color.black);
        g2.draw(path);
    }

}
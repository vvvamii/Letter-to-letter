import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.util.Objects;

// Klasa zajmująca się ruchem liter, rysowaniem wszystkich elementów i wyświetlaniem tego w stworzonym JPanelu
public class DragShapes extends JPanel {
    private static final Color Background = Color.WHITE;
    public List<MovableLetter> movableLetters = new ArrayList<>();
    public List<AnswerRectangle> answerRectangles = new ArrayList<>();
    public ImageQuestion image;

    // JPanel z elementami interaktywnymi gry
    public DragShapes() {
        setLayout(new BorderLayout());
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

        // Antyaliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rysowanie kwadratów, liter i obrazka
        for (AnswerRectangle rect : answerRectangles) {
            rect.draw(g2);
        }

        for (MovableLetter movableLetter : movableLetters) {
            movableLetter.draw(g2);
        }

        image.draw(g2);
    }


    private class MyMouse extends MouseAdapter {
        private MovableLetter movableLetter; // Aktualna litera, którą ruszamy
        private Point p; // Poprzednie położenie litery

        @Override
        public void mousePressed(MouseEvent e) {
            for (int i = movableLetters.size() - 1; i >= 0; i--) {
                // Wzięcie używanej litery
                MovableLetter movableLetter = movableLetters.get(i);
                if (movableLetter.contains(e.getPoint())) {
                    this.movableLetter = movableLetter;
                    this.p = e.getPoint();

                    // Aktualna litera na wierzchu
                    movableLetters.remove(movableLetter);
                    movableLetters.add(movableLetter);

                    repaint();
                    return;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            moveShape(e); // Ruch literą
            movableLetter = null;  // Usunięcie powiązania z konkretną literą
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            moveShape(e); // Ruch literą
        }

        private void moveShape(MouseEvent e) {
            // Jeśli nie ruszamy literą - wyjście
            if (movableLetter == null) {
                return;
            }

            // Jeśli ruszamy - translate i aktualizacja X oraz Y
            movableLetter.setX(e.getX());
            movableLetter.setY(e.getY());
            movableLetter.translate(p, e.getPoint());

            for (int i=0;i<answerRectangles.size(); ++i) movableLetter.intersecting(answerRectangles.get(i));

            repaint();

            // Reset punktu
            p = e.getPoint();
        }
    }
}

// Klasa zajmująca się tworzeniem i wyświetlaniem interaktywnych liter
class MovableLetter {
    private Color color;
    private int x;
    private int y;
    public boolean placedCorrectly;
    public String character;
    private Path2D path;
    private Shape shape;
    private Rectangle intersectionZone;

    // Konstruktor interaktywnych liter
    public MovableLetter(int x, int y, Color color, String character) {
        super();
        this.x = x;
        this.y = y;
        this.color = color;
        this.character = character;
        this.placedCorrectly = false;

        // Tworzenie litery
        TextLayout textTl = new TextLayout(character, new Font("Helvetica", Font.BOLD, 80),new FontRenderContext(null, false, false));
        AffineTransform textAt = new AffineTransform();
        textAt.translate(0, (float)textTl.getBounds().getHeight());
        shape = textTl.getOutline(textAt);
        intersectionZone = new Rectangle(x, y, 25, 25);
        path = new Path2D.Double(shape);
    }

    // Czy punkt zawarty jest w literze
    public boolean contains(Point p) {
        return path.contains(p);
    }

    // Sprawdzenie czy odpowiednia litera przecina się z odpowiednim kwadratem
    public void intersecting(AnswerRectangle answerRectangle){
        if(this.intersectionZone.intersects(answerRectangle.rect.x, answerRectangle.rect.y, answerRectangle.rect.width, answerRectangle.rect.height)) {
            if(Objects.equals(this.character, answerRectangle.character)) this.placedCorrectly = true;
            else this.placedCorrectly = false;
        }
        else this.placedCorrectly = false;
    }

    public void forceIntersecting(AnswerRectangle answerRectangle){
        if(this.intersectionZone.intersects(answerRectangle.rect.x, answerRectangle.rect.y, answerRectangle.rect.width, answerRectangle.rect.height)) {
            if(Objects.equals(this.character, answerRectangle.character)) this.placedCorrectly = true;
        }
    }

    // Rysuj literę
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


    // Przekształcenie afiniczne (jak to działa???)
    public void translate(Point p0, Point p1) {
        int tx = p1.x - p0.x;
        int ty = p1.y - p0.y;
        path.transform(AffineTransform.getTranslateInstance(tx, ty));
        intersectionZone.x = getX();
        intersectionZone.y = getY();
    }

}

// Klasa zajmująca się tworzeniem i wyświetlaniem kwadratów-pól na odpowiedź
class AnswerRectangle{
    private int x;
    private int y;
    private int SIZE = 80;
    public String character;
    private Path2D path;
    public Rectangle rect;
    public AnswerRectangle(int x, int y, String character){
        this.x = x;
        this.y = y;
        this.character = character;

        rect = new Rectangle(x, y ,SIZE, SIZE);
        path = new Path2D.Double(rect);

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fill(path);
        g2.setColor(Color.black);
        g2.draw(path);
        g2.drawString(String.valueOf(this.character), this.x, this.y);
    }
}

// Klasa zajmująca się wyświetlaniem obrazka do obecnego pytania
class ImageQuestion{
    private Image image;

    public ImageQuestion(){
    }

    void setImage(InputStream is){
        try {
            this.image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, 350, 10, 150, 150, null);
    }
}
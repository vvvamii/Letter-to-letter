import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class myMovement implements MouseListener, MouseMotionListener {

    private int coordX, coordY;

    public myMovement(Component... components){
        for(Component component : components){
            component.addMouseListener(this);
            component.addMouseMotionListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        coordX = e.getX();
        coordY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        e.getComponent().setLocation((e.getX()+e.getComponent().getX()) - coordX, (e.getY()+e.getComponent().getY()) - coordY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

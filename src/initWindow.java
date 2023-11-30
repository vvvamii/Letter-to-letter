import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class initWindow extends JFrame{

    static backgroundJFrame myFrame = new backgroundJFrame();

    void initialize(){
        setContentPane(myFrame.backgroundJPanel);
        setTitle("XDDDD");
        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


    }
   static void scoreboardMode(){
        Highscore highscore = new Highscore();
        ArrayList list = highscore.loadhighscore();
        JPanel panel = highscore.panel();
        panel.setLayout(myFrame.backgroundJPanel.getLayout());
        myFrame.backgroundJPanel.add(panel);
        panel.setVisible(true);
        System.out.println("TRYB 1 !!!!!");
    }
}

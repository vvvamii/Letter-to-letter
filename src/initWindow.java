import javax.swing.*;
import java.util.ArrayList;

public class initWindow extends JFrame{

    static backgroundJFrame myFrame = new backgroundJFrame();
    String selectedGameMode = "";

    void initialize(){
        setContentPane(myFrame.backgroundJPanel);
        setTitle("Litera do litery - projekt");
        setSize(1000,700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


    }
    void scoreboardMode(){
        Highscore highscore = new Highscore();
        ArrayList list = highscore.loadhighscore();
        JPanel panel = highscore.panel();
        myFrame.backgroundJPanel.add(panel);
        panel.setVisible(true);
    }
    void gameMode(){
        GameJPanel gameJPanel = new GameJPanel();
        JPanel panel = gameJPanel.panel(selectedGameMode);
        myFrame.backgroundJPanel.add(panel);
        panel.setVisible(true);
    }

}

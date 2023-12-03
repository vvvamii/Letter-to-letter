import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class backgroundJFrame extends JFrame{
    public JPanel backgroundJPanel;
    private JButton scoreboardButton;
    private JPanel menuJPanel;
    private JLabel mainMenuLabel;
    private JButton gameButton;
    private JPanel gameJPanel;
    private JButton quitButton;
    private JButton HRATButton;

    public backgroundJFrame() {

        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                initWindow tempInit = new initWindow();
                tempInit.scoreboardMode();
            }
        });

        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                backgroundJPanel.setVisible(false);
                movableJPanels test = new movableJPanels();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        HRATButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                GamePanel gp = new GamePanel();
                JPanel asd = gp.addingPanel();
                add(asd);
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                // landingJPanel.setVisible(true);
            }
        });
    }



}

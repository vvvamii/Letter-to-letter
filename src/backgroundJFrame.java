import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class backgroundJFrame extends JFrame{
    public JPanel backgroundJPanel;
    private JButton scoreboardButton;
    private JButton backButton;
    private JPanel menuJPanel;
    private JPanel landingJPanel;
    private JLabel mainMenuLabel;
    private JButton gameButton;
    private JPanel gameJPanel;
    private JButton quitButton;
    private JPanel hiscoreJPanel;
    private JButton HRATButton;
    private JList list1;

    static int tryb = 0;
    public backgroundJFrame() {

        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
               // landingJPanel.setVisible(true);

                initWindow inassa = new initWindow();
                tryb = 1;
                inassa.scoreboardMode();

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(true);
                landingJPanel.setVisible(false);
            }
        });

        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                landingJPanel.setVisible(false);
                backgroundJPanel.setVisible(false);
                movableJPanels test = new movableJPanels();
                gameJPanel.setVisible(true);
              //  JOptionPane.showMessageDialog(gameJPanel, "asdasdasd");
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

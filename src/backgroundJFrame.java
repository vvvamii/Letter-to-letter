import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class backgroundJFrame extends JFrame{
    public JPanel backgroundJPanel;
    private JButton scoreboardButton;
    private JPanel menuJPanel;
    private JLabel mainMenuLabel;
    private JButton gameButton;
    private JButton quitButton;

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
                initWindow tempInit = new initWindow();
                tempInit.gameMode();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }



}

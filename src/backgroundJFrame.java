import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class backgroundJFrame extends JFrame{
    public JPanel backgroundJPanel;
    private JButton scoreboardButton;
    private JPanel menuJPanel;
    private JLabel mainMenuLabel;
    private JButton gameButton;
    private JButton quitButton;
    private JComboBox gameModeCombo;
    private String selectedGameMode = "";

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
                tempInit.selectedGameMode = selectedGameMode;
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


    private void createUIComponents() {
        gameModeCombo = new JComboBox<>();
        gameModeCombo.addItem("Geografia");
        gameModeCombo.addItem("Angielski");
        gameModeCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedGameMode = gameModeCombo.getSelectedItem().toString();
                System.out.println(selectedGameMode + " selected");
            }
        });

        gameModeCombo.setSelectedIndex(1);
    }
}

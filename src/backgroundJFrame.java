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
    private JLabel modeLabel;
    private String selectedGameMode = "";

    // ActionListenery wyświetlające odpowiedni JPanel
    public backgroundJFrame() {

        // Przycisk wywołujący tryb tablicy wyników
        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                initWindow tempInit = new initWindow();
                tempInit.selectedGameMode = selectedGameMode;
                tempInit.scoreboardMode();
            }
        });

        // Przycisk wywołujący tryb gry
        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                initWindow tempInit = new initWindow();
                tempInit.selectedGameMode = selectedGameMode;
                tempInit.gameMode();
            }
        });

        // Przycisk wywołujący wyjście z programu
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // Tworzenie ComboBoxa z wyborem trybu
    private void createUIComponents() {
        gameModeCombo = new JComboBox<>();
        gameModeCombo.addItem("Geografia");
        gameModeCombo.addItem("Angielski");
        gameModeCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedGameMode = gameModeCombo.getSelectedItem().toString();
               // System.out.println(selectedGameMode + " selected");
            }
        });
        gameModeCombo.setSelectedIndex(1);
    }
}

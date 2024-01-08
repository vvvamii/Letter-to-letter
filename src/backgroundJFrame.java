import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Tworzenie JFrame - okno aplikacji
 */
public class backgroundJFrame extends JFrame{
    /**
     * Tło aplikacji
     */
    public JPanel backgroundJPanel;

    /**
     * Przycisk przechodzący do tablicy wyników
     */
    private JButton scoreboardButton;

    /**
     * JPanel będący menu głównym aplikacji
     */
    private JPanel menuJPanel;

    /**
     * Tytuł aplikacji w menu głównym
     */
    private JLabel mainMenuLabel;

    /**
     * Przycisk przechodzący do gry
     */
    private JButton gameButton;

    /**
     * Przycisk służacy do wyjścia z aplikacji
     */
    private JButton quitButton;

    /**
     * JComboBox z wyborem trybu gry i tablicy wyników
     */
    private JComboBox gameModeCombo;

    /**
     * JLabel z opisem comboboxa
     */
    private JLabel modeLabel;

    /**
     * Aktualnie wybrany tryb gry
     */
    private String selectedGameMode = "";

    /**
     * Zawiera ActionListenery wyświetlające odpowiedni JPanel
     */
    public backgroundJFrame() {

        /**
         * Przycisk wywołujący tryb tablicy wyników
         */
        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                initWindow tempInit = new initWindow();
                tempInit.selectedGameMode = selectedGameMode;
                tempInit.scoreboardMode();
            }
        });

        /**
         * Przycisk wywołujący tryb gry
         */
        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuJPanel.setVisible(false);
                initWindow tempInit = new initWindow();
                tempInit.selectedGameMode = selectedGameMode;
                tempInit.gameMode();
            }
        });

        /**
         * Przycisk wywołujący wyjście z programu
         */
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Tworzenie ComboBoxa z wyborem trybu
     */
    private void createUIComponents() {
        gameModeCombo = new JComboBox<>();
        gameModeCombo.addItem("Geografia");
        gameModeCombo.addItem("Angielski");
        gameModeCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedGameMode = gameModeCombo.getSelectedItem().toString();
            }
        });
        gameModeCombo.setSelectedIndex(1);
    }
}

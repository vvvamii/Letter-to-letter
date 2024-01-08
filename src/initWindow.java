import javax.swing.*;

/**
 * Klasa uruchamiajaca odpowiedni tryb - gra lub tablica wyników
 */
public class initWindow extends JFrame{

    /**
     * Okno główne programu
     */
    private static backgroundJFrame myFrame = new backgroundJFrame();

    /**
     * Wybrany tryb
     */
    public String selectedGameMode = "";

    /**
     * Ustawienie JFrame do wyświetlania
     */
    public void initialize(){
        setContentPane(myFrame.backgroundJPanel);
        setTitle("Litera do litery - projekt");
        setSize(1000,700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Tryb tablicy wyników
     */
    public void scoreboardMode(){
        Highscore highscore = new Highscore(selectedGameMode);
        JPanel panel = highscore.panel();
        highscore.loadScore();
        myFrame.backgroundJPanel.add(panel);
        panel.setVisible(true);
    }

    /**
     * Tryb gry
     */
    public void gameMode(){
        GameJPanel gameJPanel = new GameJPanel();
        JPanel panel = gameJPanel.panel(selectedGameMode);
        myFrame.backgroundJPanel.add(panel);
        panel.setVisible(true);
    }

}

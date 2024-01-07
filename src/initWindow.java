import javax.swing.*;

// Klasa uruchamiajaca odpowiedni tryb - gra lub tablica wyników
public class initWindow extends JFrame{
    static backgroundJFrame myFrame = new backgroundJFrame();
    String selectedGameMode = "";

    // Ustawienie JFrame do wyświetlania
    void initialize(){
        setContentPane(myFrame.backgroundJPanel);
        setTitle("Litera do litery - projekt");
        setSize(1000,700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Tryb tablicy wyników
    void scoreboardMode(){
        Highscore highscore = new Highscore(selectedGameMode);
        JPanel panel = highscore.panel();
        highscore.loadScore();
        myFrame.backgroundJPanel.add(panel);
        panel.setVisible(true);
    }

    // Tryb gry
    void gameMode(){
        GameJPanel gameJPanel = new GameJPanel();
        JPanel panel = gameJPanel.panel(selectedGameMode);
        myFrame.backgroundJPanel.add(panel);
        panel.setVisible(true);
    }

}

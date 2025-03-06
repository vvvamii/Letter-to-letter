import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * System gry - odliczanie czasu, dodawanie punktów, przechodzenie do następnego poziomu, tworzenie odpowiedniej ilości liter i kwadratów
 */
public class GameJPanel {

    /**
     * Wybrany tryb gry
     */
    private String selectedGameMode;

    /**
     * Poziom aktualnej gry
     */
    private int level = 0;

    /**
     * Int służący do przechowywania czasu
     */
    private int counter = 0;

    /**
     * Aktualny wynik
     */
    private int currentscore = 0;

    /**
     * Boolean służący do przechowywania statusu aktualnego poziomu
     */
    private boolean stageFinished = true;

    /**
     * Ilość linii (słów) w aktualnym pliku z pytaniami / poziomami
     */
    private int lines = 0;

    /**
     * Timer do odliczania czasu przeznaczonego na poziom
     */
    private Timer timer;

    /**
     * Przechowywanie danych
     */
    private ArrayList<String> data = new ArrayList<String>();

    /**
     * Ruch liter, rysowanie elementów i wyświetlanie w osobnym JPanelu
     */
    private DragShapes dragShapes = new DragShapes();

    /**
     * Tło
     */
    private JPanel backgroundjpanel = new JPanel(null);

    /**
     * Wyświetla aktualny czas
     */
    private JLabel timelabel = new JLabel("TT");

    /**
     * Wyświetla aktualny poziom
     */
    private JLabel levellabel = new JLabel("X");

    /**
     * Wyświetla aktualną punktację
     */
    private JLabel scorelabel = new JLabel("Y");

    /**
     * Wyświetla aktualny tryb gry / komunikaty (poprawna odpowiedź, błędna odpowiedź, koniec gry)
     */
    private JLabel selectedgamemodelabel = new JLabel(selectedGameMode);

    /**
     * Aktualna data i czas do zapisania do tablicy wyników
     */
    private String dateandtime = "";

    /**
     * JPanel z interfejsem gry
     * @param selectedGameMode
     * @return
     */
    public JPanel panel(String selectedGameMode){
        this.selectedGameMode = selectedGameMode;
        Highscore score = new Highscore(selectedGameMode);

        float[] hsbVal = new float[3];
        Color.RGBtoHSB(229, 189, 136, hsbVal);
        backgroundjpanel.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));

        JButton backButton = new JButton("WYJŚCIE");
        backButton.setBounds(875,25,100,50);

        JButton checkButton = new JButton("SPRAWDŹ");

        timelabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        timelabel.setBounds(680,20,200,50);
        timelabel.setForeground(Color.black);
        backgroundjpanel.add(timelabel);

        scorelabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        scorelabel.setBounds(400,20,200,50);
        scorelabel.setForeground(Color.black);
        backgroundjpanel.add(scorelabel);

        levellabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        levellabel.setBounds(55,20,200,50);
        levellabel.setForeground(Color.black);
        backgroundjpanel.add(levellabel);

        selectedgamemodelabel.setText(selectedGameMode);
        selectedgamemodelabel.setFont(new Font("JetBrains Mono", Font.ITALIC, 35));
        selectedgamemodelabel.setBounds(350,55,500,50);
        selectedgamemodelabel.setForeground(Color.black);
        backgroundjpanel.add(selectedgamemodelabel);

        String text = new String("poziom ## punkty ## czas");
        text = text.replaceAll("#", "       ");
        JLabel menulabel = new JLabel(text);
        menulabel.setForeground(Color.gray);
        menulabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        menulabel.setBounds(30,-10,900,50);
        backgroundjpanel.add(menulabel);
        backgroundjpanel.add(dragShapes);

        // Wyjście z gry i jednoczesne zapisanie dotychczasowych punktów w tablicy wyników
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Writer out = null;
                try {
                    out = new PrintWriter(new BufferedWriter(new FileWriter("res/score/" + selectedGameMode + "scoreboarddata.txt", true)));
                    getDateAndTime();
                    out.write("\n" + currentscore + ";" + dateandtime + ";");
                    out.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                backgroundjpanel.setVisible(false);
            }
        });
        backgroundjpanel.add(backButton);

        // Sprawdzanie poprawności odpowiedzi
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = 0;
                for (MovableLetter movableLetter : dragShapes.movableLetters){
                   movableLetter.placedCorrectly = false;
                   for (AnswerRectangle answerRectangle : dragShapes.answerRectangles){
                       movableLetter.forceIntersecting(answerRectangle);
                   }
                   if(movableLetter.placedCorrectly) ++check;
               }
                if(check == dragShapes.answerRectangles.size()) {
                    selectedgamemodelabel.setText("Poprawna odpowiedź!"); // Jeśli ilość poprawnie ułożonych liter równa się ilości kwadratów - poprawna odpowiedź
                    ++level;
                    stageFinished = true;
                    play();
                }
                else selectedgamemodelabel.setText("Błędna odpowiedź!"); // W przeciwnym wypadku odpowiedź jest błędna
            }
        });
        dragShapes.add(checkButton, BorderLayout.SOUTH);

        // Zliczanie linii w pliku (poziomów) oraz dodawanie danych (pytań)
        InputStream is = GameJPanel.class.getResourceAsStream("/gametext/" + selectedGameMode + ".txt");
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
            lines++;
        }
        sc.close();
        play();

        return backgroundjpanel;
    }


    /**
     * Załadowywanie plików i elementów interaktywnych gry, odmierzanie czasu, dodawanie punktów do wyniku, przechodzenie do następnego etapu
     */
    private void play(){

        int tempScore = 0;

        // Jeśli ukończono wszystkie poziomy - wyświetl napis "KONIEC GRY", zapisz wynik oraz po pięciu sekundach wyjdź z gry
        if(level >= lines){
            selectedgamemodelabel.setText("KONIEC GRY");
            Writer out = null;
            timer.stop();
            timer = new Timer(1000, new ActionListener() {;
                int counterExit = 5;
                @Override
                public void actionPerformed(ActionEvent e) {
                    timelabel.setText(String.valueOf(counterExit));
                    --counterExit;
                    if(counterExit < 0) backgroundjpanel.setVisible(false);
                }
            });
            timer.start();
            try {
                out = (new FileWriter("res/score/" + selectedGameMode + "scoreboarddata.txt", true));
                getDateAndTime();
                out.write("\n" + currentscore + ";" + dateandtime + ";");
                out.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        // Dla dalszych poziomów zatrzymujemy timer i dodajemy punkty
        if(level > 0 && level < lines) {
            timer.stop();
            tempScore = 1000 - counter * 50; // Po 20 sekundach nie dostajemy dodatkowych punktów
            if(tempScore <= 0) tempScore = 0;
            currentscore += tempScore + 10; // Dodaj do wyniku dodatkowe punkty oraz 10 punktów za ukończenie poziomu
            counter = 0;
        }

        // Po ukończonym poziomie resetuj timer i aktualizuj na nowo JLabel z czasem
        if(level < lines && stageFinished == true){
            timer = new Timer(1000, new ActionListener() {;
                @Override
                public void actionPerformed(ActionEvent e) {
                    timelabel.setText(String.valueOf(counter));
                    ++counter;
                }
            });
            timer.start();

            // Ładowanie obrazka do aktualnego pytania
            String loadedString = (String)data.get(level);
            InputStream is = GameJPanel.class.getResourceAsStream("/gamepng/" + selectedGameMode + "/" + loadedString + ".png");
            ImageQuestion image = new ImageQuestion();
            image.setImage(is);

            // Jeżeli ukończono poziom - wyczyść kontenery na litery i kwadraty oraz dodaj nowe
            if(stageFinished == true){
                dragShapes.movableLetters.clear();
                dragShapes.answerRectangles.clear();
                Random rand = new Random();
                for(int j = 0; j < loadedString.length(); j++){
                    dragShapes.movableLetters.add(new MovableLetter(10 + j*90, 200, Color.blue, Character.toString(loadedString.charAt(j))));
                    dragShapes.answerRectangles.add(new AnswerRectangle(10 + j*90,400, Character.toString(loadedString.charAt(j))));
                    dragShapes.movableLetters.get(j).translate(new Point(-400 + rand.nextInt(400), 100), new Point(rand.nextInt(400), 400));
                }
                // Dwie losowe litery by utrudnić grę
                for(int j = 0; j < 2; j++){
                    dragShapes.movableLetters.add(new MovableLetter(10 + j*90, 200, Color.blue, Character.toString((char)rand.nextInt(26) + 'a')));
                    dragShapes.movableLetters.get(loadedString.length() + j).translate(new Point(-400 + rand.nextInt(400), 100), new Point(rand.nextInt(400), 400));
                }
                stageFinished = false; // Po wszystkim ustaw poziom na nieukończony
            }
            levellabel.setText((level + 1) + "/" + lines);  // Zaaktualizuj aktualny poziom (aktualny poziom na wszystkie poziomy)
            scorelabel.setText(String.valueOf(currentscore)); // Zaaktualizuj aktualnie posiadane punkty
            dragShapes.image = image;
            dragShapes.repaint();
        }
    }

    /**
     * Aktualna data i czas potrzebna do wpisania do wyniku
     */
    private void getDateAndTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dateandtime = myDateObj.format(myFormatObj);
    }

}

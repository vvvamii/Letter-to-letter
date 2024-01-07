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

// JPanel z interfejsem gry
public class GameJPanel {
    String selectedGameMode;
    int level = 0;
    int counter = 0;
    int currentscore = 0;
    boolean stageFinished = true;
    int lines = 0;
    Timer timer;
    ArrayList<String> data = new ArrayList<String>();
    DragShapes dragShapes = new DragShapes();
    JPanel backgroundjpanel = new JPanel(null);
    JLabel timelabel = new JLabel("TT");
    JLabel levellabel = new JLabel("X");
    JLabel scorelabel = new JLabel("Y");
    JLabel selectedgamemodelabel = new JLabel(selectedGameMode);
    String dateandtime = "";

    JPanel panel(String selectedGameMode){
        this.selectedGameMode = selectedGameMode;
        Highscore score = new Highscore(selectedGameMode);

        backgroundjpanel.setBackground(Color.orange);

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
        selectedgamemodelabel.setFont(new Font("BIZ UDGothic", Font.BOLD, 35));
        selectedgamemodelabel.setBounds(350,60,500,50);
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

        // Wyjście z gry
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Writer out = null;
                try {
                    out = new PrintWriter(new BufferedWriter(new FileWriter("resources/score/" + selectedGameMode + "scoreboarddata.txt", true)));
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
                    selectedgamemodelabel.setText("Poprawna odpowiedź!");
                    ++level;
                    stageFinished = true;
                    play();
                }
                else selectedgamemodelabel.setText("Błędna odpowiedź!");
            }
        });
        dragShapes.add(checkButton, BorderLayout.SOUTH);

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


    // Załadowywanie plików i elementów interaktywnych gry, odmierzanie czasu, dodawanie punktów do wyniku, przechodzenie do następnego etapu
    void play(){

        int tempScore = 0;

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
                out = (new FileWriter("resources/score/" + selectedGameMode + "scoreboarddata.txt", true));
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
            tempScore = 1000 - counter * 50;
            if(tempScore <= 0) tempScore = 10;
            currentscore += tempScore;
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

            String loadedString = (String)data.get(level);
            InputStream is = GameJPanel.class.getResourceAsStream("/gamepng/" + selectedGameMode + "/" + loadedString + ".png");
            ImageQuestion image = new ImageQuestion();
            image.setImage(is);

            if(stageFinished == true){
                dragShapes.movableLetters.clear();
                dragShapes.answerRectangles.clear();
                Random rand = new Random();
                for(int j = 0; j < loadedString.length(); j++){
                    dragShapes.movableLetters.add(new MovableLetter(10 + j*90, 200, Color.blue, Character.toString(loadedString.charAt(j))));
                    dragShapes.answerRectangles.add(new AnswerRectangle(10 + j*90,400, Character.toString(loadedString.charAt(j))));
                    dragShapes.movableLetters.get(j).translate(new Point(-400 + rand.nextInt(400), 100), new Point(rand.nextInt(400), 400));
                }
                for(int j = 0; j < 2; j++){
                    dragShapes.movableLetters.add(new MovableLetter(10 + j*90, 200, Color.blue, Character.toString((char)rand.nextInt(26) + 'a')));
                    dragShapes.movableLetters.get(loadedString.length() + j).translate(new Point(-400 + rand.nextInt(400), 100), new Point(rand.nextInt(400), 400));
                }
                stageFinished = false;
            }
            levellabel.setText((level + 1) + "/" + lines);
            scorelabel.setText(String.valueOf(currentscore));
            dragShapes.image = image;
            dragShapes.repaint();
        }
    }

    // Aktualna data i czas potrzebna do wpisania do wyniku
    void getDateAndTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        dateandtime = myDateObj.format(myFormatObj);
    }

}

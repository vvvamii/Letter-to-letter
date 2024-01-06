import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameJPanel {

    int level = 0;
    boolean stageFinished = true;
    int lines = 0;
    Timer timer;
    ArrayList<String> data = new ArrayList<String>();
    DragShapes dragShapes = new DragShapes();
    JLabel timelabel = new JLabel("TT");
    JLabel levellabel = new JLabel("X");
    JLabel scorelabel = new JLabel("Y");

    JPanel panel(){
        JPanel backgroundjpanel = new JPanel(null);
        backgroundjpanel.setBackground(Color.cyan);

        JButton backButton = new JButton("WYJŚCIE");
        backButton.setBounds(875,25,100,50);

        JButton checkButton = new JButton("SPRAWDŹ");

        timelabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        timelabel.setBounds(680,35,200,50);
        backgroundjpanel.add(timelabel);

        scorelabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        scorelabel.setBounds(400,35,200,50);
        backgroundjpanel.add(scorelabel);

        levellabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        levellabel.setBounds(90,35,200,50);
        backgroundjpanel.add(levellabel);

        String text = new String("poziom#punkty#czas");
        text = text.replaceAll("#", "       ");
        JLabel menulabel = new JLabel(text);
        menulabel.setForeground(Color.lightGray);
        menulabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        menulabel.setBounds(30,0,900,50);
        backgroundjpanel.add(menulabel);

        backgroundjpanel.add(dragShapes);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundjpanel.setVisible(false);
            }
        });
        backgroundjpanel.add(backButton);

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
                   System.out.println(movableLetter.placedCorrectly);
               }
                if(check == dragShapes.answerRectangles.size()) {
                    ++level;
                    stageFinished = true;
                    play();
                    // System.out.println("YIPP");
                }
            }
        });
        dragShapes.add(checkButton, BorderLayout.SOUTH);

        loadData loadData = new loadData();


        try {
            data = loadData.load("dane.txt");
            BufferedReader reader = new BufferedReader(new FileReader("dane.txt"));

            try {
                while (reader.readLine() != null) lines++;
                reader.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        play();

        return backgroundjpanel;
    }



    void play(){

        int score = 0;

        if(level > 0) {
            timer.stop();
            ++score;
        }

        if(level < lines || stageFinished == true){

            timer = new Timer(1000, new ActionListener() {
                int counter = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    timelabel.setText(String.valueOf(counter));
                    ++counter;
                }
            });
            timer.start();

            String loadedString = (String)data.get(level);
            if(stageFinished == true){
                dragShapes.movableLetters.clear();
                dragShapes.answerRectangles.clear();
                for(int j = 0; j < loadedString.length(); j++){
                    dragShapes.movableLetters.add(new MovableLetter(Color.blue, Character.toString(loadedString.charAt(j))));
                    dragShapes.answerRectangles.add(new AnswerRectangle(25 + j*110,400, Character.toString(loadedString.charAt(j))));
                }
                stageFinished = false;
            }
            System.out.println("STAGE: " + level);
            levellabel.setText(String.valueOf(level + 1));
            scorelabel.setText(String.valueOf(score));
            dragShapes.repaint();
        }
    }

}

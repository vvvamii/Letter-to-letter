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
    JPanel panel(){
        JPanel backgroundjpanel = new JPanel(null);
        backgroundjpanel.setBackground(Color.cyan);

        JButton backButton = new JButton("WYJŚCIE");
        backButton.setBounds(875,25,100,50);

        Timer timer;

        JLabel timelabel = new JLabel("TT");
        timelabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        timelabel.setBounds(680,35,200,50);
        backgroundjpanel.add(timelabel);

        JLabel scorelabel = new JLabel("Y");
        scorelabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        scorelabel.setBounds(400,35,200,50);
        backgroundjpanel.add(scorelabel);

        JLabel levellabel = new JLabel("X");
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

        myMovement dragShapes = new myMovement();
        backgroundjpanel.add(dragShapes);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundjpanel.setVisible(false);
            }
        });
        backgroundjpanel.add(backButton);

        ArrayList<String> data = new ArrayList<String>();
        loadData loadData = new loadData();
        int lines = 0;

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


        boolean stageFinished = true;
        for(int i = 0; i < lines; ++i){
            timer = new Timer(1000, new ActionListener() {
                int counter = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    timelabel.setText(String.valueOf(counter));
                    ++counter;
                }
            });
            timer.start();

            String loadedString = (String)data.get(i);
            if(stageFinished == true){
                for(int j = 0; j < loadedString.length(); j++){
                    dragShapes.movableLetters.add(new ColorShape(Color.blue, Character.toString(loadedString.charAt(j))));
                    dragShapes.answerRectangles.add(new AnswerRectangle(25 + j*110,400, Character.toString(loadedString.charAt(j))));
                }
                stageFinished = false;
            }
        }

        return backgroundjpanel;
    }

}

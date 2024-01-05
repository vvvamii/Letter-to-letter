import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Highscore {
    ArrayList<String> loadhighscore(){
        loadData loadData = new loadData();
        ArrayList<String> highscores = new ArrayList<String>();
        try {
            highscores = loadData.load("scoreboard.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return highscores;
    }

    JPanel panel(){
        JPanel panel = new JPanel(null);
        loadData load = new loadData();
        ArrayList<String> myList = null;
        try {
            myList = load.load("scoreboard.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(myList);
        final JList<String> list = new JList<String>(myList.toArray(new String[myList.size()]));
        list.setFont(new Font("Monospaced", Font.ITALIC, 50));

        JLabel titlelabel = new JLabel("TABELA WYNIKÓW");
        titlelabel.setFont(new Font("Ebrima", Font.ITALIC, 50));
        titlelabel.setBounds(300,10,500,50);
        panel.add(titlelabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setBounds(10,75,960,500);
        panel.add(scrollPane);

        JButton backButton = new JButton("BACK");
        backButton.setBounds(850,600,100,50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
            }
        });
        panel.add(backButton);

        return panel;
    }


}

class ScoreInfo {
    int points;
    private String name;

    ArrayList<ScoreInfo> scoreboard(){
        ArrayList<ScoreInfo> scores = new ArrayList<ScoreInfo>();
      //  Collections.sort(scores, Comparator.);
        return scores;
    }

}
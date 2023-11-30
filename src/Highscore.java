import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        JPanel panel = new JPanel(new CardLayout());
        List<String> myList = new ArrayList<>(10);
        for (int index = 0; index < 20; index++) {
            myList.add("List Item " + index);
        }
        final JList<String> list = new JList<String>(myList.toArray(new String[myList.size()]));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        panel.add(scrollPane);
        JButton backButton = new JButton("BACK");
        backButton.setBounds(200,200,200,200);
        panel.add(backButton);

        return panel;
    }


}

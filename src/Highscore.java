import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

// Klasa zajmująca się załadowywaniem i wyświetlaniem tabeli wyników
public class Highscore {
    ArrayList<Integer> scores = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    String selectedGameMode = "";
    ArrayList<String> myList = new ArrayList<>();
    JList<String> list = new JList<String>();

    Highscore(String selectedGameMode){
        this.selectedGameMode = selectedGameMode;
    }

    // Załadowywanie wyników
    void loadScore(){
        scores.clear();
        dates.clear();
        InputStream is = Highscore.class.getResourceAsStream("/score/" + selectedGameMode+ "scoreboarddata.txt");
        Scanner input = new Scanner(is);
        input.useDelimiter(";");
        while (input.hasNext()) {
            String line = input.next();
            line = line.replaceAll("\n", "").replaceAll("\r", "");
            scores.add(Integer.parseInt(line));

            line = input.next();
            line = line.replaceAll("\n", "").replaceAll("\r", "");
            dates.add(line);
        }
        input.close();

        String[] sorted = IntStream.range(0, scores.size()).boxed()
                .sorted(Comparator.comparingInt(i -> scores.get(i)))
                .map(i -> scores.get(i) + " " + dates.get(i))
                .toArray(String[]::new);

        for(int i = sorted.length - 1; i >= 0; --i) myList.add(sorted[i]);
        list = new JList<String>(myList.toArray(new String[myList.size()]));
    }

    // JPanel z tablicą wyników
    JPanel panel(){

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.orange);
        JLabel titlelabel = new JLabel("TABELA WYNIKÓW");
        titlelabel.setFont(new Font("Ebrima", Font.ITALIC, 50));
        titlelabel.setBounds(280,10,500,50);
        panel.add(titlelabel);

        loadScore();

        list.setFont(new Font("Monospaced", Font.ITALIC, 50));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setBounds(10,75,965,520);
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

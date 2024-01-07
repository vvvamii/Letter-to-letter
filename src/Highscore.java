import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

// Klasa zajmująca się załadowywaniem i wyświetlaniem tabeli wyników
public class Highscore {
    ArrayList<Integer> scores = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    String selectedGameMode = "";
    ArrayList<String> myList = new ArrayList<>();
    JList<String> list = new JList<String>();
    JScrollPane scrollPane = new JScrollPane();

    Highscore(String selectedGameMode){
        this.selectedGameMode = selectedGameMode;
    }

    // Załadowywanie wyników
    void loadScore(){
        scores.clear();
        dates.clear();
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("resources/score/" + selectedGameMode+ "scoreboarddata.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner input = new Scanner(in);
        input.useDelimiter(";");

        // Dopóki są dane w pliku to wczytuj je do odpowiednich ArrayList
        while(input.hasNext()) {
            String line = input.next();
            line = line.replaceAll("\n", "").replaceAll("\r", "");
            scores.add(Integer.parseInt(line));

            line = input.next();
            line = line.replaceAll("\n", "").replaceAll("\r", "");
            dates.add(line);
        }
        input.close();

        // Sortowanie wczytanych danych według wartości w Arraylist scores i scalenie wszystkiego w jedną tablicę
        String[] sorted = IntStream.range(0, scores.size()).boxed()
                .sorted(Comparator.comparingInt(i -> scores.get(i)))
                .map(i -> dates.get(i) + "      " + scores.get(i) + " pkt")
                .toArray(String[]::new);

        for(int i = sorted.length - 1; i >= 0; --i)myList.add(sorted[i]);
        list = new JList<String>(myList.toArray(new String[myList.size()]));
    }

    // JPanel z tablicą wyników
    JPanel panel(){

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.orange);
        JLabel titlelabel = new JLabel("TABELA WYNIKÓW");
        titlelabel.setFont(new Font("Segoe UI", Font.ITALIC, 50));
        titlelabel.setBounds(280,10,500,50);
        panel.add(titlelabel);

        loadScore();

        list.setFont(new Font("Segoe UI", Font.PLAIN, 50));
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setBounds(10,75,965,520);
        panel.add(scrollPane);

        JButton backButton = new JButton("WYJŚCIE");
        backButton.setBounds(850,600,100,50);

        // Wyjście z tablicy wyników po wciśnięciu powrotu
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class movableJPanels {
    JPanel panel(){
        JPanel backgroundjpanel = new JPanel(null);
        backgroundjpanel.setBackground(Color.cyan);
        backgroundjpanel.setLayout(null);
        backgroundjpanel.setLocation(0,0);

        JButton backButton = new JButton("BACK");
        backButton.setBounds(850,500,100,50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundjpanel.setVisible(false);
            }
        });
        backgroundjpanel.add(backButton);

        ArrayList<String> data = new ArrayList<String>();
        loadData loadData = new loadData();
        try {
            data = loadData.load("dane.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String loadedString = (String)data.get(0);

        for(int i = 0; i < loadedString.length(); i++){
            JPanel letterjpanel = new JPanel();
            JLabel label = new JLabel();
            letterjpanel.setBackground(new Color(238+i*2, 206+i*2, 132+i*2));
            letterjpanel.setSize(75,75);
            label.setText("["+loadedString.charAt(i)+"]");
            label.setFont(new Font("Arial", 1, 45));
            //TODO wysrodkowanie label w panel / zmiana z JPanel na rectanngle
            letterjpanel.add(label);
            backgroundjpanel.add(letterjpanel);


        }

        myMovement movement = new myMovement(backgroundjpanel.getComponents());
        return backgroundjpanel;
    }

}

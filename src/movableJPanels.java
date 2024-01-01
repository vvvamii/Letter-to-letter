import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class movableJPanels {
    JPanel panel(){
        JPanel back = new JPanel(null);
        back.setBackground(Color.cyan);
        back.setLayout(null);
        back.setLocation(0,0);

        ArrayList<String> data = new ArrayList<String>();
        loadData loadData = new loadData();
        try {
            data = loadData.load("dane.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String loadedString = (String)data.get(0);

        for(int i = 0; i < loadedString.length(); i++){
            JPanel letterpanel = new JPanel();
            JLabel label = new JLabel();
            letterpanel.setBackground(new Color(238+i*2, 206+i*2, 132+i*2));
            letterpanel.setSize(75,75);
            label.setText("["+loadedString.charAt(i)+"]");
            label.setFont(new Font("Arial", 1, 45));
            //TODO wysrodkowanie label w panel
            letterpanel.add(label);
            back.add(letterpanel);


        }

        myMovement movement = new myMovement(back.getComponents());
        return back;
    }

}

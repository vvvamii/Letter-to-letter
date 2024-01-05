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

//        JPanel gamezone = new JPanel(null);
//        gamezone.setBackground(Color.yellow);
//        gamezone.setBounds(10,100,960,550);
//        backgroundjpanel.add(gamezone);

        JButton backButton = new JButton("WYJŚCIE");
        backButton.setBounds(875,25,100,50);

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
        try {
            data = loadData.load("dane.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String loadedString = (String)data.get(0);

        int coord = 0;


        for(int i = 0; i < loadedString.length(); i++){

            dragShapes.shapes.add(new ColorShape(Color.blue, coord, coord));
            coord = 50*i;

            Rectangle rect = new Rectangle(coord, coord, 100, 100);



        }
//TODO uzupelnienie z stackoverflow
        //myMovement movement = new myMovement(movementCanvas.rectangles);
        return backgroundjpanel;
    }

}

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    JPanel addingPanel(){
        JPanel panel = new JPanel();
        JButton batonik = new JButton();
        batonik.setBounds(100,100,100,100);
        panel.add(batonik);
        panel.setBackground(Color.red);
        panel.setVisible(true);

        return panel;
    }

}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class loadData {

    ArrayList<String> load(String filename) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(filename));
        ArrayList<String> list = new ArrayList<String>();

        while (sc.hasNextLine()){
            list.add(sc.nextLine());
        }

        System.out.println(list);

        return list;
    }


}

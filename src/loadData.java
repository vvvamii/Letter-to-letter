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

    void twoArraysMode(){
        Scanner input = new Scanner(System.in);
        try {
            input = new Scanner(new File("dane.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        input.useDelimiter(";");

        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> data2 = new ArrayList<>();

        while (input.hasNext()) {
            String line = input.next();
            line = line.replaceAll("\n", "").replaceAll("\r", "");
            data.add(line);
            System.out.println("ZAWARTOŚĆ ARRAYLIST: " + line);
            line = input.next();
            line = line.replaceAll("\n", "").replaceAll("\r", "");
            data2.add(line);
            System.out.println("ZAWARTOŚĆ ARRAYLIST2: " + line);
        }
        input.close();
    }

}

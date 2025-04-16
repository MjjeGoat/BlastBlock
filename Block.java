import javax.swing.*;
import javax.swing.Box;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Block{

    private ArrayList<Piece> cells;

    public ArrayList<Piece> getCells() {
        return cells;
    }

    public Block(String fileName) {
        this.cells = new ArrayList<>();
        getBlockFromFile(fileName);
    }

    BufferedReader br;



    private void getBlockFromFile(String filename){
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            int j = 0;
            while ((line = br.readLine())!= null){
                String[] parts = line.split(",");
                for (int i = 0; i < parts.length; i++) {
                    int isBlock = Integer.parseInt(parts[i]);
                    if (isBlock == 1){
                        System.out.println("j");
                        cells.add(new Piece(i,j));
                    }else {
                        System.out.println("n");
                    }
                    j++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}

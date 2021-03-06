package sample.java.io;

import java.io.*;
import java.util.Vector;

/**
 * Created by kopelevi on 27/09/2015.
 */
public class SequenceInputStreamExample {
    public static void main(String[] args) {
        String filePath1 = "c:\\1.txt";
        String filePath2 = "c:\\2.txt";
        String filePath3 = "c:\\3.txt";
        String filePath4 = "c:\\4.txt";

        try (InputStream input1 = new FileInputStream(filePath1);
             InputStream input2 = new FileInputStream(filePath2);
             InputStream input3 = new FileInputStream(filePath3);
             InputStream input4 = new FileInputStream(filePath4);
             SequenceInputStream sequence12 = new SequenceInputStream(input1, input2);
             SequenceInputStream sequence34 = new SequenceInputStream(input3, input4);
             SequenceInputStream sequence3412 = new SequenceInputStream(sequence34, sequence12);
        ) {

//            int currentByte = sequence3412.read();
//            while (currentByte != -1) {
//                System.out.println(String.valueOf((char) currentByte));
//                currentByte = sequence3412.read();
//            }

            Vector<InputStream> inputStreamVector = new Vector<>();
            inputStreamVector.add(input1);
            inputStreamVector.add(input2);
            inputStreamVector.add(input3);
            inputStreamVector.add(input4);


            StringBuilder stringBuilder = new StringBuilder();
            InputStream sequence1234 = new BufferedInputStream(new SequenceInputStream(inputStreamVector.elements()));
            int i = sequence1234.read();
            while (i != -1) {
                stringBuilder.append((char) i);
                i = sequence1234.read();
            }
            System.out.println(stringBuilder);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

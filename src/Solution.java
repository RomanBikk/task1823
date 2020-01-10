import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.frequency;

/*
Нити и байты
*/

public class Solution {
    public static Map<String, Integer> resultMap = new HashMap<String, Integer>();

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String fileName = bufferedReader.readLine();
            if (fileName.equals("exit")) {
                bufferedReader.close();
                for (Map.Entry entry: resultMap.entrySet()) {
                    String key = (String) entry.getKey();
                    Integer value = (Integer) entry.getValue();
                    System.out.println("File name is: " + key + " byte name is: " + value);

                }
                break;
            }
            else {
                new ReadThread(fileName).start();
            }
        }

    }

    public static class ReadThread extends Thread {
        private FileInputStream fileReader;
        private String fileName;
        public ReadThread(String fileName) {
            this.fileName = fileName;
            try {
                fileReader = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                readFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void readFile() throws IOException {
            List values = new ArrayList();
            while (fileReader.available() > 0) {
                values.add(fileReader.read());
            }

            int count = 0;
            Integer maxByte = null;
            for(int i = 0; i < values.size(); i ++) {
                if(frequency(values,values.get(i)) > count) {
                    count = frequency(values,values.get(i));
                }
            }
            for (Object value : values) {
                if(frequency(values,value) == count) maxByte = (Integer) value;
            }
            fileReader.close();
            resultMap.put(fileName,maxByte);
        }
    }
}

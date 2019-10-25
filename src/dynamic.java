import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class dynamic {
    public static int budget;
    public static int types;
    public static ArrayList<ArrayList<Integer>> allData;


    public static void readData(String filePath){
        allData = new ArrayList<>();
        try {
            File f = new File(filePath);
            if (f.isFile() && f.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
                String line;
                BufferedReader reader = new BufferedReader(isr);
                line = reader.readLine();
                //System.out.println(line);
                String[] fstLine = line.split(" ");
                budget = Integer.parseInt(fstLine[0]);
                types = Integer.parseInt(fstLine[1]);
                int index = 0;
                while (line != null) {
                    String[] str= line.split(" ");
                    ArrayList<Integer> arl = new ArrayList<>();
                    for (String x: str) {
                        arl.add(Integer.parseInt(x));
                    }
                    allData.add(arl);
                    line = reader.readLine();
                    index++;
                }
                allData.remove(0);
                reader.close();
            }else {
                System.out.println("Cannot find the file");
            }
        }catch (Exception e){
            System.out.println("Error detected");
            e.printStackTrace();
        }
    }

    /**
     * delete repeated data and sort the array.
     * @param arr
     */
    public static ArrayList<Integer> sort(ArrayList<Integer> arr){
        arr.remove(0);
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.addAll(arr);
        TreeSet<Integer> treeSet = new TreeSet<>(hashSet);
        Integer[] integers = treeSet.toArray(new Integer[]{});
        ArrayList<Integer> rtn = new ArrayList<>();
        rtn.addAll(Arrays.asList(integers));
        return rtn;
    }

    public static int[][] dynamic(ArrayList<ArrayList<Integer>> arrayList){
        int[][] arr = new int[budget + 1][types];
        arr[0][0] = 1;
        int len = arrayList.size() ;//0 | 1 | 2
        for (int i = 0; i < len; i++) { //types
            for (int j = 1; j < budget + 1; j++) { // budget
                for (int k = 0; k < arrayList.get(i).size(); k++) { //current type prices
                    int current = arrayList.get(i).get(k);
                        if (i == 0 && j-current == 0){
                            if (arr[j - current][i] != 0) {
                                arr[j][i] = current;
                            }
                        }else if (i > 0 && j-current > 0){
                            if (arr[j - current][i-1] != 0) {
                                arr[j][i] = current;
                            }
                        }
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        //long startTime = System.nanoTime();
        //readData(args[0]);
        readData("testcases/test10.txt");
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
        for (ArrayList<Integer> arr: allData) {
            ArrayList<Integer> sortArr = sort(arr);
            temp.add(sortArr);
        }
        int[][] test = dynamic(temp);
        int index = types - 1;
        int x = budget;
        int y = 0;
        for (int i = budget; i > 0; i--) {
            if (test[i][index] != 0 ){
                x = i;
                y = index;
                break;
            }else{
                x--;
            }

        }

        ArrayList<Integer> output= new ArrayList<>();
        output.add(x);
        int init = test[x][y];
        int pos = allData.get(y).indexOf(init) + 1;
        output.add(pos);
        for (int i = types - 2; i >= 0; i--) {
            x -= init;
            int out = test[x][i];
            pos = allData.get(i).indexOf(out) + 1;
            output.add(pos);
            init = out;
        }
        System.out.print(output.get(0));
        for (int i = output.size() - 1; i > 0; i--) {
            System.out.print(" " + output.get(i));
        }
        System.out.print(" ");
        //long endTime = System.nanoTime();
        //System.out.println("Executing time: " + (double)(endTime - startTime)/1000000 + " ms");
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//reference https://www.cnblogs.com/xiezie/p/5511707.html
public class completeSearch {

    public static int budget;
    public static int types;
    public static ArrayList<ArrayList<Integer>> allData; // store all possible permutation


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

    public static ArrayList<Integer> allPermutation(ArrayList<ArrayList<Integer>> arr){
        int n = types;//number of arrays
        int[] allLen = new int[n];//all arrays length

        int index = 0;
        int ll = n;
        while(ll--!=0){
            allLen[index] = arr.get(index).get(0);
            arr.get(index).remove(0);
            index++;
        }


        int[] allLenProduct = new int[n];
        int p = 1; //combinations
        int l = n;
        int index1 = 0;

        ArrayList<ArrayList<Integer>> results = new ArrayList<>(p);
        ArrayList<Integer> totalPrice = new ArrayList<>();
        while(--l!=-1){
            allLenProduct[index1] = p;
            p=allLen[index1++]*p;
        }

        for(int i = 0 ; i != p ; i++ ){
            ArrayList<Integer> arrayList = new ArrayList<>(n);
            int price = 0;
            for(int j = 0 ; j != n ; j ++){
                arrayList.add(arr.get(j).get(i/allLenProduct[j]%allLen[j]));
                price +=arr.get(j).get(i/allLenProduct[j]%allLen[j]);
            }
            results.add(arrayList);
            totalPrice.add(price);
        }

        int outIndex = 0;
        int difference = -budget;
        for (int i = 0; i < totalPrice.size(); i++) { // find the best set approximate to budget
            int t = totalPrice.get(i) - budget;
            if (t <= 0 && t > difference){
                outIndex = i;
                difference = t;
            }
        }

        ArrayList<Integer> rtn = new ArrayList<>(); // add pos in array
        int sum = 0;
        for (int i = 0; i < results.get(outIndex).size(); i++) {
            int in = arr.get(i).indexOf(results.get(outIndex).get(i)) + 1;
            rtn.add(in);
            sum += results.get(outIndex).get(i);
        }
        rtn.add(0, sum);
        return rtn;
    }


    public static void main(String[] args) {
        //long startTime = System.nanoTime();
        readData(args[0]);
        ArrayList<Integer> arr = allPermutation(allData);
        for (int i = 0; i < arr.size(); i++) {
            System.out.print(arr.get(i) + " ");
        }
        //long endTime = System.nanoTime();
        //System.out.println("Executing time: " + (double)(endTime - startTime)/1000000 + " ms");
    }
}

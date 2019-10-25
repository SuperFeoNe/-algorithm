import java.util.Random;

public class testCaseGenerator {
    public static void main(String[] args) {
        int budget = 10;
        int types = 2;
        int models = 0;
        System.out.println(budget + " " + types);
        Random price = new Random();
        for (int i = 0; i < types; i++) {
            Random r = new Random();
            int loop = r.nextInt(4) + 1;
            System.out.print(loop + " ");
            for (int j = 0; j < loop; j++) {
                int rtn = price.nextInt(5) + 1;
                System.out.print(rtn + " ");
            }
            System.out.println();
        }
    }
}

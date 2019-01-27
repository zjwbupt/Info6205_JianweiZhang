public class imple {
    public static void main(String[] args){
        int n = 0;
        try{
            n = Integer.parseInt(args[0]); // n is the total sites
        }catch(NumberFormatException e){
            System.out.println("wrong input, number only");
            return;
        }
        
        //int trail = Integer.parseInt(args[1]); //the time of experiment with every n
        int trail = 100;
        double[] num = new double[trail];

        for(int t=0; t<trail; t++){
            num[t] = run(n);
        }

        System.out.println("1/2 n ln n = " + 0.5 * n * Math.log(n));
        System.out.println("mean       = " + mean(num));
    }


    // to do the experiment
    public static int run(int n){
        int num = 0;
        WQUPC uf = new WQUPC(n);
        while(uf.count()>1){
            int i = (int)(Math.random()*(n));
            int j = (int)(Math.random()*(n));
            uf.union(i,j);
            num++;
        }
        return num;
    }

    // Method to calculate the mean of results n.
    public static double mean(double[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }
}

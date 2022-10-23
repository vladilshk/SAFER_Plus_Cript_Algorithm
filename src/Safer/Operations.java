package Safer;

public class Operations {

    public static int sum(int num_1, int num_2){
        int result = (num_1 + num_2) % 256;
        if(result < 0)
            return result +256;
        else
            return result;

    }

    public static int sumMod2(int num_1, int num_2){
        int result = (num_1 ^ num_2) % 256;
        if(result < 0)
            return result +256;
        else
            return result;
    }

    public static int subtraction(int num_1, int num_2){
        int result = (num_1 - num_2) % 256;
        if(result < 0)
            return result +256;
        else
            return result;

    }

    public static int log(int num_1){
        return (int) (Math.log(num_1)/Math.log(45)) % 256;
    }

    public static int pow(int num_1){
        //return (int) (Math.pow(45, num_1) % 256);
        int result = 0;
        for (int i = 0; i < num_1; i++) {
            result = (result + (45 * 45) % 256) %256;
        }
        return result;
    }

    //5 * 5 = 25 (25); 5*5*5 = 125 (125); 5*5*5*5 = 625(113); 5*5*5*5*5 = 3125(53)



    public  static int multiply(int num_1, int num_2){
        int result = (num_1 * num_2) % 256;
        if(result < 0)
            return result +256;
        else
            return result;
    }

    public static int binPow(int base, int degree, int mod) {
        base %= mod;
        if (degree == 0) return 1;
        else if (degree % 2 == 0) {
            return binPow((base * base) % mod, degree / 2, mod);
        }
        else return (base * binPow(base, degree - 1, mod)) % mod;
    }

    public static int binLog(int num){
        if(num == 0){
            return 128;
        }
        for (int i = 0; i < 257; i++) {
            if (binPow(45, i, 257) == num)
                return i;
        }
        return -1;
    }
}

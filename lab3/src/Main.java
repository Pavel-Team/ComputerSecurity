import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        char[] text = {'h','e','l','l','o','w','o','r','l','d'};
        char[] text2 = {'h','e','l','l','o','w','o','r','d','l'};

        //Стандартная сложность
        System.out.println("Стандартная сложность:");
        System.out.println(hash(text));
        System.out.println(hash(text2));

        //Повышенная сложность
        System.out.println("\nПовышенная сложность:");
        MD5 md5 = new MD5();
        System.out.println(md5.digest(text.toString()));
        System.out.println(md5.digest(text2.toString()));
    }


    //Стандартная сложность
    public static long hash(char[] text) {
        long result = 0;

        long i = 1;
        for (char c : text) {
            result += c * i;
            i*=2;
        }

        return result;
    }


    //Повышенная сложность
    public static String cryptHash(char[] text) {
        String rez = "";

        boolean isHash = true;
        BigDecimal sumHash = new BigDecimal(1);
        int LENGTH_HASH = 32;
        BigDecimal LOW_DIAPOZON = new BigDecimal(Math.pow(16, LENGTH_HASH));
        BigDecimal HIGH_DIAPOZON = new BigDecimal(Math.pow(16, LENGTH_HASH+1) - 1);

        BigDecimal deltaHash = new BigDecimal(0);
        for (char c : text) {
            BigDecimal cd = new BigDecimal(c);
            deltaHash = deltaHash.multiply(cd);
            deltaHash = deltaHash.add(cd);
        }

        while (isHash) {

            if (sumHash.compareTo(LOW_DIAPOZON) <= 0) {
                sumHash = sumHash.multiply(deltaHash);
                System.out.println("Меньше: " + sumHash);
            } else if (sumHash.compareTo(HIGH_DIAPOZON) > 0 ) {
                sumHash = sumHash.divide(new BigDecimal(8*16));
                System.out.println("Больше: " + sumHash);
            } else {
                isHash = false;
                System.out.println("Выход из цикла: " + sumHash);
            }

        }

        sumHash = sumHash.setScale(0, BigDecimal.ROUND_HALF_EVEN);
        byte[] byteArray = sumHash.toBigInteger().toByteArray();
        for (byte b : byteArray) {
            System.out.println(b);
        }
        System.out.println("length = " + byteArray.length);

        return rez;
    }


}
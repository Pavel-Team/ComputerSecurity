import java.util.Random;


public class Lab4 {

    private static final int MASKA = 0b1111;   //Маска для взятия 4 битов
    private static final int MASKA_KEY = 0b1;  //Маска для взятия 1 бита пароля


    // -- cipher parameters
    // раундов шифрования (16)
    public static final int ROUNDS = 16;

    // -- feistel parameters
    // разрядность блока данных для криптографии, менять нельзя т.к. определяет
    // тип int функции фейстеля
    public static final int DATA_BLOCK_WIDE = 32;
    // разрядность S-блока (4)
    public static final int S_BLOCK_WIDE = 4;
    public static final int MAGIC_ROTATE = 11;
    // разрядность ключа шифрования (128)
    public static final int KEY_SIZE = ROUNDS * DATA_BLOCK_WIDE / S_BLOCK_WIDE; //Число байт из L/R стороны разделить на разрядность S-блока = число S-блоков на раунд и умножить на число раундов
    // количество S-блоков в раунде (16)
    public static final int S_BLOCKS = 2 * DATA_BLOCK_WIDE / S_BLOCK_WIDE;

    // -- блоки сети фейстеля
    static int s[][][] = new int[ROUNDS][S_BLOCKS][(int) Math.pow(2, S_BLOCK_WIDE)]; // 16,16,16


    //Функция для заполнения блоков сети фейстеля рандомными значениями
    static void generate(int studentNum) {
        Random rand = new Random(studentNum);
        for (int r = 0; r < s.length; r++) {
            System.out.printf("ROUND: %d\n", r);
            for (int i = 0; i < s[r].length; i++) {
                System.out.print(" {");
                for (int j = 0; j < s[r][i].length; j++) {
                    s[r][i][j] = rand.nextInt(s[r][i].length);
                    System.out.print(s[r][i][j] + ",");
                }
                System.out.println("},");
            }
        }

    }


    static int str2int(String s) {
        int rez = 0;
        for (int i = 0; i < 4; i++) {
            rez |= (s.charAt(i) & 255) << (i * 8);
        }
        return rez;
    }


    static String int2str(int l) {
        String rez = "";
        for (int i = 0; i < 4; i++) {
            rez += (char) (l & 255);
            l >>= 8;
        }
        return rez;
    }


    //Циклический сдвиг влево java
    private static int left(int a, int s) {
        return ((a << s) | (a >> (32 - s)));
    }


    //Реализация двойного S-блока
    //Принимает 4 значения:
    //numberRound - текущий номер раунда (0..15)
    //indexSS - индекс двойного S блока  (0..7)
    //value - кодируемое 4-битное значение
    //key - пароль (1 бит, который выбирает, таблицу подстановки какого S-блока активировать: 1го или 2го)
    //Возвращает закодированное (подстановочное) 4-битное значение
    private static int getValueFromSSBlock(int numberRound, int indexSS, int value, int key) {
        return s[numberRound][indexSS*2+key][value];
    }


    //Метод обработки правого блока чисел (функция преобразования f - воздействие S и P блоков на правый блок входных данных numberR)
    //Принимает на вход 2 параметра:
    //int numberR - правый блок данных
    //currentKey - текущие 8 бит ключа
    //round - номер текущего раунда
    private static int f(int numberR, int currentKey, int round) {

        //p-блок
        numberR = left(numberR, MAGIC_ROTATE);

        //Для каждых 4 битов работаем с двумя S-блоками
        int[] resultSBlock = new int[S_BLOCKS / 2]; //Наше numberR число разбитое на блоки по 4 бит
        for (int indexSS = 0; indexSS < S_BLOCKS / 2; indexSS++) {
            int current4bit = (numberR >> (4 * indexSS)) & MASKA; //Текущие 4 бита для обработки
            resultSBlock[S_BLOCKS / 2 - 1 - indexSS] =  getValueFromSSBlock(round, indexSS, current4bit, (currentKey >> indexSS) & MASKA_KEY);
        }
        //Преобразуем массив из 8ми 4-битных полученных значений в число
        numberR = 0;
        int i = 0;
        for (int elem4bit: resultSBlock) {
            numberR |= elem4bit << (4*i);
            i++;
        }

        return numberR;
    }



    //Метод шифрования 8 символов (64 бит)
    //Принимает message - 8 символов (64 бит)
    //pass_key = 16 символов (128 бит)
    //Возвращает result - 8 символов (16 байт)
    public static String crypt(String message,String pass_key) {

        //Разделяем на L и R - сторону число
        int numberR = str2int(message.substring(0, 4));
        int numberL = str2int(message.substring(4, 8));

        //Первые 16 раундов
        for (int round = 0; round < ROUNDS; round++) {
            int key = pass_key.substring(round, round+1).charAt(0);

            int resultF = f(numberR, key, round);

            //Меняем местами для последущего раунда
            int lastR = numberR;
            numberR = resultF ^ numberL;
            numberL = lastR;
        }

        //16й раунд
        /**int resultF = f(numberR, pass_key.substring(ROUNDS-1, ROUNDS).charAt(0), ROUNDS-1) ^ numberL;
        numberR = resultF;*/


        //Преобразовываем в конечный вид
        int resultInt1 = numberL;
        int resultInt2 = numberR;

        return int2str(resultInt2) + int2str(resultInt1);
    }


    //Метод дешифрования 8 символов (16 байт данных)
    //Принимает message - 8 символов (8*2 = 16 байт)
    //pass_key = 16 символов (16 * 2 = 32 байта)
    //Возвращает result - 8 символов (16 байт)
    public static String decrypt(String message,String pass_key) {

        //Разделяем на L и R - сторону число
        int numberR = str2int(message.substring(4, 8));
        int numberL = str2int(message.substring(0, 4));

        //Первые 16 раундов
        for (int round = ROUNDS - 1; round >= 0; round--) {
            int key = pass_key.substring(round, round+1).charAt(0);

            int resultF = f(numberR, key, round);

            //Меняем местами для последущего раунда
            int lastR = numberR;
            numberR = resultF ^ numberL;
            numberL = lastR;
        }

        //16й раунд
        /**int resultF = f(numberR, pass_key.substring(0, 1).charAt(0), 0)  ^ numberR;
        numberL = resultF;*/


        //Преобразовываем в конечный вид
        int resultInt1 = numberL;
        int resultInt2 = numberR;

        return int2str(resultInt1) + int2str(resultInt2);
    }


    public static void main(String[] args) {

        generate(1);
        String str="baracuda";
        String pass_key="beistel cipher 1"; //ñ9HT
                                            //§|óº´åõ}
        System.out.printf("==========\nисходные данные(2x32бит): \"%s\"\n",str);
        System.out.printf("ключ шифрования(128 бит): \"%s\"\n",pass_key);
        String rez=crypt(str,pass_key);
        System.out.println("зашифрованные данные: "+rez);
        rez=decrypt(rez,pass_key);
        System.out.println("расшифрованные данные: "+rez);
    }

}
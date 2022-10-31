public class Main {

    private static final int OPTION = -500;                        //Номер варианта (сдвиг в шифре Цезаря)
    private static final String TEXT_FOR_CRYPT = "abc xyz ggg"; //Текст для шифрования


    public static void main(String[] args) {
        //Стандартная сложность
        EasyLab1 easyLab1 = new EasyLab1(OPTION);
        String crypt1 = easyLab1.crypt(TEXT_FOR_CRYPT);
        String decrypt1 = easyLab1.decrypt(crypt1);
        System.out.println("Crypt1 = " + crypt1);
        System.out.println("Decrypt1 = " + decrypt1);

        System.out.println();

        //Повышенная сложность
        HardLab1 hardLab1 = new HardLab1();
        String crypt2 = hardLab1.crypt(TEXT_FOR_CRYPT);
        String decrypt2 = hardLab1.decrypt(crypt2);
        System.out.println("Crypt2 = " + crypt2);
        System.out.println("Decrypt2 = " + decrypt2);
    }

}

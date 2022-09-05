//Шифр Цезаря
public class EasyLab1 {

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz"; //Кодируемый алфавит
    private int shift; //Сдвиг


    public EasyLab1(int shift) {
        this.shift = shift;
    }


    //Метод шифрования шифром Цезаря
    public String crypt(String text) {
        StringBuilder rez = new StringBuilder("");

        for (char c: text.toCharArray()) {
            if (c == ' ') {
                rez.append(c);
                continue;
            }

            rez.append(
                    alphabet.charAt(
                            (alphabet.indexOf(c) + alphabet.length() + (shift % alphabet.length())) % alphabet.length()
                    )
            );
        }

        return rez.toString();
    }


    //Метод дешифрования шифром Цезаря
    public String decrypt(String text) {
        StringBuilder rez = new StringBuilder("");

        for (char c: text.toCharArray()) {
            if (c == ' ') {
                rez.append(c);
                continue;
            }

            rez.append(
                    alphabet.charAt(
                            (alphabet.indexOf(c) + alphabet.length() - (shift % alphabet.length())) % alphabet.length()
                    )
            );
        }

        return rez.toString();
    }

}

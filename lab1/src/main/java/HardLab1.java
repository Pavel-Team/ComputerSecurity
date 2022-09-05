//Простой подстановочный шифр
public class HardLab1 {

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz"; //Исходный алфавит
    private String alphabet2 = ""; //Подстановочный алфавит (инициализируется при создании объекта)


    public HardLab1() {
        //Формируем для каждого объекта свой подстановочный алфавит
        StringBuilder oldAlphabet = new StringBuilder(alphabet);
        StringBuilder newAlphabet = new StringBuilder("");
        for (int i = alphabet.length()-1; i >= 0; i--) {
            int indexDelete = (int) Math.round(Math.random()*i);

            newAlphabet.append(oldAlphabet.charAt(indexDelete));
            oldAlphabet.deleteCharAt(indexDelete);
        }
        alphabet2 = newAlphabet.toString();
    }


    //Метод шифрования простым подстановочным шифром
    public String crypt(String text) {
        StringBuilder rez = new StringBuilder("");

        for (char c: text.toCharArray()) {
            if (c == ' ') {
                rez.append(c);
                continue;
            }

            rez.append(
                    alphabet2.charAt(
                            alphabet.indexOf(c)
                    )
            );
        }

        return rez.toString();
    }


    //Метод дешифрования простым подстановочным шифром
    public String decrypt(String text) {
        StringBuilder rez = new StringBuilder("");

        for (char c: text.toCharArray()) {
            if (c == ' ') {
                rez.append(c);
                continue;
            }

            rez.append(
                    alphabet.charAt(
                            alphabet2.indexOf(c)
                    )
            );
        }

        return rez.toString();
    }


}

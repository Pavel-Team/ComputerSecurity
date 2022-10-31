import java.util.HashMap;

public class Lab2Easy {

    private static final String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";


    //Перебор шифром Цезаря
    void enumerationByCezar(String crypt) {
        for (int i = 1; i < alphabet.length(); i++) {
            String decrypt = decrypt(crypt, i);
            System.out.println("i = " + i + ": " + decrypt + "\n");
        }
    }


    //Неполный подстановочный шифр
    void partialSubstitutionCipher(String crypt, HashMap<Character, Character> mapAlphabet) {

        StringBuilder rez = new StringBuilder("");

        for (char c: crypt.toCharArray()) {

            if (mapAlphabet.get(c) == null) {
                rez.append(c);
                continue;
            }

            rez.append(
                    mapAlphabet.get(c)
            );
        }

        System.out.println(rez);
    }


    //Метод дешиврофания текста шифром Цезаря
    private String decrypt(String text, int shift) {
        StringBuilder rez = new StringBuilder("");

        for (char c: text.toCharArray()) {
            if (alphabet.indexOf(c) == -1) {
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

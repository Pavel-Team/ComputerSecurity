import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lab2Hard {

    private static final String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";


    //Метод частотного анализа
    //На вход принимает 2 параметра:
    //cryptText : String - зашифрованный текст
    //mapTaskFrequency : HashMap<Character, Float> - частота исходных символов, взятых из задания
    //Метод выводит в консоль расшифроавнный текст
    public void frequencyAnalysis(String cryptText, HashMap<Character, Float> mapTaskFrequency) {
        HashMap<Character, Float> mapTextFrequency = getFrequencyMap(cryptText);                  //Частота сиволов в тексте
        HashMap<Character, Character> mapKey = getHashMapKey(mapTaskFrequency, mapTextFrequency); //Ключи для текста

        String decryptText = decryptText(cryptText, mapKey);

        System.out.println("Расшифрованный текст:\n" + decryptText);
    }


    //Метод возвращающий частоту используемых символов в виде словаря
    private HashMap<Character, Float> getFrequencyMap(String text) {

        //Инициализируем словарь
        HashMap<Character, Float> map = new HashMap<>();
        map.put('а', 0F);
        map.put('б', 0F);
        map.put('в', 0F);
        map.put('г', 0F);
        map.put('д', 0F);
        map.put('е', 0F);
        map.put('ё', 0F);
        map.put('ж', 0F);
        map.put('з', 0F);
        map.put('и', 0F);
        map.put('й', 0F);
        map.put('к', 0F);
        map.put('л', 0F);
        map.put('м', 0F);
        map.put('н', 0F);
        map.put('о', 0F);
        map.put('п', 0F);
        map.put('р', 0F);
        map.put('с', 0F);
        map.put('т', 0F);
        map.put('у', 0F);
        map.put('ф', 0F);
        map.put('х', 0F);
        map.put('ц', 0F);
        map.put('ч', 0F);
        map.put('ш', 0F);
        map.put('щ', 0F);
        map.put('ъ', 0F);
        map.put('ы', 0F);
        map.put('ь', 0F);
        map.put('э', 0F);
        map.put('ю', 0F);
        map.put('я', 0F);

        int countChars = text.length();

        //Считаем число вхождений букв алфавита
        for (char c: text.toCharArray()) {
            if (alphabet.indexOf(c) == -1) {
                countChars--;
                continue;
            } else {
                float count = map.get(c) + 1;
                map.put(c, count);
            }
        }

        //Получаем частоту
        for (char c: alphabet.toCharArray()) {
            float count = map.get(c);
            map.put(c, count / countChars);
        }

        //Сортируем в по возрастанию
//        map.entrySet().stream().sorted(Map.Entry.<Character, Float>comparingByValue().reversed())
//                .forEach(System.out::println);

        return map;
    }


    //Метод сопоставляющий частоты взяты из задания (taskMap) и полученные частоты из текста (frequencyMap).
    //Результат возвращается в виде {символ_зашифрованного_текста -> символ_исходного_текста}
    private HashMap<Character, Character> getHashMapKey(HashMap<Character, Float> taskMap, HashMap<Character, Float> frequencyMap) {
        HashMap<Character, Character> keyMap = new HashMap<>();

        //Проходимся по всем элементам словаря, находя элементы, разница частот между которыми минимальна. Формируем из них ключи для расшифровки
        for (Map.Entry<Character, Float> entryText : frequencyMap.entrySet()) {
            //a->0.5, b->0.4, ... полученный список
            //a->0.3, b->0.4, ... список из задния
            float minDif = 1;       //Минимальная найденная разница в частоте
            char minTaskChar = 'а'; //Ключ в исходном словаре частот
            char minTextChar = 'а'; //Ключ в найденном словаре частот
            for (Map.Entry<Character, Float> entryTask : taskMap.entrySet()) {
                float diff = Math.abs(entryTask.getValue() - entryText.getValue());

                if (diff < minDif) {
                    minTaskChar = entryTask.getKey();
                    minTextChar = entryText.getKey();
                    minDif = diff;
                }
            }

            keyMap.put(minTextChar, minTaskChar);
        }

        System.out.println(keyMap.toString());

        return keyMap;
    }


    //Метод для расшифрования текста cryptText по указанным ключам mapKey
    private String decryptText(String cryptText, HashMap<Character, Character> mapKey) {
        StringBuilder result = new StringBuilder("");

        for (char c: cryptText.toCharArray()) {
            if (alphabet.indexOf(c) == -1) {
                result.append(c);
                continue;
            }

            result.append(mapKey.get(c));
        }

        return result.toString();
    }

}

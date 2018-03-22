package ru.track.cypher;

import java.util.*;

import org.jetbrains.annotations.NotNull;

/**
 * Вспомогательные методы шифрования/дешифрования
 */
public class CypherUtil {

    public static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Генерирует таблицу подстановки - то есть каждой буква алфавита ставится в соответствие другая буква
     * Не должно быть пересечений (a -> x, b -> x). Маппинг уникальный
     *
     * @return таблицу подстановки шифра
     */
    @NotNull
    public static Map<Character, Character> generateCypher() {

        Map<Character, Character> cypherMap = new HashMap<>();
        List<Character> symbols = new ArrayList<>();
        List<Character> encodeSymbols = new ArrayList<>();

        for (char c : SYMBOLS.toCharArray()) {
            symbols.add(c);
            encodeSymbols.add(c);
        }

        Collections.shuffle(encodeSymbols);

        for (int i = 0; i < symbols.size(); i++) {
            cypherMap.put(symbols.get(i), encodeSymbols.get(i));
        }

        return cypherMap;
    }

    public static void main(String[] args) {
        Map<Character, Character> map = CypherUtil.generateCypher();
        System.out.println(map);
        System.out.println(new Encoder().encode(map, "abcdef"));
        System.out.println(new Decoder("", "").createHist("abcccccccccccdqwertyyyyyaaac"));
    }

}

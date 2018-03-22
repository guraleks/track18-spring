package ru.track.cypher;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import static java.lang.Character.isLetter;

/**
 * Класс умеет кодировать сообщение используя шифр
 */
public class Encoder {

    /**
     * Метод шифрует символы текста в соответствие с таблицей
     * NOTE: Текст преводится в lower case!
     *
     * Если таблица: {a -> x, b -> y}
     * то текст aB -> xy, AB -> xy, ab -> xy
     *
     * @param cypherTable - таблица подстановки
     * @param text - исходный текст
     * @return зашифрованный текст
     */
    public String encode(@NotNull Map<Character, Character> cypherTable, @NotNull String text) {
        if (!text.equals("")) {
            StringBuilder encodeText = new StringBuilder();
            for (char c : text.toLowerCase().toCharArray()) {
                if (isLetter(c)) {
                    encodeText.append(String.valueOf(cypherTable.get(c)));
                } else {
                    encodeText.append(c);
                }
            }
            return new String(encodeText);
        } else {
            return "";
        }
    }
}

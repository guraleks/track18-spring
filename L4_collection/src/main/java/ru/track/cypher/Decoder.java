package ru.track.cypher;

import java.util.*;

import org.jetbrains.annotations.NotNull;

import static java.lang.Character.isLetter;

public class Decoder {

    // Расстояние между A-Z -> a-z
    public static final int SYMBOL_DIST = 32;

    private Map<Character, Character> cypher;

    /**
     * Конструктор строит гистограммы открытого домена и зашифрованного домена
     * Сортирует буквы в соответствие с их частотой и создает обратный шифр Map<Character, Character>
     *
     * @param domain - текст по кторому строим гистограмму языка
     */
    public Decoder(@NotNull String domain, @NotNull String encryptedDomain) {
        Map<Character, Integer> domainHist = createHist(domain);
        Map<Character, Integer> encryptedDomainHist = createHist(encryptedDomain);
        cypher = new LinkedHashMap<>();

        List<Map.Entry<Character, Integer>> domainHistList = new ArrayList<>();
        List<Map.Entry<Character, Integer>> encryptedDomainHistList = new ArrayList<>();

        domainHistList.addAll(domainHist.entrySet());
        encryptedDomainHistList.addAll(encryptedDomainHist.entrySet());

        for (int i = 0; i < encryptedDomainHist.size(); i++) {
            if (encryptedDomainHistList.get(i) != null && domainHistList.get(i) != null) {
                cypher.put(encryptedDomainHistList.get(i).getKey(), domainHistList.get(i).getKey());
            } else {
                cypher.put(' ', domainHistList.get(i).getKey());
            }
        }
    }

    public Map<Character, Character> getCypher() {
        return cypher;
    }

    /**
     * Применяет построенный шифр для расшифровки текста
     *
     * @param encoded зашифрованный текст
     * @return расшифровка
     */
    @NotNull
    public String decode(@NotNull String encoded) {
        if (!encoded.equals("")) {
            StringBuilder encodeText = new StringBuilder();
            for (char c : encoded.toLowerCase().toCharArray()) {
                if (isLetter(c)) {
                    encodeText.append(String.valueOf(cypher.get(c)));
                } else {
                    encodeText.append(c);
                }
            }
            return new String(encodeText);
        } else {
            return "";
        }
    }

    /**
     * Считывает входной текст посимвольно, буквы сохраняет в мапу.
     * Большие буквы приводит к маленьким
     *
     * @param text - входной текст
     * @return - мапа с частотой вхождения каждой буквы (Ключ - буква в нижнем регистре)
     * Мапа отсортирована по частоте. При итерировании на первой позиции наиболее частая буква
     */
    @NotNull
    Map<Character, Integer> createHist(@NotNull String text) {

        Map<Character, Integer> hist = new HashMap<>();

        for (char c : text.toLowerCase().toCharArray()) {
            if (isLetter(c)) {
                if (!hist.containsKey(c)) {
                    hist.put(c, 1);
                } else {
                    hist.put(c, hist.get(c) + 1);
                }
            }
        }

        List<Map.Entry<Character, Integer>> list = new ArrayList<>(hist.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> current, Map.Entry<Character, Integer> another) {
                return another.getValue() - current.getValue();
            }
        });

        Map<Character, Integer> sortedHist = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> entry : list) {
            sortedHist.put(entry.getKey(), entry.getValue());
        }

        return sortedHist;
    }
}

package com.example.task03;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class Task03Main {

    public static void main(String[] args) throws IOException {
        List<Set<String>> anagrams = findAnagrams(new FileInputStream("task03/resources/singular.txt"),
                Charset.forName("windows-1251"));

        for (Set<String> anagram : anagrams) {
            System.out.println(anagram);
        }
    }

    public static List<Set<String>> findAnagrams(InputStream inputStream, Charset charset) {
        //ключ — отсортированные буквы
        Map<String, Set<String>> anagrams = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            for (String line; (line = reader.readLine()) != null; ) {
                String[] words = line.toLowerCase().split("\\s+");

                for (String word : words) {
                    // Убираем всё, что короче 3 символов или не русские буквы
                    if (word.length() >= 3 && word.matches("[а-я]+")) {
                        // Сортируем буквы в слове по алфавиту
                        char[] chars = word.toCharArray();
                        Arrays.sort(chars);
                        String sortedWord = new String(chars);

                        anagrams.computeIfAbsent(sortedWord, k -> new TreeSet<>()).add(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return anagrams.values().stream().filter(group -> group.size() >= 2) // Берём только группы с 2+ словами
                .collect(Collectors.toList());
    }


}

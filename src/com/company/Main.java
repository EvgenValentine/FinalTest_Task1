package com.company;

/*
 *
 * Classname : Main
 * Description : Class for create the glossary
 *
 *  23 June 2020
 *
 * @version 1.0 2020.06.23
 * @author Khnyznytskyj Evgen
 *
 * */
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        //read data
        List<String> listOfString = new ArrayList<>();
        Files.lines(Paths.get("F:\\FinalTest_Task1\\src\\com\\company\\harry.txt"), Charset.forName("windows-1251"))
                .forEach(string ->{
                    string = string.replaceAll("[^a-zA-Z0-9' ]" ,"");
                    String[] words = string.split(" ");
                    for (String word: words){
                        listOfString.add(word);
                    }
                });

        //set words to distinct glossary
        Map<String, Integer> distingts = new LinkedHashMap<>();
        listOfString.stream().forEach(item ->{
            if(!distingts.containsKey(item)){
                distingts.put(item,1);
            }else{
                Integer newValue = distingts.get(item);
                distingts.put(item, newValue+1);
            }
        });

        //------------------------------------------------------------------

        //sort the glossary by value
        Map<String , Integer> sortedList = distingts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        //print sorted list of all words
        sortedList.forEach((key,value)->{
            System.out.println(key + " - " + value);
        });

        //------------------------------------------------------------------

        //get top 20 pairs words
        List<String> keys = new ArrayList<>();
        Map<String, Integer> top20Words = new LinkedHashMap<>();
        sortedList.keySet().stream().forEach(key-> keys.add(key));
        for (int i = 0; i < 20; i++) {
            top20Words.put(keys.get(i), sortedList.get(keys.get(i)));
        }

        //print sorted list of all words
        top20Words.forEach((key,value)->{
            System.out.println(key + " - " + value);
        });

        //------------------------------------------------------------------
        //Get list of proper names
        Map<String, Integer> listOfProperNames  = new LinkedHashMap<>();
        distingts.entrySet().stream().forEach(word->{
            String example = word.getKey();
            String exampleLowCase = example.toLowerCase();
            if(example.length()>0 && example.charAt(0) != exampleLowCase.charAt(0)){
                if(!distingts.containsKey(exampleLowCase)){
                    listOfProperNames.put(word.getKey(),word.getValue());
                }
            }
        });

        //------------------------------------------------------------------

        //create sorted list of proper names
        Map<String , Integer> sortedListOfProperNames = listOfProperNames.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        //print sorted list of all Proper Names words
        sortedListOfProperNames.forEach((key,value)->{
            System.out.println(key + " - " + value);
        });

        //------------------------------------------------------------------------------------
        //To file
        FileWriter nFile = new FileWriter("F:\\FinalTest_Task1\\src\\com\\company\\test.txt");

        String header = "Final test. Task 1." + System.lineSeparator()
                + "Author - Кніжницький Євгеній" + System.lineSeparator() + System.lineSeparator()
                + "1.1. Download a text about Harry Potter.\n" +
                "1.2. For each distinct word in the text calculate the number of occurrence.\n" +
                "1.3. Use RegEx..\n" +
                "1.4. Sort in the DESC mode by the number of occurrence..\n" +
                "1.5. Find  the first 20 pairs.\n" +
                "1.6  Find all the proper names\n" +
                "1.7.  Count them and arrange in alphabetic order.\n" +
                "1.8.   First 20 pairs and names write into to a file test.txt\n" +
                "1.9.  Create a fine header for the file\n" +
                "1.10  Use Java  Collections to demonstrate your experience (Map, List )"
                + System.lineSeparator() + System.lineSeparator() +
                "Top 20 most occurrence word:" + System.lineSeparator();

        nFile.write(header);

        //write to file sorted list Top 20 most occurrence word
        for (Map.Entry<String, Integer> entry : top20Words.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            nFile.write(key + " - " + value + System.lineSeparator());
        }
        //small header again
        header = System.lineSeparator() + System.lineSeparator() +
                "The proper names:" + System.lineSeparator();
        nFile.write(header);

        //write to file sorted list of all Proper Names words
        for (Map.Entry<String, Integer> entry : sortedListOfProperNames.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            nFile.write(key + " - " + value + System.lineSeparator());
        }

        nFile.close();
    }
}

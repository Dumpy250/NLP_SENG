package org.example;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.Properties;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // Method to read and split the file into sentences, returning the array of sentences
    private static String[] readAndSplitFileIntoSentences(String fileName) throws FileNotFoundException {
        List<String> sentenceList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            // Use a different delimiter for sentences (e.g., period followed by space)
            scanner.useDelimiter("\\.\\s");

            while (scanner.hasNext()) {
                String sentence = scanner.next();

                // Add each sentence to the list
                sentenceList.add(sentence);
            }
        }

        // Convert the list to an array
        return sentenceList.toArray(new String[0]);
    }

    public static void main(String[] args) {

        // Replace "yourfilename.txt" with the actual path to your text file
        String fileName = "yourfilename.txt";

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        //String text = "What is the Weather in Bangalore right now?";

//        try {
//            String[] sentences = readAndSplitFileIntoSentences(fileName);
//
//            // Process the array of words as needed
//            for (String sentence : sentences) {
//                System.out.println(sentence);
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: " + e.getMessage());
//        }

        try {
            // create an empty Annotation just with the given text
            Annotation document = new Annotation(Arrays.toString(readAndSplitFileIntoSentences(fileName)));
            // run all Annotators on this text
            pipeline.annotate(document);

            List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

            for (CoreMap sentence : sentences) {
                // traversing the words in the current sentence
                // a CoreLabel is a CoreMap with additional token-specific methods
                for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    // this is the text of the token
                    String word = token.get(CoreAnnotations.TextAnnotation.class);
                    // this is the POS tag of the token
                    String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    // this is the NER label of the token
                    String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                    System.out.println(String.format("Print: word: [%s] pos: [%s] ne: [%s]", word, pos, ne));
                }
            }
        } catch (FileNotFoundException e){
            System.err.println("File not found: " + e.getMessage());
        }

    }
}
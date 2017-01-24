
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
    public static void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public static void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public static void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord markovWord = new MarkovWord(7); 
        runModel(markovWord, st, 200,844); 
    } 

    public void runEfficientMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        EfficientMarkovWord markovWord = new EfficientMarkovWord(3); 
        runModel(markovWord, st, 200,643); 
    } 
    
    public void testHashMap() {
        //String st = "this is a test yes this is really a test";
        //String st = "this is a test yes this is really a test yes a test this is wow";
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        
        int size = 100;
        int seed = 65;
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2);
        markovWord.setRandom(seed);
        markovWord.setTraining(st);
        markovWord.buildMap();
        markovWord.printHashMapInfo();
        
    }
    
    public static void compareMethods() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 100;
        int seed = 42;
        int order = 2;
        
        long startTimeMTwo = System.nanoTime();
        MarkovWordTwo mTwo = new MarkovWordTwo();
        runModel(mTwo, st, size, seed);
        long durationMTwo = System.nanoTime() - startTimeMTwo;
        
        long startTimeMEffi = System.nanoTime();
        EfficientMarkovWord effiMarkov = new EfficientMarkovWord(order);
        runModel(effiMarkov, st, size, seed);
        long durationMEffi = System.nanoTime() - startTimeMEffi;
        
        System.out.println("----------------------------------------------------");
        System.out.println("The time consumed by Markov Two is " + (durationMTwo/1000000)+" ms.");
        System.out.println("The time consumed by Markov HashMap is " + (durationMEffi/1000000)+" ms.");
        System.out.println("----------------------------------------------------");        
    }
    
    private static void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 
    
    public static void main(String[] args) {
    	System.out.println("Please select the source file where the text is generated from.");
    	System.out.println("The files are in the data folder.");
    	runMarkov();
    }
    
    

}

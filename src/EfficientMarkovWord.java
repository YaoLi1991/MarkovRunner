
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram,ArrayList<String>> keyFollowsMap;
    
    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
        keyFollowsMap = new HashMap<WordGram, ArrayList<String>>();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
    }
    
    private int indexOf(String[] words, WordGram target, int start) {
        
        for(int index = start; index < words.length - myOrder; index++) {
            String[] currentWords = new String[myOrder];
            /*
            for(int k = 0; k < myOrder; k++) {
                currentWords[k] = words[index+k];
            }
            */
            System.arraycopy(words,index,currentWords,0,myOrder);
            WordGram currentGram = new WordGram(currentWords, 0, myOrder);
            if(target.equals(currentGram)) {
                return index;
            }
        }
        return -1;
    }
    
    private ArrayList<String> getFollows(WordGram kGram) {

        return keyFollowsMap.get(kGram);
        
    }
    
    public String getRandomText(int numWords) {
        StringBuilder randomText = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);
        String[] keyGramWords = new String[myOrder];
        System.arraycopy(myText,index,keyGramWords,0,myOrder);
        WordGram keyGram = new WordGram(keyGramWords,0,myOrder);
        randomText.append(keyGram.toString());
        
        for(int k = 0; k < numWords - myOrder; k++) {
            
            ArrayList<String> followWords = getFollows(keyGram);
            if(followWords.size() == 0) {
                break;
            }
            //System.out.print(keyGram);
            //System.out.println("  The size: "+ followWords.size());
            index = myRandom.nextInt(followWords.size());
            String nextWord = followWords.get(index);
            randomText.append(nextWord);
            randomText.append(" ");
            keyGram = keyGram.shiftAdd(nextWord);
            
        }
        return randomText.toString();

    }
    
    public void buildMap() {
        for(int k=0; k <= myText.length-myOrder; k++) {
            String[] keyGramWords = new String[myOrder];
            System.arraycopy(myText,k,keyGramWords,0,myOrder);
            WordGram keyGram = new WordGram(keyGramWords,0,myOrder);             
            if(keyFollowsMap.containsKey(keyGram)) {
                continue;
            }
            else {
                ArrayList<String> nextWords = new ArrayList<String>();
                for(int index = 0; index <= myText.length-myOrder; index++) {
                    index = indexOf(myText, keyGram, index);
                    if(index == -1) {
                        keyFollowsMap.put(keyGram, nextWords);
                        break;
                    }
                    else {
                        nextWords.add(myText[index+myOrder]);
                    }
                }
            }
        }        
    }
    
    public void printHashMapInfo() {
        HashMap<WordGram, ArrayList<String>> maxKeyWordsMap = new HashMap<WordGram, ArrayList<String>>();
        /*
        for(WordGram wg: keyFollowsMap.keySet()) {
            System.out.print(wg + " : ");
            for(String s: keyFollowsMap.get(wg)) {
                System.out.print(s+" ");
            }
            System.out.println("");
        }
        */
        //print all HashMap
        System.out.println("The number of key is "+ keyFollowsMap.size());
        //print the size of keys
        int largestSoFar =0;
        for(ArrayList<String> followWords: keyFollowsMap.values()) {
            if(followWords.size() > largestSoFar) {
                largestSoFar = followWords.size();
            }
        }
        System.out.println("The size of the largest value in the HashMap is " + largestSoFar);
        for(WordGram wg : keyFollowsMap.keySet()) {
            if(keyFollowsMap.get(wg).size()==largestSoFar) {
                maxKeyWordsMap.put(wg, keyFollowsMap.get(wg));
            }
        }
        for(WordGram wg: maxKeyWordsMap.keySet()) {
            System.out.print(wg + " :");
            for(String s : maxKeyWordsMap.get(wg)){
                System.out.print("\""+s+"\"");
            }
            System.out.print("\n");
        }
    }
}
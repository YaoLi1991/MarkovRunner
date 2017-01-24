
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
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
        ArrayList<String> followWords = new ArrayList<String>();        
        for(int index = 0 ; index < myText.length - myOrder; index++) {
            index = indexOf(myText, kGram, index);

            if(index == -1) {
                return followWords;
            }
            else {
                followWords.add(myText[index+myOrder]);
            }
        }
        return followWords;
        
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
            
            index = myRandom.nextInt(followWords.size());
            String nextWord = followWords.get(index);
            randomText.append(nextWord);
            randomText.append(" ");
            keyGram = keyGram.shiftAdd(nextWord);
            
        }
        return randomText.toString();

    }
    
    public String toString() {
    	return "Markov Word Order: "+myOrder;
    }
}

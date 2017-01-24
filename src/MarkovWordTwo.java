
/**
 * Write a description of MarkovWordTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MarkovWordTwo implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    
    public MarkovWordTwo() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-2);  // random word to start with
        String key1 = myText[index];
        String key2 = myText[index+1];
        sb.append(key1);
        sb.append(" ");
        sb.append(key2);
        sb.append(" ");
        for(int k=0; k < numWords-2; k++){
            ArrayList<String> follows = getFollows(key1,key2);
            /*
            System.out.println("The key is : "+key);
            for(String s : follows) {
                System.out.print("\""+s+"\"");
            }
            System.out.print("\n");
            */
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key1 = key2;
            key2 = next;
        }        
        return sb.toString().trim();
    }
    
    private ArrayList<String> getFollows(String key1, String key2) {
        ArrayList<String> follows = new ArrayList<String>();
        for(int k = 0; k < myText.length-1; k++){
            k = indexOf(myText, key1, key2, k);
            if((k != -1)&&(k != myText.length -1)) {
                follows.add(myText[k+2]);
            }
            else {
                return follows;
            }
            
        }
        return follows;
    }
    
    private  int indexOf(String[] words, String target1, String target2, int start) {
        for(int k = start; k < words.length-1; k++ ) {
            if (words[k].equals(target1)&&words[k+1].equals(target2)) {
                return k;
            }
        }
        return -1;
    }
    
    public void testIndexOf() {
        String testString = "this is just a test yes this is a simple test";
        setTraining(testString);
        int int1 = indexOf(myText, "this","is", 0);
        int int2 = indexOf(myText, "this","is", 3);
        int int3 = indexOf(myText, "frog","is", 0);
        int int4 = indexOf(myText, "frog","is", 5);
        int int5 = indexOf(myText, "simple","test", 2);
        int int6 = indexOf(myText, "test","yes", 5);
        System.out.println("this from 0 :" + int1);
        System.out.println("this from 3 :" + int2);
        System.out.println("frog from 0 :" + int3);        
        System.out.println("frog from 5 :" + int4);
        System.out.println("simple from 2 :" + int5);
        System.out.println("test from 5 :" + int6);        
    }
    
    public String toString() {
    	return "MarkovWordTwo";
    }

}
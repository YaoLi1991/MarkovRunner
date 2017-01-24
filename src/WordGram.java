
public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        // TODO: Complete this method
        return myWords.length;
    }

    public String toString(){
        StringBuilder ret = new StringBuilder();
        for(int k = 0; k < myWords.length; k++) {
            ret.append(myWords[k]);
            ret.append(" ");
        }// TODO: Complete this method
        return ret.toString();
    }

    public boolean equals(Object o) {
        
        WordGram other = (WordGram) o;
        if(myWords.length != other.length()) {
            return false;
        }
        for(int k = 0; k < myWords.length; k++){
            if(!myWords[k].equals(other.myWords[k])) {
                return false;
            }
        }// TODO: Complete this method
        return true;

    }

    public WordGram shiftAdd(String word) { 
        WordGram out = new WordGram(myWords, 0, myWords.length);
        for(int k = 0; k < myWords.length; k++){
            if(k == myWords.length - 1) {
                out.myWords[k] = word;
            }
            else {
                out.myWords[k] = myWords[k+1];            
            }
        }// shift all words one towards 0 and add word at the end. 
        // you lose the first word
        // TODO: Complete this method
        return out;
    }
    
    public int hashCode() {
        StringBuilder words = new StringBuilder();
        words.append(toString().trim());
        int hashCode = words.toString().hashCode();
        return hashCode;
    }

}
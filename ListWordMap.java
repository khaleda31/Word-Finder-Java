package F28PB_CW;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

public class ListWordMap implements IWordMap {
	
	LinkedList<Words> entries;
	LinkedList<IPosition> pos;
	
	
	public ListWordMap() {
		entries = new LinkedList<Words>();
		pos = new LinkedList<IPosition>();
	}
	
	
	
	@Override
	public void addPosition(String word, IPosition pos) {
		 boolean added = false;
	        for (Words entry : entries) {
	            if (entry.getWord().equals(word)) {
	                entry.addPosition(pos);
	                added = true;
	                break; // Exit the loop once the word is found
	            }
	        }
	        if (added==false) {
	            entries.add(new Words(word, pos));
	        }
	}


	@Override
	public void removeWord(String word) throws WordException {
		Iterator<Words> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Words entry = iterator.next();
            if (entry.getWord().equals(word)) {
                iterator.remove(); // Use iterator to safely remove the entry
                return; // Exit the method once the word is removed
            }
        }
        // If the loop completes without finding the word, throw an exception
        throw new WordException("Word not found: " + word);
	}


	@Override
	public void removePosition(String word, IPosition pos) throws WordException {
		 // Find the entry for the specified word
		for (Words entry : entries) {
            if (entry.getWord().equals(word)) {
                entry.removePosition(pos);
                return; // Exit the method once the position is removed
            }
        }
        // If the loop completes without finding the word, throw an exception
        throw new WordException("Word not found: " + word);

	}
        

	@Override
	public Iterator<String> words() {
		// Return an iterator over the words in the map
        LinkedList<String> words = new LinkedList<>();
        for (Words entry:entries) {
            words.add(entry.getWord());
        }
    
        return words.iterator();
	}

	@Override
	public Iterator<IPosition> positions(String word) throws WordException { 
		 for (Words entry : entries) {
	            if (entry.getWord().equals(word)) {
	            	LinkedList<IPosition> sourceList = entry.getPositions();
			        pos.addAll(sourceList);

	            }
	        }
		 return pos.iterator();
	}

	@Override
	public int numberOfEntries() { 
		//obtains the total number of entries/ positions from all the files
		int total=0;
		for(Words entry:entries) {
			total+=entry.getEnt(); 
			}
		return total;
	}
	
	
	//an inner class
	private static class Words {
        private String word;
        private LinkedList<IPosition> positions= new LinkedList<IPosition>();
        
        
        public Words(String word,IPosition pos) {
        	this.word=word;
        	
        	this.positions.add(pos);
        }
        
        public String getWord() {
        	return word;
        }
        
        public void addPosition(IPosition pos) {
        	positions.add(pos);
        }
        
        public void removePosition(IPosition pos) {
        	  positions.remove(pos);
        }
        
        public LinkedList<IPosition> getPositions() {
        	return positions;
        }
        
        public int getEnt() {
        	return positions.size(); //returns the size of the linked list of the interface
        }
        
        
	}
}

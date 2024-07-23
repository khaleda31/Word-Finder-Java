package F28PB_CW;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListWordMapTest {

	
	IWordMap map = new ListWordMap();
	String word1 = "test1";
    String word2 = "test2";
    IPosition pos1 = new WordPosition("test.txt", 4, word1);
    IPosition pos2 = new WordPosition("test.txt", 5, word2);
    
    @Test
	public void signatureTest() {
		try {
			IWordMap map = new ListWordMap();
			String word1 = "test1";
			String word2 = "test2";
			IPosition pos1 = new WordPosition("test.txt", 4, word1);
			IPosition pos2 = new WordPosition("test.txt", 5, word2);
			map.addPosition(word1, pos1);
			map.addPosition(word2, pos2);
			map.words();
			map.positions(word1);
			map.numberOfEntries();
			map.removePosition(word1, pos1);
			map.removeWord(word2);
		} catch (Exception e) {
			fail("Signature of solution does not conform");
		}
	}

	// Add your own tests
    @Test
    public void testAddPosition() {
    	
        try {
            map.addPosition(word1, pos1);
            map.addPosition(word2, pos2);

         // Verify that the added positions are present in the map
            List<String> words = new ArrayList<>(); //create an array list to store all the added words
            map.words().forEachRemaining(words::add);  //to iterate through each element/ word

            assertTrue("Added positions not present in map", words.contains(word1));  
            assertTrue("Added positions not present in map", words.contains(word2));
        } catch (Exception e) {
            fail("Failed to add position: " + e.getMessage());
        }
    }
	
    @Test
    public void testRemoveWord() {
        try {
            map.addPosition(word1, pos1);
            map.addPosition(word2, pos2);
            map.removeWord(word1);

            // Remove a word and verify that it is no longer present in the map
            List<String> remainingWords = new ArrayList<>(); //create an array list to store all the added words
            map.words().forEachRemaining(remainingWords::add);

            assertFalse("Removed word still present in map", remainingWords.contains(word1));
        } catch (Exception e) {
            fail("Failed to remove word: " + e.getMessage());
        }
    }
    
    // Test to ensure that attempting to remove a word not in the map results in an exception
    @Test(expected = Exception.class)
    public void testRemoveWord_WordNotFound() throws Exception {
        map.removeWord(word1);
    }

    @Test
    public void testRemovePosition() {
        try {
            map.addPosition(word1, pos1);
            map.addPosition(word2, pos2);
            map.removePosition(word1, pos1);

         // Remove a position for a word and verify that it is no longer present in the map
            List<IPosition> remainingPositions = new ArrayList<>(); //create an array list to store all the added words
            map.positions(word1).forEachRemaining(remainingPositions::add);

            assertTrue("Removed position still present in map", remainingPositions.isEmpty());
        } catch (Exception e) {
            fail("Failed to remove position: " + e.getMessage());
        }
    }
    
    // Test to ensure that attempting to remove a position that does not exist results in an exception
    @Test(expected= Exception.class)
    public void testRemovePosition_Unsucessful()throws Exception{
    	map.removePosition(word1, pos1);
    }
	
    @Test
    public void testWords() {
        try {
            map.addPosition(word1, pos1);
            map.addPosition(word2, pos2);

         // Verify that the iterator for words returns the correct words
            List<String> words = new ArrayList<>(); //create an array list to store all the added words
            map.words().forEachRemaining(words::add);

            assertTrue("Iterator for words incorrect", words.contains(word1));
            assertTrue("Iterator for words incorrect", words.contains(word2));
        } catch (Exception e) {
            fail("Failed to retrieve words: " + e.getMessage());
        }
    }
    
    @Test
    public void testPositions() {
        try {
            map.addPosition(word1, pos1);
            map.addPosition(word2, pos2);

            // Verify that the iterator for positions returns the correct positions
            List<IPosition> positionsForWord1 = new ArrayList<>(); //create an array list to store all the added positions
            map.positions(word1).forEachRemaining(positionsForWord1::add);

            List<IPosition> positionsForWord2 = new ArrayList<>();
            map.positions(word2).forEachRemaining(positionsForWord2::add);

            assertTrue("Iterator for positions incorrect", positionsForWord1.contains(pos1));
            assertTrue("Iterator for positions incorrect", positionsForWord2.contains(pos2));
        } catch (Exception e) {
            fail("Failed to retrieve positions: " + e.getMessage());
        }
    }

	@Test
	public void testNumberOfEntries() {
		try {
			map.addPosition(word1, pos1);
			map.addPosition(word2, pos2);
			
			 // Verify that the count of entries is correct
			assertEquals("Incorrect number of entries", 2, map.numberOfEntries());
		} catch (Exception e) {
			fail("Failed to count entries: " + e.getMessage());
		}
	}


}

package F28PB_CW;
import java.io.FileReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** Main class for the Word Index program */
public class WordIndex {

	static ArrayList<WordPosition> wordPossList = new ArrayList<>();
	static final File textFilesFolder = new File("TextFiles");
	static final FileFilter commandFileFilter = (File file) -> file.getParent() == null;
	static final FilenameFilter txtFilenameFilter = (File dir, String filename) -> filename.endsWith(".txt");

	public static void main(String[] argv) throws WordException {
		if (argv.length != 1) {
			System.err.println("Usage: WordIndex commands.txt");
			System.exit(1);
		}
		try {
			File commandFile = new File(argv[0]);
			if (commandFile.getParent() != null) {
				System.err.println("Use a command file in current directory");
				System.exit(1);
			}

			// creating a command reader from a file
			WordTxtReader commandReader = new WordTxtReader(commandFile);

			// initialise map
			IWordMap wordPossMap = new ListWordMap();
			// ...
			 
			// reading the content of the command file
			while (commandReader.hasNextWord()) {
				// getting the next command
				String command = commandReader.nextWord().getWord();

				switch (command) { 
				case "addall":
					assert (textFilesFolder.isDirectory());
					File[] listOfFiles = textFilesFolder.listFiles(txtFilenameFilter);
					Arrays.sort(listOfFiles);
					System.out.println("=================================");
					System.out.println("Add all command");
					System.out.println("------------------------");
					for (File textFile : listOfFiles) {
						WordTxtReader wordReader = new WordTxtReader(textFile);

						while (wordReader.hasNextWord()) {
							WordPosition wordPos = wordReader.nextWord();
							wordPossMap.addPosition(wordPos.getWord(), wordPos);    //to add all the positions/ entries from all the files
							
						}
					}
					System.out.println(wordPossMap.numberOfEntries()+ " entries have been added from "+ listOfFiles.length+ " files");
					break;
				case "add":
					File textFile = new File(textFilesFolder, commandReader.nextWord().getWord() + ".txt");
					WordTxtReader wordReader = new WordTxtReader(textFile);
					System.out.println(" ");
					System.out.println("=================================");
					System.out.println("Add command");
					System.out.println("------------------------");
					int count=0;
					//to count all the word from the file based on the file name in the commands.txt text file
					while (wordReader.hasNextWord()) {   
						WordPosition word = wordReader.nextWord();
						 wordPossMap.addPosition(word.getWord(), word); //adds the positions of the words 
						count++;
					}
					System.out.println(count + " entries have been indexed from "+ textFile.getPath());
					break;
					
					// ...

				case "search":
				    int numberOfFiles = Integer.parseInt(commandReader.nextWord().getWord());
		                    String word = commandReader.nextWord().getWord();
		                    try {
		                        Iterator<IPosition> positions = wordPossMap.positions(word);
		                        int i = 0;

		                        System.out.println(" ");
		                        System.out.println("=================================");
		                        System.out.println("Search command");
		                        System.out.println("--------------------");
		                        String placeholder = "";
		                        int occur=0;
		                        ArrayList<Integer> ipos = new ArrayList<Integer>(); //creating an ArrayList for the position
		                        ArrayList<Integer> num = new ArrayList<Integer>();  //creating an ArrayList for the number of positions in each file
		                        ArrayList<String> filename = new ArrayList<String>(); //creating an ArrayList for the name of each file
		                        int numPos=0;
		                        int totalFiles=0;
		                        
		                        while (positions.hasNext()) {
		                            IPosition position = positions.next(); //gets the position of the word
		                            String file = position.getFileName();  //obtains the file name of the position of the word thats found
		                            int line = position.getLineNumber();	//obtains the line number of the word found
		                           

		                            if(placeholder.equals("")) {
		                           	 	placeholder= file; //Store the name of the current file being used
		                           	 	ipos.add(line); //adding the line to the ArrayList
		                           	 	numPos++; //increment of the number of position in the file
		                           	 	occur++;  //increments the amount of times the word occurs
		                           	 	filename.add(file); //adds the file name to the ArrayList
		                           	 	totalFiles++; //increment of the number of files
		                            }
		                            else if (file.equals(placeholder)) {
		                            	ipos.add(line);
		                            	numPos++;
		                            	occur++;
		                            }
		                            else { 
		                            	 placeholder= file;
		                            	 occur=1; //resets the increment after changing files
		                            	 ipos.clear(); //resets the position ArrayList after changing files
		                            	 ipos.add(line);
		                            	 num.add(numPos);
		                            	 numPos=1; //resets the increment after changing files
		                            	 filename.add(file);
		                            	 totalFiles++;
		                            }
		                            if(positions.hasNext()==false) {
		                            	numPos++;
		                            	num.add(numPos);
		                            }		                            		                           
		                            i++;
		                        }
		 
		                        ArrayList<Integer>rank=new ArrayList<Integer>();	//create an ArrayList for the ranking of total positions of the word in each file
		                        ArrayList<String>rankName=new ArrayList<String>();	//create an ArrayList for the ranking of files based on the total positions of the word
		                        for(int l=0;l<numberOfFiles;l++) {
		                        	rank.add(0); //creates a placeholder in the ArrayList based on how many files wanted
		                        	rankName.add("placeholder"); //creates a placeholder in the ArrayList based on how many files wanted
		                        }
		                        
		                        //for loops that finds the files in the order of most occurrences in a file and should be limited to the number of files given as an argument (n)
		                        for(int m=0;m<num.size();m++){
		                        	int cont=0;
		                        	int current=0;
		                        	while(cont==0){
		                        		if(num.get(m)>rank.get(current)){
		                        			rank.add(current,num.get(m));
		                        			rankName.add(current,filename.get(m));
		                        			cont=1;
		                        		}
		                        		else if(num.get(m)==rank.get(current)){
		                        			rank.add(current+1,num.get(m));
		                        			rankName.add(current+1,filename.get(m));
		                        			cont = 1;
		                        		}
		                        		current++;
		                        	}
		                        }
		                  
		                        for(int o=0;o<numberOfFiles;o++) {
		                        	boolean printed=false;
		                        	String place = "";
			                        int occurs=0;
		                        	Iterator<IPosition> printing = wordPossMap.positions(word);			           
			                        ArrayList<Integer> ipos2 = new ArrayList<Integer>();
		                        	while (printing.hasNext()&&printed==false) {
			                            IPosition position = printing.next(); //gets the position of the word
			                            String file = position.getFileName();  //obtains the file name of the position of the word thats found
			                            int line = position.getLineNumber();	//obtains the line number of the word found
			                            
			                            if(place.equals("")) {
			                           	 	place= file; //the file name
			                           	 	ipos2.add(line); //adding the line to the ArrayList
			                           	 	occurs++;  //increments the amount of times the word occurs			           
			                            }
			                            else if (file.equals(place)) {
			                            	ipos2.add(line);
			                            	occurs++;
			                            }
			                            else {
			                            	if(place.equals(rankName.get(o))) {
			                            		System.out.println(" ");
			                            	 System.out.println("found "+word + " "+ occurs+" times in "+ place);
			                            	 System.out.print(" line( " + ipos2 + ")"+ "\n");
			                            	 printed=true; //set so that the this ranking only display once
			                            	}
			                            	 place= file;
			                            	 occurs=1; //resets the increment after changing files
			                            	 ipos2.clear(); //resets the position ArrayList after changing files
			                            	 ipos2.add(line);
			                            }
			                            
			                            if(printing.hasNext()==false&&place.equals(rankName.get(o))) {
			                            	 System.out.println(" ");
			                            	System.out.println("found "+word + " "+ occur+" times in "+ placeholder);
			                            	 System.out.print(" line( " + ipos2 + ")"+ "\n");
			                            	
			                            	 printed=true;
			                            }
			                           
			                        }
		                        }

		                        System.out.println(" ");
		                        System.out.println("The word " + word + " occurs " + i +  " times in " + totalFiles + " files");
		                               

		                    } catch (WordException e) {  //catch exception
		                        System.err.println("Word not found: " + word);
		                    }
				break;	
				case "remove":
					File textFileToRemove = new File(textFilesFolder, commandReader.nextWord().getWord() + ".txt");
					
					WordTxtReader read = new WordTxtReader(textFileToRemove);
					System.out.println(" ");
					System.out.println("=================================");
					System.out.println("Remove command");
					System.out.println("------------------------");
					int count2=0;
					//to remove all the word from the file based on the file name in the commands.txt text file
					while (read.hasNextWord()) {
						WordPosition words = read.nextWord();
						
						wordPossMap.removePosition(words.getWord(), words); //removes the positions of the words 
						count2++;   //counts every word
					}
					System.out.println(count2 + " entries have been removed from "+ textFileToRemove.getPath());
					break;
					
					
				case "overview":
					
					File[] listOfFile = textFilesFolder.listFiles(txtFilenameFilter);
					int numOfWords =0;
					Iterator <String> words = wordPossMap.words();
					
					//to count the amount of words from all the files
					while(words.hasNext()){
						numOfWords++;
						words.next();
					}
					System.out.println(" ");
					System.out.println("=================================");
					System.out.println("Overview command");
					System.out.println("------------------------");
					System.out.println("Overview");
					System.out.println("number of files: "+ listOfFile.length);  //prints the number of files
					System.out.println("number of words: " + numOfWords);  //print the number of words from all the files
					System.out.println("number of position: " + wordPossMap.numberOfEntries()); //print the number of entries/ positions from all the files
					break;
				default:
					break;
				}
			}
		} catch (IOException e) { // catch exceptions caused by file input/output errors
			System.err.println("Check your file name");
			System.exit(1);
		}
	}
}

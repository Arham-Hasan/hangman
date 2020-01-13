/*Arham Hasan
 * Mr.Hofer
 * Culminating
 * Hangman game
 * either play single player mode and guess a word by entering one chatracter at a time
 or play multiplayer : one player enters the target word and a hint and the second player guesses the word by
 entering one character at a time
 */
import java.util.Scanner;
import java.util.*;

public class Hangman {
//this method creates an array with the target word and a hint. The parameters are the the target word and he hint
  //returns array
  public static String[] getTarget(String word, String info){
    String[] target= new String[] {word,info};
    return target;
  }
  // this method replaces the character in the target word with dashes and saves it as a seperate string 'unknown'
  //the parameter is the target word
  //returns a string with all dashes
  public static String dashes(String target){
    String hidden=target;
    for(int x=0;x<target.length();x++){
      if(target.charAt(x)!=' '){
        hidden=hidden.replace(target.charAt(x),'-');
      }   
    }
    return hidden;
  }
  
  //this method compares a guess character added by the user with each charcter in the target word
  // if the character is in the word, it replaces the dash in the unknown string with the guess character
  // the parameter is the target word, dashes string and the guess character
  //returns a string
  public static String guessChar(String target,String unknown,char guess){
    String result=unknown;
    for(int x=0;x<target.length();x++){
      if(guess==target.charAt(x)){
        result=result.substring(0, x) + guess + result.substring(x+1);
      }
    }
    return result;
  }
  //Checks if the guess character is present in the target word
  //parameter is the target word and guess character
  //returns a boolean
  public static boolean checking(String target,String unknown,char guess){
    String result=unknown;
    boolean ans=false;
    for(int x=0;x<target.length();x++){
      if(guess==target.charAt(x)){
        ans=true;
      }
    }
    return ans;
  }
  //checks if you won the game by checking if any dashes are left in the hidden string (with dashes)
  // the string with dashes is the parameter
  //returns a boolean
  public static boolean gameWon(String unknown){
    boolean ans=true;
    for(int x=0;x<unknown.length();x++){
      if('-'==unknown.charAt(x)){
        ans=false;
      }
    }
    return ans;
  }
  //checks if the character you entered has been entered before or not
  // parameter is the guess char and the array with all the used chars stored in it
  //returns a boolean
  public static boolean repeatChar(char guessChar,char[] usedChar){
    boolean result= false;
    for(int x=0;x<usedChar.length;x++){
      if(guessChar==usedChar[x])
        result=true;
    }
    return result;
  }
  //makes sure all the characters enetered are alphabets
  //parameter is a string 
  //returns a boolean
  public static boolean alphaValid(String target){
    boolean result=false;
    for(int x=0;x<target.length();x++){
      if((target.charAt(x)>'z' || target.charAt(x)<'a') & target.charAt(x)!=' ')
        result=true;
    }
    return result;
  }
  
  //makes sure the user only enters one character
  //parameter is a string
  //returns a boolean
  public static boolean oneCharValid(String guess){
    boolean result=false;
    if(guess.length()>1 || guess.length()==0)
      result=true;
    return result;
  }
  //makes sure the user did not only enter spaces
  //parameter is a spring
  //returns a boolean
  public static boolean onlySpace(String entry){
    boolean result=true;
    for(int x=0;x<entry.length();x++){
      if(entry.charAt(x)!=' ')
        result=false;
    }
    return result;  
  }
  //prints all characetrs entered
  //parameter is the array with chars eneterd and a couter
  //void method
  public static void printChar(char[] chars,int counter){
    System.out.print("\nCharacetrs enetered: ");
    for(int x=0;x<=counter;x++){
      System.out.printf("%c, ",chars[x]);
    }
    System.out.println("");
  }
  
  
  public static void main(String[] args) {
    //stored target words with hints for single player mode
    String[][] database={{"argentina","country"},{"portugal","country"},{"south africa","country"},{"saudia arabia","country"},{"paraguay","country"},
      {"istanbul","city"},{"new york","city"},{"amsterdam","city"},{"munich","city"},{"new delhi","city"},
      {"cricket","sport"},{"soccer","sport"},{"badminton","sport"},{"ice hockey","sport"},{"basketball","sport"},
      {"pineapple","fruit"},{"watermelon","fruit"},{"strawberry","fruit"},{"blueberry","fruit"},{"orange","fruit"}};
    //declaring some variables
    int mode=0; // this int will be used to choose the game mode
    int random=0;// random int used to slect which word to pick for the target word in single player mode
    String unknown="";// this string will store the hidden version of the target word. Example for "abc" hidden is "---"
    String[] myWord = new String[2];// this array will store the target word and its hint
    String word=""; // the user will enter this string as the target word
    System.out.println("Enter 1 to play single player mode and enter 2 to play multiplayer mode");
    Scanner input = new Scanner(System.in);
    Scanner entry = new Scanner(System.in);
    //--------------------------------------------------------------
    do{ 
      try{
        mode=entry.nextInt();                  //the scanner is an integer so used exceptions to catch errors
      } catch(InputMismatchException e){
        System.out.println("\nInvalid entry");
        entry.next();
      }
      if(mode!=1 && mode!=2)                 // only want 1 or 2
        System.out.println("\nMake sure you enter 1 or 2");
    }while(mode!=1 && mode!=2);
    
    if(mode==2){ // multiplayer mode
      System.out.printf("Enter the target Word(Only use alphabets and keep in mind : The game is not case sensitive) %n");
      do{
        word =input.nextLine().toLowerCase(); // get target word and make it lowercase
        if(alphaValid(word) || onlySpace(word)) // validation methods
          System.out.printf("%nInvalid Only use alphabets in the target word and make sure you dont leave it blank%n");
      }while(alphaValid(word) || onlySpace(word)); // validation methods
      System.out.printf("Enter a hint for the user(Example City, Country, Movie etc...) %n");
      String info =input.nextLine();
      myWord=getTarget(word,info);       // create an array by the information entered by the user
      System.out.printf("%nHint: %s%n",myWord[1]);
      unknown=dashes(myWord[0]);        // creates the hidden version of the target word
    }
    
    else if(mode==1){ // single player mode
      random=(int)(Math.random() * 20); // get a random int from 0-19
      myWord=getTarget(database[random][0],database[random][1]); // store an array from the database into the my word array
      System.out.printf("%nHint: %s%n",myWord[1]); // print out the hint
      unknown=dashes(myWord[0]); // store the hidden version of the target word
    }
    System.out.println("\nYou have 6 chances to guess the word");
    System.out.printf("%n%s%n",unknown); // print hidden version
    //-----------------------------------------
    int chances=6;// chances the user has
    char[] storedChar=new char[26]; // an array to store the characters added by the user
    int charCounter=0; // counter used to store chars in array
    String guess=" "; // this string will be used to get the character the user guesses
    char guessChar='a'; // declare the guess char
    int counter=0; // counter used to print out the characters previosly entered by the user
    
    do{
      System.out.printf("%nEnter a character %n");
      do{
        do{
          guess =input.nextLine().toLowerCase(); // get input and make it lowercase
          if(oneCharValid(guess) || alphaValid(guess) || onlySpace(guess)) // make sure input is valid and only one char
            System.out.printf("%nInvalid! make sure you only enter one character which can only be an alphabet%n");
        } while(oneCharValid(guess) || alphaValid(guess) || onlySpace(guess));
        guessChar=guess.charAt(0); // getting char from string
        if(repeatChar(guessChar,storedChar)){ // checking that char is entered for the first time
          System.out.printf("%nYou have already entered this character%n %s %n%n",unknown);
          printChar(storedChar,counter); //method used to print all the used chars
        }
      } while(repeatChar(guessChar,storedChar));
      unknown=guessChar(myWord[0],unknown,guessChar); //display hidden version with the correctly guessed chars in proper position
      storedChar[charCounter]=guessChar; // adding char to array of stored chars
      if(checking(myWord[0],unknown,guessChar)){ // checking if char is present in the target word
        System.out.printf("%ncorrect%n");
        if((gameWon(unknown))==false)
          System.out.printf("%nyou have %d chances left%n",chances);
      }
      else{
        System.out.printf("%nincorrect%n");
        chances--; // lose a chance if the char is not in the target word
        System.out.printf("%nyou have %d chances left%n",chances);
      }
      if((chances>0) && (!gameWon(unknown))){
        System.out.printf("%n%s%n",unknown);
        printChar(storedChar,counter);
      }
      counter++;           // increase both chars by 1
      charCounter++;
//------------------------------------------------------------------------      
      
    }while(chances>0 && gameWon(unknown)==false);
    if(chances==0){ // 0 chances mean u lost
      System.out.printf("%n%nyou lost :( %nThe correct word was %s%n",myWord[0]);
    }
    else{
      System.out.printf("%n%nCongratulations you won :)");
    }
  }  
}

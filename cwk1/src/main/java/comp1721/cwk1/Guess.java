package comp1721.cwk1;

import java.util.Scanner;


public class Guess {
  // Use this to get player input in readFromPlayer()
  private static final Scanner INPUT = new Scanner(System.in);
  private int guessNumber;
  private String chose_Word = null;
  private boolean visual;
  // TODO: Implement constructor with int parameter
  public Guess(int i) {
    if (i < 1 || i > 6) {
      throw new GameException("Wrong number!");
    }
    readFromPlayer();
    guessNumber = i;
  }
  // TODO: Implement constructor with int and String parameters
  public Guess (int number, String a) {
    if (a.length() != 5) {
      throw new GameException("Wrong word length.");
    }
    for (char temp : a.toCharArray()) {
      if (!Character.isLetter(temp)) {
        throw new GameException("Not a letter");
      }
    }
    if (number < 1 || number > 6) {
      throw new GameException("Wrong guess number.");
    }
    guessNumber = number;
    chose_Word = a.toUpperCase();
  }
  // TODO: Implement getGuessNumber(), returning an int
  public int getGuessNumber()
  {
    return guessNumber;
  }
  // TODO: Implement getChosenWord(), returning a String
  public String getChosenWord() {
    return chose_Word;
  }
  // TODO: Implement readFromPlayer()
  public void readFromPlayer() {
    chose_Word = INPUT.next();
    if (chose_Word.length() != 5) {
      throw new GameException("Wrong length.");
    }
    chose_Word = chose_Word.toUpperCase();
  }
  // TODO: Implement compareWith(), giving it a String parameter and String return type
  public String compareWith(String input){
    int i;
    int j;
    char x = 'y';
    chose_Word = chose_Word.toUpperCase();
    char[] word = chose_Word.toCharArray();
    char[] answer = input.toCharArray();
    char[] judgement = {'b', 'b', 'b', 'b', 'b'};
    String output = "";
    String green = "";
    String yellow = "";
    for (i=0;i< word.length;i++){
      for (j=0;j< answer.length;j++)
      {
        if(answer[j] == word[i] && i==j) {
          judgement[i] = 'g';
        }
        else if(answer[j] == word[i] && i!=j) {
          judgement[i] = 'y';
        }
      }
    }
    for(i = 0; i<word.length; i++) {
      if (judgement[i] == 'y') {
        for (j = 0; j < i; j++) {
          if (word[j] == word[i]) {
            x = 'b';
          }
        }
        if (x == 'y') {
          output += "\033[30;103m " + word[i] + " \033[0m";
        }
        if (x == 'b') {
          output += "\033[30;107m " + word[i] + " \033[0m";
        }
      }
      if(judgement[i] == 'g') {
        output += "\033[30;102m " + word[i] + " \033[0m";
      }
      if(judgement[i] == 'b') {
        output += "\033[30;107m " + word[i]+ " \033[0m";
      }
    }
      if(Wordle.begin == 1){
      if(judgement[0] == 'g') {
        green += "1st ";
      }else if(judgement[0] == 'y'){
        yellow += "1st ";
      }
      if(judgement[1] == 'g') {
        green += "2nd ";
      }else if(judgement[1] == 'y'){
        yellow += "2nd ";
      }
      if(judgement[2] == 'g') {
        green += "3rd ";
      }else if(judgement[2] == 'y'){
        yellow += "3rd ";
      }
      if(judgement[3] == 'g') {
        green += "4th ";
      }else if(judgement[3] == 'y'){
        yellow += "4th ";
      }
      if(judgement[4] == 'g') {
        green += "5th ";
      }else if(judgement[4] == 'y'){
        yellow += "5th ";
      }
      if (!green.equals("") && !yellow.equals("")) {
        output = yellow + "correct but in wrong place," + green + "perfect";
      }else if (green.equals("") && !yellow.equals("")) {
        output = yellow + "correct but in wrong place";
      }else if (!green.equals("")) {
        output = green + "perfect";
      }else {
        output = "nothing right!";
      }
    }
    return output;
  }
  // TODO: Implement matches(), giving it a String parameter and boolean return type
  public boolean matches(String answer) {return chose_Word.equals(answer);}
  // TODO: Additional features: Color blindness mode
  public void Color_blind (boolean color) { visual = color; }
}

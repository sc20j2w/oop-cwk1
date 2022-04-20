package comp1721.cwk1;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private int gameNumber;
    private int guess_time = 1;
    private String target;
    private List<String> user_guess = new ArrayList<>();
    private StringBuilder result = new StringBuilder();
    private boolean color;
  // TODO: Implement constructor with String parameter
  public Game(String words) throws IOException{
      WordList wordlist = new WordList(words);
      LocalDate today = LocalDate.now();
      LocalDate first = LocalDate.of(2021, 6, 19);
      gameNumber = (int) (today.toEpochDay() - first.toEpochDay());
      target = wordlist.getWord(gameNumber);
  }
  // TODO: Implement constructor with int and String parameters
  public Game(int number, String words) throws IOException{
      gameNumber = number;
      WordList wordlist = new WordList(words);
      target = wordlist.getWord(gameNumber);
  }
  // TODO: Judge if user need Color blindness mode
  public void Color_blind (boolean vis) { color = vis; }
  // TODO: Implement play() method
  public void play() {
      Guess guess;
      int i;
      String temp;
      for (i = 0; i <= 5; i++) {
          System.out.printf("Enter your guess (%d/6)", guess_time);
          guess_time += 1;
          guess = new Guess(i + 1);
          user_guess.add(guess.getChosenWord());
          temp = guess.compareWith(target);
          if (temp.equals("")) {
              temp += "Nothing correct.";
          }
          result.append(temp);
          result.append("\n");
          guess.Color_blind(color);
          if(color) {
              if (guess.matches(target)) {
                  System.out.println("You won!");
                  break;
              }
              else {
                  System.out.println(temp);
              }
          }else{
              System.out.println(temp);
              if (guess.matches(target)) {
                  if (i == 5) {
                      System.out.println("That was a close call!");
                      break;
                  } else if (i == 0) {
                      System.out.println("Superb - Got it in one!");
                      break;
                  } else {
                      System.out.println("Well done!");
                      break;
                  }
              }else if(i == 5){
                  System.out.println("Sorry, you didn't guess the word correctly.");
                  break;
              }
          }
      }
  }

  // TODO: Implement save() method, with a String parameter
  public void save(String file) throws IOException {
      File fd = new File(file);
      if (!fd.exists()) {
          fd.createNewFile();
      }
      FileWriter output = new FileWriter(file);
      output.write(result.toString());
      output.close();
  }
}

// Main program for COMP1721 Coursework 1
// DO NOT CHANGE THIS!

package comp1721.cwk1;

import java.io.IOException;


public class Wordle {
  public static int begin;
  public static void main(String[] args) throws IOException {
    Game game = null;
    if (args.length > 0) {
      if (args[0].equals("-a")){
        if (args.length > 1){
          {begin = 1;}
          // Player wants to specify the game
          game = new Game(Integer.parseInt(args[1]), "data/words.txt");
        }else {
          // Play today's game
          game = new Game("data/words.txt");
        }
        game.Color_blind(true);
      }
      else{
        // Player wants to specify the game
        game = new Game(Integer.parseInt(args[0]), "data/words.txt");
        game.Color_blind(false);
      }
    }else {
      // Play today's game
      game = new Game("data/words.txt");
      game.Color_blind(false);
    }

    game.play();
    game.save("build/lastgame.txt");
  }
}

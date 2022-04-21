package comp1721.cwk1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordList {
  // TODO: Implement constructor with a String parameter
  private List<String> test = new ArrayList<>();
  public WordList(String filename) throws IOException {
      File fd = new File(filename);
      if (!fd.exists()) {
          throw new FileNotFoundException("File not found.");
      }else{
          BufferedReader words = null;
          words = new BufferedReader(new FileReader(fd));
          String reader = null;
          while(true) {
              reader = words.readLine();
              if (reader == null) {
                  break;
              }else {
                  test.add(reader);
              }
            }
        }
    }

  // TODO: Implement size() method, returning an int
  public int size() { return test.size(); }

  // TODO: Implement getWord() with an int parameter, returning a String
  public String getWord(int i) throws GameException{
      if (i < 0 || i >= test.size()) {
          throw new GameException("Unsuccessful choices.");
      }
      return test.get(i);
  }
}

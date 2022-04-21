package comp1721.cwk1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Game {
    private int gameNumber;
    private int guesstime = 1;
    private int wingame = 1;
    private double winnum = 0;
    private double allnum = 0;
    private int nowstreak = 0;
    private int maxstreak = 0;
    private String target;
    private String readin;
    private List<String> userguess = new ArrayList<>();
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
  public void colorblind (boolean vis) { color = vis; }
  // TODO: Implement play() method
  public void play() {
      Guess guess;
      int i;
      String temp;
      System.out.printf("WORDLE    %d \n\n", gameNumber);
      for (i = 0; i <= 5; i++) {
          System.out.printf("Enter your guess (%d/6)", guesstime);
          guesstime += 1;
          guess = new Guess(i + 1);
          userguess.add(guess.getChosenWord());
          temp = guess.compareWith(target);
          result.append(temp);
          result.append("\n");
          guess.colorblind (color);
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
                  wingame = 0;
                  System.out.println("Sorry, you didn't guess the word correctly.");
                  System.out.println(target);
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
      FileWriter output = new FileWriter(fd);
      output.write(result.toString());
      output.close();
  }

  // TODO: read before save
  public void reader(String filename) throws IOException {
      File fh = new File(filename);
      if (!fh.exists()) {
          fh.createNewFile();
          return;
      }
  }

  // TODO: save new game
  public void record(String filename) throws IOException {
      int q;
      q = guesstime - 1;
      File fh = new File(filename);
      if (!fh.exists()) {
          fh.createNewFile();
      }
      FileWriter previous = new FileWriter(fh, true);
      previous.write("Game number: " + gameNumber + "\n");
      previous.write("Target word: " + target + "\n");
      previous.write("The number of times user guess: " + q + "\n");
      if (wingame == 1) {
          previous.write("win\n");
      }else {
          previous.write("lose\n");
      }
      previous.close();
      reader(filename);
      BufferedReader load = new BufferedReader(new FileReader(filename));
      while(true)
      {
          readin = load.readLine();
          if(readin == null)
          {
              break;
          }
          allnum++;
          readin = load.readLine();
          readin = load.readLine();
          readin = load.readLine();
          if(readin.equals("win"))
          {
              winnum += 1;
              nowstreak += 1;
              if(maxstreak <= nowstreak)
              {
                  maxstreak = nowstreak;
              }
          }else{
              nowstreak = 0;
              readin = load.readLine();
          }
      }
      System.out.printf("Percentage of games that were wins: %.0f%% \n", winnum / allnum * 100);
      System.out.printf("Length of the current winning streak: %d\n", nowstreak);
      System.out.printf("Longest winning streak: %d\n", maxstreak);
      chart();
  }

  // TODO: create a chart
  public void chart(){
      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      dataset.setValue(winnum, "win", "win");
      dataset.setValue(allnum-winnum, "loss", "lose");
      JFreeChart chart = ChartFactory.createBarChart("Guess distribution", "result", "Number",
              dataset, PlotOrientation.VERTICAL, true, true, false);
      // 解决中文乱码
      CategoryPlot plot = chart.getCategoryPlot();
      CategoryAxis domainAxis = plot.getDomainAxis();
      NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
      TextTitle textTitle = chart.getTitle();
      textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
      domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
      domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
      numberAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
      numberAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));

      chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

      try {
          ChartFrame cf = new ChartFrame("Bar chart", chart);
          cf.pack();
          cf.setSize(800, 600);
          cf.setVisible(true);
      } catch (Exception e) {
          System.err.println("Problem occurred creating chart.");
      }
  }
}

package oolala.model.OolalaModels;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Logo Model
 *
 * @author Kyle White
 */
class OolalaModelLogoTest {

  private OolalaModelLogo myTestInstance;

  @BeforeEach
  void setupForEachTest() {
    myTestInstance = new OolalaModelLogo("English");
  }

  @Test
  void parseBlockTest() throws IOException {
    parseFile("data/examples/logo/dash_line.txt", 25);
    parseFile("data/examples/logo/forward.txt", 1);
    parseFile("data/examples/logo/grid.txt", 29);
    parseFile("data/examples/logo/square.txt", 8);
    parseFile("data/examples/logo/square_centered.txt", 15);
    parseFile("data/examples/logo/triangle.txt", 7);
  }

  private void parseFile(String filename, int size) throws IOException {
    Path path = Paths.get(filename);
    String block = Files.readString(path, StandardCharsets.US_ASCII);
    Queue<TurtleMethod> queue = myTestInstance.parseBlock(block);
    assertEquals(size, queue.size());
  }

  @Test
  void updateHistoryTest() {
    Queue<TurtleMethod> first = new LinkedList<>();
    myTestInstance.updateHistory(first);
    assertEquals(first, myTestInstance.getHistory());
    Queue<TurtleMethod> second = new LinkedList<>();
    second.add(new TurtleMethod("PU"));
    myTestInstance.updateHistory(second);
    assertEquals(myTestInstance.getHistory(), second);
  }

}
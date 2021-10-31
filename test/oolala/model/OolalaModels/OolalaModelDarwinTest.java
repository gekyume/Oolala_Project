package oolala.model.OolalaModels;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Darwin Model
 *
 * @author Kyle White
 */
class OolalaModelDarwinTest {

  private OolalaModelDarwin myTestInstance;

  @BeforeEach
  void setupForEachTest() {
    myTestInstance = new OolalaModelDarwin("English");
  }

  @Test
  void parseBlockTest() throws IOException {
    parseFile("data/examples/darwin/creeper.txt", 45);
    parseFile("data/examples/darwin/flytrap.txt", 5);
    parseFile("data/examples/darwin/hopper.txt", 2);
    parseFile("data/examples/darwin/landmine.txt", 6);
    parseFile("data/examples/darwin/oodfay.txt", 2);
    parseFile("data/examples/darwin/rover.txt", 12);
  }

  private void parseFile(String filename, int size) throws IOException {
    Path path = Paths.get(filename);
    String block = Files.readString(path, StandardCharsets.US_ASCII);
    Queue<TurtleMethod> queue = myTestInstance.parseBlock(block);
    assertEquals(size, queue.size());
  }

}
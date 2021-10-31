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
 * Test class for the LSytem Model
 *
 * @author Kyle White
 */
class OolalaModelLSystemTest {

  private OolalaModelLSystem myTestInstance;

  @BeforeEach
  void setupForEachTest() {
    myTestInstance = new OolalaModelLSystem("English");
  }

  @Test
  void parseBlockTest() throws IOException {
    parseFile("data/examples/lsystem/koch_curve.txt", 14);
    parseFile("data/examples/lsystem/koch_snowflake.txt", 12);
    parseFile("data/examples/lsystem/sierpinski.txt", 8);
    parseFile("data/examples/lsystem/sierpinski2.txt", 8);
  }

  private void parseFile(String filename, int size) throws IOException {
    Path path = Paths.get(filename);
    String block = Files.readString(path, StandardCharsets.US_ASCII);
    myTestInstance.setLoops(1);
    Queue<TurtleMethod> queue = myTestInstance.parseBlock(block);
    assertEquals(size, queue.size());
  }
}
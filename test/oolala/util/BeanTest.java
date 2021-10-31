package oolala.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.Practice1;
import util.Practice2;

class BeanTest {

  @Test
  void GIVEN_oneBeanType_WHEN_beanConstructed_AND_set_THEN_followProperly() {
    Practice1 practice1 = new Practice1();
    Practice2 practice2 = new Practice2(practice1);
    assertEquals(practice1.getCoordinates(), new ArrayList<>(List.of(1, 2)));
    assertEquals(practice2.getCoordinates(), new ArrayList<>(List.of(0, 3)));
    practice1.move(10);
    assertEquals(practice1.getCoordinates(), new ArrayList<>(List.of(11, 2)));
    assertEquals(practice2.getCoordinates(), new ArrayList<>(List.of(11, 2)));
    assertEquals(practice1.getCoordinates(), practice2.getCoordinates());
  }

  @Test
  void GIVEN_anotherBeanType_WHEN_beanConstructed_AND_set_THEN_followProperly() {
    Practice1 practice1 = new Practice1();
    Practice2 practice2 = new Practice2(practice1);
    assertEquals(practice1.getRotation(), 0);
    assertEquals(practice2.getRotation(), 7);
    practice1.rotate(33);
    assertEquals(practice1.getRotation(), 33);
    assertEquals(practice2.getRotation(), 33);
    assertEquals(practice2.getRotation(), practice1.getRotation());
  }

  @Test
  void GIVEN_twoBeans_WHEN_beansConstructed_AND_set_THEN_followBothSeparately() {
    Practice1 practice1 = new Practice1();
    Practice2 practice2 = new Practice2(practice1);
    practice1.rotate(45);
    practice1.move(10);
    assertEquals(practice1.getRotation(), practice2.getRotation());
    assertEquals(practice2.getCoordinates(), practice1.getCoordinates());
    assertEquals(practice1.getCoordinates(), new ArrayList<>(List.of(6, 10)));
    assertEquals(practice1.getRotation(), 45);
  }

  @Test
  void GIVEN_sameTypeOfBeans_WHEN_constructedSeparately_THEN_onlyFollowCorrectBean() {
    Practice1 practice1 = new Practice1();
    Practice2 practice2 = new Practice2(practice1);
    Practice1 practice3 = new Practice1();
    Practice2 practice4 = new Practice2(practice3);
    practice1.rotate(45);
    practice1.move(10);
    assertEquals(practice1.getRotation(), practice2.getRotation());
    assertEquals(practice2.getCoordinates(), practice1.getCoordinates());
    assertEquals(practice1.getCoordinates(), new ArrayList<>(List.of(6, 10)));
    assertEquals(practice1.getRotation(), 45);
    assertEquals(practice3.getRotation(), 0);
    assertEquals(practice4.getRotation(), 7);
    assertEquals(practice3.getCoordinates(), new ArrayList<>(List.of(1, 2)));
    assertEquals(practice4.getCoordinates(), new ArrayList<>(List.of(0, 3)));
  }
}
package tests;

import model.Pila;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PilaTest {
    Pila pila;

    @Nested
    @DisplayName("Push Tests")
    class PushTest {

        @BeforeEach
        void setUp() {
            pila = new Pila();
        }

        @Test
        @DisplayName("Pila vacía y elemento cumple condición")
        void isEmptyAndElementInRange() {
            assertTrue(pila.isEmpty());
            pila.push(10);
            assertTrue(!pila.isEmpty() && pila.top() == 10);
            System.out.println(pila);
        }

        @Test
        @DisplayName("Pila vacía y elemento no cumple condición")
        void isEmptyAndElementNotInRange() {
            assertTrue(pila.isEmpty());

            pila.push(1);
            assertTrue(pila.isEmpty());

            pila.push(30);
            assertTrue(pila.isEmpty());

            System.out.println(pila);
        }

        @Test
        @DisplayName("Pila no vacía y elemento cumple condición")
        void isNotEmptyAndElementInRange() {
            pila.push(new Integer[]{5, 8});
            int initialSize = pila.size();

            pila.push(10);
            assertEquals(initialSize + 1, pila.size());

            System.out.println(pila);
        }

        @Test
        @DisplayName("Pila no vacía y elemento no cumple condición")
        void isNotEmptyAndElementNotInRange() {
            pila.push(new Integer[]{5, 8});
            int initialSize = pila.size();

            pila.push(1);
            assertEquals(initialSize, pila.size());

            pila.push(30);
            assertEquals(initialSize, pila.size());

            System.out.println(pila);
        }

        @Test
        @DisplayName("El dato es de tipo Integer")
        void isInteger() {
            Integer num = 10;
            assertInstanceOf(Integer.class, num);
            pila.push(num);
            assertFalse(pila.isEmpty());

            Exception exception = assertThrows(NullPointerException.class, () -> {
                Integer numToPush = null;
                pila.push(numToPush);
            });
            System.out.println(exception.getClass());
        }
    }

    @Nested
    @DisplayName("Pop Tests")
    class PopTest {

        @BeforeEach
        void setUp() {
            pila = new Pila();
        }

        @Test
        @DisplayName("La pila está vacía")
        void isEmpty() {
            Integer num = pila.pop();
            assertTrue(num == null);
            System.out.println(num);
        }

        @Test
        @DisplayName("La pila no está vacía")
        void isNotEmpty() {
            pila.push(new Integer[]{5, 8});
            int initialSize = pila.size();
            Integer num = pila.pop();

            assertTrue(num == 8 && pila.size() == initialSize - 1);
            System.out.println(num);
        }
    }

    @Nested
    @DisplayName("isEmpty Tests")
    class IsEmptyTest {

        @BeforeEach
        void setUp() {
            pila = new Pila();
        }

        @Test
        @DisplayName("La pila está vacía")
        void isEmpty() {
            assertTrue(pila.isEmpty());
            System.out.println(pila.isEmpty());
        }

        @Test
        @DisplayName("La pila no está vacía")
        void isNotEmpty() {
            pila.push(new Integer[]{5, 8});
            assertFalse(pila.isEmpty());
            System.out.println(pila.isEmpty());
        }
    }

    @Nested
    @DisplayName("Top Tests")
    class TopTest {

        @BeforeEach
        void setUp() {
            pila = new Pila();
        }

        @Test
        @DisplayName("La pila está vacía")
        void isEmpty(){
            Integer num = pila.top();
            assertTrue(num == null);
            System.out.println(num);
        }

        @Test
        @DisplayName("La pila no está vacía")
        void isNotEmpty(){
            pila.push(new Integer[]{5, 8});
            Integer num = pila.top();
            assertTrue(num == 8);
            System.out.println(num);
        }
    }
}

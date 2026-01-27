import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FactorialTest {

    @Test
    void testFactorialZero() {
        assertEquals(1, Factorial.factorial(0));
    }

    @Test
    void testFactorialOne() {
        assertEquals(1, Factorial.factorial(1));
    }

    @Test
    void testFactorialFive() {
        assertEquals(120, Factorial.factorial(5));
    }

    @Test
    void testFactorialTen() {
        assertEquals(3628800, Factorial.factorial(10));
    }

    @Test
    void testNegativeNumber() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> Factorial.factorial(-3)
        );

        assertEquals("Le nombre doit être positif !", exception.getMessage());
    }
}

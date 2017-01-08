import factorial.FactorialFXMLController;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Klasa <code>FactorialTests</code> reprezentuje zestaw testów sprawdzających 
 * poprawność obliczeń silni iteracyjnie i rekurencyjnie, dla różnych możliwych 
 * przypadków. 
 * @author AleksanderSklorz 
 */
public class FactorialTests {
    private BigInteger iterationResult, recursionResult;
    /**
     * Test sprawdzający poprawność obliczenia silni iteracyjnie dla liczby dodatniej. 
     */
    @Before
    @Test
    public void factorialIterativelyTest(){
        try{
            iterationResult = FactorialFXMLController.factorialIteratively(BigInteger.valueOf(4), null);
            assertEquals(BigInteger.valueOf(24), iterationResult);
        }catch(InterruptedException e){
            Logger.getLogger(FactorialTests.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Test sprawdzający poprawność obliczenia silni iteracyjnie dla zera. 
     */
    @Test
    public void factorialIterativelyZeroTest(){
        try{
            assertEquals(BigInteger.ONE, FactorialFXMLController.factorialIteratively(BigInteger.ZERO, null));
        }catch(InterruptedException e){
            Logger.getLogger(FactorialTests.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Test sprawdzający poprawność obliczenia silni rekurencyjnie dla liczby dodatniej. 
     */
    @Before
    @Test
    public void factorialRecursivelyTest(){
        try{
            recursionResult = FactorialFXMLController.factorialRecursively(BigInteger.valueOf(4), null);
            assertEquals(BigInteger.valueOf(24), recursionResult);
        }catch(InterruptedException e){
            Logger.getLogger(FactorialTests.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Test sprawdzający poprawność obliczenia silni rekurencyjnie dla zera. 
     */
    @Test
    public void factorialRecursivelyZeroTest(){
        try{
            assertEquals(BigInteger.ONE, FactorialFXMLController.factorialRecursively(BigInteger.ZERO, null));
        }catch(InterruptedException e){
            Logger.getLogger(FactorialTests.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Test sprawdzający czy wynik ze sposobu iteracyjnego jest równy wynikowi ze sposobu rekurencyjnego. 
     */
    @Test
    public void iterationEqualRecursionTest(){
        assertEquals(iterationResult, recursionResult);
    }
}

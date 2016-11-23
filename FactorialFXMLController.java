package factorial;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Klasa <code>FactorialFXMLController</code> reprezentuje sterowanie programu 
 * porównującego dwa sposoby obliczania silnii - rekurencyjnie oraz iteracyjnie. 
 * Program porównuje ich wyniki oraz czasy wykonań mierzone w sekundach.
 * Każdy sposób oblicza wartość silnii w osobnym wątku. Pogram posiada też możliwość 
 * przerwania obliczeń w przypadku gdy wykonywane są zbyt długo. W tym celu wątki 
 * obiczające zostały celowo opóźnione, aby dać czas na kliknięcie przycisku Zatrzymaj,
 * gdyz algorytmy te wykonywały się zbyt szybko. Program zabezpiecza podanie 
 * innej wartości niż liczba całkowita oraz dla rekursji przed podaniem większej 
 * liczby niż 10000, aby nie doszło do przepełnienia stosu. 
 * @author AleksanderSklorz
 */
public class FactorialFXMLController implements Initializable {
    private Thread iterationThread, recursionThread;
    @FXML
    private Button startButton, stopButton, exitButton;
    @FXML
    private TextField factorialTextField, iterationTextField, recursionTextField, iterationTimeTextField, recursionTimeTextField;
    @FXML
    private void startAction(ActionEvent event) {
        iterationTextField.setText("");
        iterationTimeTextField.setText("");
        recursionTextField.setText("");
        recursionTimeTextField.setText("");
        if(factorialTextField.getText().matches("[0-9]+")){
            startButton.setDisable(true);
            stopButton.setDisable(false);
            iterationThread = new Thread(new Runnable() {
                public void run(){
                    long before = System.currentTimeMillis();
                    BigInteger number = new BigInteger(factorialTextField.getText());
                    try{
                        iterationTextField.setText(factorialIteratively(number, this).toString());
                        iterationTimeTextField.setText(String.valueOf((double)(System.currentTimeMillis() - before)/1000) + " s");
                        startButton.setDisable(false);
                        stopButton.setDisable(true);
                    }catch(InterruptedException e){
                        Logger.getLogger(FactorialFXMLController.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            });
            recursionThread = new Thread(new Runnable() {
                public void run(){
                    long before = System.currentTimeMillis();
                    BigInteger number = new BigInteger(factorialTextField.getText());
                    if(number.compareTo(new BigInteger("10000")) <= 0){
                        try{
                            recursionTextField.setText(factorialRecursively(number, this).toString());
                            recursionTimeTextField.setText(String.valueOf((double)(System.currentTimeMillis() - before)/1000) + " s");
                            startButton.setDisable(false);
                            stopButton.setDisable(true);
                        }catch(InterruptedException e){
                            Logger.getLogger(FactorialFXMLController.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }else recursionTextField.setText("Za duża liczba");
                }
            });
            iterationThread.start(); 
            recursionThread.start();
        }else{
            Alert formatAlert = new Alert(AlertType.INFORMATION);
            formatAlert.setTitle("Zła liczba");
            formatAlert.setHeaderText("Podaj poprawny format liczby całkowitej");
            formatAlert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stopButton.setDisable(true);
        stopButton.setOnAction(event -> {
           iterationThread.interrupt();
           recursionThread.interrupt();
           startButton.setDisable(false);
           stopButton.setDisable(true);
        });
        exitButton.setOnAction(event -> {
            System.exit(0);
        });
    }    
    private BigInteger factorialIteratively(BigInteger number, Runnable r) throws InterruptedException{
        BigInteger product = BigInteger.ONE;
        for(BigInteger i = BigInteger.ONE; i.compareTo(number) <= 0; i = i.add(BigInteger.ONE)){
            product = product.multiply(i);
            synchronized(r){
                r.wait(200);
            }
        }
        return product;
    }
    private BigInteger factorialRecursively(BigInteger number, Runnable r) throws InterruptedException{
        synchronized(r){
            r.wait(200);
        }
        if(number.equals(BigInteger.ZERO)) return BigInteger.ONE;
        return number.multiply(factorialRecursively(number.subtract(BigInteger.ONE), r));
    }
}

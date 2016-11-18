package factorial;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        startButton.setDisable(true);
        stopButton.setDisable(false);
        iterationThread = new Thread(new Runnable() {
            public void run(){
                long before = System.currentTimeMillis();
                BigInteger number = new BigInteger(factorialTextField.getText());
                try{
                    iterationTextField.setText(factorialIteratively(number, this).toString());
                    iterationTimeTextField.setText(String.valueOf((double)(System.currentTimeMillis() - before)/1000) + " s");
                    if(recursionThread.getState().equals(Thread.State.TERMINATED)){
                        startButton.setDisable(false);
                        stopButton.setDisable(true);
                    }
                }catch(InterruptedException e){
                    Logger.getLogger(FactorialFXMLController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
        recursionThread = new Thread(new Runnable() {
            public void run(){
                long before = System.currentTimeMillis();
                BigInteger number = new BigInteger(factorialTextField.getText());
                try{
                    recursionTextField.setText(factorialRecursively(number, this).toString());
                    recursionTimeTextField.setText(String.valueOf((double)(System.currentTimeMillis() - before)/1000) + " s");
                    if(iterationThread.getState().equals(Thread.State.TERMINATED)){
                        startButton.setDisable(false);
                        stopButton.setDisable(true);
                    }
                }catch(InterruptedException e){
                    Logger.getLogger(FactorialFXMLController.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
        iterationThread.start(); 
        recursionThread.start();
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

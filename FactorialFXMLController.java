package factorial;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
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
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startButton.setOnAction(event -> {
            //iterationThread = new Thread(() -> {
                //long before = System.currentTimeMillis();
              //  BigInteger number = new BigInteger(factorialTextField.getText());
              //  iterationTextField.setText(factorialIteratively(number).toString());
                //iterationTimeTextField.setText(String.valueOf(System.currentTimeMillis() - before));
           // });
            //recursionThread = new Thread(() -> {
                //long before = System.currentTimeMillis();
              // BigInteger number = new BigInteger(factorialTextField.getText());
               // recursionTextField.setText(factorialRecursively(number).toString());
                //recursionTimeTextField.setText(String.valueOf(System.currentTimeMillis() - before));
           // });
            iterationThread = new Thread(new Watek1());
            recursionThread = new Thread(new Watek2());
            iterationThread.start(); 
            recursionThread.start();
        });
        //stopButton.setOnAction(event -> {
        //   iterationThread.interrupt();
        //   recursionThread.interrupt();
        //});
        exitButton.setOnAction(event -> {
            System.exit(0);
        });
    }    
    private BigInteger factorialIteratively(BigInteger number){
        System.out.println(number.toString());
        BigInteger product = BigInteger.ONE;
        for(BigInteger i = BigInteger.ONE; i.compareTo(number) <= 0; i = i.add(BigInteger.ONE)){
            product = product.multiply(i);
        }
        System.out.println(product.toString());
        return product;
    }
    private BigInteger factorialRecursively(BigInteger number){
        if(number.equals(BigInteger.ZERO)) return BigInteger.ONE;
        return number.multiply(factorialRecursively(number.subtract(BigInteger.ONE)));
    }
    class Watek1 implements Runnable{
        public void run(){
            BigInteger number = new BigInteger(factorialTextField.getText());
                iterationTextField.setText(factorialIteratively(number).toString());
        }
    }
    class Watek2 implements Runnable{
        public void run(){
            BigInteger number = new BigInteger(factorialTextField.getText());
                recursionTextField.setText(factorialRecursively(number).toString());
        }
    }
}

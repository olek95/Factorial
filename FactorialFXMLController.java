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
    @FXML
    private Button startButton;
    @FXML
    private TextField factorialTextField, iterationTextField, recursionTextField;
    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startButton.setOnAction(event -> {
            Thread t = new Thread(() -> {
                BigInteger number = new BigInteger(factorialTextField.getText());
                iterationTextField.setText(factorialIteratively(number).toString());
            });
            Thread t2 = new Thread(() -> {
                BigInteger number = new BigInteger(factorialTextField.getText());
                recursionTextField.setText(factorialRecursively(number).toString());
            });
            t.start(); 
            t2.start();
        });
    }    
    private BigInteger factorialIteratively(BigInteger number){
        BigInteger product = BigInteger.ONE;
        for(BigInteger i = BigInteger.ONE; i.compareTo(number) <= 0; i = i.add(BigInteger.ONE)){
            product = product.multiply(i);
        }
        return product;
    }
    private BigInteger factorialRecursively(BigInteger number){
        if(number.equals(BigInteger.ZERO)) return BigInteger.ONE;
        return number.multiply(factorialRecursively(number.subtract(BigInteger.ONE)));
    }
}

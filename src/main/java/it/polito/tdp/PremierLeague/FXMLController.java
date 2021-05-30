/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.BestPlayer;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String goal = this.txtGoals.getText();
    	if (goal.isEmpty()) {
    		this.txtResult.setText("Inserire un numero minimo di goal fatti per partita");
    		return;
    	}
    	double x;
    	try {
    		x=Double.parseDouble(goal);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inseire un valore numero come numero minimo di goal per partita");
    		return;
    	}
    	if (x<0) {
    		this.txtResult.setText("Inserire un numero positivo come soglia di goal");
    		return;
    	}
    	this.model.creaGrafo(x);
    	this.txtResult.setText("GRAFO CREATO\n");
    	this.txtResult.appendText("# Vertici: "+model.getNumVertici());
    	this.txtResult.appendText("\n# Archi: "+model.getNumArchi());
    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	this.txtResult.clear();
    	String kString = this.txtK.getText();
    	if(kString.isEmpty()) {
    		this.txtResult.setText("Inserire un valore di K");
    		return;
    	}
    	int k;
    	try {
    		k= Integer.parseInt(kString);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Errore, inserire un valore numerico di K");
    		return;
    	}
    	if (!model.isGrafoCreato()) {
    		this.txtResult.setText("Errore, creare prima il grafo");
    		return;
    	}
    	this.model.dreamTeam(k);
    	this.txtResult.setText(model.stampaDreamTeam());
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	this.txtResult.clear();
    	BestPlayer b = this.model.getTopPlayer();
    	if (!model.isGrafoCreato()) {
    		this.txtResult.setText("Prima crea il grafo");
    		return;
    	}
    	this.txtResult.setText(b.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

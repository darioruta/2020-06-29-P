/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.MatchConPeso;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	
    	try {
    		txtResult.clear();
    		int min = Integer.parseInt(txtMinuti.getText());
    		if(this.model.isGrafoCreato() == false) {
    			txtResult.setText("Devi prima creare un grafo");
    		} else {
    			
    			List<Adiacenza> res= this.model.getConnMax();
    			txtResult.clear();
    			txtResult.appendText("Coppie con connessione massima:");
    			for(Adiacenza a : res) {
    				txtResult.appendText(a.toString()+"\n");
    			}
    			
    		}
    		
    		
    	} catch(NumberFormatException e ) {
    		txtResult.clear();
    		txtResult.setText("devi inserire un numero intero");
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	
    	try {
    		txtResult.clear();
    		int min = Integer.parseInt(txtMinuti.getText());
    		int mese = cmbMese.getValue();
    		if(mese == 0) {
    			txtResult.setText("devi iSelezionare un mese");
    			return;
    		}
    		
    		this.model.creaGrafo(mese, min);
    		txtResult.appendText("#Vertici: "+ this.model.getVertexSize()+"\n");
    		txtResult.appendText("#Archi: "+ this.model.getEdgeSize()+"\n");
    		
    		cmbM1.getItems().addAll(this.model.getVertexSet());
        	cmbM2.getItems().addAll(this.model.getVertexSet());
    		
    		
    	}
    	catch(NumberFormatException e) {
    		txtResult.clear();
    		txtResult.setText("devi inserire un numero intero");
    	}
    	
    	
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	if(this.model.isGrafoCreato()== false) {
    		txtResult.setText("Devi prima creare un grafo");
    	}
    	if(cmbM1.getValue()== null || cmbM2.getValue()==null) {
    		txtResult.setText("Devi prima selezionare i match");
    	}
    	List<Match> res = this.model.cercaPercorso(cmbM1.getValue(), cmbM2.getValue());
    	
    	for (Match mm : res) {
    		txtResult.clear();
    		txtResult.appendText(mm+"\n");
    		
    	}
    	txtResult.appendText("Peso massimo: "+ this.model.getPesoMax());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.riempiCmbMese();
  
    }
    
    public void riempiCmbMese() {
    	for (int i=1; i<13; i++) {
    		cmbMese.getItems().add(i);
    	}
    }
    
}

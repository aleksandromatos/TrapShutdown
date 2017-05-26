package br.com.semnher.view;

import java.awt.Label;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import br.com.semnher.core.ServiceSnmp;
import br.com.semnher.core.TrapSnmp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControllerRoot implements Initializable {

	Boolean flag_btn = true;
	
	ServiceSnmp  svc_snmp = new ServiceSnmp();
	TrapSnmp     trp_snmp = new TrapSnmp();
	
	

	@FXML
	Button btn_service;
	
	@FXML
	Label txt_mid_sucess;
	
	@FXML
	Label txt_monitor;
	
	@FXML
	Label txt_type; 
	
	@FXML
	Circle cir_ok;
	
	@FXML
	Circle cir_nok;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		Teste para verificar se havia a inicialização da thread, ela inicializou junto com a 
//		aplicação no método main.
//		Thread t = new Thread(svc_snmp);
//		t.setDaemon(true);
//		t.start();
		btn_service.setOnAction(a -> {try {
			pressedBtn(btn_service);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
   
//    Método utlizado para destravar a UI da tarefa da classe TrapSnmp
//  	utilizando a classe service Snmp extinguir esses comentários caso funcione o 
//		metodo novo
//		btn_service.setOnAction(a -> {
//			try {
//				pressedBtn(btn_service);
//			} catch (Exception e) {
//				
//				e.printStackTrace();
//			}
//		});

	}

	protected EventHandler<ActionEvent> pressedBtn(Button button) throws UnknownHostException {
		
	
		
		
//	    Método utlizado para destravar a UI da tarefa da classe TrapSnmp
		if (flag_btn) {
			Thread t = new Thread(svc_snmp);
			t.setDaemon(true);
			t.start();
			this.btn_service = button;
			this.btn_service.setText("SERVIÇO INICIADO");
			this.btn_service.getStyleClass().add("botton-pressed");
			cir_ok.getStyleClass().add("circulo_ok");
			flag_btn = false;
			
//			txt_monitor.setText(trp_snmp.txt_monitor);
//			txt_monitor.textProperty().bind(trp_snmp.txt_monitor);
//			svc_snmp.initTrap();
		}
//			ServiceSnmp.interrupted();
//			svc_snmp.start();
//			flag_btn = false;
//			
//		}else {
//			this.btn_service.setText("SERVIO PARADO");
//			this.btn_service.getStyleClass().remove("botton-pressed");
//			this.btn_service.getStyleClass().add("botton-default");
//			flag_btn = true;
//    			svc_snmp.interrupt();
//		}
//		
		return null;

	}
	
	public void estadoCir(){
		
	}
}

package br.com.semnher.core;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * @author Aleksandro Matos
 * @serial 04/2016 
 * @param ip_host
 * @return String
 * @version 0.1 <p>Versão para teste<p>
 */
public class TrapSnmp {
	
	public String txt_monitor;
	public String txt_mid_sucess, txt_type;
	SimpleIntegerProperty xProperty = new SimpleIntegerProperty(0);
	public void initTrap () throws UnknownHostException{
		
		String ip_host = InetAddress.getLocalHost().getHostAddress();
		TrapSnmp snmpReceiverTrap = new TrapSnmp();
//		txt_monitor.getValue().concat(ip_host);
		try {
			snmpReceiverTrap.listen(new UdpAddress(ip_host+"/162"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public synchronized void listen (TransportIpAddress ip_host) throws IOException{
		
		AbstractTransportMapping<?> transporte;
		if(ip_host instanceof TcpAddress){
			transporte = new DefaultTcpTransportMapping((TcpAddress) ip_host);
		}else{
			transporte = new DefaultUdpTransportMapping((UdpAddress) ip_host);
		}
		
		ThreadPool threadPool = ThreadPool.create("DispatcherPool", 10);
        MessageDispatcher mDispathcher = new MultiThreadedMessageDispatcher(
                threadPool, new MessageDispatcherImpl());
        
        //Adciona modelos de processamento das mensagens de acordo com a versão
        mDispathcher.addMessageProcessingModel(new MPv1());
        mDispathcher.addMessageProcessingModel(new MPv2c());
        
        //Adicionar todos os protocolos de segurança
        SecurityProtocols.getInstance().addDefaultProtocols();
        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());
        
        //Verifica se o alvo é publico
        //Na proxima versão receberá essa string a partir de um arquivo
        //configproperty
        CommunityTarget alvo = new CommunityTarget();
        alvo.setCommunity(new OctetString("public"));
        
        Snmp snmp = new Snmp(mDispathcher, transporte);
        snmp.addCommandResponder(getTrap);
        
        transporte.listen();
//        return "ARMADO";
        System.out.println("Aguardando " + ip_host);
        txt_monitor = ip_host.toString();
        
        try {
        	
        	this.wait();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
        
        /* Syncronizado com o metodo init.
         */
        
	}
	
	CommandResponder getTrap = new CommandResponder() {
		
		
		public synchronized void processPdu(CommandResponderEvent cmdRespEvent) {
			
	        System.out.println("Recebendo Trap...");
	        PDU pdu = cmdRespEvent.getPDU();
	        
	        if(pdu != null){
	        	System.out.println(" Tipo de Trap = " + pdu.getType());
	        	System.out.println(" OID recebida = " + pdu.getVariableBindings());
	        	
	        	
	        	char vetor_oid[] = pdu.getVariableBindings().toString().toCharArray();
	        	
	        	if(vetor_oid[22] == '2' && vetor_oid[39] == '1'){
	        		
	        		
	        		try {
	        		Runtime.getRuntime().exec("shutdown -s -t 300");
					} catch (Exception e) {
						
					}
	        	}
	        }
			
		}
	};
	
}

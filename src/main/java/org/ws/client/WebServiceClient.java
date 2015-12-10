package org.ws.client;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;

import net.exchangenetwork.schema.node._2.Authenticate;
import net.exchangenetwork.schema.node._2.NodeDocumentType;
import net.exchangenetwork.schema.node._2.NotificationURIType;
import net.exchangenetwork.schema.node._2.TransactionStatusCode;
import net.exchangenetwork.wsdl.node._2.NetworkNode2;
import net.exchangenetwork.wsdl.node._2.NetworkNodePortType2;
import net.exchangenetwork.wsdl.node._2.NodeFaultMessage;


public class WebServiceClient  {

	public static void main(String[] args)  {
		   
		NetworkNode2 network2 = new NetworkNode2();
		NetworkNodePortType2 networkNodePortType2 = network2.getNetworkNodePort2();
		
		
		String securityToken = null;
		try {
			securityToken = networkNodePortType2.authenticate("otaqmdademo.redhat@epa.gov", "otaq_rh_6543", "default", "password");
			
			Holder<String> transactionId = null;
			
			String dataflow = "";
			String flowOperation="";
			List<String> recipient= null;
			List<NotificationURIType> notificationURI = null;
			List<NodeDocumentType> documents = null;
			Holder<TransactionStatusCode> status = null;
			Holder<String> statusDetail = null;
			networkNodePortType2.submit(securityToken, transactionId, dataflow, flowOperation, recipient, notificationURI, documents, status, statusDetail);
			
		} catch (NodeFaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("response -> " + securityToken );
		
    }
	
}

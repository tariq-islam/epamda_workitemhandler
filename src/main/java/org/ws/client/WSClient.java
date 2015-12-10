package org.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;

import net.exchangenetwork.schema.node._2.AttachmentType;
import net.exchangenetwork.schema.node._2.DocumentFormatType;
import net.exchangenetwork.schema.node._2.NodeDocumentType;
import net.exchangenetwork.schema.node._2.NotificationURIType;
import net.exchangenetwork.schema.node._2.TransactionStatusCode;
import net.exchangenetwork.wsdl.node._2.NetworkNodePortType2;
import net.exchangenetwork.wsdl.node._2.NodeFaultMessage;

public class WSClient {

	
	private Holder<TransactionStatusCode> status = null;
	private Holder<String> statusDetail = null;
	static URL url = null;
	static {
		 try {
			url = new URL("http://devngn.epacdxnode.net/ngn-enws20/services/NetworkNode2Service?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public String callService (String flowOperation, String docname, String path){
		
	
	    QName qname = new QName("http://www.exchangenetwork.net/wsdl/node/2", "NetworkNode2");
	    Service service = Service.create(url, qname);
	    String securityToken = null;
	    try {
	    	
	    	NetworkNodePortType2 networkNodePortType2 = service.getPort(NetworkNodePortType2.class);
			System.out.println("Authenticating");
	    	securityToken = networkNodePortType2.authenticate("otaqmdademo.redhat@epa.gov", "otaq_rh_6543", "default", "password");
			System.out.println("Security token: " + securityToken);
			Holder<String> transactionId = new Holder<String>("");
			
			String dataflow = "E-ACTIVITY";
			//String flowOperation="updateStatus";
			List<String> recipients= new ArrayList<String>();
			recipients.add("");
			
			List<NotificationURIType> notificationURIs = new ArrayList<NotificationURIType>();
			NotificationURIType nURI = new NotificationURIType();
			nURI.setValue("");
			notificationURIs.add(nURI);
			
			List<NodeDocumentType> documents = new ArrayList<NodeDocumentType>();
			DocumentFormatType type =  DocumentFormatType.fromValue("XML");
			
			NodeDocumentType nodeDocumentType = new NodeDocumentType();
			nodeDocumentType.setDocumentName(docname);
			nodeDocumentType.setDocumentFormat(type);
			
			AttachmentType attType = new AttachmentType();
			attType.setContentType("text/xml");
			
			URL pathURL = null;
			try {
				pathURL = Paths.get(path).toUri().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			DataHandler DHValue = new DataHandler(pathURL);
			attType.setValue(DHValue);
			nodeDocumentType.setDocumentContent(attType);
			
			documents.add(nodeDocumentType);
			
			status = (flowOperation.isEmpty()) ? 
					new Holder<TransactionStatusCode>(TransactionStatusCode.RECEIVED) : 
						new Holder<TransactionStatusCode>(TransactionStatusCode.PROCESSED);
			statusDetail = (flowOperation.isEmpty()) ?
					new Holder<String>("Report has been received and is being processed.") :
						new Holder<String>("Report has been processed.");
			networkNodePortType2.submit(securityToken, transactionId, dataflow, flowOperation, recipients, notificationURIs, documents, status, statusDetail);
			
			
		} catch (NodeFaultMessage e) {
			e.printStackTrace();
		}
	    String toReturn = (status == null || statusDetail == null) ? 
	    		"No status was returned, please contact an administrator." : 
	    			status.value + " " + statusDetail.value; 
	    System.out.println(toReturn);
	    return toReturn;

	}

}

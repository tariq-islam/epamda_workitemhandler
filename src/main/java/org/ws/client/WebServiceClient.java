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
		   
		WSClient wsClient = new WSClient();
		wsClient.callService("","newEActivity.xml","/home/tislam/r/poc/epa/newEActivity.xml");
		wsClient.callQuery();
		
    }
	
}

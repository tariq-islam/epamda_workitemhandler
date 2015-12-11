package org.ws.client;

import java.math.BigInteger;
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
import net.exchangenetwork.schema.node._2.EncodingType;
import net.exchangenetwork.schema.node._2.GenericXmlType;
import net.exchangenetwork.schema.node._2.NodeDocumentType;
import net.exchangenetwork.schema.node._2.NotificationURIType;
import net.exchangenetwork.schema.node._2.ParameterType;
import net.exchangenetwork.schema.node._2.TransactionStatusCode;
import net.exchangenetwork.wsdl.node._2.NetworkNodePortType2;
import net.exchangenetwork.wsdl.node._2.NodeFaultMessage;

import org.apache.xerces.dom.ElementNSImpl;

public class WSClient {

	private Holder<TransactionStatusCode> status = new Holder<TransactionStatusCode>();
	private Holder<String> statusDetail = new Holder<String>();
	static URL url = null;
	static {
		try {
			url = new URL(
					"http://devngn.epacdxnode.net/ngn-enws20/services/NetworkNode2Service?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String callService(String flowOperation, String docname, String path) {
		String toReturn = "no return value";
		QName qname = new QName("http://www.exchangenetwork.net/wsdl/node/2",
				"NetworkNode2");
		Service service = Service.create(url, qname);
		String securityToken = null;
		try {

			NetworkNodePortType2 networkNodePortType2 = service
					.getPort(NetworkNodePortType2.class);
			System.out.println("Authenticating");
			securityToken = networkNodePortType2.authenticate(
					"otaqmdademo.redhat@epa.gov", "otaq_rh_6543", "default",
					"password");
			System.out.println("Security token: " + securityToken);
			Holder<String> transactionId = new Holder<String>("");

			String dataflow = "E-ACTIVITY";
			// String flowOperation="updateStatus";
			List<String> recipients = new ArrayList<String>();
			recipients.add("");

			List<NotificationURIType> notificationURIs = new ArrayList<NotificationURIType>();
			NotificationURIType nURI = new NotificationURIType();
			nURI.setValue("");
			notificationURIs.add(nURI);

			List<NodeDocumentType> documents = new ArrayList<NodeDocumentType>();
			DocumentFormatType type = DocumentFormatType.fromValue("XML");

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

			networkNodePortType2.submit(securityToken, transactionId, dataflow,
					flowOperation, recipients, notificationURIs, documents,
					status, statusDetail);

		} catch (NodeFaultMessage e) {
			e.printStackTrace();
		}
		toReturn = (status == null || statusDetail == null) ? "No status was returned, please contact an administrator."
				: status.value + " " + statusDetail.value;
		System.out.println(toReturn);
		return toReturn;

	}

	/**
	 * This method calls the Node query method to retrieve an E-Activity already submitted by a user.
	 * @return
	 */
	public String callQuery() {
		String toReturn = "no return value";
		QName qname = new QName("http://www.exchangenetwork.net/wsdl/node/2",
				"NetworkNode2");
		Service service = Service.create(url, qname);
		String securityToken = null;
		try {

			NetworkNodePortType2 networkNodePortType2 = service
					.getPort(NetworkNodePortType2.class);
			System.out.println("Authenticating");
			securityToken = networkNodePortType2.authenticate(
					"otaqmdademo.redhat@epa.gov", "otaq_rh_6543", "default",
					"password");
			System.out.println("Security token: " + securityToken);

			List<ParameterType> parameters = new ArrayList<ParameterType>();
			ParameterType pt = new ParameterType();
			pt.setParameterName("user");
			pt.setParameterType(QName.valueOf("string"));
			pt.setParameterEncoding(EncodingType.NONE);
			pt.setValue("OTAQMDAREDHAT");
			parameters.add(pt);

			Holder<BigInteger> rowId = new Holder<BigInteger>(BigInteger.ZERO);
			BigInteger maxRows = BigInteger.valueOf(-1);
			Holder<BigInteger> rowCount = new Holder<BigInteger>();
			Holder<Boolean> lastSet = new Holder<Boolean>();
			Holder<GenericXmlType> results = new Holder<GenericXmlType>();

			String dataflow = "E-ACTIVITY";
			
			networkNodePortType2.query(securityToken, dataflow, "GetByUser",
					rowId, maxRows, parameters, rowCount, lastSet, results);
			
			List<Object> resultSet = results.value.getContent();
			// what to do here with response?
			List<ElementNSImpl> elementSet = new ArrayList<ElementNSImpl>();
			for (int i = 0; i < resultSet.size(); i++) {
				ElementNSImpl toAdd = (ElementNSImpl) resultSet.get(i);
				elementSet.add(toAdd);
			}
			
			toReturn = "Returned: " + elementSet.get(0).toString();
		} catch (NodeFaultMessage e) {
			e.printStackTrace();
		}

		System.out.println(toReturn);
		return toReturn;
	}

}

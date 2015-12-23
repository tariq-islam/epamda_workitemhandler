package com.epa.workitem;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.ws.client.WSClient;


public class WSCustomHandler implements WorkItemHandler {

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		
		String flowOperation = (String) workItem.getParameter("flowOperation");
		String fileName = (String) workItem.getParameter("fileName");
		String path = (String) workItem.getParameter("path");
		String tId = (String) workItem.getParameter("transactionIdIn");
		WSClient wsClient = new WSClient();
		
		Calendar date = Calendar.getInstance();
	    date.setTime(new Date());
	    Format f = new SimpleDateFormat("dd-MMMM-yyyy");
	    System.out.println(f.format(date.getTime()));
	    date.add(Calendar.YEAR,1);
	    System.out.println(f.format(date.getTime()));
		
		String responseStr = "";
		if (flowOperation.equals("GetByUser")) {
			responseStr = wsClient.callQuery();
		}
		else if (flowOperation.equals("getStatus")) {
			responseStr = wsClient.getStatus(tId);
		}
		else {
			tId = wsClient.callService(flowOperation, fileName, path);
			responseStr += "\nYour next report is due on: " + f.format(date.getTime());
		}
		
		// set the above 'response' string to the 'responseStr' process variable
		HashMap map = new HashMap();
		map.put("responseStr", responseStr);
		map.put("transactionIdOut", tId);
		
		workItemManager.completeWorkItem(workItem.getId(), map);

	}

}

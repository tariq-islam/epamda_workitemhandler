package com.epa.workitem;

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
		WSClient wsClient = new WSClient();
		
		String responseStr = (flowOperation.equals("GetByUser")) ? wsClient.callQuery() : wsClient.callService(flowOperation,fileName,path);
		
		// set the above 'response' string to the 'responseStr' process variable
		HashMap map = new HashMap();
		map.put("responseStr", responseStr);
		
		workItemManager.completeWorkItem(workItem.getId(), map);

	}

}

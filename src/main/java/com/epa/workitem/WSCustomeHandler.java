package com.epa.workitem;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.ws.client.WSClient;


public class WSCustomeHandler implements WorkItemHandler {

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
		
		String response = wsClient.callService(flowOperation,fileName,path);
		
		workItemManager.completeWorkItem(workItem.getId(), null);

	}

}

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
		
		String responseStr = (flowOperation.equals("GetByUser")) ? wsClient.callQuery() : wsClient.callService(flowOperation,fileName,path);
		// set the above 'response' string to the 'responseStr' process variable
		/**
		StatefulKnowledgeSession ksession = ((KnowledgeCommandContext) context).getStatefulKnowledgesession();  
        ProcessInstance processInstance = (ProcessInstance) ksession.getProcessInstance(piId);  
        VariableScopeInstance variableScope = (VariableScopeInstance) processInstance.getContextInstance(VariableScope.VARIABLE_SCOPE);  
Map<String, Object> variables = variableScope.getVariables();
		*/
        
		workItemManager.completeWorkItem(workItem.getId(), null);

	}

}

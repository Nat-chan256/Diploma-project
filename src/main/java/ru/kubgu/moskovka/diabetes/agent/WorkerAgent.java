package ru.kubgu.moskovka.diabetes.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class WorkerAgent extends Agent {
    // Send message to the agent-coordinator
    protected void notifyOfWorkFinishing(){
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        try {
            message.addReceiver(searchCoordinatorAgent());
        } catch (FIPAException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        message.setContent("Finished the work");
        send(message);
    }

    private AID searchCoordinatorAgent() throws FIPAException {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(CoordinatorAgent.SERVICE_TYPE);
        template.addServices(serviceDescription);
        DFAgentDescription[] result = DFService.search(this, template);
        return result[0].getName();
    }
}

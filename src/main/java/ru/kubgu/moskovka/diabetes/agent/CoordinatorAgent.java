package ru.kubgu.moskovka.diabetes.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;

public class CoordinatorAgent extends Agent {

    public static final String SERVICE_TYPE = "Clips-launch";

    private Environment clips;
    private boolean finished = false;
    private String result;

    public CoordinatorAgent(Environment clips) {
        this.clips = clips;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getResult() {
        return result;
    }

    @Override
    protected void setup() {
        super.setup();

        try {
            registerInYellowPages();
        } catch(FIPAException ex){
            ex.printStackTrace();
            return;
        }

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Receive message from each of agents
                for (int i = 0; i < 4; ++i)
                    blockingReceive();

                try {
                    clips.run();
                } catch (CLIPSException e) {
                    e.printStackTrace();
                }

                String evalStr = "(find-all-facts ((?f current_goal)) TRUE)";
                String result = "{";
                try {
                    MultifieldValue pv = (MultifieldValue) clips.eval(evalStr);
                    int tNum = pv.size();
                    if (tNum == 0)
                        return;
                    FactAddressValue fv;

                    for (int i = 0; i < tNum; i++) {
                        fv = (FactAddressValue) pv.get(i);
                        String goal = fv.getSlotValue("goal").toString();
                        String cf = fv.getSlotValue("cf").toString();
                        result += ("\"" + goal + "\":" + cf + ",");
                        System.out.println(goal + "  " + cf);
                    }
                    result = result.substring(0, result.length() - 1);
                    result += "}";
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                finished = true;
            }
        });
    }

    private void registerInYellowPages() throws FIPAException {
        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(SERVICE_TYPE);
        serviceDescription.setName(getLocalName() + "-" + SERVICE_TYPE);
        dfAgentDescription.addServices(serviceDescription);
        DFService.register(this, dfAgentDescription);
    }
}

package ru.kubgu.moskovka.diabetes.agent;

import jade.core.behaviours.OneShotBehaviour;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import ru.kubgu.moskovka.diabetes.entity.RiskFactor;

public class RiskFactorHandlerAgent extends WorkerAgent {
    private Environment clips;
    private RiskFactor riskFactor;

    public RiskFactorHandlerAgent(Environment clips, RiskFactor riskFactor) {
        this.clips = clips;
        this.riskFactor = riskFactor;
    }

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String obesity = riskFactor.getObesity();
                if (obesity.equals("")) {
                    obesity = "na";
                }
                String bloodPressure = riskFactor.getBloodPressure();
                String familyHistory = riskFactor.getFamilyHistory();
                String TG = riskFactor.getTG();
                String lowActivity = riskFactor.getLowActivity();
                String IGT = riskFactor.getIGT();
                String gestational = riskFactor.getGestational();
                String POS = riskFactor.getPOS();

                try {
                    clips.eval("(bind ?*bmi* " + obesity + ")");
                    clips.eval("(bind ?*bp* " + bloodPressure + ")");
                    clips.eval("(bind ?*fh* " + familyHistory + ")");
                    clips.eval("(bind ?*triglycerides* " + TG + ")");
                    clips.eval("(bind ?*gh* " + gestational + ")");
                    clips.eval("(bind ?*activity* " + lowActivity + ")");
                    clips.eval("(bind ?*gt* " + IGT + ")");
                    clips.eval("(bind ?*pos* " + POS + ")");
                } catch (CLIPSException e) {
                    e.printStackTrace();
                }
                notifyOfWorkFinishing();
            }
        });
    }
}

package ru.kubgu.moskovka.diabetes.agent;

import jade.core.behaviours.OneShotBehaviour;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import ru.kubgu.moskovka.diabetes.entity.Test;

public class TestHandlerAgent extends WorkerAgent {

    private Environment clips;
    private Test test;

    public TestHandlerAgent(Environment clips, Test test) {
        this.clips = clips;
        this.test = test;
    }

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String OGTT = test.getOGTT();
                // clips numberp will automatically convert null value to 0, thus, assign the numeric variables a string value for easy judge.
                if (OGTT.equals("")) {
                    OGTT = "na";
                }
                String FPG = test.getFPG();
                if (FPG.equals("")) {
                    FPG = "na";
                }
                String CPG = test.getCPG();
                if (CPG.equals("")) {
                    CPG = "na";
                }
                try {
                    clips.eval("(bind ?*ogtt* " + OGTT + ")");
                    clips.eval("(bind ?*fpg* " + FPG + ")");
                    clips.eval("(bind ?*cpg* " + CPG + ")");
                } catch (CLIPSException e) {
                    e.printStackTrace();
                }
                notifyOfWorkFinishing();
            }
        });
    }
}

package ru.kubgu.moskovka.diabetes.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import ru.kubgu.moskovka.diabetes.entity.Symptom;

public class SymptomHandlerAgent extends WorkerAgent {

    private Environment clips;
    private Symptom symptom;

    public SymptomHandlerAgent(Environment clips, Symptom symptom) {
        this.clips = clips;
        this.symptom = symptom;
    }

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String headache = symptom.getHeadache();
                String BV = symptom.getBV();
                String EU = symptom.getEU();
                String polydipsia = symptom.getPolydipsia();
                String LC = symptom.getLC();
                String NV = symptom.getNV();
                String polyphagia = symptom.getPolyphagia();
                String tiredness = symptom.getTiredness();
                String LW = symptom.getLW();
                String FST = symptom.getFST();
                String FI = symptom.getFI();
                String sensation = symptom.getSensation();
                String coldSweat = symptom.getColdSweat();
                try {
                    clips.eval("(bind ?*headache* " + headache + ")");
                    clips.eval("(bind ?*blur* " + BV + ")");
                    clips.eval("(bind ?*eu* " + EU + ")");
                    clips.eval("(bind ?*polydipsia* " + polydipsia + ")");
                    clips.eval("(bind ?*lc* " + LC + ")");
                    clips.eval("(bind ?*nausea* " + NV + ")");
                    clips.eval("(bind ?*polyphagia* " + polyphagia + ")");
                    clips.eval("(bind ?*tiredness* " + tiredness + ")");
                    clips.eval("(bind ?*lw* " + LW + ")");
                    clips.eval("(bind ?*fraction* " + FST + ")");
                    clips.eval("(bind ?*infection* " + FI + ")");
                    clips.eval("(bind ?*ls* " + sensation + ")");
                    clips.eval("(bind ?*cs* " + coldSweat + ")");
                } catch (CLIPSException e) {
                    e.printStackTrace();
                }
                notifyOfWorkFinishing();
            }
        });
    }
}

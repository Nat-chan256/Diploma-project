package ru.kubgu.moskovka.diabetes.agent;

import jade.core.behaviours.OneShotBehaviour;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import ru.kubgu.moskovka.diabetes.entity.PersonInfo;

public class PersonInfoHandlerAgent extends WorkerAgent {
    private Environment clips;
    private PersonInfo personInfo;

    public PersonInfoHandlerAgent(Environment clips, PersonInfo personInfo) {
        this.clips = clips;
        this.personInfo = personInfo;
    }

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String age = personInfo.getAge();
                String gender = personInfo.getGender();
                String pregnant = personInfo.getPregnant();

                try {
                    clips.eval("(bind ?*gender* " + gender + ")");
                    clips.eval("(bind ?*pregnant-input* " + pregnant + ")");
                    clips.eval("(bind ?*age* " + age + ")");
                } catch (CLIPSException e) {
                    e.printStackTrace();
                }
                notifyOfWorkFinishing();
            }
        });
    }
}

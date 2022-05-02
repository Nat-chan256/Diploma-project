package ru.kubgu.moskovka.diabetes.controller;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kubgu.moskovka.diabetes.agent.*;
import ru.kubgu.moskovka.diabetes.entity.PersonInfo;
import ru.kubgu.moskovka.diabetes.entity.RiskFactor;
import ru.kubgu.moskovka.diabetes.entity.Symptom;
import ru.kubgu.moskovka.diabetes.entity.Test;

import net.sf.clipsrules.jni.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

//@Controller
public class RecommenderController {

    private AgentContainer agentContainer;
    private List<Agent> agents;
    private CoordinatorAgent coordinatorAgent;
    private String coordinatorAgentName;
    private String diagnosis;

    //@GetMapping("/diabetes2")
    public String questionnairePage(Model model){
        model.addAttribute("personInfo", new PersonInfo());
        model.addAttribute("test", new Test());
        model.addAttribute("riskFactor", new RiskFactor());
        model.addAttribute("symptom", new Symptom());
        return "test";
    }

    //@GetMapping("/showDiagnosis")
    public String showDiagnosis(Model model){
        model.addAttribute("diagnosis", diagnosis);
        return "showDiagnosis";
    }

    //@PostMapping("/diabetes2")
    public String handleUsersData(@ModelAttribute("personInfo") PersonInfo personInfo,
                                  @ModelAttribute("test") Test test,
                                  @ModelAttribute("riskFactor") RiskFactor riskFactor,
                                  @ModelAttribute("symptom") Symptom symptom) throws CLIPSException {
        Environment clips = new Environment();
        clips.load("C:\\My folder\\Programing\\Idea projects\\Diabetes2\\clips\\diagnosis.CLP");
        try {
            clips.reset();
        } catch (CLIPSException e) {
            e.printStackTrace();
        }
        try {
            createAgents(clips, personInfo, test, riskFactor, symptom);
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        while (!coordinatorAgent.isFinished());

        diagnosis = coordinatorAgent.getResult();
        return "redirect:/showDiagnosis";
    }

    //===========================Util methods=======================================
    private void createAgents(Environment clips, PersonInfo personInfo, Test test, RiskFactor riskFactor,
                              Symptom symptom) throws StaleProxyException {
        System.setProperty("java.awt.headless", "false");
        agents = new ArrayList<>();
        agents.add(new CoordinatorAgent(clips));
        agents.add(new PersonInfoHandlerAgent(clips, personInfo));
        agents.add(new TestHandlerAgent(clips, test));
        agents.add(new RiskFactorHandlerAgent(clips, riskFactor));
        agents.add(new SymptomHandlerAgent(clips, symptom));

        Properties pp = new Properties();
        pp.setProperty(Profile.GUI, Boolean.TRUE.toString());
        Profile p = new ProfileImpl(pp);
        agentContainer = jade.core.Runtime.instance().createMainContainer(p);
        AgentController agentController;
        try {
            coordinatorAgent = new CoordinatorAgent(clips);
            (agentController = agentContainer.acceptNewAgent("Coordinator", coordinatorAgent))
                    .start();
            agentContainer.acceptNewAgent("PersonInfoHandler",
                    new PersonInfoHandlerAgent(clips, personInfo)).start();
            agentContainer.acceptNewAgent("TestHandler", new TestHandlerAgent(clips, test)).start();
            agentContainer.acceptNewAgent("RiskFactorHandler",
                    new RiskFactorHandlerAgent(clips, riskFactor)).start();
            agentContainer.acceptNewAgent("SymptomHandler", new SymptomHandlerAgent(clips, symptom)).start();
        } catch (StaleProxyException e) {
            throw new Error(e);
        }
        coordinatorAgentName = agentController.getName();
//        for (Agent agent : agents)
//            agent.doWake();
    }
}

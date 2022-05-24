package ru.kubgu.moskovka.diabetes.controller;

import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kubgu.moskovka.diabetes.entity.*;
import ru.kubgu.moskovka.diabetes.util.Util;

import java.util.Date;

@Controller
public class RecommenderControllerW {
    private String diagnosis;
    private User user = new User("Nata", "Moskovka", "Nat-chan", "Female",
            new Date(), "password");

    @GetMapping("/diabetesTest")
    public String questionnairePage(Model model){
        model.addAttribute("personInfo", new PersonInfo());
        model.addAttribute("test", new Test());
        model.addAttribute("riskFactor", new RiskFactor());
        model.addAttribute("symptom", new Symptom());
        model.addAttribute("user", user);
        return "test";
    }

    @GetMapping("/showDiagnosis")
    public String showDiagnosis(Model model){
        model.addAttribute("diagnosis", diagnosis);
        return "showDiagnosis";
    }

    @PostMapping("/diabetesTest")
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

        String pregnant = personInfo.getPregnant();
        if (pregnant == null)
            pregnant = "no";

        try {
            clips.eval("(bind ?*gender* " + user.getGender() + ")");
            clips.eval("(bind ?*pregnant-input* " + pregnant + ")");
            clips.eval("(bind ?*age* " + Util.calculateAge(user) + ")");
        } catch (CLIPSException e) {
            e.printStackTrace();
        }

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
                return "test";
            FactAddressValue fv;

            double cfMaxValue = -1.0;

            for (int i = 0; i < tNum; i++) {
                fv = (FactAddressValue) pv.get(i);
                double cf = Double.parseDouble(fv.getSlotValue("cf").toString());
                if (cf > cfMaxValue && cf < 1.0)
                {
                    result = fv.getSlotValue("goal").toString();
                    cfMaxValue = cf;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        diagnosis = result;
        return "redirect:/showDiagnosis";
    }

}

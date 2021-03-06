package com.hyjf.mybatis.model.auto;

import java.io.Serializable;

public class HjhPlanWithBLOBs extends HjhPlan implements Serializable {
    private String planConcept;

    private String planPrinciple;

    private String safeguardMeasures;

    private String marginMeasures;

    private String normalQuestions;

    private static final long serialVersionUID = 1L;

    public String getPlanConcept() {
        return planConcept;
    }

    public void setPlanConcept(String planConcept) {
        this.planConcept = planConcept == null ? null : planConcept.trim();
    }

    public String getPlanPrinciple() {
        return planPrinciple;
    }

    public void setPlanPrinciple(String planPrinciple) {
        this.planPrinciple = planPrinciple == null ? null : planPrinciple.trim();
    }

    public String getSafeguardMeasures() {
        return safeguardMeasures;
    }

    public void setSafeguardMeasures(String safeguardMeasures) {
        this.safeguardMeasures = safeguardMeasures == null ? null : safeguardMeasures.trim();
    }

    public String getMarginMeasures() {
        return marginMeasures;
    }

    public void setMarginMeasures(String marginMeasures) {
        this.marginMeasures = marginMeasures == null ? null : marginMeasures.trim();
    }

    public String getNormalQuestions() {
        return normalQuestions;
    }

    public void setNormalQuestions(String normalQuestions) {
        this.normalQuestions = normalQuestions == null ? null : normalQuestions.trim();
    }
}
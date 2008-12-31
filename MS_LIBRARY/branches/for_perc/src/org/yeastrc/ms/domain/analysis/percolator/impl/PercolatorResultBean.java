/**
 * PercolatorResultBean.java
 * @author Vagisha Sharma
 * Dec 10, 2008
 * @version 1.0
 */
package org.yeastrc.ms.domain.analysis.percolator.impl;

import org.yeastrc.ms.domain.analysis.percolator.PercolatorResult;
import org.yeastrc.ms.domain.search.impl.SearchResultBean;

/**
 * 
 */
public class PercolatorResultBean extends SearchResultBean implements PercolatorResult{

    private int runSearchAnalysisId;
    private double discriminantScore = -1.0;
    private double pep = -1.0;
    private double qvalue = -1.0;
    
    @Override
    public double getDiscriminantScore() {
        return discriminantScore;
    }

    public void setDiscriminantScore(double discriminantScore) {
        this.discriminantScore = discriminantScore;
    }

    @Override
    public int getRunSearchAnalysisId() {
        return runSearchAnalysisId;
    }
    
    public void setRunSearchAnalysisId(int runSearchAnalysisId) {
        this.runSearchAnalysisId = runSearchAnalysisId;
    }

    @Override
    public double getPosteriorErrorProbability() {
        return pep;
    }
    
    public void setPosteriorErrorProbability(double pep) {
        this.pep = pep;
    }

    @Override
    public double getQvalue() {
        return qvalue;
    }
    
    public void setQvalue(double qvalue) {
        this.qvalue = qvalue;
    }
}

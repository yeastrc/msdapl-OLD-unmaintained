/**
 * SequestResultPlus.java
 * @author Vagisha Sharma
 * Apr 7, 2009
 * @version 1.0
 */
package org.yeastrc.experiment;

import java.math.BigDecimal;
import java.util.List;

import org.yeastrc.ms.domain.run.MsScan;
import org.yeastrc.ms.domain.search.MsSearchResultPeptide;
import org.yeastrc.ms.domain.search.MsSearchResultProtein;
import org.yeastrc.ms.domain.search.ValidationStatus;
import org.yeastrc.ms.domain.search.sequest.SequestResultData;
import org.yeastrc.ms.domain.search.sequest.SequestSearchResult;

/**
 * 
 */
public class SequestResultPlus implements SequestSearchResult {

    private final SequestSearchResult result;
    private final int scanNumber;
    private final BigDecimal retentionTime;
    private String filename;
    
    public SequestResultPlus(SequestSearchResult result, MsScan scan) {
        this.result = result;
        this.scanNumber = scan.getStartScanNum();
        this.retentionTime = scan.getRetentionTime();
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    @Override
    public SequestResultData getSequestResultData() {
        return result.getSequestResultData();
    }
    @Override
    public int getId() {
        return result.getId();
    }
    @Override
    public List<MsSearchResultProtein> getProteinMatchList() {
        return result.getProteinMatchList();
    }
    
    public String getProteins() {
        if(result.getProteinMatchList() == null)
            return null;
        else {
            StringBuilder buf = new StringBuilder();
            for(MsSearchResultProtein protein: result.getProteinMatchList()) {
                buf.append(", "+protein.getAccession());
            }
            if(buf.length() > 0)
                buf.deleteCharAt(0);
            return buf.toString();
        }
    }
    
    @Override
    public int getRunSearchId() {
        return result.getRunSearchId();
    }
    @Override
    public int getScanId() {
        return result.getScanId();
    }
    @Override
    public int getCharge() {
        return result.getCharge();
    }
    @Override
    public BigDecimal getObservedMass() {
        return result.getObservedMass();
    }
    @Override
    public MsSearchResultPeptide getResultPeptide() {
        return result.getResultPeptide();
    }
    @Override
    public ValidationStatus getValidationStatus() {
        return result.getValidationStatus();
    }

    public int getScanNumber() {
        return scanNumber;
    }

    public BigDecimal getRetentionTime() {
        return retentionTime;
    }

    @Override
    public void setCharge(int charge) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setObservedMass(BigDecimal mass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setResultPeptide(MsSearchResultPeptide resultPeptide) {
        throw new UnsupportedOperationException();
    }
}

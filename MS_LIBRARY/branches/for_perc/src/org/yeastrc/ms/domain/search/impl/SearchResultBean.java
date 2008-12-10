/**
 * SearchResult.java
 * @author Vagisha Sharma
 * Sep 7, 2008
 * @version 1.0
 */
package org.yeastrc.ms.domain.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.ms.domain.search.MsSearchResult;
import org.yeastrc.ms.domain.search.MsSearchResultPeptide;
import org.yeastrc.ms.domain.search.MsSearchResultProtein;
import org.yeastrc.ms.domain.search.ValidationStatus;

/**
 * 
 */
public class SearchResultBean implements MsSearchResult {

    private int id; // unique id (database) for this search result
    private int runSearchId; // id (database) of the run search this result belongs to
    private int scanId; // id (database) of the scan with which this result is associated

    private int charge; 
    private ValidationStatus validationStatus = ValidationStatus.UNKNOWN;

    private List<MsSearchResultProtein> proteinMatchList;

    private MsSearchResultPeptide resultPeptide;


    public SearchResultBean() {
        proteinMatchList = new ArrayList<MsSearchResultProtein>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the searchId
     */
    public int getRunSearchId() {
        return runSearchId;
    }

    /**
     * @param runSearchId the runSearchId to set
     */
    public void setRunSearchId(int runSearchId) {
        this.runSearchId = runSearchId;
    }

    /**
     * @return the scanId
     */
    public int getScanId() {
        return scanId;
    }

    /**
     * @param scanId the scanId to set
     */
    public void setScanId(int scanId) {
        this.scanId = scanId;
    }

    /**
     * @return the charge
     */
    public int getCharge() {
        return charge;
    }

    /**
     * @param charge the charge to set
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }

    // ----------------------------------------------------------------------------------------
    // Validation status for this result
    // ----------------------------------------------------------------------------------------
    /**
     * @param validationStatus the validationStatus to set
     */
    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    // ----------------------------------------------------------------------------------------
    // Peptide sequence information for this result
    // ----------------------------------------------------------------------------------------
    public MsSearchResultPeptide getResultPeptide() {
        return resultPeptide;
    }
    
    public void setResultPeptide(MsSearchResultPeptide peptide) {
        this.resultPeptide = peptide;
    }
    
    public char getPreResidue() {
        return resultPeptide.getPreResidue();
    }
    
    public char getPostResidue() {
        return resultPeptide.getPostResidue();
    }

    public String getPeptideSequence() {
        return resultPeptide.getPeptideSequence();
    }
    
    // ----------------------------------------------------------------------------------------
    // Protein matches to this result
    // ----------------------------------------------------------------------------------------
    /**
     * @param proteinMatchList the proteinMatchList to set
     */
    public void setProteinMatchList(List<MsSearchResultProtein> proteinMatchList) {
        this.proteinMatchList = proteinMatchList;
    }

    public List<MsSearchResultProtein> getProteinMatchList() {
        return proteinMatchList;
    }
}

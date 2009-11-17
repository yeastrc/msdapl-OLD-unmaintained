/**
 * ProteinProphetFilterCriteria.java
 * @author Vagisha Sharma
 * Aug 28, 2009
 * @version 1.0
 */
package org.yeastrc.ms.domain.protinfer.proteinProphet;

import org.yeastrc.ms.domain.protinfer.ProteinFilterCriteria;
import org.yeastrc.ms.domain.protinfer.SORT_BY;
import org.yeastrc.ms.domain.protinfer.SORT_ORDER;

/**
 * 
 */
public class ProteinProphetFilterCriteria extends ProteinFilterCriteria {

    private double minGroupProbability = 0.0;
    private double maxGroupProbability = 1.0;
    
    private double minProteinProbability = 0.0;
    private double maxProteinProbability = 1.0;
    
    public static SORT_BY defaultSortBy() {
        return SORT_BY.PROTEIN_PROPHET_GROUP;
    }
    
    public static SORT_ORDER defaultSortOrder() {
        return SORT_ORDER.ASC;
    }
    
    public ProteinProphetFilterCriteria() {}
    
    public ProteinProphetFilterCriteria(ProteinFilterCriteria filterCriteria) {
        super.setCoverage(filterCriteria.getCoverage());
        super.setMaxCoverage(filterCriteria.getMaxCoverage());
        super.setMinMolecularWt(filterCriteria.getMinMolecularWt());
        super.setMaxMolecularWt(filterCriteria.getMaxMolecularWt());
        super.setMinPi(filterCriteria.getMinPi());
        super.setMaxPi(filterCriteria.getMaxPi());
        super.setNumPeptides(filterCriteria.getNumPeptides());
        super.setNumMaxPeptides(filterCriteria.getNumMaxPeptides());
        super.setNumUniquePeptides(filterCriteria.getNumUniquePeptides());
        super.setNumMaxUniquePeptides(filterCriteria.getNumMaxUniquePeptides());
        super.setNumSpectra(filterCriteria.getNumSpectra());
        super.setNumMaxSpectra(filterCriteria.getNumMaxSpectra());
        super.setAccessionLike(filterCriteria.getAccessionLike());
        super.setDescriptionLike(filterCriteria.getDescriptionLike());
        super.setPeptide(filterCriteria.getPeptide());
        super.setExactPeptideMatch(filterCriteria.getExactPeptideMatch());
        super.setExcludeIndistinGroups(filterCriteria.isExcludeIndistinGroups());
        super.setGroupProteins(filterCriteria.isGroupProteins());
        super.setNonParsimonious(filterCriteria.getNonParsimonious());
        super.setParsimonious(filterCriteria.getParsimonious());
        super.setPeptideDefinition(filterCriteria.getPeptideDefinition());
        super.setValidationStatus(filterCriteria.getValidationStatus());
        super.setSortOrder(filterCriteria.getSortOrder());
        super.setSortBy(filterCriteria.getSortBy());
    }
    
    public boolean equals(ProteinProphetFilterCriteria o) {
        if(!super.equals(o))
            return false;
        if(!(o instanceof ProteinProphetFilterCriteria))
            return false;
        ProteinProphetFilterCriteria that = (ProteinProphetFilterCriteria)o;
        if(this.minGroupProbability != that.minGroupProbability)          return false;
        if(this.maxGroupProbability != that.maxGroupProbability)          return false;
        
        if(this.minProteinProbability != that.minProteinProbability)          return false;
        if(this.maxProteinProbability != that.maxProteinProbability)          return false;

        return true;
    }

    public double getMinGroupProbability() {
        return minGroupProbability;
    }

    public void setMinGroupProbability(double minProbability) {
        this.minGroupProbability = minProbability;
    }

    public double getMaxGroupProbability() {
        return maxGroupProbability;
    }

    public void setMaxGroupProbability(double maxProbability) {
        this.maxGroupProbability = maxProbability;
    }
    
    
    public double getMinProteinProbability() {
        return minProteinProbability;
    }

    public void setMinProteinProbability(double minProteinProbability) {
        this.minProteinProbability = minProteinProbability;
    }

    public double getMaxProteinProbability() {
        return maxProteinProbability;
    }

    public void setMaxProteinProbability(double maxProteinProbability) {
        this.maxProteinProbability = maxProteinProbability;
    }
}

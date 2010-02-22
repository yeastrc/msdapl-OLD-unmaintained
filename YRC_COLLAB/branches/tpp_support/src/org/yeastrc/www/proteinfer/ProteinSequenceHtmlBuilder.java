/**
 * ProteinSequenceHtmlBuilder.java
 * @author Vagisha Sharma
 * Feb 3, 2010
 * @version 1.0
 */
package org.yeastrc.www.proteinfer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class ProteinSequenceHtmlBuilder {

    private static ProteinSequenceHtmlBuilder instance = null;
    
    private ProteinSequenceHtmlBuilder() {}
    
    public static ProteinSequenceHtmlBuilder getInstance() {
        if(instance == null) {
            instance = new ProteinSequenceHtmlBuilder();
        }
        return instance;
    }
    
    public String build(String sequence, Set<String> peptideSequences) {
        
        char[] reschars = sequence.toCharArray();
        String[] residues = new String[reschars.length];        // the array of strings, which are the residues of the matched protein
        for (int i = 0; i < reschars.length; i++) { residues[i] = String.valueOf(reschars[i]); }
        reschars = null;

        // structure of these maps is: Integer=>Integer (Residue index (0..residues.length))=>(number of peptides marking that residue thusly)
        Map<Integer, Integer> starResidues = new HashMap<Integer, Integer>();       // residues marked with a *
        Map<Integer, Integer> atResidues = new HashMap<Integer, Integer>();         // residues marked with a @
        Map<Integer, Integer> hashResidues = new HashMap<Integer, Integer>();       // residues marked with a #

        for( String peptideSequence : peptideSequences ) {

            if (peptideSequence == null) continue;          
            int pepIndex = sequence.indexOf( peptideSequence );

            // skip this peptide if it's not in the parent protein sequence
            if (pepIndex == -1)
                continue;

//            if (ypep.getSequence().indexOf( "*" ) != -1 || ypep.getSequence().indexOf("@") != -1 || ypep.getSequence().indexOf("#") != -1) {
//                char[] aas = ypep.getSequence().toCharArray();
//                int modCount = 0;
//                for (int k = 3; k < aas.length; k++) {
//                    Integer residueIndex = new Integer( pepIndex + k - 3 - modCount );
//                    Map<Integer, Integer> countMap = null;
//
//                    if (aas[k] == '*') {
//                        countMap = starResidues;
//                        modCount++;
//                    } else if (aas[k] == '@') {                     
//                        countMap = atResidues;
//                        modCount++;
//                    } else if (aas[k] == '#') {                     
//                        countMap = hashResidues;
//                        modCount++;
//                    } else {
//                        continue;
//                    }
//
//                    if (!countMap.containsKey( residueIndex ))
//                        countMap.put( residueIndex, new Integer( 1 ) );
//                    else {
//                        int count = ((Integer)countMap.get( residueIndex)).intValue();
//                        countMap.remove( residueIndex );
//                        countMap.put( residueIndex, new Integer( count + 1 ) );
//                    }                   
//                }
//            }
        }

        /* 
         * at this point, the 3 residues maps should contain a count of the number of peptides reporting the
         * respective modifications for each reportedly modified residues.
         * Go through and label each of those with styled <SPAN> tags for labelling
         * 
         */
        for ( int index : starResidues.keySet() ) {
            int count = starResidues.get( index );

            if (count == 1)
                residues[index] = "<span class=\"single_star_residue\">" + residues[index] + "</span>";
            else
                residues[index] = "<span class=\"multiple_star_residue\">" + residues[index] + "</span>";
        }


        for ( int index : atResidues.keySet() ) {
            int count = atResidues.get( index );

            if (count == 1)
                residues[index] = "<span class=\"single_at_residue\">" + residues[index] + "</span>";
            else
                residues[index] = "<span class=\"multiple_at_residue\">" + residues[index] + "</span>";
        }


        for ( int index : hashResidues.keySet() ) {
            int count = hashResidues.get( index );

            if (count == 1)
                residues[index] = "<span class=\"single_hash_residue\">" + residues[index] + "</span>";
            else
                residues[index] = "<span class=\"multiple_hash_residue\">" + residues[index] + "</span>";
        }   

        /*
         * All modified residues in the residues array should be surrounded by appropriately classed <span> tags
         */

        // clean up
        starResidues = null;
        atResidues = null;
        hashResidues = null;

        /*
         * Now add in font tags for labelling covered sequences in the parent sequence
         */

        for ( String pseq : peptideSequences ) {
            if (pseq == null) continue;

            int index = sequence.indexOf(pseq);
            if (index == -1) continue;                  //shouldn't happen
            if (index > residues.length - 1) continue;  //shouldn't happen

            // Place a red font start at beginning of this sub sequence in main sequence
            residues[index] = "<span class=\"covered_peptide\">" + residues[index];

            // this means that the sub-peptide extends beyond the main peptide's sequence... shouldn't happen but check for it
            if (index + pseq.length() > residues.length - 1) {

                // just stop the red font at the end of the main sequence string
                residues[residues.length - 1] = residues[residues.length - 1] + "</span>";
            } else {

                // add the font end tag after the last residue in the sub sequence
                residues[index + pseq.length() - 1] = residues[index + pseq.length() - 1] + "</span>";
            }
        }


        // String array should be set up appropriately with red font tags for sub peptide overlaps, format it into a displayable peptide sequence
        String retStr = "      1          11         21         31         41         51         \n";
        retStr +=       "      |          |          |          |          |          |          \n";
        retStr +=       "    1 ";

        int counter = 0;

        // retStr += "RESIDUE 0: [" + residues[0] + "]";

        for (int i = 0; i < residues.length; i++ ) {
            retStr += residues[i];

            counter++;
            if (counter % 60 == 0) {
                if (counter < 1000) retStr += " ";
                if (counter< 100) retStr += " ";

                retStr += "<font style=\"color:black;\">" + String.valueOf(counter) + "</font>";
                retStr += "\n ";

                if (counter < 100) retStr += " ";
                if (counter < 1000) retStr += " ";
                retStr += "<font style=\"color:black;\">" + String.valueOf(counter + 1) + "</font> ";

            } else if (counter % 10 == 0) {
                retStr += " ";
            }

        }

        return retStr;
    }
}

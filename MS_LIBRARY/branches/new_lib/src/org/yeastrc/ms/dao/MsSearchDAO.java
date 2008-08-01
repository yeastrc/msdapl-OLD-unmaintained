package org.yeastrc.ms.dao;

import java.util.List;

import org.yeastrc.ms.domain.MsSearch;
import org.yeastrc.ms.domain.MsSearchDb;

public interface MsSearchDAO <I extends MsSearch, O extends MsSearchDb>{

    public abstract O loadSearch(int searchId);
    
    public abstract List<O> loadSearchesForRun(int runId);

    public abstract List<Integer> loadSearchIdsForRun(int runId);
    
    public abstract int loadSearchIdForRunAndGroup(int runId, int searchGroupId);
    

    public abstract int getMaxSearchGroupId();
    
    /**
     * Saves the search in the database. Also saves:
     * 1. any associated sequence database information used for the search
     * 2. any static modifications used for the search
     * 3. any dynamic modifications used for the search 
     * @param search
     * @param runId
     * @param searchGroupId
     * @return database id of the search
     */
    public abstract int saveSearch(I search, int runId, int searchGroupId);

    /**
     * Deletes the search ONLY
     * @param searchId
     */
    public abstract void deleteSearch(int searchId);

}
package org.yeastrc.ms.parser.sqtFile;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yeastrc.ms.domain.sqtFile.SQTField;
import org.yeastrc.ms.domain.sqtFile.SQTSearch;


public class Header implements SQTSearch {

    
    private static final Pattern multiDbPattern = Pattern.compile(".*[,:;\\s]+.*");
    private static final Pattern staticModPattern = Pattern.compile("[A-Z]+");
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, hh:mm a"); // Example: 01/29/2008, 03:34 AM
    
    private String sqtGenerator;
    private String sqtGeneratorVersion;
    private String fragmentMassType;
    private BigDecimal fragmentMassTolerance;
    private String precursorMassType;
    private BigDecimal precursorMassTolerance;
    
    private String startTimeString;
    private String endTimeString;
    private Date startDate;
    private Date endDate;
    private int searchDuration = -1;
    
    private Database database;
    
    private List<HeaderItem> headerItems;
    private List<StaticModification> staticMods;
    private List<DynamicModification> dynaMods;
    private String enzyme;
    
    
    public Header() {
        headerItems = new ArrayList<HeaderItem>();
        staticMods = new ArrayList<StaticModification>();
        dynaMods = new ArrayList<DynamicModification>();
    }
   
    public boolean isHeaderValid() {
        if (sqtGenerator == null)   return false;
        if (sqtGeneratorVersion == null)    return false;
        if (database == null)  return false;
        if (fragmentMassType == null)   return false;
        if (precursorMassType == null)  return false;
        if (startTimeString == null)  return false;
        return true;
    }
    
    /**
     * @param name
     * @param value
     * @throws ParseException 
     * @throws NullPointerException if either name or value is null.
     */
    public void addHeaderItem(String name, String value) {
        
        if (name == null)
            throw new NullPointerException("name for Header cannot be null.");
        if (value == null)
            throw new NullPointerException("value for Header cannot be null.");
        
        headerItems.add(new HeaderItem(name, value));
        
        if (isSqtGenerator(name))
            sqtGenerator = value;
        else if (isSqtGeneratorVersion(name))
            sqtGeneratorVersion = value;
        else if (isDatabase(name))
            setDatabasePath(value);
        else if (isDatabaseLength(name))
            setDatabaseLength(Long.parseLong(value));
        else if (isDatabaseLocusCount(name))
            setDatabaseLocusCount(Integer.parseInt(value));
        else if (isFragmentMassType(name))
            fragmentMassType = value;
        else if (isFragmentMassTolerance(name))
            fragmentMassTolerance = new BigDecimal(value);
        else if (isPrecursorMassType(name))
            precursorMassType = value;
        else if (isPrecursorMassTolerance(name))
            precursorMassTolerance = new BigDecimal(value);
        else if (isStartTime(name))
            startTimeString = value;
        else if (isEndTime(name))
            endTimeString = value;
        else if (isStaticModification(name))
            addStaticMods(value);
        else if (isDynamicModification(name))
            addDynamicMods(value);
        else if (isEnzyme(name))
            enzyme = value; // TODO: can there be multiple enzyme headers????
    }

    
    //-------------------------------------------------------------------------------------------------------
    // Search database
    //-------------------------------------------------------------------------------------------------------
    private void setDatabasePath(String filePath) {
        if (database == null)
            database = new Database();
        // we should have at least one database
        if (filePath.trim().length() == 0)
            throw new RuntimeException("No database path found in header: "+filePath);
        // check is there are multiple databases (look for commas, semicolons, colons and spaces)
        if (multipleDatabases(filePath))
            throw new RuntimeException("Multiple databases found in header: "+filePath+"; Don't know how to handle this yet.");
        database.setServerPath(filePath);
    }
    
    boolean multipleDatabases(String filePath) {
        // remove any spaces at the beginning and end
        filePath = filePath.trim();
        Matcher matcher = multiDbPattern.matcher(filePath);
        return matcher.matches(); 
    }

    private void setDatabaseLength(long length) {
        if (database == null)
            database = new Database();
        database.setSequenceLength(length);
    }
    
    private void setDatabaseLocusCount(int count) {
        if (database == null)
            database = new Database();
        database.setProteinCount(count);
    }
    
    //-------------------------------------------------------------------------------------------------------
    // Static Modifications
    //-------------------------------------------------------------------------------------------------------
    /**
     * Example of a valid static modification String: C=160.139
     * Multiple static modifications should be present on separate StaticMod lines in a SQT file
     * @param value
     * @return
     * @throws IllegalArgumentException if an error occurs while parsing the static modification
     * @throws NumberFormatException if the modification mass is not a valid number.
     */
    void addStaticMods(String value) {
        
        // if there were no modifications we will get a empty string
        value = value.trim();
        if (value.length() == 0)
            return;
        
        String[] tokens = value.split("=");
        // The split should create exactly two tokens
        if (tokens.length < 2)
            throw new IllegalArgumentException("Invalid static modification string: "+value);
        if (tokens.length > 2)
            throw new IllegalArgumentException("Invalid static modification string (appears to have > 1 static modification): "+value);
        
        // convert modification chars to upper case 
        String modChars = tokens[0].trim().toUpperCase();
        if (modChars.length() < 1)
            throw new IllegalArgumentException("No residues found for static modification: "+value);
        if (!isValidModCharString(modChars))
            throw new IllegalArgumentException("Invalid residues found found for static modification"+value);
        
        
        String modMass = tokens[1].trim();
        if (modMass.length() < 1)
            throw new IllegalArgumentException("No mass found for static modification: "+value);
        BigDecimal mass = new BigDecimal(modMass);
        
        // this modification may be for multiple residues; 
        // add one StaticModification for each residue character
        for (int i = 0; i < modChars.length(); i++) {
            staticMods.add(new StaticModification(modChars.charAt(i), mass));
        }
    }
    
    boolean isValidModCharString(String staticModStr) {
        return staticModPattern.matcher(staticModStr).matches();
    }
    
    //-------------------------------------------------------------------------------------------------------
    // Dynamic Modifications
    //-------------------------------------------------------------------------------------------------------
    /**
     * Example of a valid dynamic modification String: STY*=+80.000
     * Multiple dynamic modifications should be present on separate DiffMod lines in a SQT file
     * @param value
     * @return
     * @throws IllegalArgumentException if an error occurs while parsing the dynamic modification
     * @throws NumberFormatException if the modification mass is not a valid number.
     */
    void addDynamicMods(String value) {
        
        // if there were no modifications we will get a empty string
        value = value.trim();
        if (value.length() == 0)
            return;
        
        
        String[] tokens = value.split("=");
        // The split should create exactly two tokens
        if (tokens.length < 2)
            throw new IllegalArgumentException("Invalid dynamic modification string: "+value);
        if (tokens.length > 2)
            throw new IllegalArgumentException("Invalid dynamic modification string (appears to have > 1 dynamic modification): "+value);
        
        String modChars = tokens[0].trim();
        // get the modification symbol (this character should follow the modification residue characters)
        if (modChars.length() < 2)
            throw new IllegalArgumentException("No modification symbol found: "+value);
        char modSymbol = modChars.charAt(modChars.length() - 1);
        if (!isValidDynamicModificationSymbol(modSymbol))
            throw new IllegalArgumentException("Invalid modification symbol: "+value);
        
        // remove the modification symbol and convert modification chars to upper case 
        modChars = modChars.substring(0, modChars.length()-1).toUpperCase();
        if (modChars.length() < 1)
            throw new IllegalArgumentException("No residues found for dynamic modification: "+value);
        if (!isValidModCharString(modChars))
            throw new IllegalArgumentException("Invalid residues found found for dynamic modification"+value);
        
        
        String modMass = tokens[1].trim();
        modMass = removeSign(modMass); // removes a + sign
        if (modMass.length() < 1)
            throw new IllegalArgumentException("No mass found for dynamic modification: "+value);
        BigDecimal mass = new BigDecimal(modMass);
        
        // this modification may be for multiple residues; 
        // add one StaticModification for each residue character
        for (int i = 0; i < modChars.length(); i++) {
            dynaMods.add(new DynamicModification(modChars.charAt(i), mass, modSymbol));
        }
    }

    boolean isValidDynamicModificationSymbol(char modSymbol) {
        modSymbol = Character.toUpperCase(modSymbol);  
        return (modSymbol < 'A' || modSymbol > 'Z');
    }
    
    private String removeSign(String massStr) {
        if (massStr.length() == 0)  return massStr;
        if (massStr.charAt(0) == '+' || massStr.charAt(0) == '-')
            return massStr.substring(1);
        return massStr;
    }
    
    
    //-------------------------------------------------------------------------------------------------------
    // These are the header names we know
    //-------------------------------------------------------------------------------------------------------
    private boolean isSqtGenerator(String name) {
        return name.equalsIgnoreCase("SQTGenerator");
    }
    
    private boolean isSqtGeneratorVersion(String name) {
        return name.equalsIgnoreCase("SQTGeneratorVersion");
    }
    
    private boolean isDatabase(String name) {
        return name.equalsIgnoreCase("Database");
    }
    
    private boolean isDatabaseLength(String name) {
        return name.equalsIgnoreCase("DBSeqLength");
    }
    
    private boolean isDatabaseLocusCount(String name) {
        return name.equalsIgnoreCase("DBLocusCount");
    }
    
    private boolean isFragmentMassType(String name) {
        return name.equalsIgnoreCase("FragmentMasses");
    }
    
    private boolean isFragmentMassTolerance(String name) {
        return name.equalsIgnoreCase("Alg-PreMassTol");
    }
    
    private boolean isPrecursorMassType(String name) {
        return name.equalsIgnoreCase("PrecursorMasses");
    }
    
    private boolean isPrecursorMassTolerance(String name) {
        return name.equalsIgnoreCase("Alg-FragMassTol");
    }
    
    private boolean isStartTime(String name) {
        return name.equalsIgnoreCase("StartTime");
    }
    
    private boolean isEndTime(String name) {
        return name.equalsIgnoreCase("EndTime");
    }
    
    private boolean isStaticModification(String name) {
        return name.equalsIgnoreCase("StaticMod");
    }
    
    private boolean isDynamicModification(String name) {
        return name.equalsIgnoreCase("DiffMod");
    }
    
    private boolean isEnzyme(String name) {
        return name.equalsIgnoreCase("EnzymeSpec");
    }
    
    
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (HeaderItem h: headerItems) {
            buf.append(h.toString());
            buf.append("\n");
        }
        if (buf.length() > 0)
            buf.deleteCharAt(buf.length() -1);
        return buf.toString();
    }
    

    /**
     * @return the headerItems
     */
    public List<HeaderItem> getHeaderItems() {
        return headerItems;
    }

    /**
     * @return the sqtGenerator
     */
    public String getSearchEngineName() {
        return sqtGenerator;
    }

    /**
     * @return the sqtGeneratorVersion
     */
    public String getSearchEngineVersion() {
        return sqtGeneratorVersion;
    }
    
    /**
     * @return the fragmentMassType
     */
    public String getFragmentMassType() {
        return fragmentMassType;
    }

    /**
     * @return the fragmentMassTolerance
     */
    public BigDecimal getFragmentMassTolerance() {
        return fragmentMassTolerance;
    }

    /**
     * @return the precursorMassType
     */
    public String getPrecursorMassType() {
        return precursorMassType;
    }

    /**
     * @return the precursorMassTolerance
     */
    public BigDecimal getPrecursorMassTolerance() {
        return precursorMassTolerance;
    }

    /**
     * @return the staticMods
     */
    public List<StaticModification> getStaticModifications() {
        return staticMods;
    }

    /**
     * @return the dynaMods
     */
    public List<DynamicModification> getDynamicModifications() {
        return dynaMods;
    }

    public List<? extends SQTField> getHeaders() {
       return headerItems;
    }

    public SearchFileFormat getSearchFileFormat() {
        return SearchFileFormat.SQT;
    }

    public List<Database> getSearchDatabases() {
        List<Database> dbList = new ArrayList<Database>(1);
        dbList.add(database);
        return dbList;
    }

    public Date getSearchDate() {
        return getStartDate();
    }

    private Date getStartDate() {
        if (startDate == null) {
            try {
                startDate = new Date(getTime(startTimeString));
            }
            catch (ParseException e) {
                throw new RuntimeException("Error parsing start time: "+startTimeString, e);
            }
        }
        return startDate;
    }
    
    private Date getEndDate() {
        if (endDate == null) {
            try {
                endDate = new Date(getTime(endTimeString));
            }
            catch (ParseException e) {
                throw new RuntimeException("Error parsing end time: "+startTimeString, e);
            }
        }
        return endDate;
    }
    
    public int getSearchDuration() {
        
        // if we don't have the end time return 0
        if (endTimeString == null) {
            searchDuration = 0;
        }
        // calculating for the first time
        else if (searchDuration == -1) {
            long start = getStartDate().getTime();
            long end = getEndDate().getTime();
            searchDuration = (int)((end - start)/(1000*60));
        }
        return searchDuration;
    }

    /**
     * Example of a valid time string: 01/29/2008, 03:34 AM
     * @param timeStr
     * @return
     * @throws ParseException 
     */
    long getTime(String timeStr) throws ParseException {
        return dateFormat.parse(timeStr).getTime();
    }
    
    
    
    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTimeString;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTimeString;
    }
    /**
     * @return the enzyme
     */
    public String getEnzyme() {
        return enzyme;
    }
}

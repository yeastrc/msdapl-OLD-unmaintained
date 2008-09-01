package org.yeastrc.ms;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.yeastrc.ms.dao.BaseDAOTestCase;
import org.yeastrc.ms.dao.DAOTestSuite;
import org.yeastrc.ms.dao.util.NrSeqLookupUtilTest;
import org.yeastrc.ms.domain.impl.MsScanDbImplTest;
import org.yeastrc.ms.parser.ms2File.Ms2FileReaderTest;
import org.yeastrc.ms.parser.prolucidParams.ProlucidParamsParserTest;
import org.yeastrc.ms.parser.sequestParams.SequestParamsParserTest;
import org.yeastrc.ms.parser.sqtFile.SQTParserTests;
import org.yeastrc.ms.service.MsDataUploaderTest;
import org.yeastrc.ms.util.DTASelectFileNameParseTest;
import org.yeastrc.ms.util.PeakStringBuilderTest;
import org.yeastrc.ms.util.PeakUtilsTest;
import org.yeastrc.ms.util.Sha1SumCalculatorTest;

public class MsLibTests {

    public static Test suite() {
        
        TestSuite suite = new TestSuite("Test for org.yeastrc.ms");
        //$JUnit-BEGIN$
        
        BaseDAOTestCase.resetDatabase();
        
        // domain classes
        suite.addTestSuite(MsScanDbImplTest.class);
        
        // dao classes
        suite.addTest(DAOTestSuite.suite());
        
        // parser classes
        suite.addTest(SQTParserTests.suite());
        suite.addTestSuite(Ms2FileReaderTest.class);
        suite.addTestSuite(SequestParamsParserTest.class);
        suite.addTestSuite(ProlucidParamsParserTest.class);
        
        // utility classes
        suite.addTestSuite(PeakStringBuilderTest.class);
        suite.addTestSuite(PeakUtilsTest.class);
        suite.addTestSuite(Sha1SumCalculatorTest.class);
        suite.addTestSuite(DTASelectFileNameParseTest.class);
        suite.addTestSuite(NrSeqLookupUtilTest.class);
        
        // upload classes
        suite.addTestSuite(MsDataUploaderTest.class);
        
        //$JUnit-END$
        return suite;
    }
}

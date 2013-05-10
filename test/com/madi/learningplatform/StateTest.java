package com.madi.learningplatform;

import org.junit.*;
import static org.junit.Assert.*;
import com.mongodb.DB;

/**
 * The class <code>StateTest</code> contains tests for the class <code>{@link State}</code>.
 *
 * @generatedBy CodePro at 5/9/13 11:36 AM
 * @author madi
 * @version $Revision: 1.0 $
 */
public class StateTest {
    /**
     * Run the DB getDatabaseConn() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetDatabaseConn_1()
        throws Exception {

        DB result = State.getDatabaseConn();

        // add additional test code here
        assertNotNull(result);
        assertEquals("learningplatform", result.toString());
        assertEquals("learningplatform", result.getName());
        assertEquals(0, result.getOptions());
        assertEquals(false, result.isAuthenticated());
    }

    /**
     * Run the DB getDatabaseConn() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetDatabaseConn_2()
        throws Exception {

        DB result = State.getDatabaseConn();

        // add additional test code here
        assertNotNull(result);
        assertEquals("learningplatform", result.toString());
        assertEquals("learningplatform", result.getName());
        assertEquals(0, result.getOptions());
        assertEquals(false, result.isAuthenticated());
    }

    /**
     * Run the State getInstance() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetInstance_1()
        throws Exception {

        State result = State.getInstance();

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the MainApp getMainApp() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetMainApp_1()
        throws Exception {

        MainApp result = State.getMainApp();

        // add additional test code here
        assertEquals(null, result);
    }

    /**
     * Run the Collection getSelectedCollection() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetSelectedCollection_1()
        throws Exception {

        Collection result = State.getSelectedCollection();

        // add additional test code here
        assertEquals(null, result);
    }

    /**
     * Run the String getUsername() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetUsername_1()
        throws Exception {

        String result = State.getUsername();

        // add additional test code here
        assertEquals("mada", result);
    }

    /**
     * Run the void setMainApp(MainApp) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetMainApp_1()
        throws Exception {
        MainApp app = new MainApp();

        State.setMainApp(app);

        // add additional test code here
    }

    /**
     * Run the void setSelectedCollection(Collection) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetSelectedCollection_1()
        throws Exception {
        Collection selectedCollection = new Collection();

        State.setSelectedCollection(selectedCollection);

        // add additional test code here
    }

    /**
     * Run the void setUsername(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetUsername_1()
        throws Exception {
        String name = "";

        State.setUsername(name);

        // add additional test code here
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Before
    public void setUp()
        throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(StateTest.class);
    }
}
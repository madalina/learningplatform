package com.madi.learningplatform.service;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.madi.learningplatform.Collection;
import com.madi.learningplatform.CollectionDuplicateException;
import com.madi.learningplatform.CollectionNotFoundException;

/**
 * The class <code>CollectionServiceTest</code> contains tests for the class <code>{@link CollectionService}</code>.
 *
 * @generatedBy CodePro at 5/9/13 11:36 AM
 * @author madi
 * @version $Revision: 1.0 $
 */
public class CollectionServiceTest {
    protected Logger log = Logger.getLogger(CollectionServiceTest.class);
    /**
     * Run the CollectionService() constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testCollectionService_1()
        throws Exception {

        CollectionService result = new CollectionService();
        Assert.assertNotNull(result);
    }

    /**
     * Run the void addCollection(Collection) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testAddCollection_1()
        throws Exception {
        CollectionService fixture = new CollectionService();
        Collection col = new Collection(new ObjectId(), "Test Collection");
        fixture.addCollection(col);
        
        Collection fetchedCol = fixture.getCollection("Test Collection");

        Assert.assertNotNull(fetchedCol);
        Assert.assertEquals(fetchedCol.getName(), "Test Collection");
        fixture.deleteCollection(fetchedCol.getId());
    }
    
    /**
     * Run the void addCollection(Collection) method test for a collection that already exists
     *
     * @throws Exception
     *
     */
    @Test
    public void testAddCollection_2()
        throws Exception {
        CollectionService fixture = new CollectionService();
        Collection col = new Collection(new ObjectId(), "Test Collection");
        fixture.addCollection(col);
        
        boolean throwedException = false;
        try
        {
            fixture.addCollection(col);    
        }
        catch(CollectionDuplicateException ex) {
            throwedException = true;
        }
        
        Assert.assertTrue(throwedException);
        
        Collection fetchedCol = fixture.getCollection("Test Collection");
        fixture.deleteCollection(fetchedCol.getId());
    }

  
    /**
     * Run the void deleteCollection(ObjectId) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testDeleteCollection_1()
        throws Exception {
        CollectionService fixture = new CollectionService();
        Collection col = new Collection(new ObjectId(), "Test Collection");
        fixture.addCollection(col);
        
        Collection fetchedCol = fixture.getCollection("Test Collection");

        Assert.assertNotNull(fetchedCol);
        Assert.assertEquals(fetchedCol.getName(), "Test Collection");
        fixture.deleteCollection(fetchedCol.getId());
        
        boolean throwsException = false;
        try
        {
            fixture.getCollection("Test Collection");
        }
        catch(CollectionNotFoundException ex) {
            throwsException = true;  
        }
        
        Assert.assertTrue(throwsException);
    }

    /**
     * Run the ObservableList<Collection> loadCollections() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testLoadCollections_1()
        throws Exception {
        CollectionService fixture = new CollectionService();
        Collection col = new Collection(new ObjectId(), "Test Collection");
        fixture.addCollection(col);
        
        Collection fetchedCol = fixture.getCollection("Test Collection");

        Assert.assertNotNull(fetchedCol);
        Assert.assertEquals(fetchedCol.getName(), "Test Collection");
        
        fixture.loadCollections();
        Assert.assertTrue(fixture.collections.size() > 0);
        log.info("Loaded " + fixture.collections.size() + " collections from the database");
        
        fixture.deleteCollection(fetchedCol.getId());
    }

    /**
     * Run the void renameCollection(ObjectId,String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testRenameCollection_1()
        throws Exception {
        CollectionService fixture = new CollectionService();
        fixture.addCollection(new Collection(new ObjectId(), "Test Collection"));
        
        Collection fetchedCol = fixture.getCollection("Test Collection");

        Assert.assertNotNull(fetchedCol);
        Assert.assertEquals(fetchedCol.getName(), "Test Collection");
        
        fixture.renameCollection(fetchedCol.getId(), "Renamed Collection");
        
        Assert.assertTrue(fixture.getCollection(fetchedCol.getId()).getName().equals("Renamed Collection"));
        
        fixture.deleteCollection(fetchedCol.getId());
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
        new org.junit.runner.JUnitCore().run(CollectionServiceTest.class);
    }
}
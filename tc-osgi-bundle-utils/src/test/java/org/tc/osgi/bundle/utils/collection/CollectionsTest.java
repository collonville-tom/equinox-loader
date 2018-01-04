package org.tc.osgi.bundle.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.collection.ITransformer;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;

/**
 * CollectionsTest.java.
 *
 * @req STD_BUNDLE_UTILS_010
 * @track SRS_BUNDLE_UTILS_010
 * @track SDD_BUNDLE_UTILS_010
 * @version 0.0.3
 * @author collonville thomas
 */
public class CollectionsTest {

    /**
     * List<Integer> listTest.
     */
    private List<Integer> listTest = null;

    /**
     * setUp.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        listTest = new ArrayList<Integer>();
        listTest.add(0);
        listTest.add(10);
        listTest.add(20);
        listTest.add(30);
        listTest.add(40);
        listTest.add(50);
        listTest.add(60);
    }

    /**
     * testCollect.
     */
    @Test
    public void testCollect() {
        Assert.assertNotNull(CollectionUtilsServiceImpl.getInstance().collect(listTest, new ITransformer<Integer>() {

            @Override
            public void evaluate(final Collection<Integer> c, final Integer e) {
                c.add(e * 10);
            }
        }));
    }

    /**
     * testDetect.
     */
    @Test
    public void testDetect() {
        Assert.assertEquals(true, CollectionUtilsServiceImpl.getInstance().detect(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(10)) {
                    return true;
                }
                return false;
            }
        }));

        Assert.assertEquals(false, CollectionUtilsServiceImpl.getInstance().detect(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(15)) {
                    return true;
                }
                return false;
            }
        }));
    }

    /**
     * testExtract.
     */
    @Test
    public void testExtract() {
        Assert.assertEquals(new Integer(10), CollectionUtilsServiceImpl.getInstance().extract(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(10)) {
                    return true;
                }
                return false;
            }
        }));

        Assert.assertNull(CollectionUtilsServiceImpl.getInstance().extract(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(15)) {
                    return true;
                }
                return false;
            }
        }));
    }

    /**
     * testReject.
     */
    @Test
    public void testReject() {
        Assert.assertEquals(2, CollectionUtilsServiceImpl.getInstance().reject(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e > 15) {
                    return true;
                }
                return false;
            }
        }).size());
    }

    /**
     * testSelect.
     */
    @Test
    public void testSelect() {
        Assert.assertEquals(2, CollectionUtilsServiceImpl.getInstance().select(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e < 15) {
                    return true;
                }
                return false;
            }
        }).size());
    }

}

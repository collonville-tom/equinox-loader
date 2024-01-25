package org.tc.osgi.bundle.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.collection.ITransformer;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnaryTest;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;

import static org.junit.jupiter.api.Assertions.*;


/**
 * CollectionsTest.java.
 *
 * @author collonville thomas
 * @version 0.0.3
 * @req STD_BUNDLE_UTILS_010
 * @track SRS_BUNDLE_UTILS_010
 * @track SDD_BUNDLE_UTILS_010
 */
public class CollectionsTest {


    private List<Integer> listTest;

    @BeforeEach
    public void initList()
    {
        System.out.println("before");
        this.listTest=new ArrayList<>();
        this.listTest.add(0);
        this.listTest.add(10);
        this.listTest.add(20);
        this.listTest.add(30);
        this.listTest.add(40);
        this.listTest.add(50);
    }


     @Test
    public void testCollect() {
        System.out.println(listTest);

        assertNotNull(CollectionUtilsServiceImpl.getInstance().collect(listTest, new ITransformer<Integer>() {

            @Override
            public void evaluate(final Collection<Integer> c, final Integer e) {
                c.add(e * 10);
            }
        }));
    }

    @Test
    public void testDetect() {
        assertEquals(true, CollectionUtilsServiceImpl.getInstance().detect(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(10)) {
                    return true;
                }
                return false;
            }
        }));

        assertEquals(false, CollectionUtilsServiceImpl.getInstance().detect(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(15)) {
                    return true;
                }
                return false;
            }
        }));
    }

    @Test
    public void testExtract() {
        assertEquals(new Integer(10), CollectionUtilsServiceImpl.getInstance().extract(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(10)) {
                    return true;
                }
                return false;
            }
        }));

        assertNull(CollectionUtilsServiceImpl.getInstance().extract(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e.equals(15)) {
                    return true;
                }
                return false;
            }
        }));
    }

    @Test
    public void testReject() {
        assertEquals(2, CollectionUtilsServiceImpl.getInstance().reject(listTest, new IPredicate<Integer>() {

            @Override
            public boolean evaluate(final Integer e) {
                if (e > 15) {
                    return true;
                }
                return false;
            }
        }).size());
    }

    @Test
    public void testSelect() {
        assertEquals(2, CollectionUtilsServiceImpl.getInstance().select(listTest, new IPredicate<Integer>() {

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

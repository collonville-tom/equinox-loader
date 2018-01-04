/**
 */
package org.tc.osgi.bundle.utils.context;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * BundleKillerTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_140
 * @req STD_BUNDLE_UTILS_140
 */
public class BundleKillerTest {

    /**
     * test.
     */
    @Test
    public void test() {
        final String bundleName = "mockBundle";

        final Bundle mockbundle = Mockito.mock(Bundle.class);
        Mockito.when(mockbundle.getSymbolicName()).thenReturn(bundleName);
        final Bundle[] bundles = { mockbundle };
        final BundleContext context = Mockito.mock(BundleContext.class);
        Mockito.when(context.getBundles()).thenReturn(bundles);

        final BundleKiller killer = new BundleKiller();
        try {
            killer.stopBundle(context, bundleName);
            Mockito.verify(mockbundle, Mockito.times(1)).stop();
        } catch (final BundleException e) {
            Assert.fail(e.getMessage());
        }

    }
}

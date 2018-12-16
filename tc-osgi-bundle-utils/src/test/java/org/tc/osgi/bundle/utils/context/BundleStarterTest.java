/**
 */
package org.tc.osgi.bundle.utils.context;

import java.util.Dictionary;

import org.eclipse.osgi.framework.util.Headers;
import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import junit.framework.Assert;

/**
 * BundleStarterTest.java.
 * @author collonville thomas
 * @version 0.2.2
 * @track SDD_BUNDLE_UTILS_125
 * @track SDD_BUNDLE_UTILS_120
 * @req STD_BUNDLE_UTILS_120
 */
public class BundleStarterTest {

    /**
     * test.
     */
    @Test
    public void test() {
        final String bundleName = "mockBundle";
        final String version = "versionBundle";

        Dictionary<String,String> d=new Headers<String,String>(1);
        d.put(BundleStarter.VERSION_H, version);
        
        final Bundle mockbundle = Mockito.mock(Bundle.class);
        Mockito.when(mockbundle.getSymbolicName()).thenReturn(bundleName);
        Mockito.when(mockbundle.getHeaders()).thenReturn(d);
        
        final Bundle[] bundles = { mockbundle };
        final BundleContext context = Mockito.mock(BundleContext.class);
        Mockito.when(context.getBundles()).thenReturn(bundles);

        final BundleStarter starter = new BundleStarter();
        try {
            starter.startBundle(context, bundleName,version);
            Mockito.verify(mockbundle, Mockito.times(1)).start();
        } catch (final BundleException e) {
            Assert.fail(e.getMessage());
        }
    }

}

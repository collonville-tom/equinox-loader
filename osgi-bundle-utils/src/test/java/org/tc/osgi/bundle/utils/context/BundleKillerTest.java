/**
 *
 */
package org.tc.osgi.bundle.utils.context;

import org.eclipse.osgi.framework.util.Headers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import java.util.Dictionary;

import static org.junit.jupiter.api.Assertions.fail;

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
        final String version = "versionBundle";

        Dictionary<String, String> d = new Headers<String, String>(1);
        d.put(BundleStarter.VERSION_H, version);

        final Bundle mockbundle = Mockito.mock(Bundle.class);
        Mockito.when(mockbundle.getSymbolicName()).thenReturn(bundleName);
        final Bundle[] bundles = {mockbundle};
        final BundleContext context = Mockito.mock(BundleContext.class);
        Mockito.when(context.getBundles()).thenReturn(bundles);
        Mockito.when(mockbundle.getHeaders()).thenReturn(d);

        final BundleKiller killer = new BundleKiller();
        try {
            killer.stopBundle(context, bundleName, version);
            Mockito.verify(mockbundle, Mockito.times(1)).stop();
        } catch (final BundleException e) {
            fail(e.getMessage());
        }

    }
}

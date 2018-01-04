/**
 */
package org.tc.osgi.equinox.loader.cmd.context;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;

/**
 * FilterValidBundleCmdTest.java.
 * @author collonville thomas
 * @version 0.1.5
 * @track SRS_EQUINOX_LOADER_050
 * @req STD_EQUINOX_LOADER_050
 */
public class FilterValidBundleCmdTest {

    @Test
    public void test() {
        final Bundle[] tab = {};

        final List<Bundle> bundles = new ArrayList<Bundle>();
        final Bundle b1 = Mockito.mock(Bundle.class);
        Mockito.when(b1.toString()).thenReturn("Bundle1");
        final Bundle b2 = Mockito.mock(Bundle.class);
        Mockito.when(b2.toString()).thenReturn("unknown");
        final Bundle b3 = Mockito.mock(Bundle.class);
        Mockito.when(b3.toString()).thenReturn("Bundle2");

        bundles.add(b1);
        bundles.add(b2);
        bundles.add(b3);

        final BundleContext context = Mockito.mock(BundleContext.class);
        Mockito.when(context.getBundles()).thenReturn(bundles.toArray(tab));

        final FilterValidBundleCmd cmd = new FilterValidBundleCmd(context);
        try {
            cmd.execute();
        } catch (final EquinoxCmdException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }
}

/**
 *
 */
package org.tc.osgi.equinox.loader.cmd.context;

import org.eclipse.osgi.framework.util.Headers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.tc.osgi.bundle.utils.context.BundleStarter;
import org.tc.osgi.equinox.loader.cmd.exception.EquinoxCmdException;
import org.tc.osgi.equinox.loader.conf.EquinoxPropertyFile;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * UninstallBundleCmdTest.java.
 * @author collonville thomas
 * @version
 * @track
 */
public class UninstallBundleCmdTest {

    @Test
    public void test() {

        final String version = "versionBundle";

        Dictionary<String, String> d = new Headers<String, String>(1);
        d.put(BundleStarter.VERSION_H, version);


        final Bundle[] tab = {};
        EquinoxPropertyFile.EQUINOX_LOADER_FILE = "equinox-loader_test";
        final List<Bundle> bundles = new ArrayList<Bundle>();
        final Bundle b1 = Mockito.mock(Bundle.class);
        Mockito.when(b1.toString()).thenReturn("Bundle1");
        Mockito.when(b1.getLocation()).thenReturn("Bundle1");
        Mockito.when(b1.getSymbolicName()).thenReturn("Bundle1");
        Mockito.when(b1.getHeaders()).thenReturn(d);

        final Bundle b2 = Mockito.mock(Bundle.class);
        Mockito.when(b2.toString()).thenReturn("unknown");
        Mockito.when(b2.getLocation()).thenReturn("unknown");
        Mockito.when(b2.getSymbolicName()).thenReturn("unknown");
        final Bundle b3 = Mockito.mock(Bundle.class);
        Mockito.when(b3.toString()).thenReturn("Bundle2");
        Mockito.when(b3.getLocation()).thenReturn("Bundle2");
        Mockito.when(b3.getSymbolicName()).thenReturn("Bundle2");

        bundles.add(b1);
        bundles.add(b2);
        bundles.add(b3);

        final BundleContext context = Mockito.mock(BundleContext.class);
        Mockito.when(context.getBundles()).thenReturn(bundles.toArray(tab));

        final UninstallBundleCmd cmd = new UninstallBundleCmd(context, "Bundle1", "version");

        try {
            cmd.execute();
        } catch (final EquinoxCmdException e) {
            fail(e.getLocalizedMessage());
        }
    }
}

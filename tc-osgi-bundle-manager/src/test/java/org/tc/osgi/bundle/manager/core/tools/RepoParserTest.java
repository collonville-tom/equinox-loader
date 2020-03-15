package org.tc.osgi.bundle.manager.core.tools;


import org.junit.jupiter.api.Test;
import org.tc.osgi.bundle.manager.core.bundle.ITarGzBundle;
import org.tc.osgi.bundle.manager.exception.RepoParserException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.RepoParser;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepoParserTest {


    public static final String TEST_FILE = "./src/test/resources/repository.list";

    public static final String DATA = "./repositories/tc-release-local/org/tc/osgi/bundle/utils/tc-osgi-bundle-utils-interfaces/0.1.0/tc-osgi-bundle-utils-interfaces-0.1.0.tar.gz";
    public static final String DATA2 = "./Repertoire/tc-osgi-bundle-utils-interfaces-0.1.0-SNAPSHOT.tar.gz";

    @Test
    public void testParse() {

        LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        RepoParser parseur = new RepoParser();
        ITarGzBundle b = parseur.bundleBuilder(DATA);
        assertEquals("tc-osgi-bundle-utils-interfaces", b.getName());
        assertEquals("0.1.0", b.getVersion());
        assertEquals(DATA, b.getUrl());

        ITarGzBundle b2 = parseur.bundleBuilder(DATA2);
        assertEquals("tc-osgi-bundle-utils-interfaces", b2.getName());
        assertEquals("0.1.0-SNAPSHOT", b2.getVersion());
        assertEquals(DATA2, b2.getUrl());


    }

    @Test
    public void testLoadFile() throws RepoParserException {
        LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        RepoParser parseur = new RepoParser();
        List<ITarGzBundle> l = parseur.parseRepoList(TEST_FILE);

        assertEquals(7, l.size());

    }


}

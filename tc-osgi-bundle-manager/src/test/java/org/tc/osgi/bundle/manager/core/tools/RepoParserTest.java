package org.tc.osgi.bundle.manager.core.tools;


import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.bundle.manager.core.TarGzBundle;
import org.tc.osgi.bundle.manager.exception.RepoParserException;
import org.tc.osgi.bundle.manager.tools.RepoParser;

public class RepoParserTest {

	
	public static final String TEST_FILE="./src/test/resources/repository.list";
	
	public static final String DATA="./repositories/tc-release-local/org/tc/osgi/bundle/utils/tc-osgi-bundle-utils-interfaces/0.1.0/tc-osgi-bundle-utils-interfaces-0.1.0.tar.gz";
	private Pattern pName=Pattern.compile(".*/(.*)-.*.tar.gz");
	private Pattern pVersion=Pattern.compile(".*-(.*).tar.gz");
	
	@Test
	public void testParse() {
		RepoParser parseur=new RepoParser();
		TarGzBundle b=parseur.bundleBuilder(DATA);
		Assert.assertEquals("tc-osgi-bundle-utils-interfaces", b.getName());
		Assert.assertEquals("0.1.0", b.getVersion());
		Assert.assertEquals(DATA, b.getUrl());
		
	}
	
	@Test
	public void testLoadFile() throws RepoParserException {
		RepoParser parseur=new RepoParser();
		List<TarGzBundle> l=parseur.parseRepoList(TEST_FILE);
		
		Assert.assertEquals(7,l.size());
		
	}
	
	

}

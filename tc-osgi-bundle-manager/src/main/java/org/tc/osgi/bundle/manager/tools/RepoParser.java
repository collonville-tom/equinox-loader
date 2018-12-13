package org.tc.osgi.bundle.manager.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;
import org.tc.osgi.bundle.manager.exception.RepoParserException;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;



public class RepoParser {
	
	private Pattern bundlePattern=Pattern.compile(".*/(.*)-(.*).tar.gz");
	private Pattern snapshotBundlePattern=Pattern.compile(".*/(.*)-(.*-SNAPSHOT).tar.gz");
	
	public static final String SNAPSHOT="SNAPSHOT";

	public RepoParser()
	{
		
	}
	
	public List<TarGzBundle> parseRepoList(String file) throws RepoParserException {
		List<TarGzBundle> bundles=new ArrayList<>();
		try {
			List<String> lurls=Files.readAllLines(new File(file).toPath());
			bundles= parseRepoElement(lurls);
			
		} catch (IOException e) {
			throw new RepoParserException("Erreur dans le parsing du fichier "+file, e);
		}
		
		return bundles;
	}
	
	public TarGzBundle bundleBuilder(String url)
	{
		LoggerServiceProxy.getInstance().getLogger(RepoParser.class).debug("Parsing "+url);
		Matcher bundleMatcher;
		if(url.contains(SNAPSHOT))
			bundleMatcher=snapshotBundlePattern.matcher(url);
		else
			bundleMatcher=bundlePattern.matcher(url);
		bundleMatcher.find();
		return new TarGzBundle(bundleMatcher.group(1),bundleMatcher.group(2),url);
	}
	
	
	public List<TarGzBundle> parseRepoElement(List<String> lurls)
	{
		return lurls.stream().map(x -> this.bundleBuilder(x) )
				.collect(Collectors.toList());
	}

	
	
}

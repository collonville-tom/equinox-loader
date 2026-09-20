package org.tc.osgi.bundle.manager.mbean;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tc.osgi.bundle.manager.core.AbstractRepository;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.core.bundle.ITarGzBundle;
import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;

public class LocalRepository extends AbstractRepository {

	private Pattern bundlePattern = Pattern.compile(".*/(.*)-(.*).tar.gz");
	private Pattern snapshotBundlePattern = Pattern.compile(".*/(.*)-(.*-SNAPSHOT).tar.gz");

	public static final String SNAPSHOT = "SNAPSHOT";

	public LocalRepository(String name, String url) {
		super(name, url);
	}

	public ITarGzBundle bundleBuilder(String url) {
		LoggerServiceProxy.getInstance().getLogger(LocalRepository.class).debug("Parsing " + url);
		Matcher bundleMatcher;
		if (url.contains(SNAPSHOT))
			bundleMatcher = snapshotBundlePattern.matcher(url);
		else
			bundleMatcher = bundlePattern.matcher(url);
		bundleMatcher.find();
		return new TarGzBundle(bundleMatcher.group(1), bundleMatcher.group(2), url);
	}

	@Override
	public void fetch() {
		try {
			List<Path> paths = Files.list(new File(this.getRepositoryUrl() + "/" + this.getRepositoryName()).toPath())
					.collect(Collectors.toList());
			List<ITarGzBundle> bundles = new ArrayList<ITarGzBundle>();
			for (Path p : paths) {

				bundles.add(this.bundleBuilder(p.toString().replace("/var/equinox-loader-manager/local", ".")));

			}
			this.setBundles(bundles);
		} catch (

		Exception e) {
			LoggerServiceProxy.getInstance().getLogger(LocalRepository.class)
					.error("Erreur while fetching local repository", e);
		}

	}

	@Override
	public void pull(String bundle, String version) {
		// TODO Auto-generated method stub

	}

}

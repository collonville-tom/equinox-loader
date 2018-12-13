package org.tc.osgi.bundle.manager.core.internal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.AbstractRepository;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.RepoParser;

public class LocalRepository extends AbstractRepository {

	public LocalRepository(String name, String url) {
		super(name, url);
	}

	@Override
	public void fetch() {
		try {
			List<Path> paths = Files.list(new File(this.getRepositoryUrl()+"/"+this.getRepositoryName()).toPath()).collect(Collectors.toList());
			String file = ManagerPropertyFile.getInstance().getWorkDirectory() + "/"+this.getRepositoryName()+"/"
					+ ManagerPropertyFile.getInstance().getStaticRepositoryFile();
			FileWriter writer = new FileWriter(new File(file));
			for (Path p : paths) {
				if(!p.endsWith(ManagerPropertyFile.getInstance().getStaticRepositoryFile()))
				{
					writer.write(p.toString());
					writer.write("\n");
				}
			}
			writer.close();
			
			RepoParser parseur=new RepoParser();
			this.setBundles(parseur.parseRepoList(file));
		} catch (Exception e) {
			LoggerServiceProxy.getInstance().getLogger(LocalRepository.class)
					.error("Erreur while fetching local repository", e);
		}

	}

	@Override
	public void pull(String bundle, String version) {
		// TODO Auto-generated method stub

	}

}

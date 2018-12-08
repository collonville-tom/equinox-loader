package org.tc.osgi.bundle.manager.core.external;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.AbstractRegistry;
import org.tc.osgi.bundle.manager.module.activator.ManagerActivator;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.Downloader;

import spark.Spark;

// registre des repository distant, permet de consolider l'ensmeble des sources de bundles sous le format tar-gz, 
// et facilite la consulation l'import et l'installation y compris le repo local qui est une sorte de remote repo mais en local
public class RepositoryRegistry extends AbstractRegistry{

	
	private List<RemoteRepository> repositories=new ArrayList<>(); 
	
	public RepositoryRegistry()
	{
		this.initStaticRepository();
	}
	
	private void initStaticRepository() {
		RemoteRepository r=new RemoteRepository("default",ManagerPropertyFile.getInstance().getStaticRepositoryUrl());
		this.repositories.add(r);
	}
	
	public String toString()
	{
		StringBuilder b=new StringBuilder("Repositories:\n");
		for(RemoteRepository r:repositories)
		{
			b.append(r.toString()).append("\n");
		}
		return b.toString();
	}

	@Override
	public void buildRegistryCmd() {
		Spark.get("/fetchRepositories", (request, response) -> this.fetchRepositories());
		
	}

	public String fetchRepositories()
	{
		StringBuilder b=new StringBuilder();
		for(RemoteRepository r: repositories)
		{
			LoggerServiceProxy.getInstance().getLogger(ManagerActivator.class).debug(r.toString());
			r.fetch();
			b.append(r.toString());
		}
		// ici il faudrai faire un moyen de consolider les informations sous la forme xml ou json ou html
		return b.toString();
	}

}

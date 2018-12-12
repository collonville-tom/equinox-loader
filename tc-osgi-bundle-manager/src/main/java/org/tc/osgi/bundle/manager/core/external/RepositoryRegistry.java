package org.tc.osgi.bundle.manager.core.external;

import java.util.ArrayList;
import java.util.List;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.AbstractRegistry;
import org.tc.osgi.bundle.manager.core.internal.LocalRepository;
import org.tc.osgi.bundle.manager.module.activator.ManagerActivator;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.manager.tools.JsonSerialiser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Response;
import spark.Spark;

// registre des repository distant, permet de consolider l'ensmeble des sources de bundles sous le format tar-gz, 
// et facilite la consulation l'import et l'installation y compris le repo local qui est une sorte de remote repo mais en local
public class RepositoryRegistry extends AbstractRegistry{

	private List<RemoteRepository> repositories=new ArrayList<>(); 
	private LocalRepository localRepository=new LocalRepository();
	
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
		Spark.get("/fetchRepositories", (request, response) -> this.fetchRepositories(response));
		Spark.get("/pullRepositories/:tarname", (request, response) -> this.pullRepositories(response,request.params(":tarname")));
		
	}

	private Object pullRepositories(Response response, String tarname) {
		// TODO Auto-generated method stub
		return null;
	}

	public String fetchRepositories(Response response)
	{
		response.type("application/json");
		LoggerServiceProxy.getInstance().getLogger(RepositoryRegistry.class).debug("Fetching remote repository");
		for(RemoteRepository r: repositories)
		{
			r.fetch();
			LoggerServiceProxy.getInstance().getLogger(ManagerActivator.class).debug(r.toString());
		}

		return new JsonSerialiser().toJson(repositories);
	}

}

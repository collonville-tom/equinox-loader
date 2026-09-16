package org.tc.osgi.bundle.manager.mbean;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tc.osgi.bundle.manager.core.AbstractRepository;
import org.tc.osgi.bundle.manager.core.RepositoryManager;
import org.tc.osgi.bundle.manager.core.bundle.ITarGzBundle;
import org.tc.osgi.bundle.manager.core.bundle.TarGzBundle;
import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RemoteRepository extends AbstractRepository {

	public RemoteRepository(String name, String url) {
		super(name, url);
	}

	public void pull(String bundle, String version) {
		// TODO
	}

	private List<ITarGzBundle> parseBundles(JsonNode repoNode) {
		List<ITarGzBundle> bundleList = new ArrayList<>();
		if (repoNode != null) {
			JsonNode bundlesNode = repoNode.get("bundles");
			if (bundlesNode != null && bundlesNode.isArray()) {
				for (JsonNode bNode : bundlesNode) {
					String name = bNode.path("name").asText();
					String version = bNode.path("version").asText();
					String url = bNode.path("url").asText();
					bundleList.add(new TarGzBundle(name, version, url));
				}
			}
		}
		return bundleList;
	}

	public void fetch() {
		try {
			String targetUrl = this.getRepositoryUrl() + "/repository";
			LoggerServiceProxy.getInstance().getLogger(RemoteRepository.class)
					.debug("Downloading repofile on " + targetUrl);
			// Création du client HTTP moderne (Java 11+)
			HttpClient client = HttpClient.newBuilder()
					.connectTimeout(Duration.ofSeconds(5))
					.build();
			// Construction de la requête REST GET avec URI
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(targetUrl))
					.header("Accept", "application/json")
					.GET()
					.timeout(Duration.ofSeconds(5))
					.build();
			// Envoi synchrone de la requête
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(response.body());

				JsonNode localRepoNode = rootNode.get("localRepository");
				if (localRepoNode != null) {
					this.getBundles().addAll(this.parseBundles(localRepoNode));
				}

			} else {
				LoggerServiceProxy.getInstance().getLogger(RemoteRepository.class)
						.error("Erreur HTTP " + response.statusCode() + " lors de l'appel REST sur " + targetUrl);
			}
		} catch (Exception e) {
			LoggerServiceProxy.getInstance().getLogger(RemoteRepository.class)
					.error("Fetching repository " + this.getRepositoryName() + " in error", e);
		}
	}

	public String toString() {
		StringBuilder b = new StringBuilder("[");
		b.append(this.getRepositoryName()).append(",").append("url").append("]\n");
		for (ITarGzBundle bundle : this.getBundles()) {
			b.append(bundle.toString()).append("\n");
		}
		return b.toString();
	}

}

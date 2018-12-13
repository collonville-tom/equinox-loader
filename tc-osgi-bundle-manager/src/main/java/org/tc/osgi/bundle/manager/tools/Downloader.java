package org.tc.osgi.bundle.manager.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.core.external.RemoteRepository;
import org.tc.osgi.bundle.manager.exception.DownloaderException;

public class Downloader {

	public Downloader() {

	}

	public String buildRepoListFileUrl(RemoteRepository repository) {
		StringBuilder urlFile = new StringBuilder();
		urlFile.append(repository.getRepositoryUrl());
		urlFile.append("/");
		urlFile.append(ManagerPropertyFile.getInstance().getStaticRepositoryFile());
		return urlFile.toString();
	}
	
	public String buildDstRepoFileDir(RemoteRepository repository) {
		StringBuilder cacheDir = new StringBuilder();
		cacheDir.append(ManagerPropertyFile.getInstance().getWorkDirectory());
		cacheDir.append("/");
		cacheDir.append(repository.getRepositoryName());
		return cacheDir.toString();
	}
	
	public String buildRepoCacheFile(RemoteRepository repository)
	{
		StringBuilder cacheFile = new StringBuilder();
		cacheFile.append(ManagerPropertyFile.getInstance().getWorkDirectory());
		cacheFile.append("/");
		cacheFile.append(repository.getRepositoryName());
		cacheFile.append("/");
		cacheFile.append(ManagerPropertyFile.getInstance().getStaticRepositoryFile());
		return cacheFile.toString();
	}

	public String getRepoList(RemoteRepository repository) throws DownloaderException {
		String remoteRepoFile = this.buildRepoListFileUrl(repository);
		String cacheDir = this.buildDstRepoFileDir(repository);
		String cacheFile = this.buildRepoCacheFile(repository);
		try {
			Files.createDirectory(new File(cacheDir).toPath());
			this.downloadFile(remoteRepoFile, cacheFile);
			return cacheFile;
		} catch (IOException e) {
			throw new DownloaderException("erreur lors de la creation du repertoire " + cacheDir, e);
		}

	}

	public void downloadFile(String url, String file) throws DownloaderException {
		InputStream in;
		try {
			in = new URL(url).openStream();
			Files.copy(in, Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new DownloaderException("Erreur de chargement du fichier " + file + " a l'url " + url, e);
		}

	}


}

package org.tc.osgi.bundle.manager.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.tc.osgi.bundle.manager.conf.ManagerPropertyFile;
import org.tc.osgi.bundle.manager.exception.DownloaderException;

public class Downloader {

	
	
	public Downloader()
	{
		
	}
	
	public String getRepoList(String urlDepot) throws DownloaderException 
	{
		String urlFile=urlDepot+"/"+ManagerPropertyFile.getInstance().getStaticRepositoryFile();
		String localDir=ManagerPropertyFile.getInstance().getWorkDirectory()+"/"+ManagerPropertyFile.getInstance().getStaticRepositoryFile();
		this.downloadFile(urlFile, localDir);
		return localDir;
	}
	
	private void downloadFile(String url,String file) throws DownloaderException 
	{
		InputStream in;
		try {
			in = new URL(url).openStream();
			Files.copy(in, Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e ) {
			throw new DownloaderException("Erreur de chargement du fichier "+file+ " a l'url "+url,e);
		} 
		
	}
	
}

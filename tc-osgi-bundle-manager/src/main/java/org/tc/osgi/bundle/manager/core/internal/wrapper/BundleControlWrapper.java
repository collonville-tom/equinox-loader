package org.tc.osgi.bundle.manager.core.internal.wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.tc.osgi.bundle.manager.exception.TcEquinoxRegistry;

public class BundleControlWrapper {

	private String packageName;
	public static final String PACKAGE ="Package:";
	private String version;
	public static final String VERSION="Version:";
	private String section;
	public static final String SECTION="Section:";
	private String priority;
	public static final String PRIORITY="Priority:";
	private String architecture;
	public static final String ARCHI="Architecture:";
	private List<String> depends;
	public static final String DEPENDS="Depends:" ;
	private String maintainer;
	public static final String MAINTENER="Maintainer:"; 
	private String homepage;
	public static final String HOMEPAGE="Homepage:";
	private String description;
	public static final String DESCRIPTION="Description:";
	
	
	
	public BundleControlWrapper(String bundleControlFile) throws TcEquinoxRegistry {
		try {
			this.extractProperties(Files.readAllLines(new File(bundleControlFile).toPath()));
			
		} catch (IOException e) {
			throw new TcEquinoxRegistry("Erreur de traitemennt du fichier de control "+bundleControlFile,e);
		}
	}

	
	private void extractProperties(List<String> values)
	{
		for(String s:values)
		{
			isPackage(s);
			isVersion(s);
			isSection(s);
			isPriority(s);
			isArchitecture(s);
			isDepends(s);
			isMaintainer(s);
			isHomePage(s);
			isDescription(s);
		}
	}
	
	private void isDescription(String s) {
		if(s.startsWith(BundleControlWrapper.DESCRIPTION))
		{
			this.description=s.replaceAll(BundleControlWrapper.DESCRIPTION,"");
		}
	}


	private void isHomePage(String s) {
		if(s.startsWith(BundleControlWrapper.HOMEPAGE))
		{
			this.homepage=s.replaceAll(BundleControlWrapper.HOMEPAGE,"");
		}
		
	}


	private void isMaintainer(String s) {
		if(s.startsWith(BundleControlWrapper.MAINTENER))
		{
			this.maintainer=s.replaceAll(BundleControlWrapper.MAINTENER,"");
		}
	}


	private void isDepends(String s) {
		if(s.startsWith(BundleControlWrapper.DEPENDS))
		{
			this.depends=Arrays.asList(s.replaceAll(BundleControlWrapper.DEPENDS,"").split(","));  
		}
	}


	private void isArchitecture(String s) {
		if(s.startsWith(BundleControlWrapper.ARCHI))
		{
			this.architecture=s.replaceAll(BundleControlWrapper.ARCHI,"");
		}
	}


	private void isPriority(String s) {
		if(s.startsWith(BundleControlWrapper.PRIORITY))
		{
			this.priority=s.replaceAll(BundleControlWrapper.PRIORITY,"");
		}
	}


	private void isSection(String s) {
		if(s.startsWith(BundleControlWrapper.SECTION))
		{
			this.section=s.replaceAll(BundleControlWrapper.SECTION,"");
		}
		
	}


	private void isVersion(String s) {
		if(s.startsWith(BundleControlWrapper.VERSION))
		{
			this.version=s.replaceAll(BundleControlWrapper.VERSION,"");
		}
	}


	private void isPackage(String s) {
		if(s.startsWith(BundleControlWrapper.PACKAGE))
		{
			this.packageName=s.replaceAll(BundleControlWrapper.PACKAGE,"");
		}
	}

	public List<String> getDepends() {
		return depends;
	}


	public void setDepends(List<String> depends) {
		this.depends = depends;
	}
	
	
	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getSection() {
		return section;
	}


	public void setSection(String section) {
		this.section = section;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getArchitecture() {
		return architecture;
	}


	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}


	
	public String getMaintainer() {
		return maintainer;
	}


	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}


	public String getHomepage() {
		return homepage;
	}


	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}

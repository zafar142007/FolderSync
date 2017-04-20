package com.zafar.folder.sync.common.file_system;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a file/folder.
 * @author zafar
 *
 */
public class Snapshot implements Serializable{
	
	private static final Logger logger = LoggerFactory.getLogger(Snapshot.class);
	/**
	 * if this Snapshot refers to a file, the hash of the file
	 */
	private long hash=0l;
	/**
	 * path to this file/folder relative to the shared folder
	 */
	private String relativePath;
	/**
	 * both files and folders
	 */
	private Set<Snapshot> children;
	private Snapshot parent;
	private BasicFileAttributes basicFileAttributes;
	public Snapshot(){
		
	}

	public void create(String path){
		File file=new File(path);
		
		try {
			File[] files=file.listFiles();
			for (File f:files){
				addChild(f);				
			}
			for(Snapshot snap:children){
				snap.create(path);
			}
		} catch (Exception e) {
			logger.error("Error: ",e);
		}		
	}
	public long getHash() {
		return hash;
	}

	public void setHash(long hash) {
		this.hash = hash;
	}

	public Set<Snapshot> getChildren() {
		return children;
	}

	public void setChildren(Set<Snapshot> children) {
		this.children = children;
	}

	public void addChild(File file){
		Snapshot snap=new Snapshot();
		snap.setParent(this);
		if(file.isFile())
			snap.setHash(calculateHash(file));
		try {
			snap.setBasicFileAttributes(Files.readAttributes(file.toPath(), BasicFileAttributes.class));
		} catch (IOException e) {
			logger.error("Error",e);
		}
		children.add(snap);		
	}
	private void setBasicFileAttributes(BasicFileAttributes readAttributes) {
		basicFileAttributes=readAttributes;
	}

	private long calculateHash(File file) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Snapshot getParent() {
		return parent;
	}

	public void setParent(Snapshot parent) {
		this.parent = parent;
	}
	

}

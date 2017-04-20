package com.zafar.folder.sync.common.file_system;

public enum FileSystemChange {
	DELETE, INSERT, PROPERTY_CHANGE;	
	private Snapshot snapshot;

	public Snapshot getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(Snapshot snapshot) {
		this.snapshot = snapshot;
	}
	
	
}

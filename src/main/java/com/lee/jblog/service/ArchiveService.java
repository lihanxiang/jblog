package com.lee.jblog.service;

import net.sf.json.JSONObject;

public interface ArchiveService {

    JSONObject getArchiveNameAndSize();

    void addArchiveName(String archiveName);
}

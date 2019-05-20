package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.ArchiveMapper;
import com.lee.jblog.mapper.ArticleMapper;
import com.lee.jblog.service.ArchiveService;
import com.lee.jblog.service.ArticleService;
import com.lee.jblog.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveMapper archiveMapper;
    private final ArticleMapper articleMapper;

    @Autowired
    public ArchiveServiceImpl(ArchiveMapper archiveMapper, ArticleMapper articleMapper) {
        this.archiveMapper = archiveMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public JSONObject getArchiveNameAndSize() {
        List<String> archives = archiveMapper.getArchives();
        JSONArray archiveJsonArray = new JSONArray();
        JSONObject archiveJson;
        TimeUtil timeUtil = new TimeUtil();
        for (String archiveName :
                archives) {
            archiveJson = new JSONObject();
            archiveJson.put("archiveName", archiveName);
            archiveName = timeUtil.yearCovertToTime(archiveName);
            archiveJson.put("archiveSize", articleMapper.getArticleCountByArchive(archiveName));
            archiveJsonArray.add(archiveJson);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", 200);
        resultJson.put("result", archiveJsonArray);
        return resultJson;
    }

    @Override
    public void addArchiveName(String archiveName) {
        int isArchiveExits = archiveMapper.getArchivesCount(archiveName);
        if (isArchiveExits == 0){
            archiveMapper.addArchive(archiveName);
        }
    }
}

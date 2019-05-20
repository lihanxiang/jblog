package com.lee.jblog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArchiveMapper {

    @Select("SELETE archive_name FROM archives ORDER BY id DESC")
    List<String> getArchives();

    @Select("SELETE IFNULL(MAX(id), 0) FROM archives WHERE archive_name = #{archiveName}")
    int getArchivesCount(String archiveName);

    @Insert("INSERT INTO archives (archive_name) VALUES #(archiveName)")
    void addArchive(String archiveName);

}

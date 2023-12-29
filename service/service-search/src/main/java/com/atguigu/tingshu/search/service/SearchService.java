package com.atguigu.tingshu.search.service;

import com.atguigu.tingshu.query.search.AlbumIndexQuery;
import com.atguigu.tingshu.vo.search.AlbumSearchResponseVo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {


    AlbumSearchResponseVo searchAlbum(AlbumIndexQuery albumIndexQuery) ;

    /**
     * 专辑上架
     * @param albumId
     */
    void upperAlbum(Long albumId);


    /**
     * 专辑下架
     * @param albumId
     */

    void lowerAlbum(Long albumId);


    /**
     * 根据一级分类Id获取数据
     * @param category1Id
     * @return
     */
    List<Map<String, Object>> channel(Long category1Id);

    /**
     * 自动补全
     * @param keyword
     * @return
     */
    List<String> completeSuggest(String keyword);

}

package com.myblog.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myblog.article.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}

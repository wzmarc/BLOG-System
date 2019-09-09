package com.itlike.service.impl;

import com.itlike.dao.ArticleDao;
import com.itlike.domian.Article;
import com.itlike.domian.Category;
import com.itlike.domian.PageBean;
import com.itlike.service.ArticleService;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public class ArticleServiceImpl implements ArticleService {
    //注入
    @Setter
    private ArticleDao articleDao;
    @Override
    public List<Article> getAllArticle() {
        //调用dao
        List<Article> allArticle = articleDao.getAllArticle();
        return allArticle;
    }

    @Override
    public PageBean getPageData(DetachedCriteria detachedCriteria, Integer currPage, int pageSize) {
        PageBean<Article> pageBean = new PageBean<>();
         //设置当前页
        pageBean.setCurrentPage(currPage);
        //设置当前一页有多少条数据
        pageBean.setPageSize(pageSize);
        //获取总记录数
        //从数据库当中查询总记录
        Integer totalCount = articleDao.getTotalCount(detachedCriteria);
        pageBean.setTotalCount(totalCount);
        //设置总页数
        pageBean.setTotalPage(pageBean.getTotalPage());
        //设置数据当前页数据
         //查询数据库
        List<Article> list =  articleDao.getPageData(detachedCriteria,pageBean.getIndex(),pageBean.getPageSize());
         //计算
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    @Override
    public List<Category> getCategory(Integer parentid) {
        //调用dao
        List<Category> list =  articleDao.getCategory(parentid);
        return list;
    }

    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Override
    public Article getOneArticle(Integer article_id) {

        Article resArticle = articleDao.getOneArticle(article_id);
        return resArticle;
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }


}

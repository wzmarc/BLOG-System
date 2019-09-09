package com.itlike.web;

import com.itlike.domian.Article;
import com.itlike.domian.Category;
import com.itlike.domian.PageBean;
import com.itlike.service.ArticleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WebAction extends ActionSupport {

    @Setter
    private ArticleService articleService;
    /*获取分页数据*/
    @Setter
    private Integer currPage = 1;
    @Setter
    private Integer parentid;
    @Setter
    private Integer cid;
    //前端分页
    public void getPageList() throws IOException {

        //离线查询条件
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);

        if(parentid != null){
            List<Category> categorys = articleService.getCategory(parentid);
            //构建一个数组
            Object[] cidArrays = new Object[categorys.size()];
            for(int i = 0; i< categorys.size(); i++){
                Category category = categorys.get(i);
                cidArrays[i] = category.getCid();
            }
            //设置条件
            System.out.println(Arrays.toString(cidArrays));
            detachedCriteria.add(Restrictions.in("category.cid",cidArrays));
        }

        if(cid != null){
            detachedCriteria.add(Restrictions.eq("category.cid",cid));
        }

        //调用业务层进行查询
        PageBean pageBean = articleService.getPageData(detachedCriteria,currPage,5);
        JsonConfig jsonConfig = new JsonConfig();
        //hibernate 延时加载
        jsonConfig.setExcludes(new String[]{"handler", "hibernateLazyInitializer"});
        JSONObject jsonObject = JSONObject.fromObject(pageBean,jsonConfig);
        // 将JSON打印到页面:
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonObject.toString());

    }


    //根据id获取指定的文章
    @Setter
    private Integer id;
    public void getDetail() throws IOException {
        Article oneArticle = articleService.getOneArticle(id);
        JsonConfig jsonConfig = new JsonConfig();
        //hibernate 延时加载
        jsonConfig.setExcludes(new String[]{"handler", "hibernateLazyInitializer"});
        JSONObject jsonObject = JSONObject.fromObject(oneArticle,jsonConfig);
        // 将JSON打印到页面:
        ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
    }

}

package com.itlike.web;
import com.itlike.domian.Article;
import com.itlike.domian.Category;
import com.itlike.domian.PageBean;
import com.itlike.service.ArticleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ArticleAction extends ActionSupport implements ModelDriven<Article> {
    private Article article = new Article();
    @Override
    public Article getModel() {
        return article;
    }
    //注入
    @Setter
    private ArticleService articleService;
    public String list(){
        System.out.println("list---------------");
        //调用业务层
        List<Article> allArticle = articleService.getAllArticle();
        System.out.println(allArticle);
        //把数据存取值栈当中,转发到jsp
        ActionContext.getContext().getValueStack().set("allArticle",allArticle);
        return "list";
    }
    /*获取分页数据*/
     @Setter
     private Integer currPage = 1;
     //搜索的关键字
     @Setter
     private String keyWord;
     public String pageList(){
         //离线查询条件
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
         //设置条件
         if(keyWord != null){
             //添加条件
             detachedCriteria.add(Restrictions.like("article_title","%"+keyWord+"%"));
         }
         //调用业务层
         PageBean pageBean = articleService.getPageData(detachedCriteria,currPage,5);
         //数据存到值栈当中
         ActionContext.getContext().getValueStack().push(pageBean);
         return "list";
     }
     /*删除*/
    public String delete(){
        //调用业务层
        Article article2 = new Article();
        article2.setArticle_id(article.getArticle_id());
        articleService.delete(article2);
        return "listres";
    }

    @Setter
    private Integer parentid;
    public  String getCategory() throws IOException {
        //根据parentid查询分类
        List<Category> list = articleService.getCategory(parentid);
        //把查询的结果转成json
        //以json(数据格式) 响应给页面
        JSONArray jsonArray = JSONArray.fromObject(list, new JsonConfig());
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
        return null;
    }

        //添加文章
        /**
         * 文件上传提供的三个属性:
         */
        @Setter
        private String uploadFileName; // 文件名称
        @Setter
        private File upload; // 上传文件
        @Setter
        private String uploadContentType; // 文件类型
        public String add() throws IOException {

            if(upload != null){
                //上传文件
                //随机生成文件名称
                //1.获取文件扩展名  ssh.jpg
                int index = uploadFileName.lastIndexOf(".");
                String etx = uploadFileName.substring(index);
                //2.随机生成文件名  拼接扩展名
                String uuid = UUID.randomUUID().toString();
                String uuidFileName = uuid.replace("-", "") + etx;
                System.out.println(uuidFileName);

                //确定上传的路径
                String path = ServletActionContext.getServletContext().getRealPath("/upload");
                File file = new File(path);
                if(!file.exists()){
                    file.mkdirs();
                }
                //拼接新文件路径
                File desFile = new File(path + "/" + uuidFileName);
                //文件上传
                FileUtils.copyFile(upload,desFile);
                //设置图片
                article.setArticle_pic(uuidFileName);
            }
            //设置当前时间
            article.setArticle_time(new Date().getTime());
            //调用业务层,保存到数据库当中
            articleService.save(article);

        return "listres";
    }

    public String edit(){
        System.out.println(article.getArticle_id());
        //从数据库当中获取当前的文章
        Article resarticle = articleService.getOneArticle(article.getArticle_id());
        //把查询的数据存放到值栈
        ActionContext.getContext().getValueStack().push(resarticle);
        //跳转到编辑界面
         return  "edit";
    }

    //修改
    public String update() throws IOException {
        System.out.println(article);
        //判断有没有新上传的图片
        if(upload != null){
            //确定上传的路径
            String path = ServletActionContext.getServletContext().getRealPath("/upload");
            //获取图片名称
            String picname = article.getArticle_pic();
            //删除原有的图片
            if(picname != null || "".equals(picname)){
                File file = new File(path + picname);
                file.delete();
            }
            //上传文件
            //随机生成文件名称
            //1.获取文件扩展名  ssh.jpg
            int index = uploadFileName.lastIndexOf(".");
            String etx = uploadFileName.substring(index);
            //2.随机生成文件名  拼接扩展名
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid.replace("-", "") + etx;
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            //拼接新文件路径
            File desFile = new File(path + "/" + uuidFileName);
            //文件上传
            FileUtils.copyFile(upload,desFile);
            //设置图片
            article.setArticle_pic(uuidFileName);
        }

        //设置时间
        article.setArticle_time(System.currentTimeMillis());

        //调用业务更新文章
        articleService.update(article);
        return "listres";
    }



}

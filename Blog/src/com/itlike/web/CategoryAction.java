package com.itlike.web;

import com.itlike.domian.Category;
import com.itlike.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;


import java.io.IOException;
import java.util.List;

public class CategoryAction extends ActionSupport implements ModelDriven<Category> {
    private Category category = new Category();
    @Override
    public Category getModel() {
        return category;
    }

    //注入service
    @Setter
    private CategoryService categoryService;
    /*添加分类*/
    public String add(){
        System.out.println("CategoryAction-----");
        System.out.println(category);
        //调用业务层
        categoryService.save(category);
        return "listAction";
    }

    public String list(){
        System.out.println("list--------------");
        //调用业务层 查询所有分类
        List<Category> list =  categoryService.getAllCategory();
        System.out.println(list);
        //把数据存到值栈当中
        ActionContext.getContext().getValueStack().set("categorylist",list);

        return "list";
    }

    public String updateUI() throws IOException {
        System.out.println(category.getCid());
        System.out.println("updateUI-----------------");
        //调用业务层
        Category category2 = categoryService.getOneCategory(category.getCid());
        System.out.println(category2);
        //把数据给页面
        //以json(数据格式) 响应给页面
        JSONArray jsonArray = JSONArray.fromObject(category2, new JsonConfig());
        System.out.println(jsonArray);
        //响应给页面
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());

        return null;
    }

    public String update(){
        System.out.println("update--------------");
        System.out.println(category);
        //调用业务层
        categoryService.update(category);
        return "listAction";
    }

    public String delete(){
        System.out.println(category);
        categoryService.delete(category);
        return "listAction";
    }

}

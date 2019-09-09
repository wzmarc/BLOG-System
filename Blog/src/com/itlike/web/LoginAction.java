package com.itlike.web;

import com.itlike.domian.User;
import com.itlike.service.LoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User>{
    private User user = new User();
    @Override
    public User getModel() {
        return user;
    }

    //注入业务层
    private LoginService loginService;
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    /*登录*/
    public String login(){
        System.out.println("login来了");
        System.out.println(user);
        //登录业务逻辑
        User resUser = loginService.login(user);
        if (resUser == null){
            //错误信息回显
            this.addActionError("用户名或密码错");
            //结果页跳转
            return LOGIN;
        }else {
            //保存用户信息
            ActionContext.getContext().getSession().put("curUser",resUser);
            return SUCCESS;
        }

    }
    /*退出*/
    public String loginout(){
        System.out.println("来到了退出");
        /*清空当前的用户session*/
        ActionContext.getContext().getSession().remove("curUser");
        return "login_out";
    }
}

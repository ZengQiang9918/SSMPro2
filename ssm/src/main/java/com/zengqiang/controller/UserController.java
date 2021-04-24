package com.zengqiang.controller;

import com.zengqiang.bean.Msg;
import com.zengqiang.bean.User;
import com.zengqiang.dao.UserMapper;
import com.zengqiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     *  返回json字符串
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/login")
    public Msg doLogin(User user, String kaptcha, HttpServletRequest req){
        //1.根据user搜索出该用户
        List<User> users = userService.queryUser(user);
        if(users.size()==0){
            //无该用户
            return Msg.fail().add("tip","用户名错误");
        }
        //有该用户且只有此用户一个
        //2.判断密码是否正确
        if(!users.get(0).getPassword().equals(user.getPassword())){
            //密码错误
            return Msg.fail().add("tip","密码错误");
        }
        //密码正确
        //3.获取验证码,确认验证码的正确
        String kaptchaSessionKey= (String) req.getSession().getAttribute("KAPTCHA_SESSION_KEY");
        //删除验证码
        req.getSession().removeAttribute("KAPTCHA_SESSION_KEY");
        if(!kaptchaSessionKey.equalsIgnoreCase(kaptcha)){
            //要用equals方法，不要用==,忽略大小写
            //验证码不相等
            return Msg.fail().add("tip","验证码错误");
        }
        //验证码相等
        return Msg.success().add("tip","验证码正确");
    }

    /**
     * 登录验证成功
     */
    @RequestMapping("/user/loginSuccess")
    public ModelAndView doLoginSuccess(User user,HttpServletRequest req){
        ModelAndView mv = new ModelAndView();
        //查出该用户，查看它的identity
        List<User> users = userService.queryUser(user);
        //System.out.println(users.get(0).getIdentity());
        if(users.get(0).getIdentity().equals("0")){
            //普通员工用户
            //把信息放在session中
            //mv.addObject("user",users.get(0));
            req.getSession().setAttribute("user",users.get(0));
            mv.setViewName("/WEB-INF/views/emp");
        }else if(users.get(0).getIdentity().equals("1")){
            //管理员用户
            //mv.addObject("user",users.get(0));
            req.getSession().setAttribute("user",users.get(0));
            mv.setViewName("/WEB-INF/views/mgr");
        }
        return mv;
    }
}

package com.neuedu.interceptors;


import com.neuedu.common.ServerResponse;
import com.neuedu.consts.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.utils.JsonUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Component
public class AdminAuthroityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html:charset=UTF-8");
        System.out.println("=====preHandle======");
        UserInfo userInfo=(UserInfo)request.getSession().getAttribute(Const.CURRENT_USER);
        if (userInfo==null)
        {  response.reset();
            response.addHeader("Content-Type","text/html;charset=UTF-8");
            PrintWriter printWriter= response.getWriter();
            ServerResponse serverResponse=ServerResponse.createServerResponseByFail(10,"用户未登录,请登录");
            String json= JsonUtils.obj2String(serverResponse);
            printWriter.write(json);
            printWriter.flush();
            printWriter.close();
            return false;

        }
        if (userInfo.getRole()!=0)
        {   response.reset();
            response.addHeader("Content-Type","text/html;charset=UTF-8");
            PrintWriter printWriter= response.getWriter();
            ServerResponse serverResponse=ServerResponse.createServerResponseByFail(1,"没有权限！");
            String json= JsonUtils.obj2String(serverResponse);
            printWriter.write(json);
            printWriter.flush();
            printWriter.close();
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("=====postHandle======");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("====afterCompletion======");
    }
}

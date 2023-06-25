package com.zhengyuan.liunao.tools;

import com.auth0.jwt.interfaces.Claim;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


//@WebFilter(filterName = "JwtFilter", urlPatterns = "/*")
@WebFilter(filterName = "JwtFilter", urlPatterns = "/v1/users/login")
public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("JwtFilter---->init()");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        //设置跨域请求
        //response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8077");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,HEAD,PUT,PATCH");
        response.setHeader("Access-Control-Max-Age", "36000");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization,authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //        属性来校验自定义请求头,在请求头header中设置了了自定义的token字段，所以跨域请求就认为这是一个复杂的请求，他就会先进行预校验，也就是我们说的Options请求，
        //        等Options请求成功之后它才会进行post或者get请求，所以在发送Options请求的时候要校验请求头
        response.setHeader("Access-Control-Allow-Headers","token");
//        response.setHeader("Access-Control-Allow-Headers","token,content-type");

        //获取请求URI
        String requestURI = request.getRequestURI();

        //让行
        /*if(requestURI=="/cargo/show_notInput"||requestURI=="/cargo/show_notOutput"){
            chain.doFilter(request, response);
            System.out.println("66666666666666"+requestURI);
        }*/

        response.setCharacterEncoding("UTF-8");
        /// 从 http 请求头中获取 header里的token
        final String token = request.getHeader("token");
        System.out.println("token是:"+token);
        System.out.println("method"+request.getMethod());


        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            System.out.println("111111111111111111111");
        }
        else {
            // Except OPTIONS, other request should be checked by JWT
            if (token == null) {
                response.getWriter().write("No token!");
                return;
            }

            Map<String, Claim> userData = JwtUtil.verifyToken(token);
            if (userData == null) {
                response.getWriter().write("token不合法！");
                System.out.println("4444444444444444444444444");
                return;
            }
            String id = userData.get("id").asString();
//            String userName = userData.get("userName").asString();
            String password= userData.get("password").asString();
            //拦截器 拿到用户信息，放到request中
            request.setAttribute("id", id);
//            request.setAttribute("userName", userName);
            request.setAttribute("password", password);
            chain.doFilter(req, res);
            System.out.println("555555555555555555555"+id);
        }
    }

    @Override
    public void destroy() {
    }
}
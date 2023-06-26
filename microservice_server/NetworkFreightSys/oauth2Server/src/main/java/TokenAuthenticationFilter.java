import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.security.util.SecurityConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //根据clientId查询用户信息 省略数据库用户信息查询
        String  username="admin";
        String password="admin";
        //账号密码认证
        //JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(username, password);
        //String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjIxMjMyZjI5N2E1N2E1YTc0Mzg5NGEwZTRhODAxZmMzIiwiaWQiOiJhZG1pbiIsImV4cCI6MTY4NzUxMTkxOSwiaWF0IjoxNjg3NTEwMTE5fQ.VxTa2Xhvey66bUq_YnhpXwO79F65McX3Hhlunem05Q4";
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 通过authenticationManager
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 认证成功后将认证信息存储到SpringSecurity上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //String token = request.getHeader(HttpConstant.Header.AUTHENTICATION);
        filterChain.doFilter(request, response);
        //在过滤器中添加return，是为了结束该方法的运行，也就是结束过滤
        return;
    }
}

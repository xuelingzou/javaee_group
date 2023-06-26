import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
//    @Autowired
//    private MyLogOutSuccessHandler logOutSuccessHandler;
//    @Autowired
//    private SimpleAccessDeniedHandler simpleAccessDeniedHandler;
//    @Autowired
//    private SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * http请求拦截认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.csrf().disable();
        http.authorizeRequests()
                .and()
                .formLogin() //新增login form支持用户登录及授权
                //自定义登录页面
                .loginPage("http://localhost:8066/")
                //认证成功跳转的页面
                .successForwardUrl("http://localhost:8066/")
                .failureForwardUrl("http://localhost:3000/login")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://localhost:8066/")
//                .logoutSuccessHandler(logOutSuccessHandler)
                .permitAll()

                //wagger 相关请求放行
                .and()
                .authorizeRequests()
                //oauth相关请求放行
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/logout/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/swagger-ui/*").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()

                // 其他所有请求需要身份认证
                .anyRequest().authenticated();

                //配置自定义异常处理
//                .and().exceptionHandling()
//                .accessDeniedHandler(simpleAccessDeniedHandler)
//                .authenticationEntryPoint(simpleAuthenticationEntryPoint);

        // 添加自定义  过滤器  ，放置在身份认证过滤器前
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        // 使用自定义登录身份认证组件
//        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
//    }


    /**
     * AuthenticationManager对象在OAuth2认证服务中要使用，提前放入IOC容器中
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
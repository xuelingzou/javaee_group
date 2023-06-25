import axios from "axios"

import routerIndex from '../router/index'

let token = '';


/*
* 用于解决前后端连接的跨域问题
* */

    const request = axios.create({
        // baseURL: '/api', // 通过/api别名指定后端路由
        // baseURL: process.env.VUE_APP_URL, // 通过/api别名指定后端路由
        timeout: 5000,
        headers:{
        },
    })


request.defaults.withCredentials = false;
request.defaults.headers.common['token'] = token;
request.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';//配置请求头


    // axios的拦截器(类似python的中间件的request)
    // 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
    request.interceptors.request.use(config => {
        let access_user = window.sessionStorage.getItem('access-user')
        let user = JSON.parse(access_user);
        if (user != null) {
            token = user.token;
        }

        config.headers.token = token;


        //设置JSON数据格式
        config.headers['Content-Type'] = 'application/json;charset=utf-8';

        // if (config.data instanceof FormData) {
        //     Object.assign(config.headers, config.data.getHeaders());
        // }

        return config
    }, err => {
        console.log(err);
    });


    // 数据返回拦截
    // 可以在接口响应后统一处理结果
    request.interceptors.response.use(response => {
            //提取响应数据
            let res = response.data;

        if (response.data && response.data.code) {
            if (parseInt(response.status) === 401 ) {
                //登录超时
                console.log(response.data.msg)
                alert("登录信息已失效，请重新登录")
                routerIndex.push('/');
            }
            if (parseInt(response.data.code) === -1) {
                console.log("请求失败");
            }
        }
        // 多用于登录时的cookies判断
        return res
    }, err => {
        console.log(err);
        let  response = err.response
        if (parseInt(response.status) === 401 ) {

            console.log("登录信息已失效，请重新登录")
            alert("登录信息已失效，请重新登录")
            routerIndex.push('/');
        }
    })
    // // 直接返回
    // return request(config)

// }





export default request;
//     export default axios;


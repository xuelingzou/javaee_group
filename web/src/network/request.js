import axios from "axios"
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

    // axios的拦截器(类似python的中间件的request)
    // 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
    request.interceptors.request.use(config => {
        // if(config.requestBase==='VUE_APP_URL'){
        //     //设置JSON数据格式
        //     config.headers['Content-Type'] = 'application/json;charset=utf-8';
        //
        // }else if (config.requestBase==='VUE_APP_URL_TWO'){
        //     //设置JSON数据格式
        //     config.headers['Content-Type'] = 'application/json;charset=utf-8';
        //     config.baseURL=process.env.VUE_APP_URL_TWO;
        //     // config.data=JSON.stringify(config.data);
        // }


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
    request.interceptors.response.use(
        response => {
            //提取响应数据
            let res = response.data;

            // // 服务端返回的字符串数据时
            // if (typeof res === 'string') {
            //     res = res ? JSON.parse(res) : res
            // }

        // 多用于登录时的cookies判断
        return res
    }, err => {
        console.log(err);
    })
    // // 直接返回
    // return request(config)

// }





export default request;
//     export default axios;


const { defineConfig } = require('@vue/cli-service')
// const webpack = require('webpack')
module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {                //记住，别写错了devServer//设置本地默认端口  选填
    port:8082,

    open: true, // 是否自动打开
    proxy: {                 //设置代理，必须填
      '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
        target: 'http://localhost:8077',     //代理的目标地址
        changeOrigin: true,              //是否设置跨域，输入是的
        pathRewrite: {                   //路径重写
          '^/api': ''                     //选择忽略拦截器里面的内容
        }
      }

    }
  },
  // configureWebpack: {
  //   plugins: [
  //     new webpack.ProvidePlugin({
  //       $: "jquery",
  //       jQuery: "jquery",
  //     })
  //   ]
  // }

})

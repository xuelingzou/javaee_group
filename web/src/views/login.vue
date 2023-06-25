<template>

    <div class="layui-unselect lau-sign-body"
      style="background-image: url('../Sys/images/mybg.jpg'); background-repeat: no-repeat; ">
     <form action="www.baidu.com" class="layui-form" id="myForm">
         <div class="layui-form layui-form-pane lau-sign-form">
             <h1 class="lau-sign-title">云上网络货运平台</h1>
             <div class="layui-form-item">
                 <label class="layui-form-label"><i
                         class="iconfont layui-icon-extend-bianhao"></i> 账号</label>
                 <div class="layui-input-block">
                     <input type="text" name="num" placeholder="请输入账号"
                            autocomplete="off" class="layui-input">
                 </div>
             </div>
             <div class="layui-form-item">
                 <label class="layui-form-label"><i
                         class="layui-icon layui-icon-password"></i> 密码</label>
                 <div class="layui-input-block">
                     <input type="password" name="psw" placeholder="请输入密码"
                            autocomplete="off" class="layui-input">
                 </div>
             </div>


             <div class="layui-form-item" pane>
                 <label class="layui-form-label" id="myradio"><i
                         class="iconfont layui-icon-extend-ziyuan"></i>身份</label>
                 <div class="layui-input-block" >
                     <input type="radio" name="identify" value="0" title="管理员"
                            lay-filter="iden" style="margin: 0px 0px 0 0;padding-right: 0;" />
                     <input type="radio" name="identify"
                            value="1" title="承运商" lay-filter="iden" style="margin: 0 0 0 0;padding-right: 0;"/>
                     <input type="radio"
                            name="identify" value="2" title="客户" lay-filter="iden" style="margin: 0 0 0 0;padding-right: 0;"/>
                 </div>

             </div>

             <div class="layui-form-item" id="loginsub">
                 <button type="button" class="layui-btn layui-btn-fluid" lay-submit
                         lay-filter="login">登 录
                 </button>
             </div>

             <div class="layui-form-item lau-sign-other">
                 <el-link type="text"  @click="goToRes" class="lau-sign-reg lau-sign-link">还没有账号？点击这里注册</el-link>

             </div>

         </div>
     </form>

 </div>

</template>

<!--<script>-->
<!--    // import axios from "axios"-->
<!-- // import {layui} from ""= "";-->

<!--export default {-->
<!--    // eslint-disable-next-line vue/multi-word-component-names-->
<!--    name: 'login',-->
<!--    // props: {-->
<!--    //   msg: String-->
<!--    // }-->
<!--    data: function () {-->
<!--        return {-->
<!--            username: '',-->
<!--            password: ''-->
<!--        }-->
<!--    },-->


<!--};-->


<!--</script>-->


<script >

import request from "../network/request";
import router from "../router"


    let idendata = "";
    const layui = window.layui;
    export default {
        // eslint-disable-next-line vue/multi-word-component-names
        name: 'login',
        // props: {
        //   msg: String
        // }
        data: function () {
            return {

            }
        },
        // setup () {
        //     // const store = useStore();
        //     // return {
        //     //     // 在 computed 函数中访问 state
        //     //     getId: computed(() => store.state.userInfo.id),
        //     //
        //     //     // 在 computed 函数中访问 getter
        //     //     getIdentify: computed(() => store.state.userInfo.identify),
        //     //     setInfo: (num,identify) =>store.commit('setInfo', num,identify)
        //     // }
        //
        //
        // },
        mounted () {

          //   清空用户缓存，特别是token数据
          window.sessionStorage.clear()

            // this.$nextTick(() => {
                const layui = window.layui;
                layui.use('form', function() {
                    var form = layui.form;
                    //监听提交
                    form.on('submit(login)', function(data) {
                        var loginjson = JSON.stringify(data.field);
                        var logindata = JSON.parse(loginjson);
                        var identify = logindata.identify;
                        let num = logindata.num;
                        let psw = logindata.psw;                        // alert(identify)

                        if (identify === '0' || identify === '1' || identify === '2') {
                            request.post(
                                // "http://localhost:8080/Sys/dealLogin",
                                "/api/user/Sys/v1/users/login",
                                {num,psw,identify}
                            ).then(data => {
                                console.log(data)
                                //接受数据
                                // let  data = res.data;
                                if (data !== "fail") {
                                    layer.msg("登录成功！！！", function() {

                                      //保存用户信息
                                      window.sessionStorage.setItem("access-user",JSON.stringify(data))

                                      if(identify ==='0'){
                                        // location.href="/adminIndex"
                                        router.push({path:'/adminIndex',query: {account:num,name:data.name,identify:identify}})
                                      }else if(identify === '1'){
                                        router.push({path:'/companyIndex',query: {id:num,name:data.name,identify:identify}})

                                        // location.href="/companyIndex"
                                      }else if(identify === '2'){
                                        // this.$router.push({path:'/clientIndex',query: {id:num,identify:identify}})
                                        router.push({path:'/clientIndex',query: {id:num,name:data[0].name,identify:identify}})
                                        // location.href="/clientIndex"

                                      }
                                    });//layer.msg
                                } else {
                                    layui.use('layer', function() {
                                        var layer = layui.layer;
                                        layer.open({
                                            type : 4,
                                            content : [ "账号或者密码或者身份不匹配",
                                                "#loginsub" ],
                                            shade : 0,
                                            tips : [ 2, '#cc0000' ],
                                            closeBtn : 0,
                                            time : 2000,

                                        })
                                    });
                                }
                                })

                        } else {
                            layui.use('layer', function() {
                                var layer = layui.layer;
                                layer.open({
                                    type : 4,
                                    content : [ "没有选择身份", "#myradio" ],
                                    shade : 0,
                                    tips : [ 4, '#91d' ],
                                    closeBtn : 0,
                                    time : 2000,

                                })
                            });
                        }

                    });
                });

                layui.use('form', function() {
                    var form = layui.form;
                    form.on('radio(iden)', function(data) {
                        idendata = data.value;
                    });
                });

            // })//this.$nextTick

        },
        methods:{
             goToRes:function() {
    layui.use('layer', function() {
        let layer = layui.layer;
        if (idendata == null || idendata === "") {
            layer.open({
                type : 4,
                content : [ "没有选择身份", "#myradio" ],
                shade : 0,
                tips : [ 4, '#91d' ],
                closeBtn : 0,
                time : 2000,
            })

        } else if (idendata === '0') {
            layer.open({
                type : 4,
                content : [ "管理员不能被注册", "#myradio" ],
                shade : 0,
                tips : [ 4, '#91d' ],
                closeBtn : 0,
                time : 2000,
            });
        }
        else if (idendata === '1'){
            layer.open(
                {
                    type: 2,
                    title: '承运商注册',
                    skin: 'layui-layer-lan',
                    shadeClose: false,
                    shade: 0.8,
                    area:  ['700px', '220px'],
                    resize:true,
                    content:'/companyAdd',
                    end: function(){
                        window.location.reload(); //刷新父页面
                    }
                });
        }
        else if (idendata === '2'){
            layer.open(
                {
                    type: 2,
                    title: '客户注册',
                    skin: 'layui-layer-lan',
                    shadeClose: false,
                    shade: 0.8,
                    area:  ['700px', '220px'],
                    resize:true,
                    content:'/clientAdd',
                    end: function(){
                        window.location.reload(); //刷新父页面
                    }
                });
        }

    });


},




}

    };



</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    /*@import url("../Sys/css/layui.css");*/
    /*@import url("../Sys/css/sign.css");*/
    /*@import url("../Sys/css/font.css");*/
    /*@import url("../Sys/css/xadmin.css");*/
    /*@import url("../Sys/js/css/modules/layui-icon-extend/iconfont.css");*/

</style>





<template>
    <div class="layui-unselect lau-sign-body" style="padding-top: 0px " >

    <form action="" class="layui-form">

        <div class="layui-form-item" >
            <div class="layui-inline">
                <label class="layui-form-label" ><i class="iconfont layui-icon-extend-bianhao" id="num" >ID</i></label>
                <div class="layui-input-block">
                    <input type="text" name="coid" placeholder="请输入承运商ID" autocomplete="off" class="layui-input" id="inputnum">
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><i class="iconfont layui-icon-extend-ziyuan"></i>名称</label>
                <div class="layui-input-block">
                    <input type="text" name="coName" placeholder="请输入承运商名称" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label"><i class="layui-icon layui-icon-password"></i>密码</label>
                <div class="layui-input-block">
                    <input type="password" name="psw" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>


            <div class="layui-inline">
                <label class="layui-form-label"><i class="iconfont layui-icon-extend-icon-test"></i>手机号</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
                </div>
            </div>

        </div>

        <div class="layui-form-item lau-sign-other" style="margin: 20px;text-align:center">
            <button type="button" class="layui-btn layui-btn-normal" lay-submit lay-filter="register" style="">提交</button>
        </div>

    </form>

    </div>
</template>

<script>
    import request from "@/network/request";
    import { useStore } from 'vuex';
    export default {
        name: "companyAdd",
        mounted(){
            this.$nextTick(()=>{

                layui.use(['jquery','form'], function() {

                    var form = layui.form;
                    //监听提交
                    form.on('submit(register)', function(data) {
                        var loginjson=(data.field);

                        data=JSON.stringify(loginjson)

                        alert(data);

                        request.post(
                            "/api/Sys/v1/companies",
                            data,
                            'json',
                            'application/json'
                        ).then(res=>{
                            console.log(res);
                            if(res.msg==="success"){
                                layer.msg("成功！",function(){

                                });
                            }else {
                                layer.msg("失败，账号已经存在或者信息未填完整！",function(){

                                });
                            }
                        }).catch(e=>{
                            // console.log(e);
                            layer.msg("失败，账号已经存在或者信息未填完整！",function(){

                            });
                        });


                    });
                });


            })
        },
    }
</script>

<style scoped>

</style>
<template>
    <div class="layui-unselect lau-sign-body" style="padding-top: 0px">
    <form action="www.baidu.com" class="layui-form">

        <div class="layui-form-item" >
            <div class="layui-inline">
                <label class="layui-form-label" ><i class="iconfont layui-icon-extend-bianhao" id="num" >承运商ID</i></label>
                <div class="layui-input-block">
                    <!--   不可编辑     -->
                    <input disabled="disabled" style="background:#eeeeee" type="text" name="coid" id="coid" v-model="company.coid" placeholder="请输入承运商ID" autocomplete="off" class="layui-input" >
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><i class="iconfont layui-icon-extend-ziyuan"></i>承运商名称</label>
                <div class="layui-input-block">
                    <input type="text" name="coName" id="coName" v-model="company.coName" placeholder="请输入承运商名称" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label"><i class="layui-icon layui-icon-password"></i>密码</label>
                <div class="layui-input-block">
                    <input type="password" name="psw" id="psw" v-model="company.psw" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>


            <div class="layui-inline">
                <label class="layui-form-label"><i class="iconfont layui-icon-extend-icon-test"></i>手机号</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" id="phone" v-model="company.phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
                </div>
            </div>

        </div>


        <div class="layui-form-item lau-sign-other" style="margin-top: 20px;text-align:center">
            <button type="button" class="layui-btn layui-btn-normal" lay-submit lay-filter="updateStu" style="margin-right: 100px">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交</button>

        </div>

    </form>



    </div>
</template>

<script>

    import request from "@/network/request";
    import router from "@/router";

    //取传入的路径上的参数
    function RequestParameter(){
        var url = window.location.search; //获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            var strs = str.split("&");
            for(var i = 0; i < strs.length; i ++) {
                theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
            }
        }
        return theRequest
    }

    export default {
        name: "coModi",
        data(){
            return{
                company:{
                    coid:"",
                    coName:"",
                    phone:"",
                    psw:""
                }

            }
        },
        mounted() {

            var num=RequestParameter()["num"];
            console.log(num)

            // var num= [[${num}]];
            //根据客户ID获取信息
            request.get(
                '/api/user/Sys/v1/companies/coid/' + num,
            ).then(res=>{
                console.log(res)
                let  data = res.data[0]
                this.company.coid=data.coid
                this.company.coName = data.coName
                this.company.phone = data.phone
            })


            layui.use('form', function() {
                var form = layui.form;
                //监听提交
                form.on('submit(updateStu)', function(data) {
                    var updateJson= data.field;
                    updateJson.oldNum = num;
                    // updateJson.photo=imgpath;
                    data=JSON.stringify(updateJson)
                    // console.log(data)


                    request.put(
                        '/api/user/Sys/v1/companies',
                        data
                        // updateJson

                    ).then(res=>{
                        console.log(res)
                        if(res.msg==="success"){
                            layer.msg("修改成功！",function(){

                            });
                        }
                    }).catch(e=>{
                        // console.log(e);
                        layer.msg("修改的承运商ID已经存在，修改失败！",function(){

                        });
                    });



                });
            });
        },
        methods:{



        }
    }
</script>

<style scoped>

</style>
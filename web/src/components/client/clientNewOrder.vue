<template>
    <div class="layui-unselect lau-sign-body" style="padding-top: 0px ; height:586px; overflow:auto" >

    <form class="layui-form layui-form-pane" action="">
        <!--  发件人卡片 -->
        <div class="layui-card" style="margin: 15px;">
            <div class="layui-card-header">发件人信息</div>
            <div class="layui-card-body">

                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="senderName" required  lay-verify="required" placeholder="请输入发件人姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">电话</label>
                    <div class="layui-input-block">
                        <input type="text" name="senderPhone" required  lay-verify="required" placeholder="请输入发件人电话" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">发件地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="departure" required  lay-verify="required" placeholder="请输入发件人地址" autocomplete="off" class="layui-input">
                    </div>
                </div>

            </div>
        </div>

        <!--  收件人卡片-->
        <div class="layui-card" style="margin: 15px;">
            <div class="layui-card-header">收件人信息</div>
            <div class="layui-card-body">

                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="receiveName" required  lay-verify="required" placeholder="请输入收件人姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">电话</label>
                    <div class="layui-input-block">
                        <input type="text" name="receivePhone" required  lay-verify="required" placeholder="请输入收件人电话" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">收件地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="destination" required  lay-verify="required" placeholder="请输入收件人地址" autocomplete="off" class="layui-input">
                    </div>
                </div>

            </div>
        </div>

        <!--货物卡片-->
        <div class="layui-card" style="margin: 15px;">
            <div class="layui-card-header">货物信息</div>
            <div class="layui-card-body">

                <div class="layui-form-item">
                    <label class="layui-form-label">货物品名</label>
                    <div class="layui-input-block">
                        <input type="text" name="cargoType" required  lay-verify="required" placeholder="请输入货物品名" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">重量</label>
                    <div class="layui-input-inline" style="width: 85%;">
                        <input type="number" name="weight" required lay-verify="required" placeholder="请输入货物重量" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid layui-word-aux">千克</div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">体积</label>
                    <div class="layui-input-inline" style="width: 85%;">
                        <input type="number" name="volume" required lay-verify="required" placeholder="请输入货物体积" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid layui-word-aux">立方米</div>
                </div>

            </div>
        </div>

        <!--  提交按钮-->
        <div class="layui-form-item">
            <div style=" align-items: center;display: flex;justify-content: center;">
                <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
<!--                <el-button type="primary" @click="goBack" style="margin-left: 10px;">返回</el-button>-->
            </div>
        </div>


    </form>




    </div>
</template>

<script>
    import router from "@/router";
    import request from "@/network/request";

    export default {
        name: "clientNewOrder",
        mounted() {
            let query = router.currentRoute.query
            let ceid  = query.id
            let layui = window.layui
            layui.use('form', function(){
                var form = layui.form;

                //监听提交
                form.on('submit(formDemo)', function(data){
                    // layer.msg(JSON.stringify(data.field));
                    var data_json = JSON.stringify(data.field)


                    request.post(
                        "/api/order/Sys/v1/orders/"+ceid,
                        data_json,
                        'json',
                        'application/json'
                    ).then(res=>{
                        console.log(res)

                        layer.msg(res.msg);
                        var logistics ={
                            location:"创建订单",
                            oid:""
                        }

                        //向服务端发送指令,添加物流信息
                        // request.post(
                        //     '/api/Sys/v1/logistics',
                        //     logistics
                        //
                        // ).then(res=>{
                        //     console.log(res)
                        //
                        //     if(res.msg==="提交物流状态成功") {
                        //
                        //         parent.layer.msg(res.msg, {icon: 1, time: 2000, shade: 0.2});
                        //         // window.location.reload(true);
                        //     }
                        // }).catch(e=>{
                        //     console.log(e);
                        //     parent.layer.msg('提交物流状态失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
                        // });

                    })
                    return false;//设置不跳转表单
                });
            });
        },
        methods:{
             goBack:function() {
                 let query = router.currentRoute.query
                // console.log(query)
                router.push({path:"/clientIndex",query:query})

            }
        }
    }
</script>

<style scoped>

</style>
<template>
    <div class="block" style="padding: 20px">
        <div class="radio" style="height: 40px">
            排序：
            <el-radio-group v-model="reverse">
                <el-radio :label="true">倒序</el-radio>
                <el-radio :label="false">正序</el-radio>
            </el-radio-group>
        </div>

        <el-timeline :reverse="reverse">
            <el-timeline-item
                    v-for="(activity, index) in activities"
                    :key="index"
                    :timestamp="activity.recordTime">
                {{activity.location}}
            </el-timeline-item>
        </el-timeline>
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
        name: "logisticsInfo",
        data() {
            return {
                reverse: true,
                activities: []
            };
        },
        mounted(){
            var oid = RequestParameter()["oid"];


            //加载数据
            // /v1/logistics/{oid}
            //向服务端发送指令
            request.get(
                '/api/order/Sys/v1/logistics/'+oid,

            ).then(res=>{
                console.log(res)
                this.activities = res.data;

            }).catch(e=>{
                console.log(e);
                parent.layer.msg('提交物流状态失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
            });

        },
    }
</script>

<style scoped>

</style>
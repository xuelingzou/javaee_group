<template>
    <div style="padding: 30px">
        <!--     弹窗样式      -->
<!--        <el-dialog title="新增物流信息"  width="30%">-->
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="物流信息">
                    <el-input v-model="logistics.location" ></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addLogistics"  style="width: 30%;float: right">确 定</el-button>
               </span>
<!--        </el-dialog>-->
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
        name: "addLogistics",
        data(){
            return{
                logistics:{
                    location:"",
                    oid:""
                }

            }
        },

        methods:{
            addLogistics(){
                //获取oid
                this.logistics.oid = RequestParameter()["oid"];
                console.log("oid",this.logistics.oid)

                //向服务端发送指令
                request.post(
                    '/api/Sys/v1/logistics',
                    this.logistics

                ).then(res=>{
                    console.log(res)

                    if(res.msg==="提交物流状态成功") {

                        parent.layer.msg(res.msg, {icon: 1, time: 2000, shade: 0.2});
                        // window.location.reload(true);
                    }
                }).catch(e=>{
                    console.log(e);
                    parent.layer.msg('提交物流状态失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
                });
            },
        }
    }
</script>

<style scoped>

</style>
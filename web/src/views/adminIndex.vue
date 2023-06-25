<template xmlns:th="http://www.w3.org/1999/xhtml">
    <div>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo">
            <a href="./index.html">
                网络货运平台</a>
        </div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
        <ul class="layui-nav right" >
            <li class="layui-nav-item"><a href="javascript:">当前用户:
                <span>{{ $route.query.name }}</span>


            </a>
                <dl class="layui-nav-child">
                    <!-- 二级菜单 -->

                    <dd>
                        <a href="/">切换帐号</a>
                    </dd>
                    <dd>
                        <a href="/">退出</a>
                    </dd>
                </dl></li>
        </ul>
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
    <!-- 左侧菜单开始 -->
    <div class="left-nav">
        <div id="side-nav" style="width: 100%; height: 57%">
            <ul id="nav">

                <li>
                    <a href="javascript:;"> <i class="iconfont">&#xe726;</i>
                        <cite>用户管理</cite> <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="nav">
                        <li><a @click="showComponent=1"> <i class="iconfont">&#xe6a7;</i>
                            <cite>已注册客户管理</cite>
                        </a></li>


                        <li><a @click="showComponent=2"> <i class="iconfont">&#xe6a7;</i>
                            <cite>已注册承运商管理</cite>
                        </a></li>
                    </ul>
                </li>

                <li>
                    <a href="javascript:;"> <i class="layui-icon " style="font-size: 20px; font-weight: 600">&#xe63c;</i>
                        <cite>订单管理</cite> <i class="iconfont nav_right ">&#xe697;</i>
                    </a>
                    <ul class="nav">
                        <li><a @click="showComponent=3"> <i class="iconfont">&#xe6a7;</i>
                            <cite>订单查询</cite>
                        </a></li>
                    </ul>
                </li>

            </ul>

        </div>
        <div
                style="
			/*background-image: url('/Sys/images/bg2.jpg'); */
			width: 100%; height: 43%; bottom: 0; background-repeat: no-repeat;"></div>
    </div>
    <!--	 <div class="x-slide_left"></div>-->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
            <ul class="layui-tab-title">

            </ul>
<!--     主体部分       -->
            <div class="layui-tab-content">
                <clienInfo v-if="showComponent===1"></clienInfo>
                <companyInfo v-if="showComponent===2"></companyInfo>
                <adminOrderSearch v-if="showComponent===3"></adminOrderSearch>

            </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright" style="text-align: center;">欢迎来到网络货运平台！</div>
    </div>

    </div>
</template>

<script>
    import clienInfo from "@/components/admin/userInfo/client/clientInfo";
    import clientModi from "@/components/admin/userInfo/client/clientModi";

    import companyInfo from "@/components/admin/userInfo/company/companyInfo";


    import adminOrderSearch from "@/components/admin/orderManage/adminOrderSearch";
    import router from "../router";
    export default {
        name: "adminIndex",
      methods: {
        router() {
          return router
        }
      },
        data(){
            return{
                showComponent:0,  //用于显示组件
            }

        },
        components:{
            clienInfo,
            clientModi,
            companyInfo,

            adminOrderSearch
        },
        mounted() {

        }
    }
</script>

<style scoped>

</style>
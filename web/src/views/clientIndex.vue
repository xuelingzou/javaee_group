<template>
    <div>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo" >
            <a href="./index.html" >网络货运平台</a>
        </div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
        <ul class="layui-nav right" lay-filter="">
            <li class="layui-nav-item"><a href="javascript:;">
                当前用户:<span>{{$route.query.name}}</span>

            </a>
                <dl class="layui-nav-child">
                    <!-- 二级菜单 -->
                    <!--					<dd>-->
                    <!--						<a onclick="x_admin_show('个人信息','clientModi?num=${session.name}')">个人信息</a>-->
                    <!--					</dd>-->
                    <dd>
                        <a href="/">切换帐号</a>
                    </dd>
                    <dd>
                        <a href="/">退出</a>
                    </dd>
                </dl>
            </li>
        </ul>
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
    <!-- 左侧菜单开始 -->
    <div class="left-nav" >
        <div id="side-nav" style="width: 100%;height: 57%">
            <ul id="nav">
                <li>
                    <a href="javascript:;">
<!--                    <a href="">-->
                        <i class="iconfont">&#xe726;</i><cite>订单管理</cite>
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
<!--                    <ul class="sub-menu">-->
                    <ul class="nav">
                        <!--						发起（提交）订单-->
                        <li >
                            <a @click="showComponent=1" >
<!--                            <a _href ="/clientNewOrder" > -->
                                <i class="iconfont">&#xe6a7;</i>
                                <cite>发起订单</cite>
                            </a>
                        </li>

                        <li >
<!--                            <a _href="/clientOrderSearch" > -->
                            <a @click="showComponent=2" >
                                <i class="iconfont">&#xe6a7;</i>
                                <cite>订单查询</cite>
                            </a>
                        </li>


                    </ul>
                </li>
            </ul>

        </div>
        <div style="
		/*background-image:url('/Sys/images/bg2.jpg');*/
		 width:100%; height:43%;bottom: 0;background-repeat: no-repeat;"></div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
<!--        <div >-->
            <ul class="layui-tab-title">

            </ul>
            <!--      主体部分      -->
<!--            <div class="layui-tab-content">-->
            <div >
                <clientNewOrder   v-if="showComponent===1"></clientNewOrder>
                <clientOrderSearch v-if="showComponent===2"></clientOrderSearch>

            </div>

        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright" style="text-align:center;">欢迎来到网络货运平台！</div>
    </div>
    <!-- 底部结束 -->

    </div>
</template>

<script>
    //导入组件
    import clientNewOrder from "../components/client/clientNewOrder"
    import clientOrderSearch from "../components/client/clientOrderSearch"

    import request from "@/network/request";
    import router from "../router"

    export default {
        name: "clientIndex",
        components:{
            clientNewOrder,
            clientOrderSearch
        },
        data(){
            return {
                showComponent:0,
                userInfo: {
                    id:"",
                    identify:"",
                    ceName:"",
                }
            }
        },
        methods:{
            toClientNewOrder:function () {
                // console.log(router.currentRoute.query)
                let query = router.currentRoute.query
                router.push({path:"/ClientNewOrder",query:query})
            },toClientOrderSearch:function () {
                let query = router.currentRoute.query
                router.push({path:"/ClientOrderSearch",query:query})
            },
        }


    }
</script>

<style scoped>

</style>
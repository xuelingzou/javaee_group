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
                当前用户：<span >{{$route.query.name}}</span>

            </a>
                <dl class="layui-nav-child">

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
    <div class="left-nav" >
        <div id="side-nav" style="width: 100%;height: 57%">
            <ul id="nav">


                <li><a href="javascript:;"> <i
                        class="iconfont layui-icon-extend-chengji"></i> <cite>货运订单管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                    <ul class="nav">
                        <li ><a @click="showComponent =1"> <i class="iconfont">&#xe6a7;</i>
                            <cite>待接订单</cite>
                        </a></li>
                    </ul>
                    <ul class="nav">
                        <li><a @click="showComponent = 2"> <i class="iconfont">&#xe6a7;</i>
                            <cite>已接订单</cite>
                        </a></li>

                    </ul>
                </li>



                <li>
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6b8;</i>
                        <cite>财务状况</cite>
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="nav">
                        <li>
                            <a @click="showComponent=3">
                                <i class="iconfont">&#xe6a7;</i>
                                <cite>订单月收入统计</cite>
                            </a>
                        </li >

                    </ul>
                </li>





            </ul>
        </div>
        <div style="
		/*background-image:url('/Sys/images/bg2.jpg'); */
		width:100%; height:43%;bottom: 0;background-repeat: no-repeat;"></div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
<!--  标头  -->
            <ul class="layui-tab-title">

            </ul>
<!--主体内容-->
            <div id ="mainContainer" class="layui-tab-content">
                <comOrderSearch v-if="showComponent===1" ></comOrderSearch>
<!--                <comOrderManage v-if="showComponent===2" ></comOrderManage>-->
                <comOrderManage2 v-if="showComponent===2" ></comOrderManage2>
                <incomeBar v-if="showComponent===3"></incomeBar>

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
    import comOrderSearch from "../components/company/comOrderSearch";
    import comOrderManage from "@/components/company/comOrderManage";
    import comOrderManage2 from "@/components/company/comOrderManage_copy";
    import incomeBar from "@/components/company/incomeBar";

    import router from "../router"

    export default {
        name: "companyIndex",
        components:{
            comOrderSearch,
            comOrderManage,
            incomeBar,
          comOrderManage2
        },
        data(){
            return{
                showComponent:0,//用来选择展示的组件

            }
        },
        methods:{
            toComOrderManage:function () {
                // console.log(router.currentRoute.query)
                let query = router.currentRoute.query
                router.push({path:"/ComOrderManage",query:query})
            },toComOrderSearch:function () {
                let query = router.currentRoute.query
                router.push({path:"/ComOrderSearch",query:query})
            },
        }
    }
</script>

<style scoped>

</style>
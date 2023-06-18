<template>
    <div>

    <div class="x-nav">
        <a class="layui-btn layui-btn-small"
           style="line-height: 1.6em; margin-top: 3px; float: right"
           href="javascript:location.replace(location.href);" title="刷新"> <i
                class="layui-icon" style="line-height: 30px">ဂ</i>
        </a>
    </div>

    <div class="x-body">
        <div class="layui-row">



            <div class="layui-form-item">


                <div class="demoTable" style="margin-bottom: 20px">
                    年份：
                    <div class="layui-inline">
                        <input class="layui-input" name="id" id="demoReload" autocomplete="off">
                    </div>
                    <button class="layui-btn" data-type="reload" ><i class="layui-icon">&#xe615;</i></button>
                </div>





            </div>

            <table id="test" lay-filter="test"></table>
        </div>

        <!--条形图-->
        <div id="main" style="width: 600px; height: 400px;"></div>

    </div>
    </div>

</template>

<script>
    import request from "@/network/request";
    import router from "../../router"

    export default {
        name: "incomeBar",
        mounted() {
            //获取路由上的信息
            let query = router.currentRoute.query

            let year = '2023';

            const chart = document.getElementById('main');

            const echart = this.$echarts.init(chart);

            function buildChart(columName, columnValue) {
                var option = {
                    tooltip : {
                        trigger : 'axis',
                        axisPointer : {
                            type : 'shadow'
                        }
                    },
                    toolbox : {
                        show : true,
                        feature : {
                            saveAsImage : {
                                show : true
                            }
                        }
                    },
                    legend : {
                        data : [year+"年月收入"]
                    },
                    grid : {
                        left : '3%',
                        right : '4%',
                        bottom : '3%',
                        containLabel : true
                    },
                    xAxis : [ {
                        length: 12,
                        axisTick: {
                            alignWithLabel: true,
                            interval :'0'
                        },
                        axisLabel:{
                            show : true
                        },
                        min : 0,
                        type : 'category',
                        data : columName
                    } ],
                    yAxis : [ {
                        min : 0,
                        type : 'value'
                    } ],
                    series: [{
                        name: year+"年月收入",
                        type: 'bar',
                        data: columnValue,
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    position: 'top'
                                }
                            }
                        }


                    }]
                    // series : columnValue
                    // itemStyle: {
                    // 	normal: {
                    // 		label: {
                    // 			show: true,
                    // 			position: 'top'
                    // 		}
                    // 	}
                    // }
                    // }
                };

                echart.setOption(option,true);
            }






            layui.use([ 'table'], function() {
                // var form = layui.form;
                // const laydate = layui.laydate;





                //根据名称查询
                const $ = layui.$, active = {

                    reload: function () {
                        year = $('#demoReload').val().toString();
                        let data={
                            account:query.id,
                            year:year,

                        }
                        let account = query.id

                        console.log(JSON.stringify({
                            account:query.id,
                            year:year,

                        }))


                        // request({
                        //     methods: get,
                        //     url:"/api/Sys/v1/incomes",
                        //     params:data
                        // })
                        // request.get(
                        request.post(
                            "/api/order/Sys/v1/incomes",
                             data

                        ).then(res=>{
                            console.log(res);
                            var columName = ['一月', '二月', '三月', "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
                           const columnValue = res.data;
                            buildChart(columName, columnValue);

                        }).catch(e=>{
                            console.log(e);
                            alert("图表请求数据失败!");
                            echart.hideLoading();
                        });

                        // $.ajax({
                        //     type: "get",
                        //     async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                        //     url: "getGscomp", //请求发送到TestServlet处
                        //     data: {
                        //         "year": year,
                        //
                        //     },
                        //     dataType: "json", //返回数据形式为json
                        //     success: function (result) {
                        //
                        //         var columName = ['一月', '二月', '三月', "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
                        //         // var columnValue = new Array();
                        //         const columnValue = result;
                        //
                        //
                        //         buildChart(columName, columnValue);
                        //
                        //     },
                        //     error: function (errorMsg) {
                        //         //请求失败时执行该函数
                        //         alert("图表请求数据失败!");
                        //         echart.hideLoading();
                        //     }
                        // });

                    }
                };

                $('.demoTable .layui-btn').on('click', function(){
                    var type = $(this).data('type');
                    active[type] ? active[type].call(this) : '';
                });




            });
        },//mounted
        methods:{

        }
    }
</script>

<style scoped>

</style>
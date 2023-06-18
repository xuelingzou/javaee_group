<template>
    <div>
    <div class="x-nav">

        <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>



    <table id="test" lay-filter="test">
        <script type="text/html" id="barDemo" >
            <a class="layui-btn layui-btn-xs" lay-event="receive">接单</a>
        </script>
    </table>

    </div>

</template>

<script>
    import router from "../../router"
    import request from "@/network/request";
    export default {
        name: "comOrderSearch",
        data(){
            return{
                d:{}
            }
        },
        mounted() {
            //获取路由上的信息
            let query = router.currentRoute.query

            var documentWidth = $(document).width();
            layui.use(['table','form'],function(){
                var table = layui.table;
                table.render({
                    elem: '#test'  //绑定table表格
                    ,id:'csInfo'
                    ,method:'get'
                    ,url: '/api/Sys/v1/orders/waiting'//后台springmvc接收路径
                    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                        layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                        //,curr: 5 //设定初始在第 5 页
                        ,groups: 1 //只显示 1 个连续页码
                        ,first: false //不显示首页
                        ,last: false //不显示尾页
                        ,limit:10
                        ,limits:[10,20,30]
                    }
                    ,cols: [
                        [
                            {type: 'checkbox',width:documentWidth*4/100,fixed: 'left'}
                            ,{field:'oid',title:'订单ID', sort: true,width: 100,fixed: 'left'}
                            ,{field:'senderName',title:'发件⼈名', sort:true,width: 150}
                            ,{field:'senderPhone',title:'发件⼈电话',sort:true,width: 150}
                            ,{field:'departure',title:'发件地址',sort:true,width: 150}
                            ,{field:'receiveName',title:'收件⼈名',sort:true,width: 150}
                            ,{field:'receivePhone',title:'收件⼈电话',sort:true,width: 150}
                            ,{field:'destination',title:'收件地址',sort:true,width: 150}
                            ,{field:'cargoType',title:'货物种类',sort:true,width: 150}
                            ,{field:'weight',title:'货物重量',sort:true,width: 150}
                            ,{field:'volume',title:'货物体积',sort:true,width: 150}
                            ,{field:'cost',title:'运费',sort:true,width: 150}
                            ,{field:'state',title:'状态',sort:true,width: 150}
                            ,{field:'submitTime',title:'提交订单时间',sort:true,width: 150}

                            ,{fixed: 'right', title:'操作', align:'center', toolbar: '#barDemo',width: 100}

                        ]
                    ]

                });

                //工具条事件
                table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    const data = obj.data; //获得当前行数据
                    const layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    const tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

                    const oid =  data.oid

                    if(layEvent === 'receive'){ //接单
                        layer.confirm('是否确定接单？', function(index){

                            //向服务端发送接单指令
                            request.post(
                                '/api/Sys/v1/orders/'+oid+'/state',
                                {
                                coid:query.id,
                                    state:'接单'
                            },

                            ).then(res=>{
                                console.log(res)
                                if(res.msg==="接单成功") {
                                    parent.layer.msg('接单成功！', {icon: 1, time: 2000, shade: 0.2});
                                    window.location.reload(true);
                                }
                            }).catch(e=>{
                                console.log(e);
                                parent.layer.msg('接单失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
                            });



                        });

                    }
                });


                $('.layui-btn').on('click', function(){
                    var type = $(this).data('type');
                    active[type] ? active[type].call(this) : '';
                });
            });

        }
    }
</script>

<style scoped>

</style>
<template>
    <div>
    <div class="x-nav">

        <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>



    <table id="test" lay-filter="test">


        <script  type="text/html" id ="barDemo" >

            <a class="layui-btn layui-btn-xs"   lay-event="send"    >发货</a>
            <a class="layui-btn layui-btn-xs"   lay-event="logistic"  >更新物流</a>
            <a class="layui-btn layui-btn-xs"   lay-event="findLogistic"  >查看物流</a>
            <a class="layui-btn layui-btn-xs"   lay-event="finish"  >送达</a>
            <span > 完成 </span>


        </script>


    </table>

<!--        {{#  if(d.state ==="已接单"){    }}-->
<!--        <a class="layui-btn layui-btn-xs" lay-event="send">发货</a>-->
<!--        {{#   } else if(d.state ==="已发货"){  }}-->
<!--        <a class="layui-btn layui-btn-xs"   lay-event="logistic"  >更新物流</a>-->
<!--        <a class="layui-btn layui-btn-xs"   lay-event="findLogistic"  >查看物流</a>-->
<!--        <a class="layui-btn layui-btn-xs" lay-event="finish">送达</a>-->
<!--        {{#    } else if(d.state ==="已到达"){    }}-->
<!--        完成-->
<!--        {{#    }   }}-->


    </div>
</template>

<script>
    import router from "../../router"
    import request from "@/network/request";


    export default {
        name: "comOrderManage",
        data(){
            return{
                // 用于新增
                newLogistics:{
                    dialogVisible:false,
                    location:"",
                    oid:""
                },

            }
        },
        mounted() {
            //获取路由上的信息
            let query = router.currentRoute.query

            var documentWidth = $(document).width();
            layui.use(['table','form',"laytpl"],function(){
                const table = layui.table;
                const laytpl = layui.laytpl;
                table.render({
                    elem: '#test'  //绑定table表格
                    ,id:'csInfo'
                    ,method:'get'
                    ,url: '/api/Sys/v1/orders/coid/'+ query.id //后台springmvc接收路径
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
                            ,{field:'sendTime',title:'发货时间',sort:true,width: 150}
                            ,{field:'receiveTime',title:'到达时间',sort:true,width: 150}

                            ,{ title:'操作', align:'center', toolbar: '#barDemo',fixed: 'right',width: 300}

                        ]
                    ]
                }); //table

                //工具条事件
                table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    const data = obj.data; //获得当前行数据
                    const layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    const tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）


                    const oid =  data.oid

                    //按钮事件
                    if(layEvent === 'send'){ //发货
                        console.log("tr",tr[2].innerHTML)

                        layer.confirm('是否确定发货？', function(index){
                            //向服务端发送发货指令
                            request.post(
                                '/api/Sys/v1/orders/'+oid+'/state',
                                {
                                    coid:query.id,
                                    state:'发货'
                                },

                            ).then(res=>{
                                console.log(res)

                                if(res.msg==="发货成功") {
                                    obj.data.state = "已发货"; //修改对应行的数据
                                    parent.layer.msg(res.msg, {icon: 1, time: 2000, shade: 0.2});
                                    // window.location.reload(true);
                                }
                            }).catch(e=>{
                                console.log(e);
                                parent.layer.msg('发货失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
                            });


                        });//layer.confirm

                    } else if (layEvent === 'logistic'){//  更新物流

                        layer.open({
                            type: 2,
                            title: '新增物流信息',
                            skin: 'layui-layer-molv',
                            shadeClose: true,
                            shade: 0.8,
                            area:  ['500px', '200px'],
                            // content: 'companyModi?coid='+coid,
                            content: 'addLogistics?oid='+oid,
                            end: function(){
                                // window.location.reload(); //刷新父页面
                            }
                        });


                    }else if (layEvent === 'findLogistic'){//  查看物流信息

                        layer.open({
                            type: 2,
                            title: '物流信息（oid：'+oid+')',
                            skin: 'layui-layer-molv',
                            shadeClose: true,
                            shade: 0.8,
                            area:  ['400px', '500px'],
                            content: 'logisticsInfo?oid='+oid,

                        });


                    }else if (layEvent === 'finish'){//送达
                        layer.confirm('是否确定货物已送达？', function(index){

                            //向服务端发送送达指令
                            request.post(
                                '/api/Sys/v1/orders/'+oid+'/state',
                                {
                                    coid:query.id,
                                    state:'送达'
                                },

                            ).then(res=>{
                                console.log(res)
                                if(res.msg==="货物已送达") {
                                    obj.data.state = "已送达"; //修改对应行的数据

                                    parent.layer.msg(res.msg, {icon: 1, time: 2000, shade: 0.2});
                                    // window.location.reload(true);
                                }

                            }).catch(e=>{
                                console.log(e);
                                parent.layer.msg('货物送达失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
                            });

                        });//layer.confirm
                    }

                    //关于工具条中d的定义

                    // this.d.state = data.state
                    // console.log(this.d.state)

                    let d = {
                        state:data.state
                    }
                    let state = data.state
                    console.log(state)
                    const barDemo = document.getElementById('barDemo');
                    const getTpl = barDemo.innerHTML
                    laytpl(getTpl).render(d);
                    laytpl(getTpl).render(state);

                    //条件判断渲染工具条
                    if(data.state==="已接单"){
                        barDemo.innerHTML = " <a class=\"layui-btn layui-btn-xs\"   lay-event=\"send\"    >发货</a>"
                        laytpl(getTpl).render(" <a class=\"layui-btn layui-btn-xs\"   lay-event=\"send\"    >发货</a>");
                    }



                });//工具条事件



                $('.layui-btn').on('click', function(){
                    var type = $(this).data('type');
                    active[type] ? active[type].call(this) : '';
                });
            });

        },

    }
</script>

<style scoped>

</style>
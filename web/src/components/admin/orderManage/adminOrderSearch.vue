<template>
    <div>
    <div class="x-nav">
        <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>



    <table id="test" lay-filter="test"></table>

    </div>

</template>

<script>
    export default {
        name: "adminOrderSearch",
        mounted() {
            var documentWidth = $(document).width();
            layui.use(['table','form'],function(){
                var table = layui.table;
                table.render({
                    elem: '#test'  //绑定table表格
                    ,id:'csInfo'
                    ,method:'get'
                    ,url: 'api/order/Sys/v1/orders' //后台springmvc接收路径
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
                            {type: 'checkbox',width:documentWidth*4/100,fixed: 'left',}
                            ,{field:'oid',title:'订单ID', sort: true,width: 100,fixed: 'left',}
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


                        ]
                    ]

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
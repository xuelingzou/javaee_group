<template>
    <div>




    <div class="x-body">

        <div class="layui-row">

            <div class="demoTable" style="margin-bottom: 20px">
                搜索客户名称（模糊查询）：
                <div class="layui-inline">
                    <input class="layui-input" name="id" id="demoReload" autocomplete="off">
                </div>
                <button class="layui-btn" data-type="reload" ><i class="layui-icon">&#xe615;</i></button>

                <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
                    <i class="layui-icon" style="line-height:30px">ဂ</i>
                </a>
            </div>


        </div>

        <xblock >
            <button class="layui-btn layui-btn-danger" @click="delAll"><i class="layui-icon"></i>批量删除</button>
            <button class="layui-btn" @click="stuAdd"><i class="layui-icon"></i>添加</button>
        </xblock>

        <table id="test" lay-filter="test">

            <script type="text/html"  id ="barDemo">
                <a title="修改信息 "   href="javascript:;" lay-event="edit"  @click="dialogVisible =true">
                    <i class="layui-icon">&#xe642;</i>
                </a>
                <a title="删除"  href="javascript:;" lay-event="del">

                    <i class="layui-icon">&#xe640;</i>
                </a>
            </script>

        </table>

        <!--    修改信息的弹窗    -->
        <!--     弹窗样式      -->
        <el-dialog title="修改客户信息（若数据为空，则默认保留原来的值）" :visible.sync="dialogVisible"  style="width: 700px;height: 450px">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="客户ID">
                    <el-input  ></el-input>
                </el-form-item>
                <el-form-item label="客户名称">
                    <el-input  ></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input  ></el-input>
                </el-form-item>

                    <el-form-item label="密码">
                    <el-input  type="password" ></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="">确 定</el-button>
               </span>
        </el-dialog>


    </div>
    </div>

</template>

<script>
    import request from "@/network/request";
    import router from "@/router"


    export default {
        name: "clientInfo",
        data(){
            return{
                dialogVisible:false,
                client_updata:{
                    ceid:"",
                    ceName:"",
                    phone:"",
                    psw:"",
                },
            }
        },
        mounted() {
            var documentWidth = $(document).width();
            layui.use('table', function(){



                var table = layui.table;
                table.render({
                    elem: '#test'  //绑定table表格
                    ,id:'stuInfo'
                    ,method:'get',
                    headers:{
                            "Content-Type":"application/json"
                    },
                    url: 'api/Sys/v1/clients/simple' //后台springmvc接收路径
                    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                        layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                        //,curr: 5 //设定初始在第 5 页
                        ,groups: 1 //只显示 1 个连续页码
                        ,first: false //不显示首页
                        ,last: false //不显示尾页
                        ,limit:5
                        ,limits:[5,10,15]
                    }
                    ,cols: [
                        [
                            {type: 'checkbox',width:documentWidth*4/100, fixed: 'left'}
                            ,{field:'ceid',title:'客户ID', sort: true}
                            ,{field:'ceName', title:'客户名称'}
                            ,{field:'phone', title:'手机号'}
                            ,{field:'operation',title:'操作',toolbar: '#barDemo',fixed: 'right'}
                        ]
                    ]
                    // ,where: {
                    //     key:{
                    //        coName:coName
                    //     }
                    // }
                });


                //工具条事件
                table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
                    var data = obj.data; //获得当前行数据
                    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
                    var num = data.ceid
                    // alert(data)
                    if(layEvent === 'del'){ //删除
                        layer.confirm('确定删除吗？', function(index){
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);

                            //向服务端发送删除指令
                            request.delete(
                                'api/Sys/v1/clients/'+num,
                                {
                                    params:{
                                        'num':num
                                    }
                                }
                            ).then(res=>{
                                if(res.msg==="success"){
                                    parent.layer.msg('删除成功！', {icon: 1,time:2000,shade:0.2});
                                    // location.reload(true);
                                }else{
                                    parent.layer.msg('删除失败！', {icon: 2,time:3000,shade:0.2});
                                }
                            })


                        });
                    } else if(layEvent === 'edit'){ //编辑
                        this.dialogVisible =true
                        //do something
                        layer.open({
                            type: 2,
                            data:111,
                            title: '修改客户信息（若数据为空，则默认保留原来的值）',
                            skin: 'layui-layer-molv',
                            shadeClose: false,
                            shade: 0.8,
                            area:  ['700px', '450px'],
                            content: 'clientModi?num='+num,
                            end: function(){
                                window.location.reload(); //刷新父页面
                            }
                        });


                    }
                });

                var $ = layui.$, active = {
                    reload: function(){
                        var demoReload = $('#demoReload');
                        console.log(demoReload.val())
                        if (demoReload.val()===""){
                            alert('取值为空')
                            table.reload('stuInfo', {
                                page: {
                                    curr: 1 //重新从第 1 页开始
                                },
                                url:'/api/Sys/v1/clients/ceName/'+" ",
                                method:'get'
                            });

                        }else {

                            table.reload('stuInfo', {
                                page: {
                                    curr: 1 //重新从第 1 页开始
                                },
                                url:'/api/Sys/v1/clients/ceName/'+demoReload.val(),
                                // where: {
                                //     key: {
                                //         id: demoReload.val()
                                //     }
                                // },
                                method:'get'
                            });
                        }
                    }
                };

                $('.demoTable .layui-btn').on('click', function(){
                    var type = $(this).data('type');
                    // alert(type)

                    active[type] ? active[type].call(this) : '';
                });

            });


        },
        methods:{

            delAll(argument) {
        layui.use('table',function(){
            var table = layui.table
            var checkStatus = table.checkStatus('stuInfo');
            if(checkStatus.data.length === 0){
                parent.layer.msg('请先选择要删除的数据行！', {icon: 2});
                return ;
            }
            var nums = "";
            for(var i=0;i<checkStatus.data.length;i++){
                nums += checkStatus.data[i].ceid+",";
            }

            parent.layer.msg('删除中...', {icon: 16,shade: 0.3,time:5000});

            request.delete(
                '/api/Sys/v1/clients',
                {params:{'nums':nums}},
            ).then(res=>{
                if(res.msg==="success"){
                    parent.layer.msg('删除成功！', {icon: 1,time:2000,shade:0.2});
                    location.reload(true);
                }else{
                    parent.layer.msg('删除失败！', {icon: 2,time:3000,shade:0.2});
                }
            })

        });

    },

    stuAdd(){

        layer.open(
            {
                type: 2,
                title: '新增客户',
                skin: 'layui-layer-lan',
                shadeClose: false,
                shade: 0.8,
                area:  ['700px', '450px'],
                resize:true,
                content:'/clientAdd',
                end: function(){
                    window.location.reload(); //刷新父页面
                }
            });

    },
            updata(id){
                request.put(

                )
            },


     screen() {
        //获取当前窗口的宽度
        var width = $(window).width();
        if (width > 1200) {
            return 3;   //大屏幕
        } else if (width > 992) {
            return 2;   //中屏幕
        } else if (width > 768) {
            return 1;   //小屏幕
        } else {
            return 0;   //超小屏幕
        }
    }

    }
    }
</script>

<style scoped>

</style>
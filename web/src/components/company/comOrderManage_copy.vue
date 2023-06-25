<template>
    <div>
    <div class="x-nav">

        <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" @click="loadData" title="刷新">
            <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>


      <!--     表格      -->
      <el-table

          :data="tableData"
          border
          style="width: 100%;margin-top: 20px;"
          align = "center"
          max-height="510px"
          :header-cell-style="{background:'#f8f8f8'}"
      >
        <!---->
        <el-table-column

            fixed
            prop="oid"
            label="订单ID"
            sortable
            min-width="100">
        </el-table-column>


        <el-table-column
            prop="senderName"
            label="发件⼈名"
            min-width="150">
        </el-table-column>
        <el-table-column
            prop="senderPhone"
            label="发件⼈电话"
            min-width="150">
        </el-table-column>



        <el-table-column
            prop="departure"
            label="发件地址"
            min-width="150">
        </el-table-column>

        <el-table-column
            prop="receiveName"
            label="收件⼈名"
            min-width="150">

        </el-table-column>
        <el-table-column
            prop="destination"
            label="收件地址"
            min-width="150">

        </el-table-column>
        <el-table-column
            prop="cargoType"
            label="货物种类"
            min-width="150">

        </el-table-column>

        <el-table-column
            prop="weight"
            label="货物重量"
            min-width="150">

        </el-table-column>

        <el-table-column
            prop="volume"
            label="货物体积"
            min-width="150">
        </el-table-column>

          <el-table-column
            prop="cost"
            label="运费"
            min-width="150">
          </el-table-column>

            <el-table-column
                fixed="right"
            prop="state"
            label="状态"
            min-width="100">
            </el-table-column>

              <el-table-column
            prop="submitTime"
            label="提交订单时间"
            min-width="150">

        </el-table-column>
        <el-table-column
            prop="receiveTime"
            label="到达时间"
            min-width="150">

        </el-table-column>



        <el-table-column
            fixed="right"
            label="操作"
            align = "center"
            min-width="260">
          <template slot-scope="scope">
            <el-button
                v-if="scope.row.state ==='已接单'"
                size="mini"
                type="success"
                @click="send(scope.$index, scope.row)" >发货</el-button>
            <el-button
                v-if="scope.row.state !=='已到达'"
                size="mini"
                type="primary"
                @click="logistic(scope.$index, scope.row)">更新物流</el-button>
            <el-button
                size="mini"
                type="primary"
                @click="findLogistic(scope.$index, scope.row)">查看物流</el-button>
            <el-button
                v-if="scope.row.state === '已发货'"
                size="mini"
                type="success"
                @click="finish(scope.$index, scope.row)">送达</el-button>
<!--            <span>完成</span>-->
          </template>
        </el-table-column>

      </el-table>

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
        name: "comOrderManage2",
        data(){
            return{
                // 用于新增
                newLogistics:{
                    dialogVisible:false,
                    location:"",
                    oid:""
                },

              allData:[],
              tableData:[], //用来展示的表格数据


            }
        },
        mounted() {
            //获取路由上的信息
            let query = router.currentRoute.query

          this.loadData()



        },methods:{
        //加载数据
        loadData(){
          //获取路由上的信息
          let query = router.currentRoute.query

          request.get('/api/Sys/v1/orders/coid/'+ query.id , //调取后端注册接口
          ).then((respond) => {
            // let res = respond
            let res = respond.data
            console.log(res);
            // console.log(res.msg);
            // console.log(res.data.records);
            this.allData = res;
            // alert(res.msg);
            this.tableData = this.allData

          });
        },

        //发货
        send(index, row) {
          //获取路由上的信息
          let query = router.currentRoute.query
          let this_ = this;

          let oid = row.oid
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
                // obj.data.state = "已发货"; //修改对应行的数据
                parent.layer.msg(res.msg, {icon: 1, time: 2000, shade: 0.2});
                // window.location.reload(true);
              }
              this_.loadData()

            }).catch(e=>{
              console.log(e);
              parent.layer.msg('发货失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
            });


          });//layer.confirm
        },
        //更新物流
        logistic(index, row) {
          let oid = row.oid
          let this_ = this;

          layer.open({
            type: 2,
            title: '新增物流信息',
            skin: 'layui-layer-molv',
            shadeClose: true,
            shade: 0.8,
            area:  ['500px', '200px'],
            // content: 'companyModi?coid='+coid,
            content: 'addLogistics?oid='+oid,
            end:function () {//层销毁回调函数
              this_.loadData()
            }
          });

        },

        // 查看物流
        findLogistic(index, row) {
          let oid = row.oid
          let this_ = this;

          layer.open({
            type: 2,
            title: '物流信息（oid：'+oid+')',
            skin: 'layui-layer-molv',
            shadeClose: true,
            shade: 0.8,
            area:  ['400px', '500px'],
            content: 'logisticsInfo?oid='+oid,
            end:function (index,layero) {//层销毁回调函数
              this_.loadData()
            }

          });


        },

        // 送达
        finish(index, row) {
          //获取路由上的信息
          let query = router.currentRoute.query

          let oid = row.oid
          let this_ = this;

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

                parent.layer.msg(res.msg, {icon: 1, time: 2000, shade: 0.2});
                // window.location.reload(true);
              }

              this_.loadData()


            }).catch(e=>{
              console.log(e);
              parent.layer.msg('货物送达失败，请稍后尝试！', {icon: 2,time:3000,shade:0.2});
            });

          });//layer.confirm

        },
      },

    }
</script>

<style scoped>

</style>
<!--
 * @Author: 李燕军
 * @Date: 2023-05-21 22:00:04
 * @LastEditors: WangCute 2605735186@qq.com
 * @LastEditTime: 2023-05-23 14:50:34
 * @FilePath: \bicrear\src\components\main\deposit\Inquiry.vue
-->
<template>
  <!-- 页头 -->
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">收费与押金管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>押金/收费查询</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
<!-- 主页面 -->
<div class="Inquiry">
    <!-- 头部操作 -->
    <div>
      <el-form :inline="true" class="demo-form-inline" style="width:100%">
        <el-form-item label="账号">
            <el-input style="width: 130px;"  placeholder="用户账号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
            <el-input style="width: 130px;"  placeholder="用户姓名">
            </el-input>
        </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="queryLeaseInfo">查询</el-button>
        </el-form-item>
        <div class="el-form-item" style="margin-left: 27.6%;">
          <el-form-item label="状态">
              <el-select style="width: 130px;" v-model="editStatus"  placeholder="状态" >
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
                  </el-select>
          </el-form-item>
          <el-form-item>
              <el-button type="success" @click="editStatus" >修改</el-button>
          </el-form-item>
        </div>
      </el-form>
     </div>
  <!-- 数据列表 -->
  <div>
    <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%">
      <el-table-column
          type="selection"
          width="60">
      </el-table-column>
      <el-table-column prop="accountcust_id" label="顾客账号"  />
      <el-table-column prop="c_name" label="顾客姓名"  />
      <el-table-column prop="b_brand" label="车辆品牌" />
      <el-table-column prop="b_name" label="车辆名称" />
      <el-table-column prop="d_price"  label="押金金额" />
      <el-table-column prop="l_datefrist" label="订单日期" />
      <el-table-column
        prop="o_status"
        label="状态"
        width="100"
        :filters="[
          { text:'未退回', value: '0' },
          { text:'已退回',value: '1' },
        ]"
        :filter-method="filterTag"
        filter-placement="bottom-end"
      >
        <template #default="scope">
          <el-tag
            :type="scope.row.o_status === '1' ? 'success' : ''"
            disable-transitions
            >{{ geto_status(scope.row.o_status) }}</el-tag
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!-- 分页 -->
  <div class="block" style="margin-top:15px;">
            <el-pagination style="align:center; margin: 0 25%;"  @size-change="handleSizeChange" @current-change="handleCurrentChange" 
            :current-page="currentPage" 
            :page-sizes="[1,5,10,20]" 
            :page-size="pageSize" 
            layout="total, sizes, prev, pager, next, jumper" 
            :total="tableData.length">
            </el-pagination>
        </div>
</div>
</template>

<script>
import { ref } from 'vue'

export default {
    name: 'Inquiry',
  data () {
    return {
      options:[
        {value:'0',label:'未退回'},
        {value:'1',label:'已退回'}
      ],
      tableData:[
        {
          accountcust_id:'0234223',
          c_name:'王狼',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'1'

        },
        {
          accountcust_id:'0234224',
          c_name:'李燕军',
          b_brand:'捷安特',
          b_name:'MY 21',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'1'
        },
        {
          accountcust_id:'0234224',
          c_name:'改柯易叶',
          b_brand:'捷安特',
          b_name:'MY 23',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        },
        {
          accountcust_id:'0234225',
          c_name:'王志宇',
          b_brand:'捷安特',
          b_name:'MY 20',
          d_price:90,
          l_datefrist:'2023-5-21',
          o_status:'0'
        }
      ],
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 10, // 每页的数据条数
      editStatus:null
    }
  },
  method(){
    this.getlivestockInfo(1);
  },
  methods: {
    geto_status(o_status){
      if(o_status==0) return '未退回';
      else  return '已退回'; 
    },
    handleSizeChange(val) {
         console.log(`每页 ${val} 条`);
         this.currentPage = 1;
         this.pageSize = val;
     },
     //当前页改变时触发 跳转其他页
     handleCurrentChange(val) {
         console.log(`当前页: ${val}`);
         this.currentPage = val;
     },
     getlivestockInfo(num1){
					var that = this;
					var params = new URLSearchParams();
					params.append('pageNum',num1);
					that.$axios.post('url',params)    //补上后台接口即可
					.then(response => {  // 请求成功
						console.log('请求成功');
						that.tableData=response.data.data;
						that.currentPage=num1;
						that.pageSize=that.tableData.pageSize;
						that.total=that.tableData.total;
						console.log(that.tableData.list.length);
					}).catch(error => {  // 请求失败
						console.log('请求失败');
						console.log(error);
					})
				}
  },
  setup() {
    const filterTag = (value, row) => {
      return row.o_status === value
    }

    return{
      filterTag
    }
  }
}
  
</script>

<style scoped>
  .Inquiry{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 1%;
    border-radius: 3px;
  }
  .Inquiry>div:nth-child(1){
    padding-left: 2%;
    text-align: left;
    display: flex;
  }
  .Inquiry>div:nth-child(2){
    padding-left: 1%;
    padding-right: 1%;
    
  }
  .el-button{
    border-radius: 1px;
  }
</style>
<!--
 * @Author: 李燕军
 * @Date: 2023-05-23 14:52:51
 * @LastEditors: WangCute 2605735186@qq.com
 * @LastEditTime: 2023-05-25 11:04:19
 * @FilePath: \bicrear\src\components\main\customer\CustomerInfo.vue
-->
<template>
   <!-- 页头 -->
   <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">顾客信息管理</a></el-breadcrumb-item>
    </el-breadcrumb>
  </div>
<!-- 主页面 -->
<div class="CustomerInfo">
    <!-- 头部操作 -->
    <div>
        <div>
            <el-form  :inline="true" class="demo-form-inline" style="width:100%">
            <el-form-item label="账号">
                <el-input style="width: 130px;"  placeholder="账号/手机号">
                </el-input>
            </el-form-item>
             <el-form-item>
                <el-button type="primary" @click="queryLeaseInfo">查询</el-button>
            </el-form-item>
            </el-form>
        </div>
     </div>
  <!-- 数据列表 -->
  <div>
    <el-table :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%">
      <el-table-column type="index" width="50" />
      <el-table-column prop="custaccount_id" label="联系方式"  />
      <el-table-column prop="c_name" label="顾客姓名"  />
      <el-table-column prop="c_sex" label="顾客性别" />
      <el-table-column prop="c_age" label="顾客年龄" />
      <el-table-column prop="c_card"  label="身份证号" show-overflow-tooltip />
      <el-table-column prop="c_email" label="邮箱地址" show-overflow-tooltip />
      <el-table-column prop="c_date" label="注册日期" />
      <!-- <el-table-column prop="c_status" label="账号状态" /> -->
      <el-table-column
        prop="c_status"
        label="账号状态"
        :filters="[
          { text: '正常', value: 0 },
          { text: '异常', value: 1 },
        ]"
      :filter-method="filterStatus"
      filter-placement="bottom-end" >
        <template #default="scope">
          <el-switch v-model="scope.row.c_status" 
            :active-value="0"
            :inactive-value="1" />
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
 name:'CustomerInfo',
 data(){
    return{
      value1: true,
      tableData:[
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:1
        },
        {
          custaccount_id:19148449234,
          c_name:'张天海',
          c_sex:'男',
          c_age:20,
          c_card:511522200006147834,
          c_email:'15623323@123.com',
          c_date:'2023-2-13',
          c_status:0
        },
        {
          custaccount_id:18234754643,
          c_name:'胡达',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:0
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:0
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:0
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:1
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:0
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:1
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:0
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:0
        },
        {
          custaccount_id:18148444679,
          c_name:'王狼',
          c_sex:'男',
          c_age:20,
          c_card:511522200009182343,
          c_email:'2921882190@123.com',
          c_date:'2023-3-12',
          c_status:0
        },
    ],
    currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 8, // 每页的数据条数
      editStatus:null
    }
 },
 method(){
    this.getlivestockInfo(1);
  },
 methods:{
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
			},
 },
 setup(){
  const filterStatus = (value, row) => {
    return row.c_status === value
  }
  return{
    filterStatus
  }
 }
}
</script>

<style scoped>
.CustomerInfo{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 2%;
    border-radius: 3px;
  }
.CustomerInfo>div:nth-child(1){
    padding-left: 2%;
    text-align: left;
    display: flex;
  }
.CustomerInfo>div:nth-child(1)>div:nth-child(2){
    text-align: right;
    flex: 1;
    padding-right: 1%;
  }
.CustomerInfo>div:nth-child(2){
    padding-left: 1%;
    padding-right: 1%;
    
  }
  .el-button{
    border-radius: 1px;
  }
</style>
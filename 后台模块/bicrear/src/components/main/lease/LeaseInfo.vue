<template>
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">租借信息管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>租借查询</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <!--头部查询-->
  <div class="leaseInfo">
    <div>
      <el-row gutter="20">
        <el-col :span="5">
          <el-form-item label="账号">
            <el-autocomplete
              v-model="state"
              :fetch-suggestions="querySearchAsync"
              placeholder="手机/账号"
              @select="handleSelect"
            />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="证件">
            <el-input v-model="name" placeholder="身份证号" />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button type="primary" @click="queryLeaseInfo">查询</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="状态">
            <el-select v-model="editStatus" class="m-2" placeholder="状态" size="default" style="width: 50%;">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="4" style="display: flex;">
          <el-form-item style="margin-left: -55%;">
            <el-button type="success" @click="editStatus">修改</el-button>
          </el-form-item>
          <el-form-item style="margin-left: 60%;">
            <el-button type="danger" @click="delLeaseInfo">删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <!--数据列表-->
    <div>
      <el-table :row-key="leaseInfoList.date" :data="leaseInfoList.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column
          prop="date"
          label="日期"
          sortable
          column-key="date"
          :filters="[
            { text: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate(), value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() },
            { text: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 1), value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 1) },
            { text: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 2), value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 2) },
          ]"
          :filter-method="filterHandler"
        />
        <el-table-column prop="u_name" label="顾客姓名" />
        <el-table-column prop="brand" label="车辆品牌" />
        <el-table-column prop="b_name" label="车辆名称" />
        <el-table-column prop="timeFirst" sortable label="开始时间" />
        <el-table-column prop="timeSecond" sortable label="租借时长" />
        <el-table-column prop="money" sortable label="租借金额" />
        <el-table-column prop="price" sortable label="押金金额" />

        <el-table-column
          prop="status"
          label="租借状态"
          width="100"
          :filters="[
            { text: '租借中', value: 0 },
            { text: '已结束', value: 1 },
            { text: '异常中', value: 2 },
          ]"
          :filter-method="filterTag"
          filter-placement="bottom-end"
        >
          <template #default="scope">
            <el-tag
              :type="getStatus(scope.row.status)"
              disable-transitions
            >
              {{ gotStatus(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="warning" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <div>
      <el-row gutter="20">
        <el-col :span="7"></el-col>
        <el-col :span="17">
          <el-pagination
            @size-change="handleSizeChange" 
            @current-change="handleCurrentChange"
            :current-page="currentPage" 
            :page-sizes="[1, 5, 10, 20]" 
            :page-size="pageSize" 
            layout="total, sizes, prev, pager, next, jumper" 
            :total="leaseInfoList.length">
          </el-pagination>
        </el-col>
      </el-row>
    </div>
    <!--编辑弹出-->
    <div>
      <el-dialog v-model="dialogFormVisible" title="租借信息修改">
        <!--顾客信息-->
        <el-row gutter="80">
          <el-col :span="2" style="line-height: 27px;">
            <el-tag class="ml-2" type="success">顾客信息</el-tag>
          </el-col>
          <el-col :span="10">
            <el-form-item label="账号">
              <!--远程搜索-->
              <el-autocomplete
                v-model="account"
                :fetch-suggestions="querySearchAccount"
                placeholder="账号/手机号"
                @select="handleSelect"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <!--车辆信息-->
        <el-row gutter="80">
          <el-col :span="2" style="line-height: 27px;">
            <el-tag class="ml-2" type="warning">车辆信息</el-tag>
          </el-col>
          <el-col :span="10">
            <el-form-item label="名称">
              <!--远程搜索-->
              <el-autocomplete
                v-model="bicycle"
                :fetch-suggestions="querySearchBicycle"
                placeholder="自行车名称"
                @select="handleSelect"
              />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="数量">
              <el-input-number v-model="number" min="1" max="3"  />
            </el-form-item>
          </el-col>
        </el-row>
        <!--日期信息-->
        <el-row gutter="80">
          <el-col :span="2" style="line-height: 27px;">
            <el-tag class="ml-2" type="danger">日期信息</el-tag>
          </el-col>
          <el-col :span="10">
            <el-form-item label="日期">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker style="width: 90%;"
                    v-model="date"
                    type="daterange"
                    range-separator="To"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    size="default"
                  />
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="10">
              <el-form-item label="时间">
                <div class="demo-range">
                  <el-time-picker style="width: 90%;"
                    v-model="times"
                    is-range
                    range-separator="To"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                  />
                </div>
              </el-form-item>
          </el-col>
        </el-row>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="save">确认</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { getCurrentInstance } from "vue";

export default {
  name: 'LeaseInfo',
  data () {
    return {
      options: [
        {value: 1, label: '已结束'},
        {value: 0, label: '租借中'},
        {value: 2, label: '异常中'},
      ],
      leaseInfoList: [
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '5.50', money: 230, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '13:00', timeSecond: null, money: null, price: 150, status: 0},
        {date: '2023-5-20', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: null, money: null, price: 100, status: 2},
        {date: '2023-5-20', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '3', money: 130, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: null, money: null, price: 100, status: 0},
        {date: '2023-5-19', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '5.50', money: 230, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '5.50', money: 230, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '13:00', timeSecond: null, money: null, price: 150, status: 0},
        {date: '2023-5-20', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: null, money: null, price: 100, status: 2},
        {date: '2023-5-20', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '3', money: 130, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: null, money: null, price: 100, status: 0},
        {date: '2023-5-19', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '5.50', money: 230, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '5.50', money: 230, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '13:00', timeSecond: null, money: null, price: 150, status: 0},
        {date: '2023-5-20', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: null, money: null, price: 100, status: 2},
        {date: '2023-5-20', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '3', money: 130, price: 100, status: 1},
        {date: '2023-5-21', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: null, money: null, price: 100, status: 0},
        {date: '2023-5-19', u_name: '王狼', brand: '捷安特', b_name: 'MY 23', timeFirst: '8:00', timeSecond: '5.50', money: 230, price: 100, status: 1},
      ],
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 10, // 每页的数据条数
      dialogFormVisible: false,  // 弹框
      index: '',
      editStatus: null,
      // 下面为弹框修改的内容
      account: '',
      bicycle: '',
      number: 1,
      date: null,  // 日期，获取的是一个数组，里面两个Date对象，拿出日期需要使用Date类的方法实现
      times: null,  // 时间，获取的是一个数组，里面两个Date对象，拿出日期需要使用Date类的方法实现
    }
  },
  methods: {
    getStatus(status) {
      if(status == 0) return ' ';
      else if(status == 1) return 'success';
      else return 'danger'
    },
    gotStatus(status) {
      if(status == 0) return '租借中';
      else if(status == 1) return '已结束';
      else return '异常中'
    },
    // 编辑
    handleEdit(index, row){
      this.dialogFormVisible = true;
      this.index = index
    },
    // 保存
    save(){
      // 后端返回是否成功执行
      if(true) {
        // 是否修改日期或时间
        if(this.date === null && this.times != null)
          this.leaseInfoList[this.index].timeFirst = this.times[0].getHours() +':'+ this.times[0].getMinutes()
        else if(this.times === null && this.date != null) 
          this.leaseInfoList[this.index].date = this.date[0].getFullYear() +'-'+ (this.date[0].getMonth()+1) +'-'+ this.date[0].getDate();
        else {
          this.leaseInfoList[this.index].date = this.date[0].getFullYear() +'-'+ (this.date[0].getMonth()+1) +'-'+ this.date[0].getDate();
          this.leaseInfoList[this.index].timeFirst = this.times[0].getHours() +':'+ this.times[0].getMinutes()
        }
        // 其他数据操作


        this.dialogFormVisible = false;
      }
    },
    //每页条数改变时触发 选择一页显示多少行
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.currentPage = 1;
      this.pageSize = val;
    },
    //当前页改变时触发 跳转其他页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
    }
  },
  setup() {
    const exData = getCurrentInstance();
    const filterHandler = (value, row, column) => {
      const property = column['property'];
      return row[property] === value
    }
    const filterTag = (value, row) => {
      return row.status === value
    }
    return {
      filterHandler,
      filterTag
    }
  }
}
</script>

<style scoped>
  .leaseInfo{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 2%;
    border-radius: 3px;
  }
  .leaseInfo>div:nth-child(1){
    padding-left: 3%;
    padding-right: 2%;
  }
  .leaseInfo>div:nth-child(2){
    padding-left: 2%;
    padding-right: 2%;
  }
  .leaseInfo>div:nth-child(3){
    margin-top: 1%;
  }
  .el-button{
    border-radius: 1px;
  }
  .leaseInfo>div:nth-child(4) .el-row{
    margin-top: 2%;
  }
</style>
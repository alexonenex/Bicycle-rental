<!--
 * @Author: WangCute 2605735186@qq.com
 * @Date: 2023-05-23 11:44:40
 * @LastEditors: WangCute 2605735186@qq.com
 * @LastEditTime: 2023-05-23 14:45:47
 * @FilePath: \bicrear\src\components\main\lease\Reservation.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
    <div style="margin-top: 3%;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">租借信息管理</a></el-breadcrumb-item>
        <el-breadcrumb-item>预约查询</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

  <div class="reservation">
    <!--头部查询-->
    <div>
      <el-row gutter="20">
        <el-col :span="5">
          <el-form-item label="账号">
            <el-input v-model="name" placeholder="手机号/账号" />
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button type="primary" @click="queryLeaseInfo">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </div>

    <!--列表数据-->
    <div>
      <el-table ref="tableRef" row-key="date" :data="reservationList.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%">
        <el-table-column
          prop="r_resdate"
          label="预约日期"
          sortable
          width="180"
          column-key="date"
          :filters="[
            { text: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate(), value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() },
            { text: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 1), value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 1) },
            { text: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 2), value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() - 2) }
          ]"
          :filter-method="filterHandler"
        />
        <el-table-column prop="custaccount_id" label="顾客账号" width="150" show-overflow-tooltip />
        <el-table-column prop="c_name" label="顾客姓名" width="100" />
        <el-table-column prop="b_brand" label="品牌" width="100" />
        <el-table-column prop="b_name" label="名称" width="120" />
        <el-table-column prop="r_number" sortable label="数量" width="80" />
        <el-table-column prop="r_totalprice" sortable label="总价" width="80" />
        <el-table-column prop="s_address" label="地址" :filters="addressList" :filter-method="filterAddress" show-overflow-tooltip />

        <el-table-column
          prop="r_status"
          label="状态"
          width="100"
          :filters="[
            { text: '未成功', value: 0 },
            { text: '已成功', value: 1 },
          ]"
          :filter-method="filterTag"
          filter-placement="bottom-end"
        >
          <template #default="scope">
            <el-tag
              :type="scope.row.r_status === '0' ? '' : 'success'"
              disable-transitions
              >{{ getStatus(scope.row.r_status) }}</el-tag
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--分页-->
    <div style="margin-top: 1%;">
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
            :total="reservationList.length">
          </el-pagination>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Reservation',
  data () {
    return {
      addressList: [
        {text: "成都锦城大道2段营业店", value: "成都锦城大道2段营业店"},
        {text: "成都武侯区天府5街营业店", value: "成都武侯区天府5街营业店"},
        {text: "成都成华大道二仙桥营业店", value: "成都成华大道二仙桥营业店"},
      ],
      reservationList: [
        {r_resdate: '2023-5-23', custaccount_id: '17345449129', c_name: '王狼', b_brand: '喜德盛', b_name: 'RC 200', r_number: '2', r_totalprice: '300', s_address: '成都锦城大道2段营业店', r_status: '0'},
        {r_resdate: '2023-5-21', custaccount_id: '17345449129', c_name: '王狼', b_brand: '喜德盛', b_name: 'RC 200', r_number: '1', r_totalprice: '200', s_address: '成都锦城大道2段营业店', r_status: '1'},
        {r_resdate: '2023-5-22', custaccount_id: '17345449129', c_name: '王狼', b_brand: '喜德盛', b_name: 'RC 200', r_number: '1', r_totalprice: '300', s_address: '成都武侯区天府5街营业店', r_status: '1'},
        {r_resdate: '2023-5-23', custaccount_id: '17345449129', c_name: '王狼', b_brand: '喜德盛', b_name: 'RC 200', r_number: '3', r_totalprice: '600', s_address: '成都武侯区天府5街营业店', r_status: '0'},
        {r_resdate: '2023-5-20', custaccount_id: '17345449129', c_name: '王狼', b_brand: '喜德盛', b_name: 'RC 200', r_number: '1', r_totalprice: '300', s_address: '成都锦城大道2段营业店', r_status: '0'},
        {r_resdate: '2023-5-21', custaccount_id: '17345449129', c_name: '王狼', b_brand: '喜德盛', b_name: 'RC 200', r_number: '3', r_totalprice: '450', s_address: '成都成华大道二仙桥营业店', r_status: '1'},
        {r_resdate: '2023-5-23', custaccount_id: '17345449129', c_name: '王狼', b_brand: '喜德盛', b_name: 'RC 200', r_number: '2', r_totalprice: '300', s_address: '成都成华大道二仙桥营业店', r_status: '0'},
      ],
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 10, // 每页的数据条数
      }
  },
  methods: {
    getStatus(status) {
      return status == '0' ? '未成功' : '已成功'
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
    const filterTag = (value, row) => {
      return row.r_status == value
    }
    const filterHandler = (value, row, column) => {
      const property = column['property']
      return row[property] == value
    }
    const filterAddress = (value, row) => {
      return row.s_address == value
    }

    return{
      filterTag,
      filterHandler,
      filterAddress
    }
  }
}
</script>

<style scoped>
  .reservation{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 1%;
    border-radius: 3px;
  }
  .reservation>div:nth-child(1){
    padding-left: 2%;
    padding-right: 2%;
  }
  .reservation>div:nth-child(2){
    padding-left: 1%;
    padding-right: 1%;
  }
  .el-button{
    border-radius: 1px;
  }
</style>
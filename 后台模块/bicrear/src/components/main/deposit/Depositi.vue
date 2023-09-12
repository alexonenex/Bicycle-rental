<template>
  <!--页头-->
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">收费与押金管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>押金/时价设计</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <!--数据-->
  <div class="depositi">
    <el-row>
      <!--押金-->
      <el-col :span="12">
        <div>
          <el-row gutter="20">
            <el-col :span="7">
              <el-form-item label="区间1">
                <el-select-v2
                  v-model="d_primary"
                  :options="primaryList1"
                  placeholder="开始价格"
                />
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="区间2">
                <el-select-v2
                  v-model="d_secondary"
                  :options="primaryList2"
                  placeholder="结束价格"
                />
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="押金">
                <el-select-v2
                  v-model="d_price"
                  :options="depositiList"
                  placeholder="押金金额"
                />
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-button type="primary">提交</el-button>
            </el-col>
          </el-row>
          <!--数据-->
          <el-table :data="depositiData" style="width: 100%" height="500">
            <el-table-column type="index" label="序数" width="70" />
            <el-table-column label="编号" prop="id"/>
            <el-table-column label="开始价格" prop="interval_1"/>
            <el-table-column label="结束价格" prop="interval_2"/>
            <el-table-column label="押金金额" sortable prop="charge"/>
            <el-table-column label="操作">
              <template #default="scope">
                <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      
      <!--时价-->
      <el-col :span="12">
        <div>
          <el-row gutter="20">
            <el-col :span="7">
              <el-form-item label="区间1">
                <el-select-v2
                  v-model="c_primary"
                  :options="primaryList1"
                  placeholder="开始价格"
                />
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="区间2">
                <el-select-v2
                  v-model="c_secondary"
                  :options="primaryList2"
                  placeholder="结束价格"
                />
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-form-item label="时价">
                <el-input-number v-model="c_price" :precision="2" :step="0.5" controls-position="right" placeholder="0.00" />
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-button type="success">提交</el-button>
            </el-col>
          </el-row>
          <!--数据-->
          <el-table :data="currentpriceData" style="width: 100%" height="500">
            <el-table-column type="index" label="序数" width="70" />
            <el-table-column label="编号" prop="id"/>
            <el-table-column label="开始价格" prop="c_primary"/>
            <el-table-column label="结束价格" prop="c_secondary"/>
            <el-table-column label="时价金额" sortable prop="c_price"/>
            <el-table-column label="操作">
              <template #default="scope">
                <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref } from 'vue';


export default {
  name: 'Depositi',
  data() {
    return{
      // 押金信息
      d_primary:'',
      d_secondary:'',
      d_price:'',
      // 时价信息
      c_primary:'',
      c_secondary:'',
      c_price:'',
      //押金信息
      depositiData: [{
          interval_1: 100,
          interval_2: 200,
          charge: '150'
        },
        {
          interval_1: 200,
          interval_2: 300,
          charge: '150'
        },
        {
          interval_1: 300,
          interval_2: 400,
          charge: '200'
        },
        {
          interval_1: 500,
          interval_2: 600,
          charge: '150'
        },
        {
          interval_1: 700,
          interval_2: 900,
          charge: '150'
        },
        {
          interval_1: 900,
          interval_2: 1000,
          charge: '150'
        }
      ],

      // 时价信息
      currentpriceData: [{
          c_primary: 100,
          c_secondary: 300,
          c_price: '20'
        },
        {
          c_primary: 300,
          c_secondary: 500,
          c_price: '30'
        },
        {
          c_primary: 300,
          c_secondary: 500,
          c_price: '30'
        },
        {
          c_primary: 600,
          c_secondary: 800,
          c_price: '20'
        },
        {
          c_primary: 300,
          c_secondary: 500,
          c_price: '30'
        },
        {
          c_primary: 300,
          c_secondary: 500,
          c_price: '30'
        }
      ]
    }
  },
  setup(){
    // 押金设计
    const primaryList1 = Array.from({ length: 200 }).map((_, idx) => ({
      value: `${idx * 2 + 1}00`,
      label: `${idx * 2 + 1}00`,
    }));
    const primaryList2 = Array.from({ length: 200 }).map((_, idx) => ({
      value: `${idx * 2 + 1}00`,
      label: `${idx * 2 + 1}00`,
    }));
    const depositiList = Array.from({ length: 50 }).map((_, idx) => ({
      value: `${(idx + 1) * 100 / 2}`,
      label: `${(idx + 1) * 100 / 2}`,
    }));
    // 时价设计
    const currentpriceList1 = Array.from({ length: 200 }).map((_, idx) => ({
      value: `${idx * 2 + 1}00`,
      label: `${idx * 2 + 1}00`,
    }));
    const currentpriceList2 = Array.from({ length: 200 }).map((_, idx) => ({
      value: `${idx * 2 + 1}00`,
      label: `${idx * 2 + 1}00`,
    }));
    return{
      primaryList1,
      primaryList2,
      depositiList,
      currentpriceList1,
      currentpriceList2,
    }
  }
}


</script>

<style scoped>
  .depositi{
    width: 98%;
    background: transparent;
    padding-top: 2%;
    padding-bottom: 1%;
    border-radius: 3px;
  }
  .depositi>.el-row>.el-col>div{
    width: 95%;
    background: white;
    border-radius: 2px;
  }
  .depositi>.el-row>.el-col>div>.el-row{
    padding: 2% 2% 0 2%;
  }
</style>
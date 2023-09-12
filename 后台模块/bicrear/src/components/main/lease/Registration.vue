<template>
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">租借信息管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>租借登记</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <div class="registration">
    <el-form :model="leaseInfo" label-width="120px">
      <!--顾客信息-->
      <el-row>
        <el-col :span="2">
          <el-tag class="ml-2" type="success">顾客信息</el-tag>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12"><div class="grid-content ep-bg-purple" />
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="手机号">
                <el-autocomplete
                  v-model="leaseInfo.phone"
                  :fetch-suggestions="querySearchAsync"
                  placeholder="手机号/账号"
                  @select="handleSelect"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="姓名">
                <el-input v-model="leaseInfo.name" placeholder="姓名" />
              </el-form-item>
            </el-col>

          </el-row>
        </el-col>
        <el-col :span="12"><div class="grid-content ep-bg-purple" />
          <el-row gutter="10">
            <el-col :span="10">
              <el-form-item label="性别">
                <el-select v-model="leaseInfo.sex" placeholder="性别">
                  <el-option label="男" value="男" />
                  <el-option label="女" value="女" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item
                label="年龄"
                prop="age"
                :rules="[
                  { required: true, message: '年龄不能为空' },
                  { type: 'number', message: '必须为整数' },
                ]"
              >
                <el-input
                  v-model.number="leaseInfo.age"
                  type="text"
                  autocomplete="off"
                  v-min="14"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12"><div class="grid-content ep-bg-purple" />
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item
                prop="email"
                label="邮箱地址"
                :rules="[
                  {
                    required: true,
                    message: '邮箱不能为空',
                    trigger: 'blur',
                  },
                  {
                    type: 'email',
                    message: '邮箱地址不正确',
                    trigger: ['blur', 'change'],
                  },
                ]"
              >
                <el-input v-model="leaseInfo.email" placeholder="xxx@xx.com" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="身份证号">
                <el-input v-model="leaseInfo.card" placeholder="身份证号" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12"><div class="grid-content ep-bg-purple" />
          <el-row gutter="10">
            <el-col :span="20">
              <el-form-item label="联系地址">
                <el-input v-model="leaseInfo.address" placeholder="联系地址" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <!--车辆信息-->
      <el-row gutter="10">
        <el-col :span="2">
          <el-tag class="ml-2" type="warning">车辆信息</el-tag>
        </el-col>
      </el-row>
      <el-row gutter="20">
        <el-col :span="12">
          <el-row gutter="10">
            <el-col :span="12">
              <el-form-item label="品牌">
                <el-select v-model="leaseInfo.brand" placeholder="自行车品牌">
                  <el-option label="捷安特" value="捷安特" />
                  <el-option label="喜德盛" value="喜德盛" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="名称"
                prop="date1"
                :rules="[{ required: true, message: '车辆不能为控' }]"
              >
                <el-autocomplete
                  v-model="leaseInfo.b_name"
                  :fetch-suggestions="querySearchAsync"
                  placeholder="自行车名称"
                  @select="handleSelect"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row gutter="10">
            <el-col :span="12">
              <el-form-item
                label="数量"
                prop="number"
                :rules="[{ required: true, message: '数量不能为控' }]"
              >
                <el-input-number v-model="leaseInfo.number" :min="1" :max="3" @change="handleChange" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <!--时间选择-->
      <el-row gutter="10">
        <el-col :span="2">
          <el-tag class="ml-2" type="danger">始发时间</el-tag>
        </el-col>
        <el-col :span="7">
          <!-- <el-text class="mx-1" type="danger" size="small">注：日期/时间不做选择时默认立即生效，即当前日期/时间</el-text> -->
        </el-col>
      </el-row>
      <el-row gutter="20">
        <el-col :span="12">
          <el-row gutter="10">
            <el-col :span="12">
              <el-form-item label="日期">
                <el-date-picker
                  v-model="leaseInfo.date"
                  type="date"
                  placeholder="开始日期"
                  size="default"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                label="时间"
                prop="time"
              >
                <el-time-select
                  v-model="leaseInfo.time"
                  start="00:00"
                  step="00:15"
                  end="23:45"
                  placeholder="开始时间"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row gutter="10">
            <el-col :span="14">
              <el-text class="mx-1" type="danger" size="small">*注：日期/时间不做选择时默认立即生效，即当前日期/时间</el-text>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <!--提交按钮-->
      <el-row gutter="20" style="margin-top: 3%;">
        <el-col :span="7"></el-col>
        <el-col :span="17">
          <el-form-item>
            <el-button type="primary" @click="submitForm(leaseInfo)">提交</el-button>
            <el-button @click="resetForm(leaseInfo)">清空</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>

</template>

<script>
import { reactive } from 'vue'
export default {
  name: 'Registration',
  data () {
    return {
      leaseInfo: {
        phone: '',
        u_name: '',
        sex: '',
        age: '',
        email: '',
        card: '',
        address: '',
        brand: '',
        b_name: '',
        number: 1,
        date: '',
        time: ''
      }
    }
  }
}
</script>

<style scoped>
  .registration{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 2%;
    border-radius: 3px;
  }
  .el-button{
    width: 100px;
    height: 33px;
    border-radius: 1px;
  }
  .el-row{
    margin-top: 1%;
  }
</style>
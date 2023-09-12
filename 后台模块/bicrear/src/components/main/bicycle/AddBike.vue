<template>
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">自行车管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>车辆新增</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <div class="addBike">
    <el-form :model="bicycleList" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="车辆品牌" prop="brand">
        <el-select class="unify" v-model="bicycleList.brand" placeholder="请选择自行车品牌">
          <el-option label="捷安特" value="捷安特"></el-option>
          <el-option label="喜德盛" value="喜德盛"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="车辆名称" prop="name">
        <el-input class="el-input" v-model="bicycleList.name" placeholder="请输入自行车名称"></el-input>
      </el-form-item>

      <el-form-item label="车辆类型" prop="type">
        <el-select v-model="bicycleList.region" placeholder="请选择自行车类型">
          <el-option label="公路车" value="公路车"></el-option>
          <el-option label="山地车" value="山地车"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="入库数量">
        <el-input-number v-model="bicycleList.number" controls-position="right" @change="handleChange" :min="1"></el-input-number>
      </el-form-item>

      <el-row gutter="20">
        <el-col :span="6">
          <el-form-item label="押金选择">
            <el-autocomplete
              v-model="bicycleList.deposit"
              :fetch-suggestions="querySearchAsync"
              placeholder="押金"
              @select="handleSelect"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="时价选择">
            <el-autocomplete
              v-model="bicycleList.price"
              :fetch-suggestions="querySearchAsync"
              placeholder="时价"
              @select="handleSelect"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="位置选择">
            <el-autocomplete
              v-model="bicycleList.address"
              :fetch-suggestions="querySearchAsync"
              placeholder="位置"
              @select="handleSelect"
            />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-form-item label="车辆图像">
        <el-upload
          v-model:file-list="fileList"
          class="upload-demo"
          action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          list-type="picture"
        >
          <el-button type="primary" style="margin-left: -140%;">点击上传</el-button>
          <template #tip>
            <div class="el-upload__tip">
              只能上传jpg/png文件，且不超过500kb
            </div>
          </template>
        </el-upload>
      </el-form-item>
      
      <el-form-item label="车辆简介">
        <el-input class="el-input" type="textarea" v-model="bicycleList.essay"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'AddBike',
  data() {
    return {
      bicycleList: {
        brand: '',
        name: '',
        type: '',
        number: 1,
        deposit: '',
        price: '',
        address: '',
        essay: ''
      },
      rules: {
        brand: [
          { required: true, message: '请选择品牌名', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入车辆名称', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20个字符', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择自行车类型', trigger: 'blur' }
        ],
        handleChange(value) {
          console.log(value);
        }
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
  .addBike{
    width: 80%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 5%;
    border-radius: 3px;
  }
  .el-input{
    width: 50%;
  }
  button{
    border-radius: 1px;
  }
</style>
<template>
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">自行车管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>车辆信息</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <div class="bikeInfo">
    <!--头部操作-->
    <div>
      <div>
        <el-form :inline="true" :model="bicycleInfoList" class="demo-form-inline">
          <el-form-item label="名称">
            <el-autocomplete
              v-model="bicycleInfoList.name"
              :fetch-suggestions="querySearchAsync"
              placeholder="自行车名称"
              @select="handleSelect"
            />
          </el-form-item>
          <el-form-item label="品牌">
            <el-select style="width: 130px;" v-model="bicycleInfoList.type" placeholder="自行车品牌">
              <el-option label="全部" value="全部"></el-option>
              <el-option label="捷安特" value="捷安特"></el-option>
              <el-option label="喜德盛" value="喜德盛"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div style="padding-right: 2%;">
        <el-button type="danger">删除</el-button>
      </div>
      
    </div>
    <!--列表数据-->
    <div>
      <el-table
        ref="multipleTable"
        :data="bicycleInfoList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
        tooltip-effect="dark"
        style="width: 100%; text-align: left;"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          label="图像"
          width="120">
          <template #default="scope">
            <div class="demo-image__preview">
              <el-image
                style="width: 80%"
                :src="scope.row.imgurl"
                :zoom-rate="1.2"
                :preview-src-list="images"
                :initial-index="0"
                preview-teleported="true"
                fit="cover"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="brand"
          label="品牌"
          width="90">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          width="80">
        </el-table-column>
        <el-table-column
          prop="stock"
          label="库存"
          sortable
          width="80">
        </el-table-column>
        <el-table-column
          prop="price"
          label="时价"
          sortable
          width="80">
        </el-table-column>
        <el-table-column
          prop="depositi"
          label="押金"
          sortable
          width="80">
        </el-table-column>
        <el-table-column
          prop="address"
          label="地址"
          width="150"
          show-overflow-tooltip
          :filters=addressList
          :filter-method="filterTag"
          filter-placement="bottom-end">
          <template #default="scope">
            {{ scope.row.address }}
          </template>
        </el-table-column>
        <el-table-column
          prop="essay"
          label="简介"
          width="190"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--修改自行车部分-->
    <el-drawer v-model="drawer" direction="ltr">
      <template #header>
        <h4>修改自行车信息</h4>
      </template>
      <template #default>
        <div>
          <el-form :model="bikeInfo" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="车辆品牌">
              <el-select class="unify" v-model="bikeInfo.brand" placeholder="请选择自行车品牌">
                <el-option label="捷安特" value="捷安特"></el-option>
                <el-option label="喜德盛" value="喜德盛"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="车辆名称">
              <el-input class="el-input" v-model="bikeInfo.name" placeholder="请输入自行车名称" style="width: 68%;"></el-input>
            </el-form-item>
            <el-form-item label="车辆类型">
              <el-select v-model="bikeInfo.type" placeholder="请选择自行车类型">
                <el-option label="公路车" value="公路车"></el-option>
                <el-option label="山地车" value="山地车"></el-option>
              </el-select>
            </el-form-item>

            <el-row gutter="20">
              <el-col :span="12">
                <el-form-item label="押金选择">
                  <el-autocomplete
                    v-model="bikeInfo.depositi"
                    :fetch-suggestions="querySearchAsync"
                    placeholder="押金"
                    @select="handleSelect"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="时价选择">
                  <el-autocomplete
                    v-model="bikeInfo.price"
                    :fetch-suggestions="querySearchAsync"
                    placeholder="时价"
                    @select="handleSelect"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="位置选择">
                  <el-autocomplete
                    v-model="bikeInfo.address"
                    :fetch-suggestions="querySearchAsync"
                    placeholder="位置"
                    @select="handleSelect"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="入库数量">
              <el-input-number v-model="bikeInfo.stock" controls-position="right" @change="handleChange" :min="1"></el-input-number>
            </el-form-item>
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
              <el-input class="el-input" type="textarea" v-model="bikeInfo.essay" style="height: 200px;"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </template>
      <template #footer>
        <div style="flex: auto">
          <el-button @click="drawer=false">取消</el-button>
          <el-button type="primary" @click="confirmClick">确定</el-button>
        </div>
      </template>
    </el-drawer>

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
            :total="bicycleInfoList.length">
          </el-pagination>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>
import { ref } from 'vue'
import { reactive } from 'vue'
import { ElMessageBox } from 'element-plus'
import { getCurrentInstance } from "vue";

// import { tr } from 'element-plus/es/locale'

export default {
  name: 'BikeInfo',
  data () {
    return {
      addressList: [
        {text: "成都锦城大道2段营业店", value: "成都锦城大道2段营业店"},
        {text: "成都武侯区天府5街营业店", value: "成都武侯区天府5街营业店"},
        {text: "成都成华大道二仙桥营业店", value: "成都成华大道二仙桥营业店"},
      ],
      bicycleInfoList: [
        {
        id: '2203050602',
        brand: '捷安特',
        name: 'MY 20',
        type: '公路车',
        stock: 29,
        price: 28,
        depositi: 250,
        address: '成都锦城大道2段营业店',
        image: 'abcdefg',
        imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
        essay: '全新的Propel的设计理念基于AeroSystem超低风阻管型技术打造。通过分析管型形状、角度以及接合点，作为一个整体的系统每一个组成部件都显得尤为关键。空力系统管型重新设计，灵动的线条和优化的椭圆截背管型以最大限度减少阻力。 专属空力套件，标配两款专属设计的水壶架以遵循我们对空气动力学设计的理念，以最大限度地减少空气阻力。整合的空力水壶经过UCI认证可正式参赛。'
      }, 
      {
        id: '2203050602',
        brand: '捷安特',
        name: 'MY 22',
        type: '公路车',
        stock: 2,
        price: 28,
        depositi: 200,
        address: '成都武侯区天府5街营业店',
        image: 'abcdefg',
        imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
        essay: '全新的Propel的设计理念基于AeroSystem超低风阻管型技术打造。通过分析管型形状、角度以及接合点，作为一个整体的系统每一个组成部件都显得尤为关键。空力系统管型重新设计，灵动的线条和优化的椭圆截背管型以最大限度减少阻力。 专属空力套件，标配两款专属设计的水壶架以遵循我们对空气动力学设计的理念，以最大限度地减少空气阻力。整合的空力水壶经过UCI认证可正式参赛。'
      }, 
      {
        id: '2203050602',
        brand: '捷安特',
        name: 'MY 23',
        type: '公路车',
        stock: 12,
        price: 12,
        depositi: 200,
        address: '成都锦城大道2段营业店',
        image: 'abcdefg',
        imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
        essay: '全新的Propel的设计理念基于AeroSystem超低风阻管型技术打造。通过分析管型形状、角度以及接合点，作为一个整体的系统每一个组成部件都显得尤为关键。空力系统管型重新设计，灵动的线条和优化的椭圆截背管型以最大限度减少阻力。 专属空力套件，标配两款专属设计的水壶架以遵循我们对空气动力学设计的理念，以最大限度地减少空气阻力。整合的空力水壶经过UCI认证可正式参赛。'
      }, 
      {
        id: '2203050602',
        brand: '捷安特',
        name: 'MY 24',
        type: '公路车',
        stock: 8,
        price: 38,
        depositi: 400,
        address: '成都武侯区天府5街营业店',
        image: 'abcdefg',
        imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
        essay: '全新的Propel的设计理念基于AeroSystem超低风阻管型技术打造。通过分析管型形状、角度以及接合点，作为一个整体的系统每一个组成部件都显得尤为关键。空力系统管型重新设计，灵动的线条和优化的椭圆截背管型以最大限度减少阻力。 专属空力套件，标配两款专属设计的水壶架以遵循我们对空气动力学设计的理念，以最大限度地减少空气阻力。整合的空力水壶经过UCI认证可正式参赛。'
      }, 
      {
        id: '2203050602',
        brand: '捷安特',
        name: 'MY 24',
        type: '公路车',
        stock: 8,
        price: 30,
        depositi: 250,
        address: '成都锦城大道2段营业店',
        image: 'abcdefg',
        imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
        essay: '全新的Propel的设计理念基于AeroSystem超低风阻管型技术打造。通过分析管型形状、角度以及接合点，作为一个整体的系统每一个组成部件都显得尤为关键。空力系统管型重新设计，灵动的线条和优化的椭圆截背管型以最大限度减少阻力。 专属空力套件，标配两款专属设计的水壶架以遵循我们对空气动力学设计的理念，以最大限度地减少空气阻力。整合的空力水壶经过UCI认证可正式参赛。'
      }, 
      {
        id: '2203050602',
        brand: '捷安特',
        name: 'MY 24',
        type: '公路车',
        stock: 8,
        price: 18,
        depositi: 100,
        address: '成都成华大道二仙桥营业店',
        image: 'abcdefg',
        imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
        essay: '全新的Propel的设计理念基于AeroSystem超低风阻管型技术打造。通过分析管型形状、角度以及接合点，作为一个整体的系统每一个组成部件都显得尤为关键。空力系统管型重新设计，灵动的线条和优化的椭圆截背管型以最大限度减少阻力。 专属空力套件，标配两款专属设计的水壶架以遵循我们对空气动力学设计的理念，以最大限度地减少空气阻力。整合的空力水壶经过UCI认证可正式参赛。'
      }
    ],
    images: [
      'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
      'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
      'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp',
    ],
    currentPage: 1, // 当前页码
    total: 20, // 总条数
    pageSize: 10, // 每页的数据条数
    multipleSelection: []

    
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
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
    const drawer = ref(false);
    const bikeInfo = reactive({id: '', brand: '', name: '', type: '', stock: null, price: '', depositi: '',  address: '', image: '', imgurl: '', essay: ''});
    let i = ref(null);
    const handleClose = done => {
      ElMessageBox.confirm('Are you sure you want to close this?')
        .then(() => {
          done()
        })
        .catch(() => {
          // catch error
        })
    }
    // 赋值
    function assignment(data) {
      bikeInfo.id = data.id;
      bikeInfo.brand = data.brand;
      bikeInfo.name = data.name;
      bikeInfo.type = data.type;
      bikeInfo.stock = data.stock;
      bikeInfo.depositi = data.depositi,
      bikeInfo.price = data.price;
      bikeInfo.address = data.address;
      bikeInfo.image = data.image;
      bikeInfo.imgurl = data.imgurl;
      bikeInfo.essay = data.essay;
    }
    // 编辑按钮
    function handleEdit(index, row) {
      drawer.value = true;
      assignment(row);
      i.value = index;
    }
    // 点击确定按钮
    function confirmClick() {
      console.log("进来了");
      drawer.value = false;
      exData.data.bicycleInfoList[i.value] = bikeInfo;
    }
    // 地区筛选
    const filterTag = (value, row) => {
      return row.address === value
    }
    return{
      drawer,
      handleClose,
      handleEdit,
      confirmClick,
      bikeInfo,
      filterTag
    }
  }
}
</script>

<style scoped>
  .bikeInfo{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 1%;
    border-radius: 3px;
  }
  .bikeInfo>div:nth-child(1){
    padding-left: 1%;
    text-align: left;
    display: flex;
  }
  .bikeInfo>div:nth-child(1)>div:nth-child(2){
    text-align: right;
    flex: 1;
    padding-right: 1%;
  }
  .bikeInfo>div:nth-child(2){
    padding-left: 1%;
    padding-right: 1%;
  }
  .demo-image__error .image-slot {
    font-size: 30px;
  }
  .demo-image__error .image-slot .el-icon {
    font-size: 30px;
  }
  .demo-image__error .el-image {
    width: 100%;
    height: 200px;
  }
  .el-button{
    border-radius: 1px;
  }
</style>
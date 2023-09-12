<template>
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">前端数据管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>板块数据设计</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <div class="segmentData">
    <!--添加-->
    <div>
      <el-row gutter="20">
        <el-col :span="4">
          <el-form-item label="名称">
            <el-autocomplete
              v-model="state"
              :fetch-suggestions="querySearchAsync"
              placeholder="自行车名称"
              @select="handleSelect"
            />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="板块">
            <el-select v-model="value" class="m-2" placeholder="板块选择">
              <el-option
                v-for="item in p_plateList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item>
            <el-button type="primary" @click="addBick">添加</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button type="warning" @click="dekBick">批量删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
    <!--列表数据-->
    <div>
      <el-table row-key="date" :data="segmentData.slice((currentPage-1)*pageSize,currentPage*pageSize)" style="width: 100%">
        <el-table-column type="selection" width="50" />
        <el-table-column
          prop="b_imgurl"
          label="图像"
          width="120">
          <template #default="scope">
            <div class="demo-image__preview">
              <el-image
                style="width: 80%"
                :src="scope.row.b_imgurl"
                :zoom-rate="1.2"
                :initial-index="0"
                preview-teleported="true"
                fit="cover"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="b_brand" label="品牌" />
        <el-table-column prop="b_name" label="名称" />
        <el-table-column prop="b_type" label="类型" />
        <el-table-column prop="b_stock" label="库存" sortable />
        <el-table-column prop="s_address" width="200" label="位置" show-overflow-tooltip/>
        <el-table-column
          prop="p_plate"
          label="板块"
          width="120"
          :filters="[
            {text: '推荐', value: '推荐'},
            {text: '公路车', value: '公路车'},
            {text: '山地车', value: '山地车'},
            {text: '配件', value: '配件'},
          ]"
          :filter-method="filterPlate"
          filter-placement="bottom-end"
        >
          <template #default="scope">
            <el-tag
              :type=getSegment(scope.row.p_plate)
              disable-transitions
              >{{ scope.row.p_plate }}</el-tag
            >
          </template>
        </el-table-column>
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
    <!--分页-->
    <div>
      <el-row gutter="20">
        <el-col :span="7"></el-col>
        <el-col :span="17">
          <el-pagination
            @size-change="handleSizeChange" 
            @current-change="handleCurrentChange"
            :current-page="currentPage" 
            :page-sizes="[1, 5, 10, 20, 30, 50, 100]" 
            :page-size="pageSize" 
            layout="total, sizes, prev, pager, next, jumper" 
            :total="segmentData.length">
          </el-pagination>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SegmentData',
  data () {
    return {
      segmentData: [
        {platedata_id: 'TJ1003', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '24', s_address: '成都锦城大道2段营业店', p_plate: '推荐', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'GLC1012', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '2', s_address: '成都锦城大道2段营业店', p_plate: '公路车', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'SDC0002', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '4', s_address: '成都锦城大道2段营业店', p_plate: '推荐', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'TJ1004', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '14', s_address: '成都锦城大道2段营业店', p_plate: '公路车', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'TJ1003', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '55', s_address: '成都锦城大道2段营业店', p_plate: '山地车', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'GLC1012', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '2', s_address: '成都锦城大道2段营业店', p_plate: '公路车', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'SDC0002', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '0', s_address: '成都锦城大道2段营业店', p_plate: '推荐', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'TJ1004', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '0', s_address: '成都锦城大道2段营业店', p_plate: '山地车', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'TJ1003', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '1', s_address: '成都锦城大道2段营业店', p_plate: '推荐', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'GLC1012', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '34', s_address: '成都锦城大道2段营业店', p_plate: '山地车', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'SDC0002', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '2', s_address: '成都锦城大道2段营业店', p_plate: '推荐', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {platedata_id: 'TJ1004', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', b_stock: '27', s_address: '成都锦城大道2段营业店', p_plate: '山地车', b_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
      ],
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 6, // 每页的数据条数
      p_plateList: [
        { label: '推荐', value: '推荐' },
        { label: '公路车', value: '公路车' },
        { label: '山地车', value: '山地车' },
        { label: '配件', value: '配件' }
      ]
    }
  },
  methods: {
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
    },
    getSegment(p_plate){
      if(p_plate == '推荐') return 'success';
      else if(p_plate == '公路车') return ' ';
      else if(p_plate == '山地车') return 'warning';
      else return 'info';
    }
  },
  setup() {
    const filterPlate = (value, row) => {
      return row.p_plate == value
    }
    return {
      filterPlate
    }
  }
}
</script>

<style scoped>
  .segmentData{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 1%;
    border-radius: 3px;
  }
  .segmentData>div:nth-child(1){
    padding-left: 2%;
  }
  .segmentData>div:nth-child(2){
    padding-left: 2%;
    padding-right: 2%;
  }
  .segmentData>div:nth-child(3){
    margin-top: 1%;
  }
  .el-button{
    border-radius: 1px;
  }
</style>
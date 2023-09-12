<!--
 * @Author: WangCute 2605735186@qq.com
 * @Date: 2023-05-23 15:18:51
 * @LastEditors: WangCute 2605735186@qq.com
 * @LastEditTime: 2023-05-23 20:38:44
 * @FilePath: \bicrear\src\components\main\frontend\Carousel.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div style="margin-top: 3%;">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">前端数据管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>轮播图设计</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  
  <div class="carousel">
    <!--添加-->
    <div>
      <el-row gutter="20">
        <el-col :span="2" style="margin-top: 0.3%;"><el-tag>快捷添加</el-tag></el-col>
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
        <el-col :span="8">
          <el-form-item>
            <el-button type="primary" @click="addBick">添加</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="2"><el-tag style="margin-top: 1%;" type="success">自定义添加</el-tag></el-col>
        <el-col :span="4">
          <el-form-item label="名称">
            <el-input v-model="name" placeholder="自定义名称" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-upload
            ref="upload"
            class="upload-demo"
            action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
            :limit="1"
            :on-exceed="handleExceed"
            :auto-upload="false"
          >
            <template #trigger>
              <el-button type="primary">选择图片</el-button>
            </template>
            <el-button class="ml-3" type="success" style="margin-left: 3%;" @click="submitUpload">
              添加
            </el-button>
          </el-upload>
        </el-col>
        <!-- <el-col :span="2">
          <el-form-item>
            <el-button type="warning" @click="addCustom">添加</el-button>
          </el-form-item>
        </el-col> -->
      </el-row>
    </div>
    <!--列表数据-->
    <div>
      <el-table ref="tableRef" row-key="date" :data="carouselList" style="width: 100%">
        <el-table-column type="index" width="50" />
        <el-table-column prop="c_image" label="图名" />
        <el-table-column
          prop="c_imgurl"
          label="图像"
          width="120">
          <template #default="scope">
            <div class="demo-image__preview">
              <el-image
                style="width: 80%"
                :src="scope.row.c_imgurl"
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
        <el-table-column prop="c_imgurl" width="300" label="图片地址" show-overflow-tooltip />
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
  </div>
</template>

<script>
export default {
  name: 'Carousel',
  data () {
    return {
      carouselList: [
        {c_image: 'GIANT-MY23', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', c_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {c_image: 'GIANT-MY23', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', c_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
        {c_image: 'GIANT-MY23', b_brand: '捷安特', b_name: 'MY 23', b_type: '公路车', c_imgurl: 'https://giant-img.oss-cn-shanghai.aliyuncs.com/bike/20220819/166087296229868.jpg@!giantWeb_Vehicle_Small.webp'},
      ]
    }
  }
}
</script>

<style scoped>
  .carousel{
    width: 90%;
    background: white;
    margin-top: 1%;
    padding-top: 2%;
    padding-bottom: 2%;
    border-radius: 3px;
  }
  .carousel>div:nth-child(1){
    padding-left: 1%;
    padding-right: 2%;
  }
  .carousel>div:nth-child(2){
    padding-left: 1%;
    /* padding-right: 1%; */
  }
  .el-button{
    border-radius: 1px;
  }
</style>
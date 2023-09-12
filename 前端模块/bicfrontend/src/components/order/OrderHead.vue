<!--
 * @Author: WangCute 2605735186@qq.com
 * @Date: 2023-05-14 20:55:43
 * @LastEditors: WangCute 2605735186@qq.com
 * @LastEditTime: 2023-05-31 12:14:35
 * @FilePath: \bicfrontend\src\components\order\OrderHead.vue
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
-->
<template>
  <div class="orderHead">
    <div class="row">
      <div class="col-2">
        <router-link to="/home"><i class="bi bi-chevron-left"></i></router-link>
      </div>
      <div class="col-8">
        <!-- <p style="font-size: 17px;line-height: 45px;">预约订单</p> -->
        <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasTop" aria-controls="offcanvasTop" style="background-color: rgb(29, 32, 136); border: 0;">预约/日期</button>
        <span class="dropdown-toggle" style="color: white;"></span>
      </div>
      <div class="col-2">
        <p @click="isSwitch">{{ title }}</p>
      </div>
    </div>

    <div class="offcanvas offcanvas-top" tabindex="-1" id="offcanvasTop" aria-labelledby="offcanvasTopLabel">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasTopLabel">日期选择</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <div v-for="(item, index) in date" :key="index">
          <label><input type="radio" name="date" v-model="selectDate" @click="selectDateValue(item.value)" :value="item.value" :checked="item.id == 1"/>&nbsp;{{ item.label }}&nbsp;({{ item.value }})</label>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OrderHead',
  data () {
    return {
      isManage: true,
      title: '管理',
      selectDate: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate(),
      date: [
        {id: 1, value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate(), label: '今天'},
        {id: 2, value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() + 1), label: '明天'},
        {id: 3, value: new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + (new Date().getDate() + 2), label: '后天'},
      ]
    }
  },
  methods: {
    isSwitch() {
      this.isManage = !this.isManage;
      if(this.title == '管理') this.title = '完成';
      else this.title = '管理';
      this.$emit("isManage", this.isManage);
      console.log(this.isManage);
    },
    selectDateValue(date) {
      this.$emit("selectDate", date);
    }
  },
  watch: {
    selectDate() {
      console.log("selectDate" + this.selectDate);
    }
  }
}
</script>

<style scoped>
  .orderHead{
    width: 100%;
    height: 50px;
    background: rgb(29, 32, 136);
    padding-right: 3%;
  }
  .orderHead>div{
    line-height: 50px;
  }
  .orderHead i{
    color: white;
    font-size: 18px;
  }
  .orderHead p{
    color: white;
    font-size: 15px;
  }
  
</style>
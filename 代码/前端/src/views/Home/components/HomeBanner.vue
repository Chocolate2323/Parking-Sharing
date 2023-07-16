<script setup>
// import { getBannerPI } from '@/apis/home'
// import { onMounted,ref } from 'vue'

// const bannerList = ref([])
// const getBanner = async () =>{
//   const res =await getBannerPI()
//   console.log(res)
//   bannerList.value=res.result
// }
// onMounted(() => getBanner())
import { ref } from 'vue';

const BannerList = ref([
  'src/assets/images/show1.png',
  'src/assets/images/show2.png',
  'src/assets/images/show3.png',
  'src/assets/images/show4.png',
  'src/assets/images/show5.png',
]);
</script>

<template>
  <div class="home-banner">
    <el-carousel :interval="3000" type="card" height="500px">
<!--      <el-carousel-item v-for="item in bannerList" :key="item">-->
<!--        <img :src="item.imgUrl" alt="">-->
<!--      </el-carousel-item>-->
      <el-carousel-item v-for="item in BannerList" :key="item">
        <img :src="item" alt="">
      </el-carousel-item>
    </el-carousel>
  </div>
</template>
<style scoped lang='scss'>
.home-banner {
  width: 1240px;
  height: 500px;
  position: absolute;
  left: 0;
  top: 0;
  z-index: 98;
  img {
    width: 100%;
    height: 500px;
  }
}
</style>

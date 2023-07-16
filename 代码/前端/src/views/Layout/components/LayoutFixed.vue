<script setup>
import LayoutHeaderUl from '@/views/Layout/components/LayoutHeaderUl.vue'
//吸顶导航组件
// vueUse
import { useScroll } from '@vueuse/core'
const { y } = useScroll(window)
//使用pinia中的数据
import { useCategoryStore } from "@/stores/category";
const categoryStore = useCategoryStore()

</script>

<template>
  <div class="app-header-sticky" :class="{ show: y > 78 }">
    <div class="container">
      <RouterLink class="logo" to="/" />
      <!-- 导航区域 -->
      <ul class="app-header-nav">
        <li class="home">
          <RouterLink to="/">首页</RouterLink>
        </li>
        <li>
          <RouterLink to="/parking-reservation">车位预定</RouterLink>
        </li>
<!--        <li>-->
<!--          <RouterLink to="/parking-publish">车位发布</RouterLink>-->
<!--        </li>-->
        <li>
          <RouterLink to="/parking-review">车位评价</RouterLink>
        </li>
        <li>
          <RouterLink to="/my-order">我的订单</RouterLink>
        </li>
        <li>
          <RouterLink to="/user-information-maintenance">用户信息</RouterLink>
        </li>
        <li>
          <RouterLink to="/parking-information-maintenance">车位信息</RouterLink>
        </li>
      </ul>
<!--      <LayoutHeaderUl />-->
      <div class="right">
        <RouterLink to="/baidu">专题</RouterLink>
      </div>
    </div>
  </div>
</template>


<style scoped lang='scss'>
.app-header-sticky {
  width: 100%;
  height: 80px;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 999;
  background-color: #fff;
  border-bottom: 1px solid #e4e4e4;
  // 状态一：往上平移自身高度 + 完全透明
  transform: translateY(-100%);
  opacity: 0;

  // 状态二：移除平移 + 完全不透明
  &.show {
    transition: all 0.3s linear;
    transform: none;
    opacity: 1;
  }

  .container {
    display: flex;
    align-items: center;
  }

  .logo {
    width: 200px;
    height: 80px;
    background: url("@/assets/images/head_logo.png") no-repeat right 2px;
    background-size: 160px auto;
  }

  .right {
    width: 220px;
    display: flex;
    text-align: center;
    padding-left: 40px;
    border-left: 2px solid $xtxColor;

    a {
      width: 38px;
      margin-right: 40px;
      font-size: 16px;
      line-height: 1;

      &:hover {
        color: $xtxColor;
      }
    }
  }
}
</style>

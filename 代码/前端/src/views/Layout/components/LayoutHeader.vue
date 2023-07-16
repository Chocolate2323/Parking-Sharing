<script setup>
// import { useCategoryStore } from "@/stores/category";
// const categoryStore = useCategoryStore()
</script>

<template>
  <header class='app-header'>
    <div class="container">
      <h1 class="logo">
        <RouterLink to="/">车位通</RouterLink>
      </h1>
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
      <div class="right">
        <RouterLink to="/baidu">专题</RouterLink>
      </div>
    </div>
  </header>
</template>


<style scoped lang='scss'>
.app-header {
  background: #fff;

  .container {
    display: flex;
    align-items: center;
  }

  .logo {
    width: 200px;

    a {
      display: block;
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      background: url('@/assets/images/head_logo.png') no-repeat center 18px / contain;
    }
  }


  .search {
    width: 170px;
    height: 32px;
    position: relative;
    border-bottom: 1px solid #e7e7e7;
    line-height: 32px;

    .icon-search {
      font-size: 18px;
      margin-left: 5px;
    }

    input {
      width: 140px;
      padding-left: 5px;
      color: #666;
    }
  }

  .cart {
    width: 50px;

    .curr {
      height: 32px;
      line-height: 32px;
      text-align: center;
      position: relative;
      display: block;

      .icon-cart {
        font-size: 22px;
      }

      em {
        font-style: normal;
        position: absolute;
        right: 0;
        top: 0;
        padding: 1px 6px;
        line-height: 1;
        background: $helpColor;
        color: #fff;
        font-size: 12px;
        border-radius: 10px;
        font-family: Arial;
      }
    }
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

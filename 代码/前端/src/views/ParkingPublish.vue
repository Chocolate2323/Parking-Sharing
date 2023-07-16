<template>
  <div>
    <LayoutFixed/>
    <LayoutNav/>
    <LayoutHeader/>
    <h1>车位发布</h1>
    <div class="container">
      <input type="text" v-model="searchId" placeholder="搜索ID" />
      <button @click="searchParking">搜索</button>

      <ul>
        <li v-for="parking in parkingList" :key="parking.Parking_ID">
          {{ parking.Parking_Number }}
        </li>
      </ul>

      <button @click="previousPage" :disabled="currentPage === 1">上一页</button>
      <span>{{ currentPage }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">下一页</button>
    </div>
    <LayoutFooter/>
  </div>
</template>

<script>
import LayoutHeader from '@/views/Layout/components/LayoutHeader.vue';
import LayoutFixed from '@/views/Layout/components/LayoutFixed.vue';
import LayoutNav from '@/views/Layout/components/LayoutNav.vue';
import LayoutFooter from '@/views/Layout/components/LayoutFooter.vue';
export default {
  components: {
    LayoutFixed,
    LayoutNav,
    LayoutHeader,
    LayoutFooter,
  },
  data() {
    return {
      parkingList: [], // 存储车位信息列表
      currentPage: 1, // 当前页码
      totalPages: 1, // 总页数
      searchId: '', // 搜索的ID
    };
  },
  mounted() {
    this.fetchParkingList();
  },
  methods: {
    fetchParkingList() {
      // 调用后端API获取车位信息列表
      // 可以使用axios或其他HTTP库发送请求
      // 将获取到的数据赋值给parkingList、currentPage和totalPages
    },
    searchParking() {
      // 根据searchId调用后端API搜索车位信息
      // 将获取到的数据赋值给parkingList、currentPage和totalPages
    },
    previousPage() {
      // 切换到上一页
      // 更新currentPage并调用fetchParkingList方法获取车位信息列表
      this.currentPage--;
      this.fetchParkingList();
    },
    nextPage() {
      // 切换到下一页
      // 更新currentPage并调用fetchParkingList方法获取车位信息列表
      this.currentPage++;
      this.fetchParkingList();
    },
  },
};
</script>

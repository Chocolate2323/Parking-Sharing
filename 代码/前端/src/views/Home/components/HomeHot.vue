<script setup>
import HomePanel from "@/views/Home/components/HomePanel.vue";

</script>

<template>
  <HomePanel title="人气推荐" sub-title="人气推荐 好评如潮">
    <!-- 主体内容区域 -->
    <el-row>
      <el-col
          v-for="(item) in hotList.slice(0, 3)"
          :key="item.id"
          :span="8"
          :offset="0"
      >
        <el-card :body-style="{ padding: '0px' }">
          <img
              :src="item.image"
              class="image"
          />
          <div style="padding: 14px">
            <p>{{ item.name }}</p>
            <div class="details">
              <p class="desc">车位编号: {{ item.details.Parking_Number }}</p>
              <p class="desc">位置: {{ item.details.Position }}</p>
              <p class="desc">小区名称: {{ item.details.Community_Name }}</p>
              <p class="desc">价格: {{ item.details.Price }}</p>
              <p class="desc">面积: {{ item.details.Area }}</p>
              <p class="desc">经度: {{ item.details.Longitude }}</p>
              <p class="desc">纬度: {{ item.details.Latitude }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </HomePanel>
</template>


<script>
export default {
  data() {
    return {
      hotList: [
        {
          id: 1,
          image: 'src/assets/images/show3.png',
          details: {
            Parking_Number: 'A001',
            Position: '地下一层',
            Community_Name: '某小区',
            Price: '10元/小时',
            Area: '15平方米',
            Longitude: '120.87123',
            Latitude: '30.81726',
          },
        },
        {
          id: 2,
          image: 'src/assets/images/show1.png',
          details: {
            Parking_Number: 'A002',
            Position: '地下一层',
            Community_Name: '某小区',
            Price: '10元/小时',
            Area: '15平方米',
            Longitude: '120.87123',
            Latitude: '30.81726',
          },
        },
        {
          id: 3,
          image: 'src/assets/images/show5.png',
          details: {
            Parking_Number: 'A003',
            Position: '地下一层',
            Community_Name: '某小区',
            Price: '10元/小时',
            Area: '15平方米',
            Longitude: '120.87123',
            Latitude: '30.81726',
          },
        },
      ],
    };
  },
  methods: {

  },
};
</script>

<style scoped lang="scss">
.image {
  width: 450px; /* 设置图片宽度 */
  height: 250px; /* 设置图片高度 */
  object-fit: cover; /* 控制图片如何适应容器尺寸 */
  display: block;
}
</style>

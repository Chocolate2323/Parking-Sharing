<script setup lang="ts">
import { ref, onMounted, watchEffect  } from 'vue'
import {
  BMap,
  BZoom,
  BScale,
  BCityList,
  BNavigation3d,
  BControl,
  BCircle,
  BMarker,
  useBrowserLocation,
  BInfoWindow,
} from 'vue3-baidu-map-gl'
import LocationIcon from "./LocationIcon.vue";
import { fetchParkingListAPI, getPositionAPI, getCommunityNameAPI, getCommunityLongitudeAPI, getCommunityLatitudeAPI }
  from '../apis/parking.js';


const map = ref()
const { get, location, isLoading } = useBrowserLocation(null, () => {
  map.value.resetCenter()
})
const mapType = ref('BMAP_NORMAL_MAP')
const parkingList = ref([]);
const showDetailsDialog = ref(false);
const selectedParking = ref(null);

const showParkingDetails = (parking) => {
  selectedParking.value = parking;
  showDetailsDialog.value = true;
};


const updateParkingList = async () => {
  try {
    const response = await fetchParkingListAPI();
    const parkingListData = (response);
    const updatedParkingList = [];
    for (const parking of parkingListData) {
      const position = await getPositionAPI({ Community_ID: parking.communityID });
      const communityName = await getCommunityNameAPI({ Community_ID: parking.communityID });
      const longitude = await getCommunityLongitudeAPI({Community_ID: parking.communityID});
      const latitude = await getCommunityLatitudeAPI({Community_ID: parking.communityID});
      const updatedParking = {
        ...parking,
        Position: position,
        position: { lng: longitude, lat: latitude },
        communityName: communityName,
        radius: 100,
      };
      updatedParkingList.push(updatedParking);
    }
    parkingList.value = updatedParkingList;
    // console.log(parkingList.value)
  } catch (error) {
    console.error(error);
  }
}
onMounted(() => {
  get()
  updateParkingList() // 获取车位列表信息
})
watchEffect(() => {
  // 在控制台输出 parkingList 的值
  // const parkingArray = Array.from(parkingList.value);
  // console.log(parkingArray);
});

</script>
<template>
  <div>
    <LayoutFixed/>
    <LayoutNav/>
    <LayoutHeader/>
    <!-- 仿百度地图官网 -->
    <BMap :zoom="16"
        width="100%"
        height="100vh"
        ak="XFgpGWBGm4vGvPIXpmmIPX8qFe3oH35u"
        enableScrollWheelZoom
        ref="map"
        :center="location.point || { lng: 114.305215, lat: 30.592935 }"
        @initd="get"
        :mapType="mapType"
    >
      <BZoom :offset="{ x: 22, y: 40 }" />
      <BCityList :offset="{ x: 20, y: 20 }"/>
      <BScale anchor="BMAP_ANCHOR_BOTTOM_RIGHT" />
      <BNavigation3d anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :offset="{ x: 10, y: 140 }" />
      <template v-if="!isLoading">
<!--        <BMarker :position="location.point"></BMarker>-->
<!--        <BCircle-->
<!--            strokeStyle="solid"-->
<!--            strokeColor="#0099ff"-->
<!--            :strokeOpacity="0.8"-->
<!--            fillColor="#0099ff"-->
<!--            :fillOpacity="0.5"-->
<!--            :center="location.point"-->
<!--            :radius="location.accuracy"-->
<!--        />-->
      </template>
      <BControl
          anchor="BMAP_ANCHOR_TOP_RIGHT"
          :offset="{ x: 20, y: 20 }"
          class="custom-control"
      >
        <select class="select" name="" id="" v-model="mapType">
          <option value="BMAP_NORMAL_MAP">普通地图</option>
          <option value="BMAP_SATELLITE_MAP">卫星地图</option>
          <option value="BMAP_EARTH_MAP">地球模式</option>
        </select>
      </BControl>
      <BControl
          anchor="BMAP_ANCHOR_BOTTOM_RIGHT"
          :offset="{ x: 22, y: 103 }"
          @click="get"
          class="custom-control location-btn"
      >
        <span class="location-text" v-if="isLoading">定位中..</span>
        <LocationIcon v-if="!isLoading" />
      </BControl>
      <template v-if="!isLoading">
        <!-- 显示当前位置的标记点和圆圈 -->
        <BMarker :position="location.point"/>
        <BCircle
            strokeStyle="solid"
            strokeColor="#773ac2"
            :strokeOpacity="0.8"
            fillColor="#773ac2"
            :fillOpacity="0.5"
            :center="location.point"
            :radius="location.accuracy"
        />
      </template>
      <template v-for="parking in parkingList" :key="parking.parkingID">
        <!-- 显示车位标记点 -->
        <BMarker :position="parking.position" @click="showParkingDetails(parking)" />
        <!-- 显示车位圆圈 -->
        <BCircle
            strokeStyle="solid"
            strokeColor="#773ac2"
            :strokeOpacity="0.8"
            fillColor="#773ac2"
            :fillOpacity="0.5"
            :center="parking.position"
            :radius="parking.radius"
        />
        <!-- 信息窗口 -->
        <BInfoWindow v-if="showDetailsDialog && selectedParking === parking" v-model="showDetailsDialog" :position="selectedParking.position" title="车位详细信息">
          <!-- 对话框内容 -->
          <h2>{{ selectedParking.parkingNumber }}</h2>
          <p>位置: {{ selectedParking.Position }}</p>
          <p>小区名: {{ selectedParking.communityName }}</p>
          <p>价格: {{ selectedParking.price }}￥/小时</p>
          <p>面积: {{ selectedParking.area }}平米</p>
          <p>状态: {{ selectedParking.states }}</p>
          <!-- 关闭按钮 -->
<!--          <button @click="hideParkingDetails">关闭</button>-->
        </BInfoWindow>
      </template>
      <!-- 车位详细信息对话框 -->
    </BMap>
    <LayoutFooter/>
  </div>
<!--  <BaiduMap> </BaiduMap>-->

</template>


<script lang="ts">
import LayoutHeader from '../views/Layout/components/LayoutHeader.vue';
import LayoutFixed from '../views/Layout/components/LayoutFixed.vue';
import LayoutNav from '../views/Layout/components/LayoutNav.vue';
import LayoutFooter from '../views/Layout/components/LayoutFooter.vue';

export default {
  components: {
    LayoutFixed,
    LayoutNav,
    LayoutHeader,
    LayoutFooter,
  },
  data() {
    return {
      // mapOptions: {
      //   ak: 'XFgpGWBGm4vGvPIXpmmIPX8qFe3oH35u',
      //   plugins: ['TrackAnimation']
      // }
      // parkingList: [],
    };
  },
  methods: {

  }
}
</script>

<style>
body {
  margin: 0;
}

.location-btn {
  border: none;
  font-size:12px;
}
.custom-control{
  border-radius:4px;
  display: flex;
  color: #666666;
  background-color: #fff;
  padding: 6px;
  cursor: pointer;
  box-shadow: rgb(0 0 0 / 15%) 1px 2px 1px;
}
.select{
  font-size: 12px;
  color: #666666;
  outline: none;
  border-radius: 6px;
  padding: 1px 10px;

  appearance: menulist-button;
}
</style>


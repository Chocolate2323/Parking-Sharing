<template>
  <div>
    <LayoutFixed/>
    <LayoutNav/>
    <LayoutHeader/>
    <div class="container">
      <el-button color="#773ac2" style="width: 100%" @click="fetchParkingList">点击获取车位列表</el-button>
      <el-input style="width: 100%" type="text" v-model="searchNumber" placeholder="搜索编号"  @keyup.enter="searchParkingNumber"/>
      <el-table :data="paginatedParkingList"  stripe style="width: 100%">
        <el-table-column prop="position" label="行政区" sortable />
        <el-table-column prop="communityName" label="小区" sortable />
        <el-table-column prop="parkingNumber" label="编号" sortable />
        <el-table-column prop="price" label="单价（时）" sortable />
        <el-table-column prop="states" label="状态" sortable />
        <el-table-column label="更多">
          <template #default="scope">
            <div class="flex">
              <el-button-group class="ml-4">
                <el-button color="#773ac2" type="primary" v-if="scope.row.states === '空闲' && reservationTime !== ''" @click="changeStates(scope.row)">预定</el-button>
                <el-button color="#773ac2" type="primary" v-else-if="reservationTime === ''" disabled>未选择时间</el-button>
                <el-button color="#773ac2" type="primary" v-else disabled>已预定</el-button>
                <el-button color="#773ac2" type="primary" @click="showParkingDetails(scope.row)">选择</el-button>
              </el-button-group>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          v-model="currentPage"
          :page-size="TableSize"
          :total="ParkingListArray.length"
          layout="prev, pager, next"
          @prev-click="previousPage"
          @current-change="handlePageChange"
          @next-click="nextPage"
      />
    </div>
    <LayoutFooter/>
    <el-dialog v-model="dialogVisible" title="车位详情" width="30%" :draggable="true" class="center-dialog">
      <p class="dialog-text">车位编号: {{ ParkingDetails.Parking_Number }}</p>
      <p class="dialog-text">位置: {{ ParkingDetails.Position }}</p>
      <p class="dialog-text">小区名称: {{ ParkingDetails.Community_Name }}</p>
      <p class="dialog-text">车位ID: {{ ParkingDetails.Parking_ID }}</p>
      <p class="dialog-text">价格: {{ ParkingDetails.Price }}每小时</p>
      <p class="dialog-text">面积: {{ ParkingDetails.Area }}平米</p>
      <p class="dialog-text">经度: {{ ParkingDetails.Longitude }}</p>
      <p class="dialog-text">纬度: {{ ParkingDetails.Latitude }}</p>
      <el-time-picker
          v-model="reservationTime"
          is-range
          range-separator="到"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
      />
    </el-dialog>
  </div>
</template>
<style>
.center-dialog {
  text-align: center;
}
.dialog-text {
  font-size: 18px;
}
</style>
<script>
import LayoutHeader from '@/views/Layout/components/LayoutHeader.vue';
import LayoutFixed from '@/views/Layout/components/LayoutFixed.vue';
import LayoutNav from '@/views/Layout/components/LayoutNav.vue';
import LayoutFooter from '@/views/Layout/components/LayoutFooter.vue';
import {
  addRentalAPI, addReservationAPI, changeStates1API,
  fetchParkingListAPI, getCommunityLatitudeAPI, getCommunityLongitudeAPI,
  getCommunityNameAPI,
  getPositionAPI,
  searchParkingNumberAPI
} from "@/apis/parking";


const storedUserDetails = localStorage.getItem('userDetails');
const userDetails = JSON.parse(storedUserDetails);
const TableSize = 6;

export default {
  created() {
    this.fetchParkingList();
  },
  components: {
    LayoutFixed,
    LayoutNav,
    LayoutHeader,
    LayoutFooter,
  },
  data() {
    return {
      TableSize: TableSize,
      userDetails: userDetails,
      parkingList: [], // 存储车位信息列表
      currentPage: 1, // 当前页码
      totalPages: 1, // 总页数
      searchNumber: '',
      dialogVisible: false, // 对话框可见性
      reservationTime: '', // 用于存储用户选择的预定时间
      ParkingDetails: {
        Parking_Number: '',
        Position: '',
        Community_Name: '',
        Parking_ID: '',
        Price: '',
        States: '',
        Area: '',
        Longitude: '',
        Latitude: '',
      },
      Parking: {
        Parking_ID: '',
        Parking_Number: '',
        Community_ID: '',
        States: '',
        Area: '',
        Price: '',
      },
      Community: {
        Community_ID: '',
        Community_Name: '',
        Position: '',
        Longitude: '',
        Latitude: '',
      },
      Rental: {
        Rental_ID: '',
        Parking_ID: '',
        User_ID: '',
        States: '',
        Start_Time: '',
        End_Time: '',
      },
    };
  },
  computed: {
    ParkingListArray() {
      // 提取数组数据并返回
      return Object.values(this.parkingList);
    },
    paginatedParkingList() {
      const startIndex = (this.currentPage - 1) * TableSize;
      const endIndex = startIndex + TableSize;
      return this.ParkingListArray.slice(startIndex, endIndex);
    }
  },
  methods: {
    async fetchParkingList() {
      try {
        const response = await fetchParkingListAPI();
        const parkingList = Object.values(response);
        const updatedParkingList = [];
        for (const parking of parkingList) {
          const positionResponse = await getPositionAPI({ Community_ID: parking.communityID });
          const communityNameResponse = await getCommunityNameAPI({ Community_ID: parking.communityID });
          const updatedParking = {
            ...parking,
            position: positionResponse,
            communityName: communityNameResponse,
          };
          updatedParkingList.push(updatedParking);
        }
        this.parkingList = updatedParkingList;
        // console.log(this.parkingList)
        // 更新总页数
        this.totalPages = Math.ceil(this.parkingList.length / TableSize);
      } catch (error) {
        console.error(error);
      }
    },
    async searchParkingNumber() {
      try {
        const response = await searchParkingNumberAPI({ Parking_Number: this.searchNumber });
        const parkingList = Object.values(response);
        const updatedParkingList = [];
        for (const parking of parkingList) {
          const positionResponse = await getPositionAPI({ Community_ID: parking.communityID });
          const communityNameResponse = await getCommunityNameAPI({ Community_ID: parking.communityID });
          const updatedParking = {
            ...parking,
            position: positionResponse,
            communityName: communityNameResponse,
          };
          updatedParkingList.push(updatedParking);
        }
        this.parkingList = updatedParkingList;
      } catch (error) {
        console.error(error);
      }
    },
    async showParkingDetails(parking) {
      this.ParkingDetails = {
        Parking_Number: parking.parkingNumber,
        Position: await getPositionAPI({Community_ID: parking.communityID}),
        Community_Name: await getCommunityNameAPI({Community_ID: parking.communityID}),
        Parking_ID: parking.parkingID,
        Price: parking.price,
        Area: parking.area,
        Longitude: await getCommunityLongitudeAPI({Community_ID: parking.communityID}),
        Latitude: await getCommunityLatitudeAPI({Community_ID: parking.communityID}),
      };
      this.dialogVisible = true; // 打开对话框
      // console.log(this.ParkingDetails)
    },
    async changeStates(parking) {
      try {
        // console.log(this.reservationTime)
        //添加对应的租借记录
        await addRentalAPI({
          Parking_ID: parking.parkingID,
          User_ID: userDetails.User_ID,
          Price: parking.price,
          Start_Time: this.reservationTime[0],
          End_Time: this.reservationTime[1]});
        await addReservationAPI({
          Parking_ID: parking.parkingID,
          User_ID: userDetails.User_ID,
          Start_Time: this.reservationTime[0],
          End_Time: this.reservationTime[1]});
        await changeStates1API({ Parking_ID: parking.parkingID });
        // 更新按钮状态为"已预定"且禁用按钮
        parking.States = "已预定";
        parking.disabled = true;
        await this.fetchParkingList();
      } catch (error) {
        console.error(error);
      }
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
  },
};
</script>

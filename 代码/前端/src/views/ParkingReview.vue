<template>
  <div>
    <LayoutFixed/>
    <LayoutNav/>
    <LayoutHeader/>
    <div class="container">
      <el-button color="#773ac2" style="width: 100%" @click="fetchParkingList">点击刷新车位评价</el-button>
      <el-input style="width: 100%" type="text" v-model="searchNumber" placeholder="搜索编号"  @keyup.enter="searchParkingNumber"/>
      <el-table :data="paginatedParkingList"  stripe style="width: 100%">
        <el-table-column prop="Position" label="行政区" sortable />
        <el-table-column prop="Community_Name" label="小区" sortable />
        <el-table-column prop="parkingNumber" label="编号" sortable />
        <el-table-column prop="price" label="单价（时）" sortable />
        <el-table-column prop="startTime" label="开始时间" sortable />
        <el-table-column prop="endTime" label="结束时间" sortable />
        <el-table-column prop="userName" label="用户" sortable />
        <el-table-column prop="score" label="评分" sortable />
        <el-table-column label="更多">
          <template #default="scope">
            <el-button color="#773ac2" type="primary" v-if="scope.row.states_1 === '已评价'" @click="showComment(scope.row)">查看评价</el-button>
            <el-button color="#773ac2" type="primary" v-else disabled>未评价</el-button>
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
    <el-dialog v-model="dialogVisible" title="评价详情" width="30%" :draggable="true" >
      <el-rate v-model="Comment.Score" :max="5" allow-half disabled></el-rate>
      <p class="dialog-text">评价: {{ Comment.Comments }}</p>
    </el-dialog>
  </div>
</template>
<script>
import LayoutHeader from '@/views/Layout/components/LayoutHeader.vue';
import LayoutFixed from '@/views/Layout/components/LayoutFixed.vue';
import LayoutNav from '@/views/Layout/components/LayoutNav.vue';
import LayoutFooter from '@/views/Layout/components/LayoutFooter.vue';
import {
  fetchRentalAPI,
  getCommunityIdAPI,
  getCommunityNameAPI, getParkingNumberAPI,
  getPositionAPI, searchCommentAPI,
  searchParkingNumberAPI
} from "@/apis/parking";
import {getUserNameAPI} from "@/apis/user";


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
        const response = await fetchRentalAPI();
        const parkingList = Object.values(response);
        // console.log(parkingList)
        const updatedParkingList = [];
        for (const parking of parkingList) {
          const communityId = await getCommunityIdAPI({Parking_ID: parking.parkingID});
          const parkingNumber = await getParkingNumberAPI({Parking_ID: parking.parkingID});
          const positionResponse = await getPositionAPI({ Community_ID: communityId });
          const communityNameResponse = await getCommunityNameAPI({ Community_ID: communityId });
          const userName = await getUserNameAPI({User_ID: parking.userID});
          const response_1 = await searchCommentAPI({Rental_ID: parking.rentalID});
          const updatedParking = {
            ...parking,
            Position: positionResponse,
            Community_Name: communityNameResponse,
            states_1: '',
            parkingNumber: parkingNumber,
            userName: userName,
            score: response_1.score,
            comments: response_1.comments,
          };
          if(response_1 !== "") {
            updatedParking.states_1 = "已评价";
          }
          // console.log(updatedParking)
            updatedParkingList.push(updatedParking);
        }
        updatedParkingList.reverse();//逆序输出，显示最新评价
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
        const historyResponse = await fetchRentalAPI();
        const parkingArray = Object.values(response);
        const parkingList = Object.values(historyResponse);
        // console.log(parkingList)
        const updatedParkingList = [];
        for (const parking of parkingList) {
          const communityId = await getCommunityIdAPI({Parking_ID: parking.parkingID});
          const parkingNumber = await getParkingNumberAPI({Parking_ID: parking.parkingID});
          const positionResponse = await getPositionAPI({ Community_ID: communityId });
          const communityNameResponse = await getCommunityNameAPI({ Community_ID: communityId });
          const userName = await getUserNameAPI({User_ID: parking.userID});
          const response_1 = await searchCommentAPI({Rental_ID: parking.rentalID});
          const updatedParking = {
            ...parking,
            Position: positionResponse,
            Community_Name: communityNameResponse,
            states_1: '',
            parkingNumber: parkingNumber,
            userName: userName,
            score: response_1.score,
            comments: response_1.comments,
          };
          if(response_1 !== "") {
            updatedParking.states_1 = "已评价";
          }
          // console.log(updatedParking)
          const foundInResponse = parkingArray.find(item => item.parkingID === updatedParking.parkingID);
          if(foundInResponse)
          updatedParkingList.push(updatedParking);
        }
        this.parkingList = updatedParkingList;
        // 更新总页数
        this.totalPages = Math.ceil(this.parkingList.length / TableSize);
      } catch (error) {
        console.error(error);
      }
    },
    async showComment(parking) {
      console.log(parking)
      const response = await searchCommentAPI({Rental_ID: parking.rentalID});
      this.Comment = {
        Score: response.score,
        Comments: response.comments,
      }
      this.dialogVisible = true;
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

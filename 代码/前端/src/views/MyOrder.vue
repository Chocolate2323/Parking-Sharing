<template>
  <div>
    <LayoutFixed/>
    <LayoutNav/>
    <LayoutHeader/>
    <div class="container">
      <el-button color="#773ac2" style="width: 100%" @click="fetchParkingList">点击刷新订单</el-button>
      <el-input style="width: 100%" type="text" v-model="searchNumber" placeholder="搜索编号"  @keyup.enter="searchParkingNumber"/>
      <el-table :data="paginatedParkingList"  stripe style="width: 100%">
        <el-table-column prop="position" label="行政区" sortable />
        <el-table-column prop="communityName" label="小区" sortable />
        <el-table-column prop="parkingNumber" label="编号" sortable />
        <el-table-column prop="price" label="单价（时）" sortable />
        <el-table-column prop="startTime" label="开始时间" sortable />
        <el-table-column prop="endTime" label="结束时间" sortable />
        <el-table-column prop="states" label="状态" sortable />
        <el-table-column label="更多">
          <template #default="scope">
            <div class="flex">
              <el-button-group class="ml-4">
                <el-button color="#773ac2" type="primary"  v-if="scope.row.states === '已预定'" @click="changeStates(scope.row)">结束</el-button>
                <el-button color="#773ac2" type="primary" v-else disabled>空闲</el-button>
                <el-button color="#773ac2" type="primary"  @click="showParkingDetails(scope.row)">详情</el-button>
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
    <div class="container">
      <el-button color="#773ac2" style="width: 100%" @click="fetchParkingList_1">点击刷新历史订单</el-button>
      <el-input style="width: 100%" type="text" v-model="searchNumber_1" placeholder="搜索编号"  @keyup.enter="searchParkingNumber_1"/>
      <el-table :data="paginatedParkingList_1"  stripe style="width: 100%">
        <el-table-column prop="position" label="行政区" sortable />
        <el-table-column prop="communityName" label="小区" sortable />
        <el-table-column prop="parkingNumber" label="编号" sortable />
        <el-table-column prop="startTime" label="开始时间" sortable />
        <el-table-column prop="endTime" label="结束时间" sortable />
        <el-table-column prop="price" label="总价" sortable />
        <el-table-column prop="states_1" label="状态" sortable />
        <el-table-column label="更多">
          <template #default="scope">
            <el-button color="#773ac2" type="primary" v-if="scope.row.states_1 === '已评价'" @click="showComment(scope.row)">查看评价</el-button>
            <el-button color="#773ac2" type="primary" v-else @click="addComment(scope.row)">评价</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          v-model="currentPage_1"
          :page-size="TableSize"
          :total="ParkingListArray_1.length"
          layout="prev, pager, next"
          @prev-click="previousPage_1"
          @current-change="handlePageChange_1"
          @next-click="nextPage_1"
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
    </el-dialog>
    <el-dialog v-model="dialogVisible_1" title="用户评价" width="30%" :draggable="true">
      <el-rate v-model="score" :max="5" allow-half></el-rate>
      <el-input
          v-model="comment" placeholder="请输入评价" type="textarea" :rows="5"
      ></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog">取消</el-button>
        <el-button type="primary" @click="submitComment">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog v-model="dialogVisible_2" title="评价详情" width="30%" :draggable="true" >
      <el-rate v-model="Comment.Score" :max="5" allow-half disabled></el-rate>
      <p class="dialog-text">评价: {{ Comment.Comments }}</p>
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
  addCommentAPI,
  changeStates2API, deleteReservationAPI, fetchHistoryAPI,
  fetchParkingListAPI, getCommunityIdAPI, getCommunityLatitudeAPI, getCommunityLongitudeAPI,
  getCommunityNameAPI, getEndTimeAPI, getParkingNumberAPI,
  getPositionAPI, getStartTimeAPI, getUserIdAPI, searchCommentAPI,   searchParkingNumberAPI
} from "@/apis/parking";


const storedUserDetails = localStorage.getItem('userDetails');
const userDetails = JSON.parse(storedUserDetails);
const TableSize = 6;

export default {
  created() {
    this.fetchParkingList();
    this.fetchParkingList_1();
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
      parkingList_1: [],
      currentPage: 1, // 当前页码
      currentPage_1: 1,
      totalPages: 1, // 总页数
      totalPages_1: 1,
      searchNumber: '',
      searchNumber_1: '',
      dialogVisible: false, // 对话框可见性
      dialogVisible_1: false,
      dialogVisible_2: false,
      score: '',
      comment: '',
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
        States_1: '',
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
      Comment: {
        Comment_ID: '',
        Rental_ID: '',
        Parking_ID: '',
        User_ID: '',
        Comments: '',
        Score: '',
      },
    };
  },
  computed: {
    ParkingListArray() {
      // 提取数组数据并返回
      return Object.values(this.parkingList);
    },
    ParkingListArray_1() {
      // 提取数组数据并返回
      return Object.values(this.parkingList_1);
    },
    paginatedParkingList() {
      const startIndex = (this.currentPage - 1) * TableSize;
      const endIndex = startIndex + TableSize;
      return this.ParkingListArray.slice(startIndex, endIndex);
    },
    paginatedParkingList_1() {
      const startIndex = (this.currentPage_1 - 1) * TableSize;
      const endIndex = startIndex + TableSize;
      return this.ParkingListArray_1.slice(startIndex, endIndex);
    },
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
          const startTime = await getStartTimeAPI({Parking_ID: parking.parkingID});
          const endTime = await getEndTimeAPI({Parking_ID: parking.parkingID});
          const userId = await getUserIdAPI({Parking_ID: parking.parkingID});
          const updatedParking = {
            ...parking,
            position: positionResponse,
            communityName: communityNameResponse,
            startTime: startTime,
            endTime: endTime,
          };
          if (updatedParking.states === "已预定" && userDetails.User_ID === userId) {
            updatedParkingList.push(updatedParking);
          }
        }
        this.parkingList = updatedParkingList;
        // console.log(this.parkingList)
        // 更新总页数
        this.totalPages = Math.ceil(this.parkingList.length / TableSize);
      } catch (error) {
        console.error(error);
      }
    },
    async fetchParkingList_1() {
      try {
        const response = await fetchHistoryAPI({User_ID: userDetails.User_ID});
        const parkingList = Object.values(response);
        // console.log(parkingList)
        const updatedParkingList = [];
        for (const parking of parkingList) {
          const communityId = await getCommunityIdAPI({Parking_ID: parking.parkingID});
          const parkingNumber = await getParkingNumberAPI({Parking_ID: parking.parkingID});
          const positionResponse = await getPositionAPI({ Community_ID: communityId });
          const communityNameResponse = await getCommunityNameAPI({ Community_ID: communityId });
          const userId = parking.userID;
          const updatedParking = {
            ...parking,
            position: positionResponse,
            communityName: communityNameResponse,
            states_1: '未评价',
            parkingNumber: parkingNumber,
          };
          if (userDetails.User_ID === userId) {
            const response = await searchCommentAPI({Rental_ID: updatedParking.rentalID});
             // console.log(updatedParking)
            if(response !== "") {
              updatedParking.states_1 = "已评价";
            }
            updatedParkingList.push(updatedParking);
          }
        }
        updatedParkingList.reverse();//逆序输出，显示最新评价
        this.parkingList_1 = updatedParkingList;
        // console.log(this.parkingList)
        // 更新总页数
        this.totalPages_1 = Math.ceil(this.parkingList_1.length / TableSize);
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
          const startTime = await getStartTimeAPI({Parking_ID: parking.parkingID});
          const endTime = await getEndTimeAPI({Parking_ID: parking.parkingID});
          const userId = await getUserIdAPI({Parking_ID: parking.parkingID});
          const updatedParking = {
            ...parking,
            position: positionResponse,
            communityName: communityNameResponse,
            startTime: startTime,
            endTime: endTime,
          };
          if (updatedParking.states === "已预定" && userDetails.User_ID === userId) {
            updatedParkingList.push(updatedParking);
          }
        }
        this.parkingList = updatedParkingList;
        // 更新总页数
        this.totalPages = Math.ceil(this.parkingList.length / TableSize);
      } catch (error) {
        console.error(error);
      }
    },
    async searchParkingNumber_1() {
      try {
        const response = await searchParkingNumberAPI({ Parking_Number: this.searchNumber_1 });
        const historyResponse = await fetchHistoryAPI({ User_ID: userDetails.User_ID });
        const parkingArray = Object.values(response);
        const parkingList = Object.values(historyResponse);
        const updatedParkingList = [];
        for (const parking of parkingList) {
          const communityId = await getCommunityIdAPI({Parking_ID: parking.parkingID});
          const parkingNumber = await getParkingNumberAPI({Parking_ID: parking.parkingID});
          const positionResponse = await getPositionAPI({ Community_ID: communityId });
          const communityNameResponse = await getCommunityNameAPI({ Community_ID: communityId });
          const userId = parking.userID;
          // 筛选满足条件的数据
          const updatedParking = {
            ...parking,
            position: positionResponse,
            communityName: communityNameResponse,
            states_1: '未评价',
            parkingNumber: parkingNumber,
          };
          const foundInResponse = parkingArray.find(item => item.parkingID === updatedParking.parkingID);
          if (userDetails.User_ID === userId && foundInResponse) {
            const response = await searchCommentAPI({Rental_ID: updatedParking.rentalID});
            // console.log(updatedParking)
            if(response !== "") {
              updatedParking.states_1 = "已评价";
            }
            updatedParkingList.push(updatedParking);
          }
        }
        this.parkingList_1 = updatedParkingList;
        // 更新总页数
        this.totalPages_1 = Math.ceil(this.parkingList_1.length / TableSize);
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
    async addComment(parking) {
      this.dialogVisible_1 = true;
      this.Comment = {
        Comment_ID: '',
        Rental_ID: parking.rentalID,
        Parking_ID: parking.parkingID,
        User_ID: parking.userID,
        Comments: '',
        Score: '',
      };
    },
    async showComment(parking) {
       console.log(parking)
      const response = await searchCommentAPI({Rental_ID: parking.rentalID});
      this.Comment = {
        Score: response.score,
        Comments: response.comments,
      }
      this.dialogVisible_2 = true;
    },
    async changeStates(parking) {
      try {
        await deleteReservationAPI({ Parking_ID: parking.parkingID, User_ID: userDetails.User_ID})
        await changeStates2API({ Parking_ID: parking.parkingID });
        parking.States = "空闲";

        await this.fetchParkingList();
      } catch (error) {
        console.error(error);
      }
    },
    cancelDialog() {
      this.dialogVisible_1 = false;
      this.score = '';
      this.comment = '';
    },
    submitComment() {
      if (this.isValidScore() && this.comment) {
        this.dialogVisible_1 = false;
        this.Comment.Comments = this.comment;
        this.Comment.Score = this.score;
        // console.log(this.Comment)
        addCommentAPI({Rental_ID: this.Comment.Rental_ID,
                                                            Parking_ID: this.Comment.Parking_ID,
                                                            User_ID: this.Comment.User_ID,
                                                            Comments: this.Comment.Comments,
                                                            Score: this.Comment.Score});
        this.fetchParkingList_1();
        // console.log(this.Comment)
      } else {
        console.log('评分无效或评价为空');
      }
    },
    isValidScore() {
      const score = parseFloat(this.score);
      return !isNaN(score) && score >= 0 && score <= 5;
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
    previousPage_1() {
      if (this.currentPage_1 > 1) {
        this.currentPage_1--;
      }
    },
    nextPage_1() {
      if (this.currentPage_1 < this.totalPages_1) {
        this.currentPage_1++;
      }
    },
    handlePageChange_1(page) {
      this.currentPage_1 = page;
    },
  },
};
</script>

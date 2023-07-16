<script setup>
import { ElButton, } from "element-plus";
import { Delete, Edit} from '@element-plus/icons-vue';
</script>

<template>
  <div>
    <LayoutFixed/>
    <LayoutNav/>
    <LayoutHeader/>
    <div class="container" v-if="userDetails.Admin">
      <el-button color="#773ac2" style="width: 100%" @click="fetchParkingList">点击刷新车位信息</el-button>
      <el-input color="#773ac2" style="width: 50%" type="text" v-model="searchID" placeholder="搜索ID"  @keyup.enter="searchParkingID"/>
      <el-input style="width: 50%" type="text" v-model="searchNumber" placeholder="搜索编号"  @keyup.enter="searchParkingNumber"/>
      <el-table :data="paginatedParkingList"  stripe style="width: 100%">
        <el-table-column prop="parkingID" label="ID" sortable />
        <el-table-column prop="position" label="行政区" sortable />
        <el-table-column prop="communityName" label="小区" sortable />
        <el-table-column prop="parkingNumber" label="编号" sortable />
        <el-table-column prop="price" label="单价（时）" sortable />
        <el-table-column prop="area" label="面积" sortable />
        <el-table-column prop="longitude" label="经度" sortable />
        <el-table-column prop="latitude" label="纬度" sortable />
        <el-table-column prop="states" label="状态" sortable>
          <template #default="scope">
            <span>{{ scope.row.states }}</span>
          </template>
        </el-table-column>
        <el-table-column label="编辑">
          <template #default="scope">
            <div class="flex">
              <el-button-group class="ml-4">
                <el-button color="#773ac2" type="primary" :icon="Edit" @click="editParking(scope.row)" :disabled="scope.row.states === '已预定'"></el-button>
                <el-button color="#773ac2" type="primary" :icon="Delete" @click="deleteParking(scope.row)"></el-button>
              </el-button-group>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-button color="#773ac2" style="width: 100%" @click="showAddDialog">添加车位</el-button>
      <el-pagination
          v-model="currentPage"
          :page-size="TableSize"
          :total="parkingListArray.length"
          layout="prev, pager, next"
          @prev-click="previousPage"
          @current-change="handlePageChange"
          @next-click="nextPage"
      />
    </div>
    <div class="container" v-else>
      <!-- 权限不够的提醒 -->
      <el-button color="#773ac2" style="width: 100%" @click="showAddDialog">添加车位</el-button>
    </div>
    <LayoutFooter/>
    <el-dialog
        v-model="editDialogVisible"
        title="编辑车位"
        width="30%"
        :draggable="true"
    >
      <el-form ref="editForm" :model="editedParking" label-width="100px">
        <el-form-item label="ID" prop="parkingId">
          <el-input v-model="editedParking.Parking_ID" readonly ></el-input>
        </el-form-item>
        <el-form-item label="小区名" prop="communityName">
          <el-input v-model="editedParking.CommunityName" :maxlength="20" placeholder="请输入小区名" required></el-input>
        </el-form-item>
        <el-form-item label="车位编号" prop="parkingNumber">
          <el-input v-model="editedParking.ParkingNumber" placeholder="请输入编号" required></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input v-model="editedParking.Price" placeholder="请输入单价" required></el-input>
        </el-form-item>
        <el-form-item label="面积" prop="area">
          <el-input v-model="editedParking.Area" placeholder="请输入面积" required></el-input>
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="editedParking.Longitude" placeholder="请输入经度" required></el-input>
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="editedParking.Latitude" placeholder="请输入纬度" required></el-input>
        </el-form-item>
      </el-form>
      <div v-if="editDialogVisible" class="dialog-footer" slot="footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveParking">保存</el-button>
      </div>
    </el-dialog>
    <el-dialog
        v-model="addDialogVisible"
        title="添加车位"
        width="30%"
        :draggable="true"
    >
      <el-form ref="addForm" :model="newParking" label-width="100px">
        <el-form-item label="行政区" prop="position">
          <el-input v-model="newParking.Position" :maxlength="20" placeholder="请输入行政区" required></el-input>
        </el-form-item>
        <el-form-item label="小区名" prop="communityName">
          <el-input v-model="newParking.CommunityName" :maxlength="20" placeholder="请输入小区名" required></el-input>
        </el-form-item>
        <el-form-item label="车位编号" prop="parkingNumber">
          <el-input v-model="newParking.ParkingNumber" placeholder="请输入编号" required></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input v-model="newParking.Price" placeholder="请输入单价" required></el-input>
        </el-form-item>
        <el-form-item label="面积" prop="area">
          <el-input v-model="newParking.Area" placeholder="请输入面积" required></el-input>
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="newParking.Longitude" placeholder="请输入经度" required></el-input>
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="newParking.Latitude" placeholder="请输入纬度" required></el-input>
        </el-form-item>
      </el-form>
      <div v-if="addDialogVisible" class="dialog-footer" slot="footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addParking">添加</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import LayoutHeader from '@/views/Layout/components/LayoutHeader.vue';
import LayoutFixed from '@/views/Layout/components/LayoutFixed.vue';
import LayoutNav from '@/views/Layout/components/LayoutNav.vue';
import LayoutFooter from '@/views/Layout/components/LayoutFooter.vue';
import {
  addParkingAPI1, addParkingAPI2, deleteCommunityAPI,
  deleteParkingAPI,
  fetchParkingListAPI, getCommunityLatitudeAPI, getCommunityLongitudeAPI, getCommunityNameAPI, getPositionAPI,
  searchParkingIDAPI,
  searchParkingNumberAPI,
  updateParkingAPI1, updateParkingAPI2
} from "@/apis/parking";

const storedUserDetails = localStorage.getItem('userDetails');
const userDetails = JSON.parse(storedUserDetails);
const TableSize=6;
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
      TableSize:TableSize,
      userDetails: userDetails,
      parkingList: [], // 存储用户信息列表
      currentPage: 1, // 当前页码
      totalPages: 1, // 总页数
      searchNumber: '',
      searchID: '',
      editDialogVisible: false,
      editedParking: {
        Position: '',
        CommunityName: '',
        ParkingNumber: '',
        Price: '',
        Area: '',
        Longitude: '',
        Latitude: '',
        States: '',
        Parking_ID: '',
        Community_ID: '',
      },
      addDialogVisible: false,
      newParking: {
        Position: '',
        CommunityName: '',
        ParkingNumber: '',
        Price: '',
        Area: '',
        Longitude: '',
        Latitude: '',
        States: '',
        Parking_ID: '',
        Community_ID: '',
      },
      editRules: {
        // userId: [
        //   { required: true, message: '请输入用户名', trigger: 'blur' },
        //   { min: 3, max: 20, message: '用户名长度在 2 到 16 个字符之间', trigger: 'blur' },
        // ],
        // email: [
        //   { required: true, message: '请输入邮箱', trigger: 'blur' },
        //   { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
        // ],
        // phone: [
        //   { required: true, message: '请输入电话', trigger: 'blur' },
        //   { pattern: /^\d{11}$/, message: '电话号码必须是 11 位数字', trigger: 'blur' },
        // ],
        // score: [
        //   { required: true, message: '请输入信用分', trigger: 'blur' },
        //   { type: 'number', message: '信用分必须是数字', trigger: 'blur' },
        //   { min: 0, max: 100, message: '信用分必须在 0 到 100 之间', trigger: 'blur' },
        // ],
      },
    };
  },
  computed: {
    parkingListArray() {
      // 提取数组数据并返回
      return Object.values(this.parkingList);
    },
    paginatedParkingList() {
      const startIndex = (this.currentPage - 1) * TableSize;
      const endIndex = startIndex + TableSize;
      return this.parkingListArray.slice(startIndex, endIndex);
    }
  },
  methods: {
    async fetchParkingList() {
      try {
        const response = await fetchParkingListAPI();
        const parkingList = Object.values(response);
        const updatedParkingList = [];
        for (const parking of parkingList) {
          const position = await getPositionAPI({ Community_ID: parking.communityID });
          const communityName = await getCommunityNameAPI({ Community_ID: parking.communityID });
          const longitude = await getCommunityLongitudeAPI({Community_ID: parking.communityID});
          const latitude = await getCommunityLatitudeAPI({Community_ID: parking.communityID});
          const updatedParking = {
            ...parking,
            position: position,
            communityName: communityName,
            longitude: longitude,
            latitude: latitude,
          };
          updatedParkingList.push(updatedParking);
        }
        this.parkingList = updatedParkingList;
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
          const position = await getPositionAPI({ Community_ID: parking.communityID });
          const communityName = await getCommunityNameAPI({ Community_ID: parking.communityID });
          const longitude = await getCommunityLongitudeAPI({Community_ID: parking.communityID});
          const latitude = await getCommunityLatitudeAPI({Community_ID: parking.communityID});
          const updatedParking = {
            ...parking,
            position: position,
            communityName: communityName,
            longitude: longitude,
            latitude: latitude,
          };
          updatedParkingList.push(updatedParking);
        }
        this.parkingList = updatedParkingList;
        // 更新总页数
        this.totalPages = Math.ceil(this.parkingList.length / TableSize);
      } catch (error) {
        console.error(error);
      }
    },
    async searchParkingID() {
      try {
        const response = await searchParkingIDAPI({ Parking_ID: this.searchID });
        if (response) {
          const parking = response;
          const position = await getPositionAPI({ Community_ID: parking.communityID });
          const communityName = await getCommunityNameAPI({ Community_ID: parking.communityID });
          const longitude = await getCommunityLongitudeAPI({ Community_ID: parking.communityID });
          const latitude = await getCommunityLatitudeAPI({ Community_ID: parking.communityID });
          const updatedParking = {
            ...parking,
            position: position,
            communityName: communityName,
            longitude: longitude,
            latitude: latitude,
          };
          this.parkingList = [updatedParking];
          this.totalPages = 1; // 只有一个结果，总页数设置为1
        } else {
          this.parkingList = [];
          this.totalPages = 0; // 总页数设置为0
        }
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
    async editParking(parking) {
      this.editedParking = {
        Position: parking.position,
        CommunityName: parking.communityName,
        ParkingNumber: parking.parkingNumber,
        Price: parking.price,
        Area: parking.area,
        Longitude: parking.longitude,
        Latitude: parking.latitude,
        States: parking.states,
        Parking_ID: parking.parkingID,
        Community_ID: parking.communityID,
      };
      this.editDialogVisible = true;
    },
    saveParking() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          // 发送请求将修改后的数据传递给后端
          try {
            await updateParkingAPI1({
              ParkingNumber: this.editedParking.ParkingNumber,
              Price: this.editedParking.Price,
              Area: this.editedParking.Area,
              States: this.editedParking.States,
              Parking_ID: this.editedParking.Parking_ID,
              Community_ID: this.editedParking.Community_ID,
            })
            await updateParkingAPI2({
              Position: this.editedParking.Position,
              CommunityName: this.editedParking.CommunityName,
              Longitude: this.editedParking.Longitude,
              Latitude: this.editedParking.Latitude,
              Community_ID: this.editedParking.Community_ID,
            })
            await this.fetchParkingList();
            this.editDialogVisible = false;
          } catch (error) {
            console.error(error);
          }
        } else {
          return false;
        }
      });
    },
    showAddDialog() {
      this.addDialogVisible = true;
    },
    addParking() {
      this.$refs.addForm.validate(async valid => {
        if (valid) {
          // 发送请求将修改后的数据传递给后端
          try {
            console.log(this.newParking)
            const newCommunity = await addParkingAPI2({
              Position: this.newParking.Position,
              CommunityName: this.newParking.CommunityName,
              Longitude: this.newParking.Longitude,
              Latitude: this.newParking.Latitude,
              Community_ID: this.newParking.Parking_ID,
            })
            await addParkingAPI1({
              ParkingNumber: this.newParking.ParkingNumber,
              Price: this.newParking.Price,
              Area: this.newParking.Area,
              States: '空闲',
              Parking_ID: this.newParking.Parking_ID,
              Community_ID: newCommunity.communityID,
            })

            await this.fetchParkingList();
            this.addDialogVisible = false;
          } catch (error) {
            console.error(error);
          }
        } else {
          return false;
        }
      });
    },
    deleteParking(parking){
      console.log(parking)
      if (confirm(`确定要删除车位 ${parking.parkingNumber} 吗？`)) {
        deleteParkingAPI({Parking_ID: parking.parkingID})
        deleteCommunityAPI({Community_ID: parking.communityID})
            .then(() => {
              // 删除成功后重新获取用户列表
              this.fetchParkingList();
            })
            .catch(error => {
              console.error(error);
            });
      }
    }
  },
};
</script>

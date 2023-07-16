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
      <el-button color="#773ac2" style="width: 100%" @click="fetchUserList">点击刷新用户信息</el-button>
      <el-input style="width: 50%" type="text" v-model="searchID" placeholder="搜索ID"  @keyup.enter="searchUserID"/>
      <el-input style="width: 50%" type="text" v-model="searchName" placeholder="搜索用户名"  @keyup.enter="searchUserName"/>
      <el-table :data="paginatedUserList"  stripe style="width: 100%">
        <el-table-column prop="userId" label="ID" sortable />
        <el-table-column prop="userName" label="用户名" sortable />
        <el-table-column prop="email" label="邮箱" sortable />
        <el-table-column prop="phone" label="电话" sortable />
        <el-table-column prop="score" label="信用分" sortable />
        <el-table-column prop="admin" label="管理员" sortable >
          <template #default="{ row }">
            {{ row.admin === 1 ? "是" : "否" }}
          </template>
        </el-table-column>
        <el-table-column label="编辑">
          <template #default="scope">
            <div class="flex">
              <el-button-group class="ml-4">
                <el-button color="#773ac2" type="primary" :icon="Edit" @click="editUser(scope.row)"></el-button>
                <el-button color="#773ac2" type="primary" :icon="Delete" @click="deleteUser(scope.row)"></el-button>
              </el-button-group>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-button color="#773ac2" style="width: 100%" @click="showAddDialog">添加用户</el-button>
      <el-pagination
          v-model="currentPage"
          :page-size="TableSize"
          :total="userListArray.length"
          layout="prev, pager, next"
          @prev-click="previousPage"
          @current-change="handlePageChange"
          @next-click="nextPage"
      />
    </div>
    <div class="container" v-else>
      <!-- 权限不够的提醒 -->
      <el-button color="#773ac2" style="width: 100%" @click="showAddDialog">注册</el-button>
    </div>
    <LayoutFooter/>
    <el-dialog
        v-model="editDialogVisible"
        title="编辑用户"
        width="30%"
        :draggable="true"
    >
      <el-form ref="editForm" :model="editedUser" label-width="100px">
        <el-form-item label="ID" prop="userId">
          <el-input v-model="editedUser.User_ID" readonly ></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="editedUser.User_Name" :maxlength="20" placeholder="请输入用户名" required></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editedUser.Email" placeholder="请输入邮箱" required></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="editedUser.Phone" placeholder="请输入电话" required></el-input>
        </el-form-item>
        <el-form-item label="信用分" prop="score">
          <el-input-number v-model="editedUser.Score" :min="0" :max="100" placeholder="请输入信用分" required></el-input-number>
        </el-form-item>
        <el-form-item label="管理员" prop="admin">
          <el-radio-group v-model="editedUser.Admin">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div v-if="editDialogVisible" class="dialog-footer" slot="footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
      </div>
    </el-dialog>
    <el-dialog
        v-model="addDialogVisible"
        title="添加用户"
        width="30%"
        :draggable="true"
    >
      <el-form ref="addForm" :model="newUser" label-width="100px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="newUser.User_Name" :maxlength="20" placeholder="请输入用户名" required></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="newUser.Password" placeholder="请输入密码" required></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="newUser.Email" placeholder="请输入邮箱" required></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="newUser.Phone" placeholder="请输入电话" required></el-input>
        </el-form-item>
        <el-form-item v-if="userDetails.Admin" label="信用分" prop="score">
          <el-input-number v-model="newUser.Score" :min="0" :max="100" placeholder="请输入信用分" required></el-input-number>
        </el-form-item>
        <el-form-item v-if="userDetails.Admin" label="管理员" prop="admin">
          <el-radio-group v-model="newUser.Admin">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div v-if="addDialogVisible" class="dialog-footer" slot="footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addUser">添加</el-button>
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
  addUserAPI,
  deleteUserAPI,
  fetchUserListAPI,
  findMaxIdAPI, searchUserIDAPI,
  searchUserNameAPI,
  updateUserAPI
} from "@/apis/user";


const storedUserDetails = localStorage.getItem('userDetails');
const userDetails = JSON.parse(storedUserDetails);
const TableSize=6;
export default {
  created() {
    this.fetchUserList();
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
      userList: [], // 存储用户信息列表
      currentPage: 1, // 当前页码
      totalPages: 1, // 总页数
      searchName: '', // 搜索的用户名
      searchID: '',
      editDialogVisible: false,
      editedUser: {
        User_ID: 0,
        User_Name: "",
        Password: "",
        Email: "",
        Phone: "",
        Score: 0,
        Admin: 0,
      },
      addDialogVisible: false,
      newUser: {
        User_ID: 0,
        User_Name: "",
        Password: "",
        Email: "",
        Phone: "",
        Score: 100,
        Admin: 0,
      },
      editRules: {
        userId: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '用户名长度在 2 到 16 个字符之间', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^\d{11}$/, message: '电话号码必须是 11 位数字', trigger: 'blur' },
        ],
        score: [
          { required: true, message: '请输入信用分', trigger: 'blur' },
          { type: 'number', message: '信用分必须是数字', trigger: 'blur' },
          { min: 0, max: 100, message: '信用分必须在 0 到 100 之间', trigger: 'blur' },
        ],
      },
    };
  },
  computed: {
    userListArray() {
      // 提取数组数据并返回
      return Object.values(this.userList);
    },
    paginatedUserList() {
      const startIndex = (this.currentPage - 1) * TableSize;
      const endIndex = startIndex + TableSize;
      return this.userListArray.slice(startIndex, endIndex);
    }
  },
  methods: {
    async fetchUserList() {
      try {
        const response = await fetchUserListAPI();
        this.userList = Object.values(response);
        // 更新总页数
        this.totalPages = Math.ceil(this.userList.length / TableSize);
      } catch (error) {
        console.error(error);
      }
    },
    async searchUserName() {
      try {
        const response = await searchUserNameAPI({ User_Name: this.searchName });
        this.userList = Object.values(response);
        // 更新总页数
        this.totalPages = Math.ceil(this.userList.length / TableSize);
      } catch (error) {
        console.error(error);
      }
    },
    async searchUserID() {
      try {
        const response = await searchUserIDAPI({ User_ID: this.searchID });
        if (response) {
          this.userList = [response]; // 将用户对象直接赋值给userList
          this.totalPages = 1; // 只有一个结果，总页数设置为1
        } else {
          this.userList = []; // 没有找到匹配的用户，清空userList
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
    async editUser(user) {
      this.editedUser = {
        User_ID: user.userId,
        User_Name: user.userName,
        Password: user.password,
        Email: user.email,
        Phone: user.phone,
        Score: user.score,
        Admin: user.admin,
      };
      this.editDialogVisible = true;
    },
    saveUser() {
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          // 发送请求将修改后的数据传递给后端
          try {
            await updateUserAPI({
              User_ID: this.editedUser.User_ID,
              User_Name: this.editedUser.User_Name,
              Password: this.editedUser.Password,
              Email: this.editedUser.Email,
              Phone: this.editedUser.Phone,
              Score: this.editedUser.Score,
              Admin: this.editedUser.Admin,})
            await this.fetchUserList();
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
    addUser() {
      this.$refs.addForm.validate(async valid => {
        if (valid) {
          // 发送请求将修改后的数据传递给后端
          try {
            await addUserAPI({
              User_ID: await this.findMaxId() + 1,
              User_Name: this.newUser.User_Name,
              Password: this.newUser.Password,
              Email: this.newUser.Email,
              Phone: this.newUser.Phone,
              Score: this.newUser.Score,
              Admin: this.newUser.Admin,})
            await this.fetchUserList();
            this.addDialogVisible = false;
          } catch (error) {
            console.error(error);
          }
        } else {
          return false;
        }
      });
    },
    async findMaxId() {
      try {
        return await findMaxIdAPI();
      } catch (error) {
        console.error(error);
      }
    },
    deleteUser(user){
      if (confirm(`确定要删除用户 ${user.userName} 吗？`)) {
        deleteUserAPI({User_ID: user.userId})
            .then(() => {
              // 删除成功后重新获取用户列表
              this.fetchUserList();
            })
            .catch(error => {
              console.error(error);
            });
      }
    }
  },
};
</script>

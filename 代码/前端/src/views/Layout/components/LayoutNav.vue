<script setup>
</script>
<template>
  <nav class="app-topnav">
    <div class="container">
      <ul>
        <template v-if="isUserLoggedIn">
          <li><a href="javascript:"><i class="iconfont icon-user"></i>{{ userDetails.User_Name }}</a></li>
          <li>
            <el-popconfirm title="确认退出吗?"  confirm-button-text="确认" cancel-button-text="取消" @confirm="handleLogout">
              <template #reference>
                <a href="javascript:">退出登录</a>
              </template>
            </el-popconfirm>
          </li>
        </template>
        <template v-else>
          <li><a href="javascript:" @click="$router.push('/login')">请先登录</a></li>
          <li><a href="javascript:" onclick="window.location.href='https://github.com/Chocolate2323';">关于我们</a></li>
        </template>
      </ul>

    </div>
  </nav>
</template>
<script>
const storedUserDetails = localStorage.getItem('userDetails');
const userDetails = JSON.parse(storedUserDetails);

export default {
  data() {
    return {
      userDetails: userDetails,
      isUserLoggedIn: true, // 初始值为true，表示已登录
    };
  },
  methods: {
    handleLogout() {
      // 在退出登录的处理函数中将isUserLoggedIn设置为false
      this.isUserLoggedIn = false;
      // 其他退出登录的逻辑
      const userDetails = {
        User_ID: 0,
        User_Name: "",
        Password: "null",
        Email: "null",
        Phone: "null",
        Score: "null",
        Admin: 0,
      };
      localStorage.setItem('userDetails', JSON.stringify(userDetails));
    },
  },
  refreshPage() {
    // 在这里执行刷新页面的操作，比如重新调用后端API获取最新的用户信息
    // 更新this.userDetails的值
    // 例如：
    // this.userDetails = await getUserDetailsAPI();
    location.reload();
  },
};

</script>
<style scoped lang="scss">
.app-topnav {
  background: #333;
  ul {
    display: flex;
    height: 53px;
    justify-content: flex-end;
    align-items: center;
    li {
      a {
        padding: 0 15px;
        color: #cdcdcd;
        line-height: 1;
        display: inline-block;
        i {
          font-size: 14px;
          margin-right: 2px;
        }
        &:hover {
          color: $xtxColor;
        }
      }
      ~li {
        a {
          border-left: 2px solid #666;
        }
      }
    }
  }
}
</style>

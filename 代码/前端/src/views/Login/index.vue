<script setup>
import {ref} from 'vue'
import {ElMessage} from 'element-plus'
// import { useUserStore } from '@/stores/userStore'
import 'element-plus/theme-chalk/el-message.css'
import {useRouter} from 'vue-router'
import {getUserDetailsAPI, loginAPI} from "@/apis/user";

// import { useStore } from 'pinia';
// const userStore = useUserStore()

// 表单数据对象
const userInfo = ref({
  User_Name: 'Chocolate',
  Password: '20031015',
  agree: true
})
// 规则数据对象
const rules = {
  User_Name: [
    { required: true, message: '用户名不能为空' }
  ],
  Password: [
    { required: true, message: '密码不能为空' },
    { min: 6, max: 14, message: '密码长度要求6-14个字符' }
  ],
  agree: [
    {
      validator: (rule, value, callback) => {
        console.log(value)
        // 自定义校验逻辑
        // 勾选就通过 不勾选就不通过
        if (value) {
          callback()
        } else {
          callback(new Error('请勾选协议'))
        }
      }
    }
  ]
}
//统一校验
const formRef = ref(null)
const router = useRouter()
// const doLogin = () => {
//   const { account, password } = userInfo.value
//   // 调用实例方法
//   formRef.value.validate(async (valid) => {
// // valid: 所有表单都通过校验 才为true
//     console.log(valid)
//     if (valid) {
//       try {
//         await userStore.getUserInfo({ account, password })
//         ElMessage({ type: 'success', message: '登录成功' });
//         router.replace({ path: '/' });
//       } catch (error) {
//         console.error(error);
//         ElMessage({ type: 'error', message: '登录失败' });
//       }
//     }
//   })
// }
// 在登录方法中
const doLogin = () => {
  const { User_Name, Password } = userInfo.value;

  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await loginAPI({ User_Name, Password });
                // 在登录成功后，请求更多用户信息
        const response = await getUserDetailsAPI({User_Name, Password});
        // 假设获取到的用户详细信息存储在 userDetails 对象中
        const userDetails = {
          User_ID: response.userId,
          User_Name: response.userName,
          Password: response.password,
          Email: response.email,
          Phone: response.phone,
          Score: response.score,
          Admin: response.admin
        };
        localStorage.setItem('userDetails', JSON.stringify(userDetails));
        ElMessage({ type: 'success', message: '登录成功' });
        router.replace({ path: '/' })
            .then(() => {
              window.location.reload();
            });
      } catch (error) {
        console.error(error);
        ElMessage({ type: 'error', message: '登录失败' });
      }
    }
  });
};


</script>

<template>
  <div>
    <header class="login-header">
      <div class="container m-top-20">
        <h1 class="logo">
          <RouterLink to="/">车位通</RouterLink>
        </h1>
        <RouterLink class="entry" to="/">
          进入网站首页
          <i class="iconfont icon-angle-right"></i>
          <i class="iconfont icon-angle-right"></i>
        </RouterLink>
      </div>
    </header>
    <section class="login-section">
      <div class="wrapper">
        <nav>
          <a href="javascript:;">账户登录</a>
        </nav>
        <div class="account-box">
          <div class="form">
            <el-form ref="formRef" :model="userInfo" :rules="rules" label-position="right" label-width="60px"
                     status-icon>
              <el-form-item prop="account" label="账户">
                <el-input v-model="userInfo.User_Name"/>
              </el-form-item>
              <el-form-item prop="password" label="密码">
                <el-input v-model="userInfo.Password"/>
              </el-form-item>
              <el-form-item prop="agree" label-width="22px">
                <el-checkbox size="large" v-model="userInfo.agree">
                  我已同意隐私条款和服务条款
                </el-checkbox>
              </el-form-item>
              <el-button size="large" class="subBtn" @click="doLogin">点击登录</el-button>
            </el-form>
          </div>
        </div>
      </div>
    </section>
    <footer class="login-footer">
      <div class="container">
        <p>
          <a href="javascript:" onclick="window.location.href='https://github.com/Chocolate2323';">关于我们</a>
          <a href="javascript:" onclick="window.location.href='http://www.baidu.com';">售后服务</a>
          <a href="javascript:" onclick="window.location.href='http://www.bing.com';">搜索推荐</a>
          <a href="javascript:" onclick="window.location.href='http://www.cug.edu.cn';">友情链接</a>
        </p>
        <p>CopyRight &copy; 车位通</p>
      </div>
    </footer>
  </div>
</template>
<style scoped lang='scss'>
.login-header {
  background: #fff;
  border-bottom: 1px solid #e4e4e4;
  .container {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
  }
  .logo {
    width: 200px;
    a {
      display: block;
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      background: url("@/assets/images/head_logo.png") no-repeat center 18px / contain;
    }
  }
  .sub {
    flex: 1;
    font-size: 24px;
    font-weight: normal;
    margin-bottom: 38px;
    margin-left: 20px;
    color: #666;
  }
  .entry {
    width: 120px;
    margin-bottom: 38px;
    font-size: 16px;
    i {
      font-size: 14px;
      color: $xtxColor;
      letter-spacing: -5px;
    }
  }
}
.login-section {
  background: url('@/assets/images/login_logo.png') no-repeat center / cover;
  height: 488px;
  position: relative;
  .wrapper {
    width: 380px;
    background: #fff;
    position: absolute;
    left: 50%;
    top: 54px;
    transform: translate3d(100px, 0, 0);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
    nav {
      font-size: 14px;
      height: 55px;
      margin-bottom: 20px;
      border-bottom: 1px solid #f5f5f5;
      display: flex;
      padding: 0 40px;
      text-align: right;
      align-items: center;
      a {
        flex: 1;
        line-height: 1;
        display: inline-block;
        font-size: 18px;
        position: relative;
        text-align: center;
      }
    }
  }
}
.login-footer {
  padding: 30px 0 50px;
  background: #fff;
  p {
    text-align: center;
    color: #999;
    padding-top: 20px;
    a {
      line-height: 1;
      padding: 0 10px;
      color: #999;
      display: inline-block;
      ~a {
        border-left: 1px solid #ccc;
      }
    }
  }
}
.account-box {
  .toggle {
    padding: 15px 40px;
    text-align: right;
    a {
      color: $xtxColor;
      i {
        font-size: 14px;
      }
    }
  }
  .form {
    padding: 0 20px 20px 20px;
    &-item {
      margin-bottom: 28px;
      .input {
        position: relative;
        height: 36px;
        >i {
          width: 34px;
          height: 34px;
          background: #cfcdcd;
          color: #fff;
          position: absolute;
          left: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 18px;
        }
        input {
          padding-left: 44px;
          border: 1px solid #cfcdcd;
          height: 36px;
          line-height: 36px;
          width: 100%;
          &.error {
            border-color: $priceColor;
          }
          &.active,
          &:focus {
            border-color: $xtxColor;
          }
        }
        .code {
          position: absolute;
          right: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 14px;
          background: #f5f5f5;
          color: #666;
          width: 90px;
          height: 34px;
          cursor: pointer;
        }
      }
      >.error {
        position: absolute;
        font-size: 12px;
        line-height: 28px;
        color: $priceColor;
        i {
          font-size: 14px;
          margin-right: 2px;
        }
      }
    }
    .agree {
      a {
        color: #069;
      }
    }
    .btn {
      display: block;
      width: 100%;
      height: 40px;
      color: #fff;
      text-align: center;
      line-height: 40px;
      background: $xtxColor;
      &.disabled {
        background: #cfcdcd;
      }
    }
  }
  .action {
    padding: 20px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .url {
      a {
        color: #999;
        margin-left: 10px;
      }
    }
  }
}
.subBtn {
  background: $xtxColor;
  width: 100%;
  color: #fff;
}
</style>

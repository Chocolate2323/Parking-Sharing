import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login/index.vue'
import Layout from '@/views/Layout/index.vue'
import Home from '@/views/Home/index.vue'
import CateGory from '@/views/Category/index.vue'
import ParkingReservation from '@/views/ParkingReservation.vue';
import ParkingPublish from '@/views/ParkingPublish.vue';
import ParkingReview from '@/views/ParkingReview.vue';
import InformationMaintenance from '@/views/UserInformationMaintenance.vue';
import MyOrder from '@/views/MyOrder.vue';
import UserInformationMaintenance from "@/views/UserInformationMaintenance.vue";
import ParkingInformationMaintenance from "@/views/ParkingInformationMaintenance.vue";
import Baidu from "@/views/Baidu.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  //path和component对应关系
  routes: [
    {
      path:'/',
      component:Layout,
      children:[
        {
          path:'',
          component:Home
        },
        {
          path:'category',
          component:CateGory
        }
      ],
    },
    {
      path:'/login',
      component:Login
    },
    {
      path: '/parking-reservation',
      name: 'ParkingReservation',
      component: ParkingReservation
    },
    {
      path: '/parking-publish',
      name: 'ParkingPublish',
      component: ParkingPublish
    },
    {
      path: '/parking-review',
      name: 'ParkingReview',
      component: ParkingReview
    },
    {
      path: '/user-information-maintenance',
      name: 'UserInformationMaintenance',
      component: UserInformationMaintenance
    },
    {
      path: '/parking-information-maintenance',
      name: 'ParkingInformationMaintenance',
      component: ParkingInformationMaintenance
    },
    {
      path: '/my-order',
      name: 'MyOrder',
      component: MyOrder
    },
    {
      path: '/baidu',
      name: 'Baidu',
      component: Baidu
    }
  ]
})

export default router

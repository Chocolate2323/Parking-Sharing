import request from '@/utils/http'

export const loginAPI = ({ User_Name, Password }) => {
    return request({
        url: '/login',
        method: 'POST',
        data: {
            User_Name,
            Password
        }
    })
}
export const getUserDetailsAPI = ({User_Name, Password }) => {
    return request({
        url: '/user/details',
        method: 'POST',
        data: {
            User_Name,
            Password
        }
    });
};

export const fetchUserListAPI = () => {
    return request({
        url: '/userList',
        method: 'POST',
    });
};
export const findMaxIdAPI = () => {
    return request({
        url: '/maxUserId',
        method: 'POST',
    });
};

export const searchUserNameAPI = ({User_Name}) => {
    return request({
        url: '/userList/searchname',
        method: 'POST',
        data:{
            User_Name,
        }
    });
};

export const searchUserIDAPI = ({User_ID}) => {
    return request({
        url: '/userList/searchid',
        method: 'POST',
        data:{
            User_ID,
        }
    });
};

export const getUserNameAPI = ({User_ID}) => {
    return request({
        url: '/parking/getname',
        method: 'POST',
        data:{
            User_ID,
        }
    });
};
export const updateUserAPI = ({User_ID,User_Name,Password,Email,Phone,Score,Admin}) => {
    return request({
        url: `/userUpdate`,
        method: 'POST',
        data:{
            User_ID,
            User_Name,
            Password,
            Email,
            Phone,
            Score,
            Admin
        }
    });
};

export const addUserAPI = ({User_ID,User_Name,Password,Email,Phone,Score,Admin}) => {
    return request({
        url: `/adduser`,
        method: 'POST',
        data:{
            User_ID,
            User_Name,
            Password,
            Email,
            Phone,
            Score,
            Admin
        }
    });
};
export const deleteUserAPI = ({User_ID}) => {
    return request({
        url: `/deleteuser`,
        method: 'POST',
        data:{
            User_ID,
        }
    });
};

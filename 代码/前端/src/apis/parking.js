import request from '@/utils/http'

export const getPositionAPI = ({ Community_ID }) => {
    return request({
        url: '/parking/getposition',
        method: 'POST',
        data: {
            Community_ID
        }
    })
}
export const getCommunityNameAPI = ({Community_ID }) => {
    return request({
        url: '/parking/getcommunityname',
        method: 'POST',
        data: {
            Community_ID
        }
    });
};
export const getCommunityLongitudeAPI = ({Community_ID }) => {
    return request({
        url: '/parking/getcommunitylongitude',
        method: 'POST',
        data: {
            Community_ID
        }
    });
};
export const getCommunityLatitudeAPI = ({Community_ID }) => {
    return request({
        url: '/parking/getcommunitylatitude',
        method: 'POST',
        data: {
            Community_ID
        }
    });
};
export const getCommunityIdAPI = ({Parking_ID }) => {
    return request({
        url: '/parking/getcommunityid',
        method: 'POST',
        data: {
            Parking_ID
        }
    });
};
export const getParkingNumberAPI = ({Parking_ID }) => {
    return request({
        url: '/parking/getparkingnumber',
        method: 'POST',
        data: {
            Parking_ID
        }
    });
};
export const getStartTimeAPI = ({Parking_ID }) => {
    return request({
        url: '/parking/getstarttime',
        method: 'POST',
        data: {
            Parking_ID
        }
    });
};
export const getEndTimeAPI = ({Parking_ID }) => {
    return request({
        url: '/parking/getendtime',
        method: 'POST',
        data: {
            Parking_ID
        }
    });
};
export const getUserIdAPI = ({Parking_ID }) => {
    return request({
        url: '/parking/getuserid',
        method: 'POST',
        data: {
            Parking_ID
        }
    });
};
export const fetchParkingListAPI = () => {
    return request({
        url: '/parking/parkingList',
        method: 'POST',
    });
};
export const fetchHistoryAPI = ({User_ID}) => {
    return request({
        url: '/history',
        method: 'POST',
        data:{
            User_ID
        }
    });
};
export const fetchRentalAPI = () => {
    return request({
        url: '/rentalList',
        method: 'POST',
    });
};
export const fetchAPI = () => {
    return request({
        url: '/rentalList',
        method: 'POST',
    });
};
export const searchParkingNumberAPI = ({Parking_Number}) => {
    return request({
        url: '/parkingList/searchnumber',
        method: 'POST',
        data:{
            Parking_Number,
        }
    });
};
export const searchParkingIDAPI = ({Parking_ID}) => {
    return request({
        url: '/parkingList/searchid',
        method: 'POST',
        data:{
            Parking_ID,
        }
    });
};
export const updateParkingAPI1 = ({ParkingNumber,Price,Area,States,Parking_ID,Community_ID}) => {
    return request({
        url: `/parkingUpdate1`,
        method: 'POST',
        data:{
            ParkingNumber,
            Price,
            Area,
            States,
            Parking_ID,
            Community_ID
        }
    });
};
export const updateParkingAPI2 = ({Position,CommunityName,Longitude,Latitude,Community_ID}) => {
    return request({
        url: `/parkingUpdate2`,
        method: 'POST',
        data:{
            Position,
            CommunityName,
            Longitude,
            Latitude,
            Community_ID
        }
    });
};
export const addParkingAPI1 = ({ParkingNumber,Price,Area,States,Parking_ID,Community_ID}) => {
    return request({
        url: `/addparking1`,
        method: 'POST',
        data:{
            ParkingNumber,
            Price,
            Area,
            States,
            Parking_ID,
            Community_ID
        }
    });
};
export const addParkingAPI2 = ({Position,CommunityName,Longitude,Latitude,Community_ID}) => {
    return request({
        url: `/addparking2`,
        method: 'POST',
        data:{
            Position,
            CommunityName,
            Longitude,
            Latitude,
            Community_ID
        }
    });
};
export const deleteParkingAPI = ({Parking_ID}) => {
    return request({
        url: `/deleteparking`,
        method: 'POST',
        data:{
            Parking_ID,
        }
    });
};
export const deleteCommunityAPI = ({Community_ID}) => {
    return request({
        url: `/deletecommunity`,
        method: 'POST',
        data:{
            Community_ID,
        }
    });
};
export const changeStates1API = ({ Parking_ID }) => {
    return request({
        url: '/parking/changestates1',
        method: 'POST',
        data: {
            Parking_ID
        }
    })
}
export const changeStates2API = ({ Parking_ID }) => {
    return request({
        url: '/parking/changestates2',
        method: 'POST',
        data: {
            Parking_ID
        }
    })
}
export const addRentalAPI = ({ Parking_ID ,User_ID ,Price ,Start_Time ,End_Time}) => {
    return request({
        url: '/parking/addrental',
        method: 'POST',
        data: {
            Parking_ID,
            User_ID,
            Price,
            Start_Time,
            End_Time,
        }
    })
}
export const addReservationAPI = ({ Parking_ID ,User_ID ,Start_Time ,End_Time}) => {
    return request({
        url: '/parking/addreservation',
        method: 'POST',
        data: {
            Parking_ID,
            User_ID,
            Start_Time,
            End_Time,
        }
    })
}
export const addCommentAPI = ({ Rental_ID ,Parking_ID ,User_ID ,Comments ,Score}) => {
    return request({
        url: '/parking/addcomment',
        method: 'POST',
        data: {
            Rental_ID,
            Parking_ID,
            User_ID,
            Comments,
            Score,
        }
    })
}
export const deleteReservationAPI = ({ Parking_ID ,User_ID }) => {
    return request({
        url: '/parking/deletereservation',
        method: 'POST',
        data: {
            Parking_ID,
            User_ID,
        }
    })
}
export const searchCommentAPI = ({Rental_ID}) => {
    return request({
        url: '/parking/searchcomment',
        method: 'POST',
        data:{
            Rental_ID,
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

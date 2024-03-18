import { defineStore } from "pinia";
import axios from "axios";
import {toRaw} from "vue";

const backend = 'http://localhost:8080'

export const useChatRoomStore = defineStore("chatRoom", {
    state: () => ({
        roomName: "",
        roomList: [],
        chatRoomId: "",
    }),
    actions: {
        async getRoomList() {
            let response = await axios.get(`${backend}/chat/rooms`, {
                headers: {
                    Authorization: localStorage.getItem("accessToken")
                },
            });
            console.log(response.data.result);
            response.data.result.forEach((chatRoom) => {
                this.roomList.push(chatRoom);
            })
            return toRaw(this.roomList);
        },
        async createChatRoom(roomName, memberList) {
            const roomInfo = {
                chatRoomName: roomName,
                memberId: memberList
            };

            let response = await axios.post(`${backend}/chat/room/create`, roomInfo);
            console.log(response.data);

            this.visible = false;
        },
    }
})
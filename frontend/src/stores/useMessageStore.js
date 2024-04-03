import { defineStore } from "pinia";
import axios from "axios";
import { useMainStore } from "@/stores/useMainStore";

const backend = process.env.VUE_APP_API_ENDPOINT;
export const useMessageStore = defineStore("message", {
    state: () => ({
        recvList: [],
    }),
    actions: {
         addMessage(message) {
             const date = new Date(message.createdAt);
             const minutes = date.getMinutes().toString().padStart(2, '0');
             const amOrPm = date.getHours() >= 12 ? '오후 ' : '오전 ';
             const formatHour = date.getHours() % 12 === 0 ? 12 : date.getHours() % 12;
             message.createdAt = amOrPm + formatHour + ':' + minutes;
             useMainStore().getChatProfile(message.memberId).then(result => {
                 message.profileImage = result;
             })
             this.recvList.push(message);
         },
        async getChatList(chatRoomId, token, page, size) {
             if (chatRoomId !== undefined) {
                 try {
                     let response = await axios.get(`${backend}/chat/room/chatlist?chatRoomId=${chatRoomId}&page=${page}&size=${size}`, {
                         headers: {
                             Authorization: token
                         },
                     });
                     response.data.result.forEach((message) => {
                         this.addMessage(message);
                     })
                 } catch (error) {
                    console.log(error);
                 }
             }
        },
    },
    getters: {
        getAllMessage(state) {
            return state.recvList;
        }
    }
})
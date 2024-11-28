<script>
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('LoginUser')),
      tableData: '',
      title: '',
      rowid: '',
      row:'',
      msg: [],
      show: false,
      message:'',
      intervalId: null,
    };
  },
  methods: {
    getList() {
      let data = new FormData();
      data.append("id", this.user.id);
      this.$axios.post(this.$httpurl + '/msg/getList', data).then(res => res.data).then(res => {
        this.tableData = res.data;
      });
    },
    clickOption(row) {
      this.row = row;
      this.title = row.username;
      this.rowid = row.id;
      let data = new FormData();
      data.append("fromid", this.user.id);
      data.append("toid", row.id);
      this.$axios.post(this.$httpurl + '/msg/get', data).then(res => res.data).then(res => {
        this.msg = res.data;
        this.show = true;
        this.scrollToBottom();
      });
    },

    get(){
      let data = new FormData();
      data.append("fromid", this.user.id);
      data.append("toid", this.row.id);
      this.$axios.post(this.$httpurl + '/msg/get', data).then(res => res.data).then(res => {
        this.msg = res.data;
        this.show = true;
        this.scrollToBottom();
      });
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const messagesContainer = this.$refs.messagesContainer;
        if (messagesContainer) {
          messagesContainer.scrollTop = messagesContainer.scrollHeight;
        }
      });
    },
    send(){
      console.log("点击了发送消息")
      let data = new FormData
      data.append("fromid", this.user.id);
      data.append("toid", this.rowid);
      data.append("msg", this.message);
      data.append("type",1);
      this.$axios.post(this.$httpurl + '/msg/send', data).then(res => res.data).then(res => {
        this.clickOption(this.row)
        console.log(res)
        this.message = ''
      })
    },
  },
  mounted() {
    this.getList();
    this.scrollToBottom();
    this.intervalId = setInterval(this.get,3000)
  },

  beforeDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
};
</script>

<template>
  <div>
    <!-- 联系人列表容器 -->
    <div class="contact-container" style="width: 15%; height: 100vh; overflow-y: auto; position: fixed;">
      <el-table :data="tableData">
        <el-table-column prop="username" label="联系人" width="100%"></el-table-column>
        <el-table-column prop="name" label="操作" width="100%">
          <template slot-scope="scope">
            <el-button type="primary" @click.native="clickOption(scope.row)">聊天</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 聊天区域容器 -->
    <div class="chat-container" style="margin-left: 20%; width: 75%; height: 90vh; position: fixed; right: 0;">
      <div class="chat-header" v-if="title !== ''">{{ title }}的聊天</div>
      <div class="messages" ref="messagesContainer">
        <el-row v-for="(message, index) in msg" :key="index">
          <el-col :span="14" :offset="message.mine ? 8 : 0">
            <el-card class="message-card">
              <div class="message-content">
                <div class="message-header">
                  <span>{{ message.mine ? '我' : title }}</span>
                </div>
                <div class="message-body">
                  {{ message.msg }}
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <div class="chat-footer">
        <el-input v-show="show" type="text"   @keydown.enter.native="send" placeholder="输入消息" style="width: 75%" v-model="message"></el-input>
        <el-button  v-show="show" type="primary" @click="send">发送</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.contact-container {
  overflow-y: auto;
  height: 100vh;
  position: fixed;
  z-index: 1; /* 确保联系人列表在背后 */
}

.chat-container {
  display: flex;
  z-index: 2; /* 确保聊天区域在前面 */
  flex-direction: column;
  margin-left: 20%;
  width: 85%;
  height: 90vh;
  position: fixed;
  right: 0;
}

.chat-header {
  flex-shrink: 0;
  padding: 10px;
  background-color: #f5f5f5;
  text-align: center;
  font-size: 25px;
  z-index: 10;
}

.messages {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
}

.message-card {
  margin-bottom: 20px;
}

.message-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.message-body {
  background-color: #f3f3f3;
  padding: 10px;
  border-radius: 4px;
  max-width: 70%;
  word-wrap: break-word;
}

.message-body.isMine {
  background-color: #e0f7fa;
  align-self: flex-end;
}

.chat-footer {
  flex-shrink: 0;
  display: flex;
  padding: 10px;
  background-color: #f5f5f5;
  z-index: 1; /* 确保输入框在最前面 */
  margin-bottom: 10px;
}
</style>

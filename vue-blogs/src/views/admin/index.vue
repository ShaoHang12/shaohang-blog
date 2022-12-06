<template>
  <div class="box">
    <div class="sidebar">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="1" @click="toUserView">用户管理</el-menu-item>
        <el-menu-item index="2" @click="toNewsView">文章管理</el-menu-item>
        <el-menu-item index="3" @click="toCgView">草稿箱</el-menu-item>
      </el-menu>
    </div>
    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    toUserView() {
      this.color = 
      this.$router.push({ name: "userManager" });
    },
    toNewsView() {
      this.$router.push({ name: "newsManager" });
    },
    toCgView() {
      this.$router.push({ name: "cgManager" });
    },
  },

  data() {
    return {
      activeIndex:'1'
    };
  },
};
</script>

<style>
.box {
  padding: 50px;
  margin-top: 100px;
  /* display: flex; */
}
.sidebar {
  margin-bottom: 20px;
  margin-right: 15px;
}
.container {
  flex: 8;
}
</style>

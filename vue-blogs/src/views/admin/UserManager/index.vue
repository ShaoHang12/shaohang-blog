<template>
  <div>
    <el-button class="addUser" @click="addUser" type="success"
      >新增用户</el-button
    >
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="username" label="用户"> </el-table-column>
      <el-table-column prop="nickname" label="昵称"> </el-table-column>

      <el-table-column label="性别">
        <template slot-scope="scope">
          {{ scope.row.sex == "0" ? "男" : "女" }}
        </template>
      </el-table-column>

      <el-table-column label="角色">
        <template slot-scope="scope">
          {{
            scope.row.role == "admin"
              ? "管理员":"普通用户"
              
          }}
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="edit(scope.row.userId)" type="primary"
            >编辑</el-button
          >
          <el-button
            @click="deleteUser(scope.row.userId)"
            size="mini"
            type="warning"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="block pagination">
      <el-pagination
        background="#f9f9f9"
        layout="prev, pager, next"
        :total="total"
      />
    </div>

    <el-dialog title="编辑" :visible.sync="dialogFormVisible">
      <el-form label-width="100px" :model="form">
        <el-form-item label="用户名">
          <el-input
            :disabled="isEdit"
            v-model="form.username"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.sex" placeholder="请选择角色">
            <el-option label="男" value="0"></el-option>
            <el-option label="女" value="1"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="角色">
          <el-select v-model="form.role" placeholder="请选择角色">
            <el-option label="普通用户" value="user"></el-option>
            <el-option label="管理员" value="admin"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            type="password"
            v-model="form.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDiog">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import { Message } from "element-ui";
import {
  fetchList,
  getUserById,
  updateUser,
  deleteUser,
  saveUser,
} from "../../../api/admin/user";

export default {
  mounted() {
    this.getUserList();
  },

  data() {
    return {
      isEdit: true,
      formLabelWidth: "120px",
      form: {},
      dialogFormVisible: false,
      total: 0,
      queryParam: {
        pagesize: 10,
        pagenum: 1,
      },
      tableData: [],
    };
  },
  methods: {
    //  关闭弹窗
    cancelDiog() {
      this.dialogFormVisible = false;
      this.form = {};
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    toUserView() {
      console.log("toUserView");
      this.$router.push({ name: "userManager" });
    },

    // 删除用户信息
    async deleteUser(id) {
      console.log("delete");
      const res = await deleteUser(id);
      if (res.status == 200) {
        Message({
          message: res.msg,
          type: "success",
          duration: 3 * 1000,
        });
      }

      this.getUserList();
    },
    // 修改用户信息
    async update() {
       this.dialogFormVisible = false;
      if (this.isEdit) {
        console.log('edit');
        const res = await updateUser(this.form);
        if (res.status == 200) {
          Message({
            message: res.msg,
            type: "success",
            duration: 3 * 1000,
          });
        }

    
      }else{
        console.log('save');
        const res = await saveUser(this.form);
        if (res.status == 200) {
          Message({
            message: res.msg,
            type: "success",
            duration: 3 * 1000,
          });
        }
      }
      this.getUserList();
    },
    // 获取用户列表
    async getUserList() {
      const { data: res } = await fetchList({
        params: {
          pagesize: this.queryParam.pagesize,
          pagenum: this.queryParam.pagenum,
        },
      });
      this.tableData = res.records;
      this.total = res.total;
      console.log("11231", this.tableData);
    },
    async edit(id) {
      this.isEdit = true;
      this.dialogFormVisible = true;
      const { data: res } = await getUserById(id);
      this.form = res;
    },
    // 新增用户
    async addUser() {
      console.log("add");
      this.isEdit = false;
      this.dialogFormVisible = true;
    },
  },
};
</script>

<style scoped>

.pagination {
  margin-top: 10px;
  background-color: #f9f9f9;
}
.addUser {
  margin-bottom: 15px;
}
</style>

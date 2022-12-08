<template>
  <div class="newsManger">
    <el-button class="addBtn" @click="add" type="success">新增文章</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="articleTitle" label="文章标题"> </el-table-column>
      <el-table-column prop="articleBrief" label="文章简介"> </el-table-column>

      <el-table-column prop="articleLook" label="浏览量"> </el-table-column>
      <el-table-column prop="articleAuthor" label="作者"> </el-table-column>
      <el-table-column prop="articleTime" label="发布时间"> </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="edit(scope.row.articleId)"
            type="primary"
            >编辑</el-button
          >
          <el-button
            @click="deleteUser(scope.row.articleId)"
            size="mini"
            type="warning"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="block pagination">
      <el-pagination
        @prev-click="prePage"
        @next-click="nextPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagenum"
        :page-size="pagesize"
        background="#f9f9f9"
        layout="prev, pager, next"
        :total="total"
      />
    </div>

    <el-dialog
      title="编辑信息"
      :visible.sync="dialogFormVisible"
      :before-close="handleClose"
    >
      <el-form label-width="100px" :model="form">
        <el-form-item label="文章标题">
          <el-input v-model="form.articleTitle" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="文章封面">
          <!-- action:设置图片上传地址
            :on-success:可以检查图片上传成功,会成功执行一次
            :before-upload:图片上传之前
           -->
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8080/business/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img :src="form.articleCover" class="avatar" />
          </el-upload>
        </el-form-item>

        <el-form-item label="文章简介">
          <el-input v-model="form.articleBrief" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="文章内容">
          <Toolbar
            style="border-bottom: 1px solid #ccc"
            :editor="editor"
            :defaultConfig="toolbarConfig"
            :mode="mode"
          />
          <Editor
            style="height: 500px; overflow-y: hidden; border: 1px solid #ccc"
            v-model="form.articleContent"
            :defaultConfig="editorConfig"
            :mode="mode"
            @onCreated="onCreated"
          />
        </el-form-item>
        <el-form-item label="浏览量">
          <el-input v-model="form.articleLook" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.articleAuthor" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker
            v-model="form.articleTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="commitInfo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import "@wangeditor/editor/dist/css/style.css";
import { Message } from "element-ui";
import { IEditorConfig } from "@wangeditor/editor";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";

import {
  fetchList,
  getArticleById,
  updateArticle,
  saveArticle,
  deleteArticle,
} from "../../../api/admin/article";

export default {
  mounted() {
    this.getUserList();
    this.initWangEditor();
  },
  created() {
    this.initWangEditor();
  },
  components: { Editor, Toolbar },
  data() {
    return {
      isEdit: false,
      formLabelWidth: "120px",
      form: {},
      dialogFormVisible: false,
      total: 0,
      pagesize: 10,
      pagenum: 1,
      tableData: [],
      editor: {},
      html: "<p>hello</p>",
      toolbarConfig: {},
      mode: "default",
      editorConfig: {
        placeholder: "请输入内容...",
        MENU_CONF: {
          // 配置图片
          uploadImage: {
            server: "http://localhost:8080/business/file/upload",
            fieldName: "file",

            onFailed: function(file, res) {
              console.log(res);
            },
          },
        },
      },
      mode: "default", // or 'simple',
    };
  },
  beforeDestroy() {
    const editor = this.editor;
    if (editor == null) return;
    editor.destroy(); // 组件销毁时，及时销毁编辑器
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor); // 一定要用 Object.seal() ，否则会报错
      console.log(this.editor);
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    toUserView() {
      console.log("toUserView");
      this.$router.push({ name: "userManager" });
    },
    handleClose() {
      // this.$confirm("确认关闭？")
      //   .then((_) => {
      //     this.dialogFormVisible = false;
      //     this.form = {};
      //   })
      //   .catch((_) => {});
      this.dialogFormVisible = false;
      this.form = {};
    },
    //前进
    prePage() {
      this.pagenum -= 1;
      console.log(this.pagenum, "前进");
      this.getUserList();
    },
    //后退
    nextPage() {
      this.pagenum += 1;
      console.log(this.pagenum, "后退");
      this.getUserList();
    },
    // 初始页currentPage、初始每页数据数pagesize和数据data
    handleSizeChange(size) {
      this.pagesize = size;
      this.getUserList();
    },
    handleCurrentChange(currentPage) {
      console.log(currentPage);
      this.pagenum = currentPage;
      this.getUserList();
    },
    // 删除用户信息
    async deleteUser(id) {
      console.log("delete");
      const res = await deleteArticle(id);
      if (res.status == 200) {
        Message({
          message: res.msg,
          type: "success",
          duration: 3 * 1000,
        });
      }

      this.getUserList();
    },
    // 修改文章信息
    async update() {
      this.dialogFormVisible = false;
      const res = await updateArticle(this.form);
      if (res.status == 200) {
        Message({
          message: res.msg,
          type: "success",
          duration: 3 * 1000,
        });
      }

      this.getUserList();
    },
    // 新增文章信息
    async save() {
      this.dialogFormVisible = false;
      const res = await saveArticle(this.form);
      if (res.status == 200) {
        Message({
          message: res.msg,
          type: "success",
          duration: 3 * 1000,
        });
      }

      this.getUserList();
    },
    async commitInfo() {
      if (this.isEdit) {
        this.update();
      } else {
        this.save();
      }
    },
    // 获取文章列表
    async getUserList() {
      this.form = {};
      const { data: res } = await fetchList({
        params: {
          pagesize: this.pagesize,
          pagenum: this.pagenum,
        },
      });
      this.tableData = res.records;
      this.total = res.total;
      console.log(res);
    },
    async edit(id) {
      this.isEdit = true;
      this.dialogFormVisible = true;
      const { data: res } = await getArticleById(id);
      this.form = res;
    },
    // 新增用户
    async add() {
      console.log("add");
      this.form = {};
      this.isEdit = false;
      this.dialogFormVisible = true;
    },

    async handleAvatarSuccess(res, file) {
      // res:上传成功之后返回的数据
      //file:上传成功之后服务器返回给前端
      console.log("res", res.data.url);
      this.form.articleCover = res.data.url;
      console.log(this.form);
    },
    async beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 100;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 100MB!");
      }
      return isJPG && isLt2M;
    },

    // 配置编辑器
    initWangEditor() {
      this.editorConfig.MENU_CONF["uploadImage"] = {
        server: "http://localhost:8080/business/file/upload",
        // form-data fieldName ，默认值 'wangeditor-uploaded-image'
        fieldName: "file",
        // 单个文件的最大体积限制，默认为 2M
        maxFileSize: 100 * 1024 * 1024, // 1M
        // 最多可上传几个文件，默认为 100
        maxNumberOfFiles: 10,
        // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
        allowedFileTypes: ["image/*"],

        // 自定义上传参数，例如传递验证的 token 等。参数会被添加到 formData 中，一起上传到服务端。
        // meta: {
        //   token: localStorage.getItem("token"),
        // },

        // 将 meta 拼接到 url 参数中，默认 false
        metaWithUrl: false,

        // 自定义增加 http  header
        headers: {
          token: localStorage.getItem("token"),
        },

        // 跨域是否传递 cookie ，默认为 false
        withCredentials: true,

        // 超时时间，默认为 10 秒
        timeout: 5 * 1000, // 5 秒
      };
    },
  },
};
</script>

<style>
.newsManger {
  margin-top: 40px;
}
.pagination {
  margin-top: 10px;
  background-color: #f9f9f9;
}
.addBtn {
  margin-bottom: 15px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<template>
  <div class="articles">
    <banner></banner>
    <div class="site-content animate">
     
      <main class="site-main">
        <article class="hentry">
          <!-- 文章头部 -->
          <header class="entry-header">
            <!-- 标题输出 -->
            <h1 class="entry-title">{{data.articleTitle}}</h1>
            <hr />
            <div class="breadcrumbs">
              <div id="crumbs">最后更新时间：{{data.articleTime}}</div>
            </div>
          </header>
          <!-- 正文输出 -->
          <div class="entry-content" v-html="data.articleContent" v-highlight>

          </div>
          <!-- 文章底部 -->
          <section-title>
            <footer class="post-footer">
              <!-- 阅读次数 -->
              <div class="post-like">
                <i class="iconfont iconeyes"></i>
                <span class="count">685</span>
              </div>
            </footer>
          </section-title>
        </article>
      </main>
    </div>
  </div>
</template>

<script>
import Banner from "@/components/banner";
import sectionTitle from "@/components/section-title";
import comment from "@/components/comment";
import menuTree from "@/components/menu-tree";
import { getArticleById } from "../api";
export default {
  name: "articles",
  data() {
    return {
      showDonate: false,
      comments: [],
      menus: [],
      data: [],
      id: 1,
    };
  },
  components: {
    Banner,
    sectionTitle,
    comment,
    menuTree,
  },
  methods: {
    async getArticleById() {
      const { data: res } = await getArticleById(this.id);
      console.log(res);
      this.data = res;
    },
    fetchH(arr, left, right) {
      if (right) {
        return arr.filter(
          (item) => item.offsetTop > left && item.offsetTop < right
        );
      } else {
        return arr.filter((item) => item.offsetTop > left);
      }
    },
   
  },
  mounted() {
    this.id = this.$route.params.id;
    
  },
  created() {
    this.id = this.$route.params.id;
    this.getArticleById();
  },
};
</script>
<style scoped lang="less">
.site-content {
  position: relative;
  .site-main {
    padding: 80px 0 0 0;
  }
}
#article-menus {
  position: sticky;
  top: 0;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  border-radius: 3px;
  padding: 15px;
  width: 300px;
  transform: translateX(-120%) translateY(150px);
  font-size: 14px;
}
article.hentry {
  .entry-header {
    .entry-title {
      font-size: 23px;
      font-weight: 600;
      color: #737373;
      margin: 0.67em 0;

      &:before {
        content: "#";
        margin-right: 6px;
        color: #d82e16;
        font-size: 20px;
        font-weight: 600;
      }
    }

    hr {
      height: 1px;
      border: 0;
      background: #efefef;
      margin: 15px 0;
    }

    .breadcrumbs {
      font-size: 14px;
      color: #d2d2d2;
      text-decoration: none;
      margin-bottom: 30px;
    }
  }

  .entry-content {
  }

  footer.post-footer {
    width: 100%;
    padding: 20px 10px;
    margin-top: 30px;
    height: 65px;
    position: relative;
    i {
      font-size: 18px;
      margin-right: 5px;
    }
    .post-like {
      float: right;
      margin: 7px 0 0 20px;
    }

    .post-share {
      float: right;
      list-style: none;
      margin-right: 20px;
    }

    .donate {
      float: left;
      line-height: 36px;
      border-radius: 100%;
      -webkit-border-radius: 100%;
      -moz-border-radius: 100%;
      border: 1px solid #2b2b2b;
      &:hover {
        border: 1px solid goldenrod;
        span {
          color: goldenrod;
        }
      }
      span {
        color: #2b2b2b;
        padding: 10px;
        position: relative;
        cursor: pointer;
      }

      .donate_inner {
        display: none;
        margin: 0;
        list-style: none;
        position: absolute;
        left: 80px;
        top: -40px;
        background: #fff;
        padding: 10px;
        border: 1px solid #ddd;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
        border-radius: 3px;
        &.show {
          display: block;
        }
        li {
          float: left;
        }

        img {
          width: 100px;
        }
        p {
          text-align: center;
          font-size: 15px;
          color: #d2d2d2;
          line-height: 1rem;
        }
      }

      .donate_inner:after,
      .donate_inner:before {
        content: "";
        position: absolute;
        left: 0;
        bottom: 45%;
        margin-left: -8px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-right: 8px solid #fff;
      }

      .donate_inner:before {
        left: -1px;
        border-right: 8px solid #ddd;
      }
    }

    .post-tags {
      margin: 7px 0 0 20px;
      float: left;
      text-transform: uppercase;
      a:hover {
        color: #ff6d6d;
      }
    }
  }
  .open-message {
    margin: 50px 0;
    position: relative;
    background: #2b2b2b;
    padding: 10px 30px;
    border-radius: 3px;
    font-size: 14px;
    color: #fff;
    &:after {
      content: "";
      border-left: 10px solid transparent;
      border-right: 10px solid transparent;
      border-bottom: 10px solid #2b2b2b;
      position: absolute;
      top: -8px;
      left: 48%;
    }
    p {
      margin: 10px 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    a {
      color: #a0dad0;
      padding: 0 5px;
    }
  }
}
@media (max-width: 1490px) {
  #article-menus {
    display: none;
  }
}
@media (max-width: 800px) {
  #article-menus {
    display: none;
  }
}
</style>

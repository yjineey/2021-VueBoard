<template>
  <card>
    <h4 slot="header" class="title" style="color:white;">★ 게시글보기 ★</h4>
    <div>
        <el-form abel-width="120px" label-position="right" style="width: 70%; margin:auto;">
          <el-form-item label="제목" >
            <el-input v-model="title" size="small" disabled  ></el-input>
          </el-form-item>
          <el-form-item label="작성자" >
            <el-input v-model="writer" size="small" disabled></el-input>
          </el-form-item>
          <el-form-item label="내용"/>
            <div class="tui-editor-contents" style="height:500px">
              <viewer 
                ref="viewer"
                height="500px">
              </viewer>
            </div>
        <el-form-item label="첨부파일" />
          <div class="tui-editor-contents" style="height:90px" ><br>

        <span style="text-align:center; padding:3px; " v-for="(file,fileId) in fileInfo " :key="fileId" >
          <span v-if="file.formatName==='.jpg' || file.formatName==='.jpeg' || file.formatName==='.png' || file.formatName==='.jfif'">
          <!-- DB_value -->
          <!-- <span v-if="file.fileExt == '.jpg' || file.fileExt == '.jpeg' || file.fileExt == '.png' || file.fileExt == '.jfif'" > -->
            <img src='@/assets/Icon/img.png' style="width:50px; height:50px;"  v-on:click="fileDownload(file)">
            <el-button type="success" @click="fileDownload(file)">{{ file.orgFileName  }}</el-button>
          </span>

          <span v-else-if="file.formatName==='.ppt' || file.formatName==='.pptx'">
            <img src='@/assets/Icon/ppt.png' style="width:40px; height:40px; padding:3px;" @click="fileDownload(file)">
            <el-button type="success" @click="fileDownload(file)">{{ file.orgFileName  }}</el-button>
          </span>

          <span v-else-if="file.formatName==='.xlsx'">
            <img src='@/assets/Icon/excel.png' style="width:40px; height:40px;  padding:3px;" @click="fileDownload(file)">
            <el-button type="success" @click="fileDownload(file)">{{ file.orgFileName  }}</el-button>
          </span>

          <span v-else>
            <img src='@/assets/Icon/etc.png' style="width:40px; height:40px;"  @click="fileDownload(file)">
            <el-button type="success" @click="fileDownload(file)">{{ file.orgFileName  }}</el-button>
          </span>
        </span>
          </div>
      </el-form>
    </div><br><br>
    <div style="text-align: center;">
      <el-row>
          <el-button type="primary" round @click="detailUpdate(id)" >수정</el-button>
          <el-button type="danger" round @click="detailDelete(id)"  >삭제</el-button>
      </el-row><br><br><br>
    </div>
  </card>
</template>
<script>
import { Viewer } from '@toast-ui/vue-editor';
import "codemirror/lib/codemirror.css";
import '@toast-ui/editor/dist/toastui-editor-viewer.css';

export default {
  name:"boardView",
  components: {
    viewer: Viewer,
  },
  data(){
    return{
      boardList:'',
      id:'',
      title:'',
      contents:'',
      writer:'',
      fileInfo:'',
      fileAdd:[], // 수정화면_ 파일 추가
      format:[],    //파일확장자
      formatLen : '',
      formatDot :'',
      formatName :'',
    }
  },
  created() {
    console.log("create");
  },
  mounted(){
    console.log("======게시글보기=======")
    console.log("getBno>>", this.$store.getters.bno);
    // this.id=this.$route.query.id
    this.id= this.$store.getters.bno;

    this.$axios.get('/api/boardView/'+this.id)
      .then((response)=>{
        // this.boardList = response.data.boardView;   //contents 출력시 필요

        this.title = response.data.boardView.title;
        this.contents = response.data.boardView.contents;
        this.writer = response.data.boardView.writer;
        this.fileInfo  = response.data.fileView;
        this.$refs.viewer.invoke("setMarkdown", this.contents);

        console.log(this.fileInfo);

        for(let i=0; i<this.fileInfo.length; i++){
          this.format = this.fileInfo[i].orgFileName

          this.formatLen = this.format.length;
          this.formatDot = this.format.lastIndexOf('.');
          // 확장자 명만 추출한 후 소문자로 변경
          this.formatName = this.format.substring(this.formatDot, this.formatLen).toLowerCase();
          Object.assign(this.fileInfo[i], {formatName: this.formatName});
          
          console.log(this.fileInfo, "test");
        }
      }).catch(function (e) {
        console.error(e)                
      })
    },

  methods:{
  //파일다운로드
    fileDownload(file){
      console.log(file.fileName);
      console.log(file.orgFileName);
      console.log(file.filePath);

      //새창을 열면 다음 url로 이동하겠다
      const url = '/api/fileDownload/'+ file.orgFileName +"?fileName="+file.fileName;
      window.open(url,'popupView'); 
    },

    // 상세보기(수정버튼)
    detailUpdate(id){
      console.log(id);
      this.$router.push({path:'/boardUpdate',query:{id:id}
      })
    },

    // 상세보기(삭제버튼)
    detailDelete(id){
      this.$axios.delete('/api/boardDelete2',{
        params:{
          id:id
        }
      }).then(()=>{
        console.log("detailDelete::>>>"+id)
        this.$message({
          type: 'success',
          message: '삭제되었습니다.'
      });
        this.$router.push({path:'/BoardList'})
        this.$emit("detailDelete");
      }).catch(() => {
        console.log(error)
      })          
    },
  }
}
</script>
<style scoped>
  .tui-editor-contents{
    margin: 10px 0;
    background-color: #F5F7FA;
    border-color: #E4E7ED;
    border: 1px solid #E4E7ED;
  }
</style>
<style scoped>
  .test {
    padding: 10px;
    border: 1px solid #EBEEF5;
    height: 100px
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

</style>
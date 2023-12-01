<template>
  <card>
    <h4 slot="header" class="title" style="color:white;">★ 글수정 ★</h4>
    <div>
      <el-form abel-width="120px" label-position="right" style="width: 70%; margin:auto;">
        <el-form-item label="제목" >
          <el-input v-model="title" size="small"  ></el-input>
        </el-form-item>
        <el-form-item label="작성자" >
          <el-input v-model="writer" size="small" disabled ></el-input>
        </el-form-item>
        <el-form-item label="내용">
        </el-form-item>
          <editor
            ref="eContents"
            height="500px"
            initialEditType="wysiwyg"
          /><br>
        <el-form-item label="첨부파일"></el-form-item>
          <div class="tui-editor-contents" style="height:140px" ><br>
            <span  v-for="(file,fileId) in fileInfo" :key="fileId">
              <span v-if="file.formatName==='.jpg' || file.formatName==='.jpeg' || file.formatName==='.png' || file.formatName==='.jfif'">
              <!-- DB_value -->
              <!-- <span v-if="file.fileExt == '.jpg' || file.fileExt == '.jpeg' || file.fileExt == '.png' || file.fileExt == '.jfif'" > -->
                <img src='@/assets/Icon/img.png' style="width:50px; height:50px;">
                <el-button type="success" style="margin:2px;" @click="fileDelete(file.fileName)">{{ file.orgFileName  }} 삭제</el-button>
              </span>

              <span v-else-if="file.formatName==='.ppt' || file.formatName==='.pptx'">
                <img src='@/assets/Icon/ppt.png' style="width:40px; height:40px; padding:3px;">
                <el-button type="success" style="margin:2px;" @click="fileDelete(file.fileName)">{{ file.orgFileName  }} 삭제 </el-button>
              </span>

              <span v-else-if="file.formatName==='.xlsx'">
                <img src='@/assets/Icon/excel.png' style="width:40px; height:40px;  padding:3px;">
                <el-button type="success" style="margin:2px;" @click="fileDelete(file.fileName)">{{ file.orgFileName  }} 삭제 </el-button>
              </span>

              <span v-else>
                <img src='@/assets/Icon/etc.png' style="width:40px; height:40px;" >
                <el-button type="success" style="margin:2px;" @click="fileDelete(file.fileName)">{{ file.orgFileName  }} 삭제</el-button>
              </span>
            </span>
            <div>
            <input type="file" @change="onFileSelect" multiple style="padding:15px;"><br/>
           </div>
          </div>
      </el-form>
    </div><br><br>
    <div style="text-align: center;">
      <el-row>
        <el-button type="primary" round @click="updateSave" >저장</el-button>
        <el-button type="danger" round @click="updateCancel" >취소</el-button>
      </el-row>
    </div><br><br><br>
  </card>
</template>
<script>
import { Editor } from '@toast-ui/vue-editor';
import 'codemirror/lib/codemirror.css'; // Editor's Dependency Style
import '@toast-ui/editor/dist/toastui-editor.css';// Editor's Stylex

export default {
  name:"boardUpdate",
  components: {
    editor: Editor
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
  created(){
    console.log("======게시글수정=======")
      console.log("routerId>>",this.$route.query)
      this.id=this.$route.query.id

      this.$axios.get('/api/boardView/'+this.id)
        .then((response)=>{
          this.title=response.data.boardView.title;
          this.contents=response.data.boardView.contents;
          this.writer=response.data.boardView.writer;
          this.fileInfo=response.data.fileView;
          this.$refs.eContents.invoke("setMarkdown", this.contents);

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
    //첨부파일 클릭
    onFileSelect(event){
      console.log("files ::::::::::: "+event.target.files);
      this.fileAdd = event.target.files;
      console.log(this.fileAdd)
    },

    //수정화면(파일삭제)
    fileDelete(fileName){
      const formData = new FormData();
      formData.append('fileName', fileName);
            this.$axios({
            method: "post",   //파일업로드는 put 사용 x
            url: '/api/fileDelete', 
            data: formData,
            headers: { 
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(()=>{
            this.$emit("detailDelete");
            this.$message({
              type: 'success',
              message: '파일삭제 완료'
            })
            
          this.$router.push({path:'/BoardList'})
          this.$emit("detailDelete");
        }).catch((error)=>{
            console.log(error)
        })
    },
    // 수정화면(저장버튼)
    updateSave(){
      console.log("수정버튼:::::::::::")
      const formData = new FormData();
      for(let i=0;i < this.fileAdd.length; i++){
        let file= this.fileAdd[i];
        formData.append('files', file);
      }
      formData.append('id', this.id);
      formData.append('title', this.title);
      formData.append('writer', this.writer);
      this.contents = this.$refs.eContents.invoke('getMarkdown');
      formData.append('contents', this.contents);

      console.log("eContents>>>>>>>>>>",this.$refs.eContents.invoke('getMarkdown'))
      console.log("eContents>>>>>>>>>>",this.$refs.eContents.invoke('getHtml'))     //html타입
              this.$axios({
              method: "post",   //파일업로드는 put 사용 x
              url: '/api/boardUpdate2', 
              data: formData,
              headers: { 
                'Content-Type': 'multipart/form-data'
                // 'Content-type': 'application/x-www-form-urlencoded',   : 기본값
              }
            })
            .then(()=>{
              this.$message({
                type: 'success',
                message: '수정이 완료되었습니다.'
              })
            this.$router.push({path:'/BoardList'})
              this.$emit("detailDelete");
          }).catch((error)=>{
              console.log(error)
          })

    },

  // 수정화면: 취소버튼
    updateCancel(){
      this.$router.push({path:'/BoardList'})
      this.$emit("detailDelete");
    }
  }
}
</script>
<style scoped>
.tui-editor-contents{
    margin: 10px 0;
    background-color: #F5F7FA;
    border-color: #0d48be;
    color: #b56010;
    cursor: not-allowed;
    border: 1px solid #ede4e7;
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
<style lang="scss">
  .card {
    h1,h2,h3,h4,h5,h6,p{
      color: black ;
    }
  }
</style>
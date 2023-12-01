<template>
<card>
  <h4 slot="header" class="title" style="color:white;">★ 글작성 ★</h4>
  <div>
      <el-form abel-width="120px" label-position="right" style="width: 70%; margin:auto;">
      <el-form-item label="제목" >
      <el-input class="input-group" v-model="title" placeholder="게시글입력" ></el-input>
      </el-form-item>
      <el-form-item label="작성자" >
        <el-input v-model="writer"  placeholder="로그인해주세요" disabled></el-input>
      </el-form-item>
      <el-form-item label="내용" ></el-form-item>
        <editor
          ref="eContents"
          height="500px"
          initialEditType="wysiwyg"
          />  
      <el-form-item label="첨부파일"></el-form-item>
        <input type="file" @change="onFileSelect" multiple><br><br><br>
      </el-form><br>
      <div style="text-align: center;">
        <el-row>
          <el-button type="primary" round  @click="writeSave">저장</el-button>
          <el-button type="danger" round @click="writeCancel">취소</el-button>
        </el-row>
      </div>
    </div>
</card>
</template>

<script>
import { Editor } from '@toast-ui/vue-editor';
import 'codemirror/lib/codemirror.css'; // Editor's Dependency Style
import '@toast-ui/editor/dist/toastui-editor.css';// Editor's Style

export default {
  components: { 
    editor: Editor
  },

  name: "boardWrite",
  data(){
    return {
      selectedFile:'',
      FileList:[],
      title:'',
      contents:'',
      writer:this.$store.state.id,
      // eContents:'',
    }
  },
  methods:{
    //첨부파일 클릭
    onFileSelect(event){
      console.log(event);
      console.log("files ::::::::::: "+event.target.files);
      // this.selectedFile = event.target.files[0];
      this.FileList = event.target.files;
      console.log(this.FileList)
    },

    // 글작성(저장버튼)
    writeSave(){
      const formData = new FormData();
      for(let i=0;i < this.FileList.length; i++){
        let file= this.FileList[i];
        console.log(file);
        formData.append('files', file);
      // formData.append('files', this.selectedFile);   //단일처리
      }
        formData.append('title', this.title);
        formData.append('writer', this.writer);
        // wysiwyg 위지윅 / Markdown 마크다운
        this.contents = this.$refs.eContents.invoke('getMarkdown');
        formData.append('contents', this.contents);

        console.log("eContents>>>>>>>>>>",this.$refs.eContents.invoke('getMarkdown'))
        console.log("eContents>>>>>>>>>>",this.$refs.eContents.invoke('getHtml'))     //html타입

          this.$axios({
          method: "post",
          url: '/api/boardInsert', 
          data: formData,
          headers: { 
            'Content-Type': 'multipart/form-data'
          }
          }).then(()=>{
            this.$message({
              type: 'success',
              message: '게시글이 등록 되었습니다.'

            })
          this.$router.push({path:'/BoardList'})
          this.$emit("detailDelete");
          }).catch((error)=>{
              console.log(error)
          })
    },
    // 글작성(취소버튼)
    writeCancel(){
      this.$router.push({path:'/BoardList',query:this.body})
      .then(() => {
        this.updateFlag=true;
        this.vshow=false;
      }).catch((error)=>{
        console.log(error)
      })
    },
  } //methods
}//export
</script>
<style lang="scss">
  .card {
    h1,h2,h3,h4,h5,h6,p{
      color: black ;
    }
  }
</style>
// 형식의 차이가 있다.
<style scoped>
.card h1, .card h2, .card h3, .card h4, .card h5, .card h6, .card p {
    color: black;
}
 </style> 
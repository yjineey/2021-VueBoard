<template>
  <div class="row">
    <div class="col-md-8">
      <card >
        <h4 slot="header" class="title" style="color:white;">★ Login ★</h4>
          <div>
      <el-form :model="ruleForm" status-icon ref="ruleForm" label-width="120PX" style="width: 50%; margin:auto;" class="demo-ruleForm">
        <el-form-item label="id" >
          <el-input v-model="ruleForm.id" size="small"  placeholder="아이디를 입력해주세요"></el-input>
        </el-form-item>
        
        <el-form-item label="Password" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off" placeholder="패스워드를 입력해주세요"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="loginSumit('ruleForm')">Submit</el-button>
          <el-button @click="resetForm('ruleForm')">Cancel</el-button>
        </el-form-item>
      </el-form>
    </div>
  </card>
    </div>
    <div class="col-md-4">
      <card type="user">
        <p class="card-text"></p>
        <div class="author">
          <div class="block block-one"></div>
          <div class="block block-two"></div>
          <div class="block block-three"></div>
          <div class="block block-four"></div>
          <a href="#">
            <img class="avatar" src="img/anime6.png" alt="...">
            <h5 class="title">{{user.fullName}}</h5>
          </a>
          <p class="description">
            {{user.title}}
          </p>
          </div>
        <p class="card-description">
          {{user.description}}
        </p>
        <div slot="footer" class="button-container">
          <base-button icon round class="btn-facebook">
            <i class="fab fa-facebook"></i>
          </base-button>
          <base-button icon round class="btn-twitter">
            <i class="fab fa-twitter"></i>
          </base-button>
          <base-button icon round class="btn-google">
            <i class="fab fa-google-plus"></i>
          </base-button>
        </div>
      </card>
      </div>
   </div>
</template>
<script>
import EditProfileForm from './Profile/EditProfileForm';
import UserCard from './Profile/UserCard'
  export default {
       components: {
      EditProfileForm,
      UserCard
    },
  data() {
      return {
         user: {
          fullName: 'Mike Andrew',
          title: 'Ceo/Co-Founder',
          description: `Do not be scared of the truth because we need to restart the human foundation in truth And I love you like Kanye loves Kanye I love Rick Owens’ bed design but the back is...`,
        },
        ruleForm: {
          id: '',
          pass: '',
        },
        token:'',
        setId:'',
      };
    },
    
    methods: { 
      loginSumit(){
        if(this.ruleForm.id == null || this.ruleForm.id ==''){
          alert("아이디를 입력해주세요")
        }else if(this.ruleForm.pass == null || this.ruleForm.pass ==''){
          alert("패스워드를 입력해주세요")
        }else{       
          this.$axios.get('/api/loginCheck',{
            params:{
              id:this.ruleForm.id,
              password:this.ruleForm.pass,
            }
          })
          .then((response)=>{
            console.log(response);
            console.log("============================");

            if(response.data==''||response.data==null){
                alert("아이디 또는 패스워드를 확인해주세요")
              // this.$router.push("/login");
              // 같은 페이지 에러발생, 커서 이동으로 바꾸기
            }
            else{
              alert("로그인 성공")
              this.$store.commit('setId', response.data.id)
              this.$store.getters.local;
              this.$router.push({path:'/'})
            }
          })
        }
      },
      local(){
        localStorage.setItem(localId,this.localId);
      },
      resetForm() {
        this.$router.push({path:'/'})
        // this.$refs[formName].resetFields();    //패스워드 리셋
      },
      initForm(){
        this.id="";
        this.password="";
      }
    }
  }
</script>

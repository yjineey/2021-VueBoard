<template>
  <card>
    <h4 slot="header" class="title" style="color:white;">★ 회원가입 ★</h4>
    <div>
    <el-form ref="ruleForm" :model="ruleForm" status-icon :rules="rules"  style="width: 80%; margin:auto;" class="demo-ruleForm">
      <el-form-item label="이름" >
        <el-input v-model="ruleForm.name" size="small"  placeholder="이름을 입력해주세요"></el-input>
      </el-form-item>
      <el-form-item label="아이디" >
        <el-input v-model="ruleForm.id" size="small"  placeholder="아이디를 입력해주세요" ></el-input>
        <el-button type="info" @click="checkId">ID 중복확인</el-button>
      </el-form-item>
      <el-form-item label="비밀번호" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"  placeholder="패스워드를 입력해주세요"></el-input>
      </el-form-item>
      <el-form-item label="비밀번호 확인" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="패스워드를 확인해주세요"></el-input>
      </el-form-item>

      <el-form-item label="개인정보동의" prop="delivery">
        <el-switch v-model="ruleForm.agree"></el-switch>
      </el-form-item>

      <el-form-item label="성별">
        <el-radio-group v-model="ruleForm.sex">
          <el-radio label="male"></el-radio>
          <el-radio label="female"></el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="직업">
        <el-select v-model="ruleForm.job" placeholder="직업을 선택해주세요" >
          <el-option label="strudent" value="strudent"></el-option>
          <el-option label="officeWorker" value="officeWorker"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="학년" prop="type">
        <el-checkbox-group v-model="ruleForm.grade">
        <el-checkbox label="One" name="1"></el-checkbox>
        <el-checkbox label="Two" name="2"></el-checkbox>
        <el-checkbox label="Three" name="3"></el-checkbox>
        <el-checkbox label="Four" name="4"></el-checkbox>
      </el-checkbox-group>
      </el-form-item>


      <el-form-item>
      <el-button type="primary" @click="onSubmit">Create</el-button>
      <el-button @click="resetForm('ruleForm')">Cancel</el-button>

      </el-form-item> 
    </el-form>
  </div>
  </card>
</template>
<script>
  export default {
    props: {
      model: {
        type: Object,
        default: () => {
          return {};
        }
      }
    },
      data() {
     var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('패스워드를 입력해주세요'));
        }
         else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value == '') {
          callback(new Error('패스워드를 확인해주세요'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('패스워드가 일치하지 않습니다'));
        } else {
          callback();
        }
      };
        
      return {
        ruleForm: {
          id:'',
          name:'',
          pass: '',
          checkPass: '',
          grade: ["One"],
          sex: 'male',
          job: 'student',
          agree: false,
        },
        rules: {
          name: [
            { required: true, message: 'Please input Activity name', trigger: 'blur' },
            { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' }
          ],
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
          job: [
            { required: true, message: 'Please select Activity zone', trigger: 'change' }
          ],
          grade: [
            { type: 'array', required: true, message: 'Please select at least one activity type', trigger: 'change' }
          ],
          sex: [
            { required: true, message: 'Please select activity resource', trigger: 'change' }
          ],
        },
      }
    },
    methods: {
      resetForm() {
        this.$router.push({path:'/'})
        // this.$refs[formName].resetFields();    //패스워드 리셋
      },

      checkId(){
        console.log(this.ruleForm.id)
        if(this.ruleForm.id ==null || this.ruleForm.id ==''){
          alert("아이디를 입력해주세요")
        }else{
          this.$axios.post('/api/loginCheckId',{
            id:this.ruleForm.id
          })
            .then((response)=>{
              console.log(response)
              if(response.data==0){
                alert("사용중인 아이디 입니다.")
              } else{
                alert("사용할 수 있는 아이디 입니다.")
              }
          })
        }
      },
      onSubmit() {
        console.log('submit!');
        if(this.ruleForm.name==''||this.ruleForm.id==''||this.ruleForm.pass==''||this.ruleForm.checkPass==''){
          alert("필수항목을 입력해주세요");
        }else if(this.ruleForm.grade.length>1){
          alert("학년은 1개만 입력해주세요")
        }else{
          this.$axios.post('/api/loginCheckId',{
            id:this.ruleForm.id
          })
            .then((response)=>{
              console.log(response)
            if(response.data==0){
              alert("사용중인 아이디 입니다.")
            } else{
              this.$axios.post('/api/signUp',{
                name:this.ruleForm.name,
                id:this.ruleForm.id,
                password:this.ruleForm.pass,
                agree:this.ruleForm.agree,
                sex:this.ruleForm.sex,
                job:this.ruleForm.job,
                grade:this.ruleForm.grade[0]
              })
            alert("회원가입 완료!! 로그인 해주세요")
            this.$router.push("/login");
            }
          })
        }
      }
    }
  }
</script>

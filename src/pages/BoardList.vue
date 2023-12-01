<template>
  <card>
    <h4 slot="header" class="title" style="color:white;">★ 게시판 ★</h4>
    <div>
      <div >
        <el-input  style="width: 70%; margin:auto; padding:10px;" placeholder="검색어를 입력해주세요" v-model="searchVal"  @keypress.enter="buttonSelect" > </el-input>&nbsp;&nbsp;
        <base-button type="info"  @click="buttonSelect" style="margin:5px">search</base-button>
        <base-button type="primary"  @click="boardWrite"  >Write</base-button>
      </div>
      <div class="button-bar">
          <!-- 그룹짓기 -->
            <!-- 
            <button v-on:click="onAllSelect()">전체그룹</button>
            <button v-on:click="onNoGroups()">그룹해지</button>
            <button v-on:click="onBoardInfo()">BoardInfo</button>
            <button v-on:click="onWriteInfo()">WriteInfo</button>
            <button v-on:click="autoSizeAll(true)">새로고침</button>
              -->
          <!-- 글씨사이즈에 따른정렬  -->
        <base-button type="Warning" style="margin:5px" class="button-bar" @click="getSelectedRows()">선택삭제</base-button>
        <base-button type="Warning" style="margin:5px" v-on:click="autoSizeAll(false)">전체정렬</base-button>
        <base-button type="Warning" style="margin:5px" v-on:click="autoSizeAll(true)">내용정렬</base-button>
      </div>
      <div v-if="openList">
        <ag-grid-vue 
          style="width: 80%; padding:10px;"
          class="ag-theme-alpine"
          :columnDefs="columnDefs"
          :rowData="rowData"
          :gridOptions="gridOptions"
          :defaultColDef="defaultColDef"
          :rowSelection="rowSelection"
          @rowClicked="rowClicked"
          @grid-ready="onGridReady"
        >        
        </ag-grid-vue><br><br>
        <el-pagination
          :background="true"
          :total="total"
          :current-page.sync="currentPage"
          :page-size="limit"
          :page-sizes="[5,10,15,20,30]"
          layout="total, sizes, prev, pager, next"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        >
        </el-pagination>
        <!-- 
          background : 페이징배경
          total : 총 항목 수
          current-page.sync : 현재 페이지 번호, .sync 수정 자 지원
          limit   : 각 페이지의 항목 수, .sync 수정 자 지원   
          page-size.sync : 페이지당 항목 수
          page-sizes : 페이지 당 항목 수 옵션
          auto-scroll : 페이지 매김 후 자동으로 맨 위로 스크롤할지 여부
        -->
      </div>
      <div v-if="openSearch" >
        <ag-grid-vue
          style="width: 80%; padding:10px;"
          class="ag-theme-alpine"
          :columnDefs="columnDefs"
          :rowData="rowData"
          :gridOptions="gridOptions"
          :defaultColDef="defaultColDef"
          :rowSelection="rowSelection"
          @rowClicked="rowClicked"
          @grid-ready="onGridReady"
          >
        </ag-grid-vue><br><br>
        <el-pagination
          :background="true"
          :total="total"
          :current-page.sync="currentPage"
          :page-size="limit"
          :page-sizes="[5,10,15,20,30,]"
          layout="total, sizes, prev, pager, next"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        >
        </el-pagination>
      </div><br><br>
    </div>
  </card>
</template>

<script>
import { AgGridVue } from "ag-grid-vue";
export default {
  name:"boardList",
  components:{
  AgGridVue,
  },
  data(){
    return{
      columnDefs: null,   //열
      rowData: [],       //행

      gridOptions: null,    //gridOptions
      gridApi: null,      //gridOptions.Api
      columnApi: null,      //gridOptions.columnApi
      defaultColDef: null,      //(공통)기본정의

      rowSelection: null,
      checkDelete:[],
      selectDelete:'',

      openList:false,     //전체 리스트
      openSearch:false,   //검색 리스트 
      
      fileList:[],
      viewData:null,     //상세보기 데이터
      searchVal:'',    //검색 파라미터 값

      currentPage: 1,   //현재페이지
      total: 0,        //전체데이터 수
      limit: 10    // 몇 페이지씩 출력

    }
  },
  //data, computed, methods, watch활성화 / 단, dom에 추가되지 x
  created(){
    },
  //Dom생성, 템플릿 있는지 확인 후 렌더링
  beforeMount() {
    // console.log("cookie>>",document.cookie);
    this.gridOptions={};
    this.ag_grid ();
    this.openList=true;
    this.openSearch=false;
  },
  //Dom부착
  mounted(){
    let delay = 1000;
    let timerId = null;

    this.gridApi = this.gridOptions.api;
    this.gridColumnApi = this.gridOptions.columnApi;
    
    console.log("gridApi:::::::::::", this.gridApi);
    window.addEventListener('resize', () => {
      // debounce
      // clearTimeout을 이용하여 이벤트 발생을 무시,
      // 마지막 호출 이후, 일정 시간이 지난 후에 단 한 번만, 이벤트가 호출 = debound
      // Debounce 는 아무리 많은 이벤트가 발생해도 모두 무시하고 특정 시간사이에 어떤 이벤트도 발생하지 않았을 때 딱 한번만 마지막 이벤트를 발생

        // clearTimeout(timerId);      //초기화 해서 timerId를 실행하지 않겠다
        //  timerId = setTimeout(()=>{        //지정한 초 뒤에 실행
        //   console.log('resize event!');
        //   this.gridApi.sizeColumnsToFit();    // ag_grid 열 크기 자동정렬 하기위해 필요
        // }, delay);

    // throttle
    // Throttle 의 설정시간으로 1ms 를 주게되면 해당 이벤트는 1ms 동안 최대 한번만 발생하게 됩니다.
    // 마지막 함수가 호출된 후 일정 시간이 지나기 전에 다시 호출되지 않도록 하는 것
      if (!timerId) {
        timerId = setTimeout(()=>{      
          console.log('resize event!');
          this.gridApi.sizeColumnsToFit();  
          timerId = false;
      }, delay);
    }});
  },

  methods: {
    //게시글 상세보기
    rowClicked(row) {
      console.log("======게시글리스트=======")
      this.$store.commit('getBno', row.data.id)
      this.$axios.get('/api/boardView/'+row.data.id)
      .then((response)=>{
        console.log("response>>",response)
        this.$router.push({path:'/boardView' ,query: {
          id:row.data.id
        }})
      }).catch(function (e) {
        console.error(e)                
      })
    },

    // javascript의 onload 이벤트와 유사한 것으로 ready 이 후 필요한 이벤트를 삽입한다. 보통 그리드 자동 사이즈 등을 지정한다.  
    onGridReady(params) {
      const updateData = (data) => {
      this.rowData = data;
      // this.gridApi.sizeColumnsToFit();    // 열 크기 자동정렬
      }
    },

    //리스트 출력
    ag_grid(){
      this.gridOptions = {};
      this.columnDefs = [
        { headerName: 'boardInfo',
          children:[
            { headerName: "글번호", field: 'id' ,  },
            { headerName: "제목", field: 'title', },
        ]},
        { headerName: 'writeInfo',
          children:[
            { headerName: "작성자", field: 'writer' ,  resizable:false, },
            { headerName: "작성일", field: 'reg_date', resizable:false, },
            { headerName: "파일", field: 'fileCount', },
          ]  
        }
      ];
      this.defaultColDef = {
        flex: 1,
        minWidth: 100,
        // maxWidth: 150,
        resizable: true,      //열 크기 조절
        headerCheckboxSelection: this.isFirstColumn,  //header 셀렉트박스
        checkboxSelection: this.isFirstColumn,      //셀 checkbox
      };
      this.rowSelection = 'multiple';
      this.pageList();
    },

    pageList(){
      // this.totalCount();
      this.$axios.get('/api/perPageCount', {
        params:{
          pageStart : (this.currentPage-1)*this.limit,
          perPageNum: this.limit,
        }
      })
      .then(response=>{
        console.log(response)
          console.log(this.rowData)
          this.total=response.data.boardCount;
          this.rowData = response.data.boardList;
          // 2개의 데이터를 같이 받는다
        })
      .catch(() => {
        alert('에러가  발생했습니다.');
      })
    },

    //검색버튼
    buttonSelect(){
      this.detailFlag=false;
      if (this.searchVal==''||this.searchVal==null) {
        this.pageList();
      } else {
          this.boardSearch();
      }
    },

    // 글 검색
    boardSearch(){
      this.$axios.get('/api/boardSearch',{
        params:{
          searchVal:this.searchVal,
          pageStart : (this.currentPage-1)*this.limit,
          perPageNum: this.limit,
       }
      })
      .then(response=>{
        console.log(response);
          console.log(response.data.searchList);
          console.log(response.data.searchCount);
          
          this.total=response.data.searchCount;
          this.rowData = response.data.searchList;

        this.openList=false;
        this.openSearch=true;
      }).catch(() => {
        alert('boardSearch 에러');
        })
      },

    handleSizeChange(val) {
      console.log(`${val} items per page`);   //페이지당 몇개
      this.limit=val;
        if(this.searchVal!=''||this.searchVal!=null){
          this.boardSearch();
        }else{
            this.pageList();
        }
    },

    handleCurrentChange(val) {
      this.currentPage=val;
        console.log(`current page: ${this.currentPage}`);    //현재페이지는
        if(this.searchVal!=''||this.searchVal!=null){
          this.boardSearch();
        }else{
            this.pageList();
        }
    },

    // boardListAll(){
    //   this.$axios.get('/api/boardListAll')
    //   .then(response=>{
    //       this.rowData = response.data;
    //       this.rowData= this.rowData.slice(this.currentPage-1,this.limit);
    //       this.total= response.data.length;
    //     })
    //   .catch(() => {
    //     alert('에러가  발생했습니다.');
    //   })
    // },

    //글작성버튼
    boardWrite(){
      this.$router.push({path:'/boardWrite'})
    },

    //BoardView에서 홈화면으로 넘어올때 ag_grid출력
    detailDelete(){
      this.ag_grid();
    },
    
    // 선택삭제
    getSelectedRows(){
      console.log(this.gridApi)
      console.log(this.gridApi.getSelectedRows())

      this.checkDelete=this.gridApi.getSelectedRows()
      console.log(this.checkDelete)
      console.log(this.checkDelete[0].id)

          for(let i=0; i < this.checkDelete.length; i++){
            this.selectDelete=this.checkDelete[i].id
            this.$axios.delete('/api/boardDelete2',{
              params:{
                id:this.selectDelete
              }
            })
          }
        this.pageList();
    },
    
    //헤더 그룹화하여 표시
    onAllSelect(){
      this.columnDefs = [
        {headerName: 'boardInfo',
          children:[
            {  headerName: "글번호", field: 'id' ,  },
            {  headerName: "제목",  field: 'title',  },
        ]
        },
        {  headerName: 'writeInfo',
          children:[
            {  headerName: "작성자",  field: 'writer' ,   },
            {  headerName: "작성일",  field: 'reg_date',  },
            { headerName: "파일", field: 'orgFileName', },
          ]  
        }];
      this.gridApi.setColumnDefs(columnDefs);
    },
    onNoGroups(){
      this.columnDefs = [
        {  headerName: "글번호", field: 'id' ,  },
        {  headerName: "제목",  field: 'title',  },
        {  headerName: "작성자",  field: 'writer' ,   },
        {  headerName: "작성일",  field: 'reg_date',  }
      ]
      this.gridApi.setColumnDefs(columnDefs);
    },
    onBoardInfo(){
      this.columnDefs = [
        {headerName: 'boardInfo',
          children:[
            {  headerName: "글번호", field: 'id' ,  },
            {  headerName: "제목",  field: 'title',  },
        ]
        },
        {  headerName: "작성자",  field: 'writer' ,   },
        {  headerName: "작성일",  field: 'reg_date',  },
        { headerName: "파일", field: 'orgFileName', },

      ]  
      this.gridApi.setColumnDefs(columnDefs);
    },
    onWriteInfo(){
      this.columnDefs = [
        {  headerName: "글번호", field: 'id' ,  },
        {  headerName: "제목",  field: 'title',   },
        {  headerName: 'writeInfo',
          children:[
            {  headerName: "작성자",  field: 'writer' ,   },
            {  headerName: "작성일",  field: 'reg_date',  },
            { headerName: "파일", field: 'orgFileName', },
          ]
        }
      ]
      this.gridApi.setColumnDefs(columnDefs);
    },

    // 글씨 사이즈에 따른 정렬
    autoSizeAll(skipHeader) {
      let allColumnIds = [];
      this.gridColumnApi.getAllColumns().forEach(function (column) {
        allColumnIds.push(column.colId);
      })
      this.gridColumnApi.autoSizeColumns(allColumnIds, skipHeader);
    },

    // 첫번째컬럼 checkbox
    isFirstColumn(params) {
      let displayedColumns = params.columnApi.getAllDisplayedColumns();
      let thisIsFirstColumn = displayedColumns[0] === params.column;
      return thisIsFirstColumn;
    },
  }
}
</script>
<style scoped>
  .wrap_padding{
    min-width: 350px;
    width: 100%;
    margin: auto;
    margin-bottom: auto;
    padding:10px;
    margin-bottom: 1rem;
  }
  .button-bar {
  margin:2px;
  }
  
  .test-header {
    font-family: Verdana, Geneva, Tahoma, sans-serif;
    font-size: 13px;
    margin-bottom: 1rem;
  }
</style>
// .ag-root-wrapper-body.ag-layout-normal : 2개의 class를 같이 사용하는 곳에만 
// .ag-root-wrapper-body .ag-layout-normal : 하위클래스
// .ag-root-wrapper-body, ag-layout-normal : 각각 클래스 / 다른곳에서 사용하는 css를 가져다 쓸때는 style 따로 분리해서 적용
<style>
  .ag-root-wrapper-body.ag-layout-normal {
    min-height: 100px;
    height:100%;
  }
</style>
<template>
  <div class="wrapper">
    <side-bar>
        <template slot="links"  v-if="isUserLogin" >
        <sidebar-link to="/dashboard" :name="$t('sidebar.dashboard')" icon="tim-icons icon-chart-pie-36"/>
        <sidebar-link to="/BoardList" :name="$t('sidebar.BoardList')" icon="tim-icons icon-puzzle-10"/>
        <sidebar-link to="/BoardWrite" :name="$t('sidebar.BoardWrite')" icon="tim-icons icon-pin"/>
        </template>

      <template slot="links" v-else>
        <sidebar-link to="/dashboard" :name="$t('sidebar.dashboard')" icon="tim-icons icon-chart-pie-36"/>
        <sidebar-link to="/BoardList" :name="$t('sidebar.BoardList')" icon="tim-icons icon-puzzle-10"/>
        <sidebar-link to="/BoardWrite" :name="$t('sidebar.BoardWrite')" icon="tim-icons icon-pin"/>
        <sidebar-link to="/SignUp" :name="$t('sidebar.SignUp')" icon="tim-icons icon-single-02"/>
        <sidebar-link to="/Login" :name="$t('sidebar.Login')" icon="tim-icons icon-bell-55"/>
        <!-- <sidebar-link to="/typography" :name="$t('sidebar.typography')" icon="tim-icons icon-align-center"/> -->
        <!-- <sidebar-link to="/dashboard?enableRTL=true" :name="$t('sidebar.rtlSupport')" icon="tim-icons icon-world"/> -->
      </template>

    </side-bar>
    <div class="main-panel">
      <top-navbar></top-navbar>

      <dashboard-content @click.native="toggleSidebar">

      </dashboard-content>

      <content-footer></content-footer>
    </div>
  </div>
</template>
<style lang="scss">
</style>
<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "./MobileMenu";
  import login from "@/pages/Login.vue"

export default {
  components: {
    TopNavbar,
    ContentFooter,
    DashboardContent,
    MobileMenu,
    login
  },
      computed:{
      isUserLogin() {
        return this.$store.getters.isUserLogin;
      },
    },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  }
};
</script>

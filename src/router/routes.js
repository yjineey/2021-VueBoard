import DashboardLayout from "@/layout/dashboard/DashboardLayout.vue";
// GeneralViews
import NotFound from "@/pages/NotFoundPage.vue";

// Admin pages
const Dashboard = () => import(/* webpackChunkName: "dashboard" */"@/pages/Dashboard.vue");
const BoardList = () => import(/* webpackChunkName: "common" */ "@/pages/BoardList.vue");
const BoardWrite = () => import(/* webpackChunkName: "common" */ "@/pages/BoardWrite.vue");
const BoardView = () => import(/* webpackChunkName: "common" */ "@/pages/BoardView.vue");
const BoardUpdate = () => import(/* webpackChunkName: "common" */ "@/pages/BoardUpdate.vue");
const SignUp = () => import(/* webpackChunkName: "common" */ "@/pages/SignUp.vue");
const Login = () => import(/* webpackChunkName: "common" */"@/pages/Login.vue");
const LogOut = () => import(/* webpackChunkName: "common" */"@/pages/BoardList.vue");
const Icons = () => import(/* webpackChunkName: "common" */ "@/pages/Icons.vue");
const Typography = () => import(/* webpackChunkName: "common" */ "@/pages/Typography.vue");

const routes = [
  {
    path: "/",
    component: DashboardLayout,
    redirect: "/BoardList",
    children: [
      {
        path: "dashboard",
        name: "dashboard",
        component: Dashboard
      },
      {
        path: "BoardList",
        name: "BoardList",
        component: BoardList
      },
      {
        path: "BoardWrite",
        name: "BoardWrite",
        component: BoardWrite
      },
      {
        path: "BoardView",
        name: "BoardView",
        component: BoardView
      },
      {
        path: "BoardUpdate",
        name: "BoardUpdate",
        component: BoardUpdate
      },
      {
        path: "Login",
        name: "Login",
        component: Login
      },
      {
        path: "LogOut",
        name: "LogOut",
        component: LogOut
      },
      {
        path: "SignUp",
        name: "SignUp",
        component: SignUp
      },
      {
        path: "icons",
        name: "icons",
        component: Icons
      },
      {
        path: "Typography",
        name: "Typography",
        component: Typography
      },
    ]
  },
  { path: "*", component: NotFound },
];

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes;

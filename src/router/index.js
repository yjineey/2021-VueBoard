import VueRouter from "vue-router";
import routes from "./routes";
import login from './login';
// configure router
const router = new VueRouter({
  mode:'history',
  routes, // short for routes: routes
  linkExactActiveClass: "active",
  login,
  scrollBehavior: (to) => {
    if (to.hash) {
      return {selector: to.hash}
    } else {
      return { x: 0, y: 0 }
    }
  }
});

export default router;

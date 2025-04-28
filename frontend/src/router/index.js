import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserList from '../views/UserList.vue'
import BusinessList from "@/views/BusinessList.vue";
import ExperienceList from "@/views/ExperienceList.vue";
import AdminReviewList from "@/views/AdminReviewList.vue";

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/admin/users',
        name: 'UserList',
        component: UserList //
    },
    {
        path: '/admin/businesses',
        name: 'BusinessList',
        component: BusinessList //
    },

    {
        path: '/admin/Experiences',
        name: 'Applications',
        component: ExperienceList //
    },
    {
        path: '/admin/reviews',
        name: 'AdminReviewList',
        component: AdminReviewList //
    }


]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router

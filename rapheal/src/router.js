import {createRouter, createWebHistory} from "vue-router";

import NotFound from "@/pages/NotFound";


const router = createRouter({
    history: createWebHistory(),
    routes: [

        {path: '/:notFound(.*)', component: NotFound},
    ],
});

export default router;
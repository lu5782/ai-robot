//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
        '<li class="layui-nav-item">',
        '<a v-if="item.type === 0" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        ' <cite>{{item.name}}</cite>',
        '</a>',
        '<dl v-if="item.type === 0" class="layui-nav-child">',
        '<dd v-for="item in item.list" >',
        '<a v-if="item.type === 1" :lay-href="item.url">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        ' <cite>{{item.name}}</cite>',
        '</a>',
        '</dd>',
        '</dl>',
        '<a v-if="item.type === 1" :lay-href="item.url">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        ' <cite>{{item.name}}</cite>',
        '</a>',
        '</li>'
    ].join('')
});

//注册菜单组件
Vue.component('menuItem', menuItem);

var vm = new Vue({
    el: '#LAY_app',
    data: {
        user: {}
        , menuList: {}
    },
    methods: {
        getMenuList: function (event) {
            $.getJSON("sys/menu/nav?_" + $.now(), function (r) {
                vm.menuList = r.menuList;
            });
        },
        getUser: function () {
            $.getJSON("sys/user/info?_" + $.now(), function (r) {
                vm.user = r.user;
            });
        }
    },
    created: function () {
        this.getUser();
        this.getMenuList();
    },
    updated: function () {
        layui.use('element', function () {
            console.log("开始渲染");
            var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
            element.render('nav', 'layadmin-system-side-menu');
        });
    }
});


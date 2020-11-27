import App from './pages/App.vue'
import Vue from 'vue'
import VueResource from 'vue-resource'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import Vuelidate from 'vuelidate'

Vue.use(Vuelidate)
Vue.use(Vuetify)
Vue.use(VueResource)

new Vue({
    el: '#app',
    render: a => a(App)
})
// // id_1  name_1
// // id_2  name_2
// // id_3  name_3
//
// import "../js/components/DetailsList";

// function getIndex(list, id) {
//     for (var i = 0; i < list.length; i++) {
//         if (list[i].id === id) {
//             return i;
//         }
//     }
//
//     return -1;
// }
//
// Vue.component("details-form", {
//     props: ['details'],
//     data: function () {
//         return {
//             detailName: '',
//             type: '',
//             production: '',
//             quantityOfAll: '',
//             price: '',
//             storage: ''
//         }
//     },
//     template:
//         '<div>' +
//         '<input type="text" placeholder="Detail name" v-model="detailName" />' + '</br>' +
//         '<input type="text" placeholder="Type" v-model="type" />' + '</br>' +
//         '<input type="text" placeholder="Production" v-model="production" />' + '</br>' +
//         '<input type="text" placeholder="Quantity" v-model="quantityOfAll" />' + '</br>' +
//         '<input type="text" placeholder="Price" v-model="price" />' + '</br>' +
//         '<input type="text" placeholder="Storage" v-model="storage" />' + '</br>' +
//         '<input type="button" value="Save" @click="save" />' + '</br>' +
//         '</div>',
//     methods: {
//         save: function () {
//             var detail = {
//                 detailName: this.detailName,
//                 type: this.type,
//                 production: this.production,
//                 quantityOfAll: this.quantityOfAll,
//                 price: this.price,
//                 storage: this.storage
//             }
//             Vue.resource('/details').save({}, detail).then(result =>
//                 result.json().then(data => {
//                     this.details.push(data);
//                     this.text = ''
//                 })
//             )
//         }
//     }
// });
//
// Vue.component('detail-delete', {
//     props: ['idDetail', 'details','detail'],
//     template: '<input type="button" value="Delete" @click="del"/>',
//     methods: {
//         del: function () {
//             Vue.resource('/details{/id}').remove({id: '222a'}).then(result => {
//                 if (result.ok) {
//                     this.details.splice(this.details.indexOf(this.detail), 1)
//                 }
//                 if(result.statusCode===404){
//                     alert("aegaegasg");
//                 }
//             }, e => {
//                 alert(e.status);
//             })
//         }
//     }
//
// });
//
// Vue.component('detail-row', {
//     props: ['detail', 'details'],
//     template: '<div>' +
//         '{{detail.id}}' +
//         '{{detail.type}}' +
//         '{{detail.production}}' +
//         '{{detail.quantityOfAll}}' +
//         '{{detail.price}}' +
//         '{{detail.storage}}' +
//         '<detail-delete :idDetail="detail.id" :details="details" :detail="detail"/>' +
//         '</div>'
// });
//
//
// Vue.component('details-list', {
//     props: ["details"],
//     template:
//         '<div>' +
//         '<detail-row v-for="detail in details" :detail="detail" :details="details" :key="detail.id">' +
//         '<detail-update :detailId="detail.id"/>' +
//         '</detail-row>' +
//         '</div>',
//     created: function () {
//         Vue.resource('/details').get().then(result =>
//             result.json().then(rows =>
//                 rows.forEach(row => {
//                     this.details.push(row);
//                 })
//             )
//         )
//     }
// })
//
// let app = new Vue({
//     el: "#appa",
//     template: '<div>' +
//         '<details-form :details="details"/> ' +
//         '<details-list :details="details"/>' +
//         '</div>',
//     data: {
//         details: []
//     }
// });

// new Vue({
//     el: '#main2',
//     data() {
//         return {
//             all_comment:1,
//             comments: [{
//                 name: '妖怪',
//                 create_time: "2010-10-1",
//                 content: "show time!",
//             }
//             ]
//         }
//     }, methods: {
//         setComment() {
//             this.searchParams = new URLSearchParams(window.location.search);
//             const id = this.searchParams.get('id');
//             axios({
//                 method: "get",
//                 url: "/comment/add",
//                 params: {
//                     user_id:
//                     type: 1，
//                 }
//             }).then(ress=>{
//                 this.comments = ress.data.data;
//                 console.log(this.comments);
//                 this.getAllComments();
//         //
//             })
//             this.getAllComments();
//         }
//         ,
//         getAllComments() {
//             axios({
//                 method: "get",
//                 url: "/comment/gets",
//                 params: {
//                     type: 1,
//                     user_id:1,
//                 }
//             }).then(res => {
//                 this.comments = res.data.data;
//                 console.log(this.comments);
//             })
//         }
//     }, mounted() {
//         this.getAllComments();
//     }
// })
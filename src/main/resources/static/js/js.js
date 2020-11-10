

Vue.component('messages-list', {
    props:["messages"],
    template:
                  '<message-row v-for="message in messages">' +
                       ' <div>{{message.id}} {{message.text}}</div>  ' +
                  '</message-row>'
});

let app=new Vue({
    el:"#app",
    template:'<messages-list :messages="messages"/>',
    data:{
        messages: [
            {id:"1",text:"text-1"},
            {id:"2",text:"text-2"},
            {id:"3",text:"text-3"}
        ]
    }
});

// <div>
//     <message-row v-for="message in messages">\
//         <div>{{message.id}} {{message.text}}</div>
//     </message-row>
// </div>
//
//
//
//
// let app=new Vue({
//     el:"#app",
//     template: '\n' +
//         '<div>\n' +
//         '    <message-row v-for="message in messages">\\\n' +
//         '        <div>{{message.id}} {{message.text}}</div>\n' +
//         '    </message-row>\n' +
//         '</div>',
//     data:{
//         messages: [
//             {id:"1",text:"text-1"},
//             {id:"2",text:"text-2"},
//             {id:"3",text:"text-3"}
//         ]
//     }
// });
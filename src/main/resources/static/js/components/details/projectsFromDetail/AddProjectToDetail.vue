<template>
  <div>
    <v-btn @click="submit" outline small flat round color="indigo" class="my-3">submit</v-btn>
    <div class=" font-weight-thin title">
      Quantity of details, which available:
      <span class="font-weight-medium" style="color: red">
        {{quantityOfAvailable.quantity}}
      </span>
    </div>
  <v-layout align-start justify-center row fill-height>
    <div  v-for="(project, index) in projectsWhichNotIncludeInDetail">
      <add-project-to-detail-card :detail="detail" :quantityOfAvailable="quantityOfAvailable" :index="index" :projectWhichChose="projectWhichChose" :project="project" :projectsWhichNotIncludeInDetail="projectsWhichNotIncludeInDetail"/>
    </div>
  </v-layout>
  </div>
</template>

<script>
import {getIndex} from "util/collections";
import AddProjectToDetailCard from 'components/details/projectsFromDetail/AddProjectToDetailCard.vue'

export default {
  props: ['projects', 'detail'],
  components: {AddProjectToDetailCard},
  data: function () {
    return {
      projectsWhichNotIncludeInDetail: [],
      projectWhichChose:{
        projectsId:[],
        quantity:[]
      },
      quantityOfAvailable:{
        quantity: this.detail.quantityOfAvailable
      },
      // quantityArray: this.projectWhichChose.quantity,
      // projectArray: this.projectWhichChose.projects,
      // quantityArray: this.projectWhichChose.quantity,
    }
  },
  watch: {
    projectWhichChose: {
      deep: true,
      // We have to move our method to a handler field
      handler: function () {
        alert("feaf")
        //   let finalQuantity = 0
        //   let i = 0;
        //   this.projectWhichChose.projectsId.forEach(project => {
        //     if (typeof project !== null) {
        //       finalQuantity += this.projectWhichChose.quantity[i]
        //     }
        //     i++
        //   })
        //   this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - finalQuantity
        // }
      }
    }
  },
    // quantityArray: {
    //   deep: true,
    //   // We have to move our method to a handler field
    //   handler: function () {
    //     let finalQuantity = 0
    //     let i = 0;
    //     this.projectWhichChose.projects.forEach(project => {
    //       if (typeof project !== null) {
    //         finalQuantity += this.projectWhichChose.quantity[i]
    //       }
    //       i++
    //     })
    //     this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - finalQuantity
    //   }
    // }
    // projectArray: {
    //   deep: true,
    //   // We have to move our method to a handler field
    //   handler: function () {
    //     let finalQuantity = 0
    //     let i = 0;
    //     this.projectWhichChose.projects.forEach(project => {
    //       if (typeof project !== null) {
    //         finalQuantity += this.projectWhichChose.quantity[i]
    //       }
    //       i++
    //     })
    //     this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - finalQuantity
    //   }
    // }


  methods:{
    submit:function (){
      let finalQuantity = 0
      let i = 0;
      this.projectWhichChose.projects.forEach(project => {
        if(typeof project !== null){
            finalQuantity+=this.projectWhichChose.quantity[i]
        }
        i++
      })
      alert(finalQuantity)
    }
  },
  created: function () {
    this.$resource('/projects').get().then(result =>
        result.json().then(projectsFromDb => {
              this.projects.forEach(project => {
                projectsFromDb.splice(getIndex(projectsFromDb, project.id), 1)
              })
              this.projectsWhichNotIncludeInDetail = projectsFromDb
              this.projectsWhichNotIncludeInDetail.sort((a, b) => -(a.id - b.id));
            }
        )
    )
  }
}
</script>

<style>

</style>
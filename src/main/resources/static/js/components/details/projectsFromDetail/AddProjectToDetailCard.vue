<template>
  <v-card class="card ">
    <v-card-text primary-title>

      <v-checkbox v-bind:value="project.id" v-model="projectWhichChose.projectsId[index]" hide-details></v-checkbox>
      <!--        <v-text-field :disabled="!projectWhichChose.projects[index]" v-model="projectWhichChose.quantity[index]" label="Quantity of details"></v-text-field>-->
      <!--      <v-slider :disabled="!projectWhichChose.projects[index]" v-model="projectWhichChose.quantity[index]" :min="0" :max="50">-->
      <!--      </v-slider>-->
      <v-slider
          v-model="projectWhichChose.quantity[index]"
          :disabled="!projectWhichChose.projectsId[index]"
          label="Quantity of details"
          color="deep-orange lighten-2"
          thumb-label="always"
          :min="0"
          :max="200"
      ></v-slider>

      <b>Id:</b>
      {{ project.id }} <br>

      <b>Project name:</b>
      {{ project.projectName }} <br>

      <b>Type:</b>
      {{ project.type }} <br>

      <b>Quantity:</b>
      {{ project.quantity }} <br>

      <b>Storage:</b>
      {{ project.storage }} <br>

    </v-card-text>
    <v-card-actions>
    </v-card-actions>
  </v-card>
</template>

<script>
import {getIndex} from "util/collections";

export default {
  props: ['projectsWhichNotIncludeInDetail', 'project', 'projectWhichChose', 'index', 'quantityOfAvailable', 'detail'],
  data: function () {
    return {
      enabled: false,
      number: {},
      // quantityArray: this.projectWhichChose.quantity,
      projectsId: this.projectWhichChose.projectsId,
      // quantityArray: this.projectWhichChose.quantity,
    }
  },
  watch: {
    // quantityArray: {
    //   deep: true,
    //   // We have to move our method to a handler field
    //   handler: function () {
    //     let finalQuantity = 0
    //     let i = 0;
    //     this.projectWhichChose.projectsId.forEach(project => {
    //       if (typeof project !== null) {
    //         finalQuantity += this.projectWhichChose.quantity[i]
    //       }
    //       i++
    //     })
    //     this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - finalQuantity
    //   }
    // },
    // projectsId: {
    //   deep: true,
    //   // We have to move our method to a handler field
    //   handler: function () {
    //
    //     let finalQuantity = 0
    //     let i = 0;
    //     this.projectWhichChose.quantity.forEach(quantity => {
    //       alert(quantity)
    //       if (quantity > 0 && typeof this.projectsId[i] === null) {
    //         this.quantityOfAvailable.quantity -= quantity
    //       } else {
    //         if (quantity > 0 && typeof this.projectsId[i] !== null) {
    //           this.quantityOfAvailable.quantity += quantity
    //         }
    //         else{
    //           this.projectWhichChose.projectsId[this.index]=null
    //         }
    //         i++
    //       }
    //     })
    //   }
    // }
  },

// this.projectWhichChose.projectsId.forEach(project => {
//   if (typeof project !== null) {
//     if (i >= this.projectWhichChose.quantity) {
//       finalQuantity += 0
//     } else if (this.projectWhichChose.quantity[i] > 0) {
//       this.quantityOfAvailable.quantity -= this.projectWhichChose.quantity[i]
//     } else {
//       this.quantityOfAvailable.quantity += this.projectWhichChose.quantity[i]
//     }
//   }
//   i++
// })
// this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - finalQuantity
//   }
// }
// },
methods: {
  addProjectsToDetail: function () {

  }
}
}
</script>

<style>

</style>
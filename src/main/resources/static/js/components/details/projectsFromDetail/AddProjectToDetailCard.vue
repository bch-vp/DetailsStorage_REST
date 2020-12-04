<template>
  <v-card class="card ">
    <v-card-text primary-title>

      <v-checkbox v-bind:value="project" v-model="projectWhichChose.projects[index]" hide-details></v-checkbox>
      <!--        <v-text-field :disabled="!projectWhichChose.projects[index]" v-model="projectWhichChose.quantity[index]" label="Quantity of details"></v-text-field>-->
      <!--      <v-slider :disabled="!projectWhichChose.projects[index]" v-model="projectWhichChose.quantity[index]" :min="0" :max="50">-->
      <!--      </v-slider>-->
      <v-slider
          v-model="projectWhichChose.quantity[index]"
          :disabled="!projectWhichChose.projects[index]"
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
      projectArray: this.projectWhichChose.projects,
      quantityArray: this.projectWhichChose.quantity,
    }
  },
  watch: {
    quantityArray: {
      deep: true,
      // We have to move our method to a handler field
      handler: function () {
        let finalQuantity = 0
        let i = 0;
        this.projectWhichChose.projects.forEach(project => {
          if (typeof project !== null) {
            finalQuantity += this.projectWhichChose.quantity[i]
          }
          i++
        })
        this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - finalQuantity
      }
    },
    projectArray: {
      deep: true,
      // We have to move our method to a handler field
      handler: function () {
        let finalQuantity = 0
        let i = 0;
        this.projectWhichChose.projects.forEach(project => {
          if (typeof project === null) {
            finalQuantity += this.projectWhichChose.quantity[i]
          }
          i++
        })
        this.quantityOfAvailable.quantity = this.detail.quantityOfAvailable - finalQuantity
      }
    }
  },
  methods: {
    addProjectsToDetail: function () {

    }
  }
}
</script>

<style>

</style>
<template>
  <v-card class="card ">
    <v-card-text primary-title>

      <v-checkbox v-bind:value="project.id" v-model="projectWhichChose.projectsId[index]" hide-details></v-checkbox>
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
      projectsId: this.projectWhichChose.projectsId,
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
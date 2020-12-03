<template>
  <v-card class="card ">
    <v-card-text primary-title>

      <v-checkbox v-bind:value="project" v-model="projectWhichChose.projects[index]" hide-details ></v-checkbox>
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

      <button @click="number.numberr++">
        hello
      </button>

        <b>length:</b>
        {{ projectsWhichNotIncludeInDetail.length }} <br>

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
export default {
  props: ['projectsWhichNotIncludeInDetail', 'project', 'projectWhichChose', 'index'],
  data: function () {
    return {
      enabled: false,
      number:{
        
      }
    }
  },
  methods: {
    addProjectsToDetail: function () {

    }
  },
}
</script>

<style>

</style>
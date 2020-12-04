<template>
  <v-card class="card ">
    <v-card-text primary-title>
      <v-slider
          style="padding-top: 15px"
          v-model="projectWhichChose.quantity[index]"
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
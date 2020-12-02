<template>
  <v-layout align-start justify-center row fill-height>
    <div v-for="project in projects">
      <v-card class="card ">
        <v-card-text primary-title>

          <v-checkbox v-model="enabled" hide-details class="shrink mr-2"></v-checkbox>
          <v-text-field :disabled="!enabled" label="Quantity of details"></v-text-field>

          <b style="color: cornflowerblue"> Quantity of details, which used:
            <span style="color: darkblue"> {{ project.quantityInUsed }}</span>
          </b>
          <br><br>

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
    </div>
  </v-layout>
</template>

<script>
export default {
  props: ['projects'],
  methods: {
    addProjectsToDetail: function () {

    }
  },
  data: function () {
    return {
      projectsWhichNotIncludeInDetail: [],
      enabled: false
    }
  },
  created: function () {
    this.$resource('/projects').get().then(result =>
        result.json().then(projectsFromDb => {
          this.projects.forEach(project => {
            projectsFromDb.splice(projectsFromDb.indexOf(project), 1)
          })
          this. projectsWhichNotIncludeInDetail = projectsFromDb
          this.projectsWhichNotIncludeInDetail.sort((a, b) => -(a.id - b.id));
              //
              // projects.forEach(project => {
              //   this.projectsWhichNotIncludeInDetail.push(project);
              // })
              // this.projectsWhichNotIncludeInDetail.sort((a, b) => -(a.id - b.id));
            }
        )
    )
  }
}
</script>

<style>

</style>
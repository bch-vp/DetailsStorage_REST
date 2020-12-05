<template>
  <v-card class="card lime lighten-2">
    <v-card-text primary-title>
      <b>Id:</b>
      {{ project.id }} <br>
      <v-text-field v-model="projectName"
                    :counter="25"
                    label="Project name"
      ></v-text-field>
      <v-text-field v-model="type"
                    :counter="25"
                    label="Type"
      ></v-text-field>
      <v-text-field v-model="storage"
                    :counter="10"
                    label="Storage"
      ></v-text-field>
    </v-card-text>
    <v-card-actions>
      <v-btn @click="saveEditedProject" outline small fab color="indigo">
        <v-icon>done</v-icon>
      </v-btn>
      <v-btn outline small fab color="indigo" v-on:click="editObject.edit = !editObject.edit">
        <v-icon>clear</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: ["project", 'editObject','projects'],
  data: function () {
    return {
      projectName: this.project.projectName,
      type: this.project.type,
      storage: this.project.storage,
    }
  },
  methods: {
    saveEditedProject: function () {
      if (this.projectName != this.project.projectName ||
          this.type != this.project.type ||
          this.storage != this.project.storage) {
        var projectForUpdate = {
          projectName: this.projectName,
          type: this.type,
          storage: this.storage,
        }
        this.$resource('/projects/' + this.project.id).update(projectForUpdate).then(result =>
            result.json().then(row => {
                  this.projects.splice(this.projects.indexOf(this.project), 1)
                  this.projects.push(row)
                  this.projects.sort((a, b) => -(a.id - b.id));
                  this.editObject.edit = false
                }
            )
        )
      }
      this.editObject.edit = false
    }
  }
}
</script>

<style>

</style>
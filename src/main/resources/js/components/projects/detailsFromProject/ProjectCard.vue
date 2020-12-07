<template>
  <div>
    <b>Project:</b>
    <v-layout align-start justify-space-between row fill-height>
      <v-card class="card lime lighten-2">
        <v-card-text primary-title>

          <b>Id:</b>
          {{ showDetails.project.id }} <br>

          <b>Project name:</b>
          {{ showDetails.project.projectName }} <br>

          <b>Type:</b>
          {{ showDetails.project.type }} <br>

          <b>Storage:</b>
          {{ showDetails.project.storage }}

        </v-card-text>
      </v-card>
    </v-layout>
  </div>
</template>

<script>
export default {
  props: ['showDetails']
}
</script>

<style>

</style>
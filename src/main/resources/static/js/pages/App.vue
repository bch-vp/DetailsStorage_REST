<template>
  <v-app>
    <v-toolbar dark="dark" app>
      <v-toolbar-title>DetailStorage</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn flat>Link One</v-btn>
        <v-btn flat>Link Two</v-btn>
        <v-btn icon href="#/">
          <v-icon>exit_to_app</v-icon>
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>

    <v-content>
      <v-container>
        <details-list :details="details"/>
      </v-container>
    </v-content>

  </v-app>
</template>

<script>

import DetailsList from "components/details/DetailsList.vue";

export default {
  components: {DetailsList},
  comments: {
    DetailsList
  },
  data: function () {
    return {
      details: []
    }
  },
  created: function () {
    this.$resource('/details').get().then(result =>
        result.json().then(rows =>
            rows.forEach(row => {
              this.details.push(row);
            })
        )
    )
  }
}
</script>


<style>
</style>
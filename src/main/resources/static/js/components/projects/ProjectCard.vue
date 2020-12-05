<template>
  <div>
    <v-card class="card lime lighten-2">
      <v-card-text primary-title>
        <b>Id:</b>
        {{ project.id }} <br>

        <b>Project name:</b>
        {{ project.projectName }} <br>

        <b>Type:</b>
        {{ project.type }} <br>


<!--        <b>Quantity:</b>-->
<!--        <span style="color: forestgreen"> {{ project.quantity }}-->
<!--          <v-btn @click="addQuantityOfProjects" outline small flat icon color="indigo">-->
<!--            <v-icon>add</v-icon>-->
<!--          </v-btn>-->
<!--        <v-btn v-if="project.quantity>0" @click="subtractQuantityOfProjects" outline small flat icon-->
<!--               color="indigo">-->
<!--        <v-icon>horizontal_rule</v-icon>-->
<!--        </v-btn>-->
<!--      </span>-->
<!--        <br>-->

<!--&lt;!&ndash;        <b>Quantity:</b>&ndash;&gt;-->
<!--&lt;!&ndash;        <span style="color: red"> {{ project.quantity }} </span> <br>&ndash;&gt;-->

        <b>Storage:</b>
        {{ project.storage }}

      </v-card-text>

      <v-btn round outline small color="deep-purple " @click="showProjectsMethod">
        Show details
      </v-btn>

      <v-card-actions>
        <v-btn outline small fab color="deep-purple" v-on:click="editObject.edit = !editObject.edit">
          <v-icon>edit</v-icon>
        </v-btn>
        <project-delete :projects="projects" :project="project"/>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import ProjectDelete from 'components/projects/ProjectCardDelete.vue'

export default {
  props: ['project', 'editObject', 'projects', 'showDetails'],
  components: {ProjectDelete},
  methods: {
    showProjectsMethod: function () {
      this.showDetails.project = this.project
      this.showDetails.show = !this.showDetails.show
    }
  //   addQuantityOfDetails: function () {
  //     var quantity = 1
  //     this.$resource('/details/' + this.detail.id + '/addQuantity').update(quantity).then(result => {
  //       this.detail.quantityOfAll += 1
  //       this.detail.quantityOfAvailable += 1
  //     }, ex => {
  //       alert(ex.status);
  //     })
  //   },
  //   subtractQuantityOfDetails: function () {
  //     var quantity = 1
  //     if (this.detail.quantityOfAvailable >= quantity) {
  //       this.$resource('/details/' + this.detail.id + '/subtractQuantity').update(quantity).then(result => {
  //         this.detail.quantityOfAll -= 1
  //         this.detail.quantityOfAvailable -= 1
  //       }, ex => {
  //         alert(ex.status);
  //       })
  //     }
  //   }
  }
}
</script>

<style>

</style>
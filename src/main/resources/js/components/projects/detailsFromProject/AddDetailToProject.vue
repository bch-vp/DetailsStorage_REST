<template>
  <div>
    <div class="my-3 text-center">
      <v-btn @click="submit" outline small flat round color="indigo" class="my-3">submit</v-btn>
      <v-btn @click="showAddDetailsToProject.show = false" v-if="showAddDetailsToProject.show" outline small flat round
             color="indigo">
        <v-icon>close</v-icon>
        Close
      </v-btn>
    </div>
    <v-layout align-start justify-center row fill-height>
      <div v-for="(detail, index) in detailsWhichNotIncludeInProject">
        <add-detail-to-project-card :project="project" :index="index"
                                    :detailWhichChose="detailWhichChose" :detail="detail"
                                    :detailsWhichNotIncludeInProject="detailsWhichNotIncludeInProject"/>
      </div>
    </v-layout>
  </div>
</template>

<script>
import {getIndex} from "util/collections";
import AddDetailToProjectCard from 'components/projects/detailsFromProject/AddDetailToProjectCard.vue'

export default {
  props: ['details', 'project', 'showAddDetailsToProject'],
  components: {AddDetailToProjectCard},
  data: function () {
    return {
      detailsWhichNotIncludeInProject: [],
      detailWhichChose: {
        detailId: [],
        quantity: []
      }
    }
  },
  watch: {
    detailWhichChose: {
      deep: true,
      handler: function () {
        let i = 0
        this.detailWhichChose.quantity.forEach(quantity => {
          if (quantity !== 0) {
            this.detailWhichChose.detailId[i] = this.detailsWhichNotIncludeInProject[i]
          } else {
            this.detailWhichChose.detailId[i] = null
          }
          i++
        })
      }
    }
  },
  methods: {
    submit: function () {
      let i = 0
      this.detailWhichChose.detailId.forEach(detailId => {
        if (detailId !== null) {
          let quantityObject = {
            quantity: String(this.detailWhichChose.quantity[i])
          }
          this.$resource('projects/' + this.project.id + '/details/' + detailId.id).save({}, quantityObject).then(result => {
            let indexx = getIndex(this.detailsWhichNotIncludeInProject, detailId.id)
            this.detailsWhichNotIncludeInProject[indexx].quantityOfAvailable -= parseInt(quantityObject.quantity)
            this.detailsWhichNotIncludeInProject[indexx].quantityInUsed = quantityObject.quantity
            this.details.push(this.detailsWhichNotIncludeInProject[indexx])
            this.details.sort((a, b) => -(a.id - b.id));
            this.detailsWhichNotIncludeInProject.splice(indexx, 1)
            this.showAddDetailsToProject.show = false
          }, ex => {
            alert(ex.status);
          })
        }
        i++
      })
    }
  },
  created: function () {
    this.$resource('/details').get().then(result =>
        result.json().then(detailsFromDb => {
              this.details.forEach(detail => {
                detailsFromDb.splice(getIndex(detailsFromDb, detail.id), 1)
              })
              this.detailsWhichNotIncludeInProject = detailsFromDb
              this.detailsWhichNotIncludeInProject.sort((a, b) => -(a.id - b.id));
              let i = 0;
              this.detailsWhichNotIncludeInProject.forEach(detail => {
                if (typeof this.detailWhichChose.detailId[i] !== null) {
                  this.detailWhichChose.quantity[i] = 0
                  this.detailWhichChose.detailId[i] = null
                }
                i++
              })
            }
        )
    )
  }
}
</script>

<style>

</style>
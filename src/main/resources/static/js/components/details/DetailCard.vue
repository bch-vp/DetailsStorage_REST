<template>
  <div>
    <v-card class="card deep-orange lighten-3">
      <v-card-text primary-title>
        <b>Id:</b>
        {{ detail.id }} <br>

        <b>Detail name:</b>
        {{ detail.detailName }} <br>

        <b>Type:</b>
        {{ detail.type }} <br>

        <b>Production:</b>
        {{ detail.production }} <br>

        <b>Quantity of all:</b>
        <span style="color: forestgreen"> {{ detail.quantityOfAll }}
          <v-btn @click="addQuantityOfDetails" outline small flat icon color="indigo">
            <v-icon>add</v-icon>
          </v-btn>
        <v-btn v-if="detail.quantityOfAvailable>0" @click="subtractQuantityOfDetails" outline small flat icon
               color="indigo">
        <v-icon>horizontal_rule</v-icon>
        </v-btn>
      </span>
        <br>

        <b>Quantity of available:</b>
        <span style="color: red"> {{ detail.quantityOfAvailable }} </span> <br>

        <b>Price:</b>
        {{ detail.price }} $<br>

        <b>Storage:</b>
        {{ detail.storage }}

      </v-card-text>

      <v-btn round outline small color="indigo" @click="showProjectsMethod">
        Show projects
      </v-btn>

      <v-card-actions>
        <v-btn outline small fab color="indigo" v-on:click="editObject.edit = !editObject.edit">
          <v-icon>edit</v-icon>
        </v-btn>
        <detail-delete :details="details" :detail="detail"/>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import DetailDelete from 'components/details/DetailCardDelete.vue'

export default {
  props: ['detail', 'editObject', 'details', 'showProjects'],
  components: {
    DetailDelete
  },
  methods: {
    showProjectsMethod: function () {
      this.showProjects.detail = this.detail
      this.showProjects.show = !this.showProjects.show
    },
    addQuantityOfDetails: function () {
      var quantity = 1
      this.$resource('/details/' + this.detail.id + '/addQuantity').update(quantity).then(result => {
        this.detail.quantityOfAll += 1
        this.detail.quantityOfAvailable += 1
      }, ex => {
        alert(ex.status);
      })
    },
    subtractQuantityOfDetails: function () {
      var quantity = 1
      if (this.detail.quantityOfAvailable >= quantity) {
        this.$resource('/details/' + this.detail.id + '/subtractQuantity').update(quantity).then(result => {
          this.detail.quantityOfAll -= 1
          this.detail.quantityOfAvailable -= 1
        }, ex => {
          alert(ex.status);
        })
      }
    }
  }
}
</script>

<style>

</style>
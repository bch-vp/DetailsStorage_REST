<template>
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
      <span style="color: forestgreen"> {{ detail.quantityOfAll }}</span> <br>

      <b>Quantity of available:</b>
      <span style="color: red"> {{ detail.quantityOfAvailable }} </span> <br>

      <b>Price:</b>
      {{ detail.price }} $<br>

      <b>Storage:</b>
      {{ detail.storage }}
    </v-card-text>

    <v-card-actions>
      <v-btn outline small fab color="indigo" v-on:click="editObject.edit = !editObject.edit">
        <v-icon>edit</v-icon>
      </v-btn>
      <detail-delete :details="details" :detail="detail"/>
    </v-card-actions>
  </v-card>
</template>

<script>
import DetailDelete from 'components/details/DetailDelete.vue'

export default {
  props: ['detail', 'editObject','details'],
  components: {
    DetailDelete
  }
}
</script>

<style>

</style>
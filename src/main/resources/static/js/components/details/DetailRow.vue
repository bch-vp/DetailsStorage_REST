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
      {{ detail.price }} <br>

      <b>Storage:</b>
      {{ detail.storage }}
    </v-card-text>

    <v-card-actions>
      <detail-edit :details="details" :detail="detail"/>
      <detail-delete :details="details" :detail="detail"/>
    </v-card-actions>
  </v-card>

</template>

<script>
import DetailDelete from 'components/details/DetailDelete.vue'
import DetailEdit from 'components/details/DetailEdit.vue'

export default {
  components: {DetailDelete, DetailEdit},
  props: ['detail', 'details'],
  comments:{
    DetailDelete,
    DetailEdit
  }
}
</script>

<style>
.card{
  margin-top: 30px;
  margin-right: 30px;
  margin-left: 30px;
  margin-bottom: 30px;
}
</style>
<template>
  <v-card class="card ">
    <v-card-text primary-title>
      <v-slider
          style="padding-top: 15px"
          v-model="detailWhichChose.quantity[index]"
          label="Quantity of details"
          color="lime lighten-1"
          thumb-label="always"
          :min="0"
          :max="detail.quantityOfAvailable"
      ></v-slider>

      <b>Id:</b>
      {{ detail.id }} <br>

      <b>Detail name:</b>
      {{ detail.detailName }} <br>

      <b>Type:</b>
      {{ detail.type }} <br>

      <b>Production:</b>
      {{ detail.production }} <br>

      <b>Quantity of all:</b>
      <span style="color: forestgreen"> {{ detail.quantityOfAll }} </span> <br>

      <b>Quantity of available:</b>
      <span style="color: red"> {{ detail.quantityOfAvailable }} </span> <br>

      <b>Price:</b>
      {{ detail.price }} $<br>

      <b>Storage:</b>
      {{ detail.storage }}

    </v-card-text>
    <v-card-actions>
    </v-card-actions>
  </v-card>
</template>

<script>

export default {
  props: ['detailsWhichNotIncludeInProject', 'detail', 'detailWhichChose', 'index', 'detail'],
  data: function () {
    return {
      detailsId: this.detailWhichChose.detailId,
    }
  },
}
</script>

<style>

</style>
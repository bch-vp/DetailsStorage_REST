<template>
  <v-card class="ma-5">
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
      {{ detail.quantityOfAll }} <br>

      <b>Quantity of available:</b>
      {{ detail.quantityOfAvailable }} <br>

      <b>Price:</b>
      {{ detail.price }} <br>

      <b>Storage:</b>
      {{ detail.storage }}
    </v-card-text>

    <v-card-actions>
      <v-btn outline small fab color="indigo">
        <v-icon>edit</v-icon>
      </v-btn>
      <v-btn outline small fab color="indigo">
        <v-icon>delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>

</template>

<script>
export default {
  props: ['detail', 'details']
}
</script>

<style>

</style>
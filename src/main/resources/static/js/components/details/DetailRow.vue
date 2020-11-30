<template>
  <!--  <v-card class="card deep-orange lighten-3">-->
  <!--    <v-card-text primary-title>-->
  <!--      <b>Id:</b>-->
  <!--      {{ detail.id }} <br>-->


  <!--      <div v-if="!editShow">-->
  <!--        <b>Detail name:</b>-->
  <!--        {{ detail.detailName }} <br>-->
  <!--      </div>-->
  <!--      <div v-else>-->
  <!--        <v-text-field v-model="detailName"-->
  <!--                      :counter="25"-->
  <!--                      label="Detail name"-->
  <!--        ></v-text-field>-->
  <!--      </div>-->

  <!--      <div v-if="!editShow">-->
  <!--        <b>Type:</b>-->
  <!--        {{ detail.type }} <br>-->
  <!--      </div>-->
  <!--      <div v-else>-->
  <!--        <v-text-field v-model="type"-->
  <!--                      :counter="25"-->
  <!--                      label="Type"-->
  <!--        ></v-text-field>-->
  <!--      </div>-->

  <!--      <div v-if="!editShow">-->
  <!--        <b>Production:</b>-->
  <!--        {{ detail.production }} <br>-->
  <!--      </div>-->
  <!--      <div v-else>-->
  <!--        <v-text-field v-model="production"-->
  <!--                      :counter="25"-->
  <!--                      label="Production"-->
  <!--        ></v-text-field>-->
  <!--      </div>-->

  <!--      <b>Quantity of all:</b>-->
  <!--      <span style="color: forestgreen"> {{ detail.quantityOfAll }}</span> <br>-->

  <!--      <b>Quantity of available:</b>-->
  <!--      <span style="color: red"> {{ detail.quantityOfAvailable }} </span> <br>-->

  <!--      <div v-if="!editShow">-->
  <!--        <b>Price:</b>-->
  <!--        {{ detail.price }} $<br>-->
  <!--      </div>-->
  <!--      <div v-else>-->
  <!--        <v-text-field v-model="price"-->
  <!--                      :counter="10"-->
  <!--                      label="Price $"-->
  <!--        ></v-text-field>-->
  <!--      </div>-->

  <!--      <div v-if="!editShow">-->
  <!--        <b>Storage:</b>-->
  <!--        {{ detail.storage }}-->
  <!--      </div>-->
  <!--      <div v-else>-->
  <!--        <v-text-field v-model="storage"-->
  <!--                      :counter="10"-->
  <!--                      label="Storage"-->
  <!--        ></v-text-field>-->
  <!--      </div>-->
  <!--    </v-card-text>-->
  <!--    <v-card-actions>-->
  <!--      <div v-if="!editShow">-->
  <!--        <v-btn outline small fab color="indigo" v-on:click="editShow = !editShow">-->
  <!--          <v-icon>edit</v-icon>-->
  <!--        </v-btn>-->
  <!--        <detail-delete :details="details" :detail="detail"/>-->
  <!--      </div>-->
  <!--      <div v-else>-->
  <!--        <v-btn outline small fab color="indigo">-->
  <!--          <v-icon>done</v-icon>-->
  <!--        </v-btn>-->
  <!--        <v-btn outline small fab color="indigo" v-on:click="editShow = !editShow">-->
  <!--          <v-icon>clear</v-icon>-->
  <!--        </v-btn>-->
  <!--      </div>-->
  <!--    </v-card-actions>-->
  <!--  </v-card>-->
  <div>
<!--    <transition name="fade" mode="out-in">-->
      <detail-card v-if="!editObject.edit" :detail="detail" :editObject="editObject" :details="details"/>
      <detail-card-edit v-if="editObject.edit" :detail="detail" :editObject="editObject"/>
<!--    </transition>-->
  </div>
</template>

<script>
import DetailCard from 'components/details/DetailCard.vue'
import DetailCardEdit from 'components/details/DetailCardEdit.vue'

export default {
  components: {DetailCardEdit, DetailCard},
  props: ['detail', 'details'],
  data: function () {
    return {
      editObject: {
        edit: false
      }
    };
  }
}
</script>

<style>
.card {
  margin-top: 30px;
  margin-right: 30px;
  margin-left: 30px;
  margin-bottom: 30px;
}
</style>
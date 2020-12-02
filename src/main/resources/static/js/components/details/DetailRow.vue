<template>
  <div>
      <detail-card v-if="!editObject.edit" :showProjects="showProjects" :detail="detail" :editObject="editObject" :details="details" />
      <detail-card-edit v-if="editObject.edit" :details="details" :detail="detail" :editObject="editObject"/>
  </div>
</template>

<script>
import DetailCard from 'components/details/DetailCard.vue'
import DetailCardEdit from 'components/details/DetailCardEdit.vue'

export default {
  components: {DetailCardEdit, DetailCard},
  props: ['detail', 'details', 'showProjects'],
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
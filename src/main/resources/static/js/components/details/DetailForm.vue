<template>
  <v-layout align-start justify-center row>
    <form>
      <v-text-field
          v-model="detailName"
          :error-messages="detailNameErrors"
          :counter="25"
          label="Detail name (required)"
          required
          @input="$v.detailName.$touch()"
          @blur="$v.detailName.$touch()"
      ></v-text-field>
      <v-text-field
          v-model="type"
          :error-messages="typeErrors"
          :counter="25"
          label="Type"
          required
          @input="$v.type.$touch()"
          @blur="$v.type.$touch()"
      ></v-text-field>
      <v-text-field
          v-model="production"
          :error-messages="productionErrors"
          :counter="25"
          label="Production"
          required
          @input="$v.production.$touch()"
          @blur="$v.production.$touch()"
      ></v-text-field>
      <v-text-field
          v-model="quantity"
          :error-messages="quantityErrors"
          :counter="10"
          label="Quantity (required)"
          required
          @input="$v.quantity.$touch()"
          @blur="$v.quantity.$touch()"
      ></v-text-field>
      <v-text-field
          v-model="price"
          :error-messages="priceErrors"
          :counter="10"
          label="Price $ (required)"
          required
          @input="$v.price.$touch()"
          @blur="$v.price.$touch()"
      ></v-text-field>
      <v-text-field
          v-model="storage"
          :error-messages="storageErrors"
          label="Storage (required)"
          required
          @input="$v.storage.$touch()"
          @blur="$v.storage.$touch()"
      ></v-text-field>

        <v-btn @click="submit" small flat round color="indigo">submit</v-btn>
        <v-btn @click="clear" outline small fab color="indigo">
          <v-icon>autorenew</v-icon>
        </v-btn>
    </form>
  </v-layout>
</template>

<script>
import {validationMixin} from 'vuelidate'
import {required, maxLength, email, numeric} from 'vuelidate/lib/validators'

export default {
  mixins: [validationMixin],
  props: ['details'],
  validations: {
    detailName: {required, maxLength: maxLength(25)},
    type: {maxLength: maxLength(25)},
    production: {maxLength: maxLength(25)},
    quantity: {required, maxLength: maxLength(10), numeric},
    price: {required, maxLength: maxLength(10), numeric},
    storage: {required},
  },

  data: () => ({
    detailName: '',
    type: '',
    production: '',
    quantity: '',
    price: '',
    storage: '',
  }),

  computed: {
    detailNameErrors() {
      const errors = []
      if (!this.$v.detailName.$dirty) return errors
      !this.$v.detailName.maxLength && errors.push('Detail name must be at most 25 characters long')
      !this.$v.detailName.required && errors.push('Detail name is required.')
      return errors
    },
    typeErrors() {
      const errors = []
      if (!this.$v.type.$dirty) return errors
      !this.$v.type.maxLength && errors.push('Type must be at most 25 characters long')
      return errors
    },
    productionErrors() {
      const errors = []
      if (!this.$v.production.$dirty) return errors
      !this.$v.production.maxLength && errors.push('Production must be at most 25 characters long')
      return errors
    },
    quantityErrors() {
      const errors = []
      if (!this.$v.quantity.$dirty) return errors
      !this.$v.quantity.maxLength && errors.push('Quantity must be at most 10 characters long')
      !this.$v.quantity.required && errors.push('Quantity is required.')
      if (!this.$v.quantity.numeric) {
        errors.push('Quantity not correct')
      }
      return errors
    },
    priceErrors() {
      const errors = []
      if (!this.$v.price.$dirty) return errors
      !this.$v.price.maxLength && errors.push('Price be at most 10 characters long')
      !this.$v.price.required && errors.push('Price is required.')
      if (!this.$v.price.numeric) {
        errors.push('Price not correct')
      }
      return errors
    },
    storageErrors() {
      const errors = []
      if (!this.$v.storage.$dirty) return errors
      !this.$v.storage.required && errors.push('Storage is required.')
      return errors
    },
  },

  methods: {
    submit() {
      this.$v.$touch()
      if (!this.$v.$invalid) {
        var detail = {
          detailName: this.detailName,
          type: this.type,
          production: this.production,
          quantityOfAll: this.quantity,
          price: this.price,
          storage: this.storage
        }
        this.$resource('/details').save({}, detail).then(result =>
            result.json().then(data => {
              this.details.push(data);
              this.clear()
            })
        )
      }
    },
    clear() {
      this.$v.$reset()
      this.detailName = ''
      this.type = ''
      this.production = ''
      this.quantity = ''
      this.price = ''
      this.storage = ''
    }
  }
}

</script>

<style>

</style>
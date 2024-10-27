<template>
    <v-app>
      <v-app-bar app color="#FF9800">
        <v-toolbar-title>Presta Banco: Créditos Hipotecarios</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn to="/registerUser" text>Register Costumer</v-btn>
        <v-btn icon @click="toggleDrawer(true)">
          <v-icon>mdi-menu</v-icon>
        </v-btn>
      </v-app-bar>

    </v-app>
  </template>
  
 
  
  <style scoped>
  /* Puedes agregar estilos personalizados aquí */
  </style>
<template>
    <div id="map" ref="mapElement" style="height: 100vh; width: 100%;"></div>
  </template>
  
  <script>
  import { defineComponent, onMounted, ref } from 'vue';
  import { Loader } from '@googlemaps/js-api-loader';
  
  export default defineComponent({
    setup() {
      const mapElement = ref(null);
      const coordinates = { lat: -33.45075, lng: -70.69092 }; // Coordenadas de prueba
  
      onMounted(async () => {
        const loader = new Loader({
          apiKey: 'AIzaSyC4BWW5STc8lcUBnf7MqufZpNj3bcPBTY0', // Reemplaza con tu clave API
          version: 'weekly',
        });
  
        await loader.load();
  
        if (mapElement.value) {
          const { Map } = await google.maps.importLibrary("maps");
          const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
  
          // Crear el mapa centrado en las coordenadas
          const map = new Map(mapElement.value, {
            zoom: 18,
            center: coordinates,
            mapId: "6bd1e3902d09f191",
          });
  
          // Crear el marcador
          const marker = new AdvancedMarkerElement({
            map: map,
            position: coordinates,
            title: "Ubicación de Prueba",
          });
        } else {
          console.error("Error: El elemento del mapa no está disponible.");
        }
      });
  
      return { mapElement };
    },
  });
  </script>
  
  <style>
  #map {
    height: 100vh; /* Ajusta la altura según sea necesario */
    width: 100%;   /* Ajusta el ancho según sea necesario */
  }
  </style>
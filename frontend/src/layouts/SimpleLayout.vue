<script setup lang="ts"></script>

<template>
  <div class="flex flex-col min-h-screen">
    <div
      class="h-[60px] bg-santaIvory flex justify-center p-8 items-center text-deepGreen sticky top-0 z-10 shadow">
      <router-link to="/">
        <span class="text-2xl font-bold">SANTA</span>
      </router-link>
    </div>
    <div
      class="flex flex-col items-center justify-center flex-1 w-full overflow-auto bg-santaWhite">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
const kakaoUrl = `${import.meta.env.VITE_OAUTH_KAKAO_URL}?response_type=code&client_id=${import.meta.env.VITE_OAUTH_KAKAO_CLIENT_ID}&redirect_uri=${import.meta.env.VITE_OAUTH_KAKAO_REDIRECT_URI}`;
</script>

<template>
  <div class="w-[350px] flex flex-col justify-between h-[35%] gap-20">
    <div class="flex flex-col items-center logoContainer jusify-center">
      <img
        class="w-[160px]"
        src="/santaLogo.png"
        alt="santa" />
      <span class="font-bold text-[50px] tracking-wide text-deepGreen"
        >SANTA</span
      >
    </div>
    <div>
      <a :href="kakaoUrl"
        ><img
          src="/kakao_login_large_wide.png"
          alt="kakao-login"
      /></a>
    </div>
  </div>
</template>

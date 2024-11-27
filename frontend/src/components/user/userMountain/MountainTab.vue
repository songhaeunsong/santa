<script setup lang="ts">
import TabsContent from '../../ui/tabs/TabsContent.vue';
import TabsList from '../../ui/tabs/TabsList.vue';
import TabsTrigger from '../../ui/tabs/TabsTrigger.vue';
import MountainCompleted from './MountainCompleted.vue';
import MountainLike from './MountainLike.vue';
import Tabs from '../../ui/tabs/Tabs.vue';
</script>

<template>
  <Tabs
    default-value="completed"
    class="w-[100%]]">
    <TabsList class="w-[100%] bg-santaWhite p-10 pb-0 rounded-t-2xl">
      <TabsTrigger
        value="completed"
        class="">
        완등
      </TabsTrigger>
      <TabsTrigger value="like"> 관심 </TabsTrigger>
    </TabsList>
    <div class="p-[30px] bg-santaWhite rounded-b-2xl min-h-[38vh]">
      <TabsContent value="completed"> <MountainCompleted /> </TabsContent>
      <TabsContent value="like"> <MountainLike /> </TabsContent>
    </div>
  </Tabs>
</template>

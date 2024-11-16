<script setup lang="ts">
import TabsContent from '../../ui/tabs/TabsContent.vue';
import TabsList from '../../ui/tabs/TabsList.vue';
import TabsTrigger from '../../ui/tabs/TabsTrigger.vue';
import MountainIncompleted from './MountainIncompleted.vue';
import MountainCompleted from './MountainCompleted.vue';
import MountainLike from './MountainLike.vue';
import Tabs from '../../ui/tabs/Tabs.vue';
</script>

<template>
  <Tabs
    default-value="completed"
    class="w-[100%]">
    <TabsList class="w-[100%] bg-santaWhite">
      <TabsTrigger value="completed"> 완등 </TabsTrigger>
      <TabsTrigger value="incompleted"> 진행중 </TabsTrigger>
      <TabsTrigger value="like"> 관심 </TabsTrigger>
    </TabsList>
    <div class="p-[30px]">
      <TabsContent value="completed"> <MountainCompleted /> </TabsContent>
      <TabsContent value="incompleted"> <MountainIncompleted /> </TabsContent>
      <TabsContent value="like"> <MountainLike /> </TabsContent>
    </div>
  </Tabs>
</template>

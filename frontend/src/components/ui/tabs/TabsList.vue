<script setup lang="ts">
import { TabsList, type TabsListProps } from 'radix-vue';
import { computed, type HTMLAttributes } from 'vue';
import { cn } from '../../../lib/utils';

const props = defineProps<
  TabsListProps & { class?: HTMLAttributes['class'] }
>();

const delegatedProps = computed(() => {
  const { class: _, ...delegated } = props;

  return delegated;
});
</script>

<template>
  <TabsList
    v-bind="delegatedProps"
    :class="
      cn(
        'inline-flex items-center justify-center bg-muted p-1 text-santaGreen',
        props.class
      )
    ">
    <slot />
  </TabsList>
</template>

<script setup lang="ts">
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle
} from '../../ui/card';
import { Switch } from '../../ui/switch';
import { cn } from '../../../lib/utils';

const notifications = [
  {
    title: 'Your call has been confirmed.',
    description: '1 hour ago'
  },
  {
    title: 'You have a new message!',
    description: '1 hour ago'
  },
  {
    title: 'Your subscription is expiring soon!',
    description: '2 hours ago'
  }
];
</script>

<template>
  <Card :class="cn('w-full', $attrs.class ?? '')">
    <CardHeader>
      <CardTitle>Notifications</CardTitle>
      <CardDescription>You have 3 unread messages.</CardDescription>
    </CardHeader>
    <CardContent class="grid gap-4">
      <div>
        <div
          v-for="(notification, index) in notifications"
          :key="index"
          class="mb-4 grid grid-cols-[25px_minmax(0,1fr)] items-start pb-4 last:mb-0 last:pb-0">
          <span
            class="flex w-2 h-2 translate-y-1 rounded-full bg-santaLightGreen" />
          <div class="space-y-1">
            <p class="text-sm font-medium leading-none">
              {{ notification.title }}
            </p>
            <p class="text-sm text-muted-foreground">
              {{ notification.description }}
            </p>
          </div>
        </div>
      </div>
      <div class="flex items-center p-4 space-x-4 border rounded-md">
        <div class="flex-1 space-y-1">
          <p class="text-sm font-medium leading-none">이 일정에 참여하기</p>
        </div>
        <Switch />
      </div>
    </CardContent>
  </Card>
</template>

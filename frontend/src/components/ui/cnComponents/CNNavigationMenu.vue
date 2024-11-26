<script setup lang="ts">
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger
} from '../navigation-menu';

const components: { title: string; route: string; description: string }[] = [
  {
    title: 'Mountain',
    route: '/mountain',
    description: '난이도 별, 위치 별 등산 보기'
  },
  {
    title: 'Rank',
    route: '/rank',
    description: '나는 등산 랭킹 몇번째일까?'
  },
  {
    title: 'Group',
    route: '/group',
    description: '우리 그룹의 등정 현황 보기'
  },
  {
    title: 'Search',
    route: '/search',
    description: '산 추천 및 주변 볼거리 정보 검색하기'
  }
];
</script>

<template>
  <NavigationMenu>
    <NavigationMenuList>
      <NavigationMenuItem>
        <NavigationMenuTrigger>Menu</NavigationMenuTrigger>
        <NavigationMenuContent>
          <ul class="grid w-[300px] gap-3 p-4 md:grid-cols-1">
            <li
              v-for="component in components"
              :key="component.title">
              <NavigationMenuLink as-child>
                <router-link
                  :to="component.route"
                  class="block p-3 space-y-1 leading-none no-underline transition-colors rounded-md outline-none select-none hover:bg-santaIvory focus:bg-santaIvory">
                  <div class="text-sm font-medium leading-none text-santaBlack">
                    {{ component.title }}
                  </div>
                  <p
                    class="text-sm leading-snug line-clamp-2 text-santaLightGray">
                    {{ component.description }}
                  </p>
                </router-link>
              </NavigationMenuLink>
            </li>
          </ul>
        </NavigationMenuContent>
      </NavigationMenuItem>
    </NavigationMenuList>
  </NavigationMenu>
</template>

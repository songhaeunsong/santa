<script setup lang="ts">
import { useGetLogout } from '../../api/member/authentication';
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger
} from '../ui/navigation-menu';

const { mutate: getLogout } = useGetLogout();

const handleLogout = () => {
  getLogout();
};
</script>

<template>
  <NavigationMenu>
    <NavigationMenuList>
      <NavigationMenuItem>
        <NavigationMenuTrigger class="px-0">MY</NavigationMenuTrigger>
        <NavigationMenuContent>
          <ul class="gap-3 p-4 w-[130px]">
            <li>
              <NavigationMenuLink as-child>
                <router-link
                  to="/user"
                  class="block p-3 space-y-1 leading-none no-underline transition-colors rounded-md outline-none select-none text-santaBlack hover:bg-santaIvory hover:text-accent-foreground focus:text-accent-foreground">
                  <div class="text-sm font-medium leading-none">내 정보</div>
                </router-link>
              </NavigationMenuLink>
            </li>
            <li>
              <NavigationMenuLink as-child>
                <div
                  class="block p-3 space-y-1 text-sm font-medium leading-none no-underline transition-colors rounded-md outline-none select-none text-santaBlack hover:bg-santaIvory hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground"
                  @click="handleLogout">
                  로그아웃
                </div>
              </NavigationMenuLink>
            </li>
          </ul>
        </NavigationMenuContent>
      </NavigationMenuItem>
    </NavigationMenuList>
  </NavigationMenu>
</template>

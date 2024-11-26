import js from '@eslint/js';
import eslintPluginVue from 'eslint-plugin-vue';
import typescriptEslint from '@typescript-eslint/eslint-plugin';
import typescriptParser from '@typescript-eslint/parser';
import prettierConfig from 'eslint-config-prettier';
import vueParser from 'vue-eslint-parser';

export default [
  js.configs.recommended,
  prettierConfig,
  {
    files: ['**/*.{js,ts,vue}'],
    ignores: ['tailwind.config.js', 'vite.config.ts'],
    languageOptions: {
      parser: vueParser,
      parserOptions: {
        parser: typescriptParser,
        ecmaVersion: 2020,
        sourceType: 'module'
      }
    },
    plugins: {
      vue: eslintPluginVue,
      '@typescript-eslint': typescriptEslint
    },
    rules: {
      ...eslintPluginVue.configs['vue3-recommended'].rules,
      ...typescriptEslint.configs.recommended.rules,
      'no-console': 'warn',
      eqeqeq: 'error',
      curly: 'error',
      'no-multi-spaces': 'error',
      semi: ['error', 'always'],
      quotes: ['error', 'single'],
      '@typescript-eslint/no-explicit-any': 'warn',
      '@typescript-eslint/ban-ts-comment': 'warn',
      'no-unused-vars': [
        'warn',
        { argsIgnorePattern: '^_', varsIgnorePattern: '^_' }
      ],
      '@typescript-eslint/no-unused-vars': 'warn',
      kakao: 'readonly'
    }
  }
];

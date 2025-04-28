<template>
  <div>
    <header class="navbar">
      <nav>
        <router-link to="/admin/users">사용자 관리</router-link>
        <router-link to="/admin/experiences">체험단 신청 목록</router-link>
        <router-link to="/admin/reviews">리뷰 목록</router-link>
        <router-link to="/admin/businesses">사업체 관리</router-link>
      </nav>
    </header>

    <main class="main">
      <router-view />
    </main>
  </div>
</template>

<script setup>
// 기본 템플릿용 컴포넌트는 제거됨
</script>

<style scoped>
.navbar {
  background-color: #1e1e1e;
  padding: 10px;
}
nav {
  display: flex;
  gap: 20px;
}
nav a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}
nav a.router-link-active {
  text-decoration: underline;
}
.main {
  padding: 20px;
}
</style>

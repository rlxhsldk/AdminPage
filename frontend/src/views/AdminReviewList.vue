<template>
  <div class="admin-reviews">
    <h1>리뷰 관리</h1>
    <table>
      <thead>
      <tr>
        <th>ID</th><th>작성자</th><th>별점</th><th>내용</th><th>작성일</th><th>액션</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(rev, idx) in reviews" :key="rev.id">
        <td>{{ rev.id }}</td>
        <td>{{ rev.username }}</td>
        <td>{{ rev.rating }}</td>
        <td>
          <input v-model="rev.content" @input="rev._edited = true" />
        </td>
        <td>{{ rev.createdAt }}</td>
        <td>
          <button class="save" @click="saveReview(rev)" :disabled="!rev._edited">저장</button>
          <button class="delete" @click="removeReview(rev.id)">삭제</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const reviews = ref([])

async function fetchReviews() {
  try {
    const res = await axios.get(
        'http://localhost:8000/admin/reviews',
        { withCredentials: true }
    )
    reviews.value = res.data.map(r => ({ ...r, _edited: false }))
  } catch (e) {
    console.error('리뷰 목록 조회 실패:', e)
  }
}

async function saveReview(rev) {
  try {
    const { id, rating, content } = rev
    const dto = { rating, content }
    await axios.put(
        `http://localhost:8000/admin/reviews/${id}`,
        dto,
        { withCredentials: true }
    )
    rev._edited = false
    alert('저장되었습니다.')
  } catch (e) {
    console.error('리뷰 수정 실패:', e)
  }
}

async function removeReview(id) {
  if (!confirm('정말 삭제하시겠습니까?')) return
  try {
    await axios.delete(
        `http://localhost:8000/admin/reviews/${id}`,
        { withCredentials: true }
    )
    await fetchReviews()
  } catch (e) {
    console.error('리뷰 삭제 실패:', e)
  }
}

onMounted(fetchReviews)
</script>

<style scoped>
.admin-reviews {
  max-width: 1200px;
  margin: 40px auto;
  padding: 20px;
  background: #f5f6fa;
  border-radius: 8px;
}

.admin-reviews h1 {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 20px;
  font-size: 24px;
}

table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  background: #ffffff;
}

thead {
  background: #2c3e50;
}

thead th {
  color: #ffffff;
  padding: 14px;
  text-align: left;
  font-weight: 500;
}

tbody tr {
  border-bottom: 1px solid #eaecef;
}

tbody tr:nth-child(even) {
  background: #f9f9f9;
}

tbody tr:hover {
  background: #eef1f5;
}

td {
  padding: 12px;
  color: #34495e;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #d1d8e0;
  border-radius: 4px;
  font-size: 14px;
}

button {
  margin: 0 4px;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  transition: background 0.3s, transform 0.1s;
  cursor: pointer;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

button.save {
  background: #27ae60;
  color: #ffffff;
}

button.save:hover:not(:disabled) {
  background: #219150;
  transform: translateY(-1px);
}

button.delete {
  background: #e74c3c;
  color: #ffffff;
}

button.delete:hover {
  background: #cf3c30;
  transform: translateY(-1px);
}
</style>
<template>
  <div class="review-board">
    <h1>체험단 리뷰 게시판</h1>

    <!-- 카드 그리드 -->
    <div class="cards">
      <div class="card" v-for="rev in reviews" :key="rev.id">
        <div class="card-image">
          <!-- 이미지 URL이 없으면 placeholder -->
          <img :src="rev.imageUrl || defaultImage" alt="리뷰 이미지" />
        </div>

        <div class="card-body">
          <p class="review-text">{{ rev.content }}</p>
          <span class="review-date">{{ formatDate(rev.createdAt) }}</span>
        </div>

        <!-- 카드 액션: 수정/삭제 -->
        <div class="card-actions">
          <button class="btn edit" @click="startEdit(rev)">수정</button>
          <button class="btn delete" @click="confirmDelete(rev.id)">삭제</button>
        </div>
      </div>
    </div>

    <!-- 수정 모달 -->
    <div v-if="editing" class="modal-backdrop">
      <div class="modal-card">
        <h2>리뷰 수정</h2>
        <textarea v-model="editing.content" rows="4"></textarea>
        <div class="modal-actions">
          <button @click="saveEdit" class="btn save">저장</button>
          <button @click="cancelEdit" class="btn cancel">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const reviews = ref([])
const defaultImage = '/placeholder.png'  // 프로젝트에 placeholder 이미지 위치
const editing = ref(null)  // { id, content, ... } 인 경우 수정 모달 표시

const fetchReviews = async () => {
  try {
    const res = await axios.get('http://localhost:8000/user/reviews', { withCredentials: true })
    reviews.value = res.data
  } catch (e) {
    console.error('리뷰 목록 조회 실패:', e)
  }
}

const formatDate = dt => new Date(dt).toLocaleDateString()

const startEdit = rev => {
  // 깊은 복사로 바인딩 깨기
  editing.value = { ...rev }
}

const saveEdit = async () => {
  try {
    await axios.put(
        `http://localhost:8000/user/reviews/${editing.value.id}`,
        { ...editing.value },
        { withCredentials: true }
    )
    editing.value = null
    await fetchReviews()
  } catch (e) {
    console.error('리뷰 수정 실패:', e)
  }
}

const cancelEdit = () => {
  editing.value = null
}

const confirmDelete = id => {
  if (!confirm('정말 삭제하시겠습니까?')) return
  axios.delete(`http://localhost:8000/user/reviews/${id}`, { withCredentials: true })
      .then(fetchReviews)
      .catch(e => console.error('리뷰 삭제 실패:', e))
}

onMounted(fetchReviews)
</script>

<style scoped>
.review-board {
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 16px;
}
.review-board h1 {
  text-align: center;
  margin-bottom: 24px;
  font-size: 2rem;
  color: #333;
}

.cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 16px;
}

.card {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.card-image img {
  width: 100%;
  height: 140px;
  object-fit: cover;
}

.card-body {
  flex: 1;
  padding: 12px;
}
.review-text {
  margin: 0 0 12px;
  line-height: 1.4;
  color: #444;
  min-height: 48px;
}
.review-date {
  font-size: 0.85rem;
  color: #888;
}

.card-actions {
  display: flex;
  justify-content: space-between;
  padding: 8px;
  background: #f9f9f9;
}
.btn {
  padding: 6px 10px;
  border: none;
  border-radius: 6px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.2s;
}
.btn.edit {
  background: #4caf50;
  color: #fff;
}
.btn.edit:hover { background: #43a047; }
.btn.delete {
  background: #e53935;
  color: #fff;
}
.btn.delete:hover { background: #d32f2f; }

/* 모달 */
.modal-backdrop {
  position: fixed; top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0,0,0,0.4);
  display: flex; justify-content: center; align-items: center;
  z-index: 1000;
}
.modal-card {
  background: #fff;
  padding: 24px;
  border-radius: 8px;
  width: 360px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.1);
}
.modal-card h2 {
  margin-top: 0;
  font-size: 1.2rem;
  color: #333;
}
.modal-card textarea {
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 6px;
  padding: 8px;
  resize: vertical;
}
.modal-actions {
  margin-top: 16px;
  text-align: right;
}
.modal-actions .btn {
  margin-left: 8px;
}
.modal-actions .save {
  background: #2196f3;
  color: #fff;
}
.modal-actions .save:hover { background: #1976d2; }
.modal-actions .cancel {
  background: #bbb;
  color: #333;
}
.modal-actions .cancel:hover { background: #999; }
</style>
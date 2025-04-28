<template>
  <div class="admin-applications-page">
    <h1>체험단 신청 관리</h1>

    <!-- 필터 바 -->
    <div class="filter-bar">
      <select v-model="filterStatus" @change="fetchApplications">
        <option value="">전체 상태</option>
        <option value="PENDING">대기</option>
        <option value="APPROVED">승인</option>
        <option value="CANCELLED">거부</option>
      </select>

      <select v-model="filterCampaign" @change="fetchApplications">
        <option value="">전체 캠페인</option>
        <option v-for="camp in campaigns" :key="camp.id" :value="camp.id">
          {{ camp.name }}
        </option>
      </select>
    </div>

    <!-- 신청 목록 카드 -->
    <div class="table-card">
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>신청자</th>
          <th>캠페인</th>
          <th>신청일</th>
          <th>상태</th>
          <th>액션</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="app in applications" :key="app.id">
          <td>{{ app.id }}</td>
          <td>{{ app.username }}</td>
          <td>{{ app.campaignName }}</td>
          <td>{{ app.applyDate }}</td>
          <td>
              <span
                  class="badge"
                  :class="{
                  pending: app.status==='PENDING',
                  approved: app.status==='APPROVED',
                  cancelled: app.status==='CANCELLED'
                }"
              >
                {{ statusText(app.status) }}
              </span>
          </td>
          <td>
            <button
                class="btn approve"
                @click="openModal(app.id, 'approve')"
                :disabled="app.status!=='PENDING'"
            >승인</button>
            <button
                class="btn reject"
                @click="openModal(app.id, 'reject')"
                :disabled="app.status!=='PENDING'"
            >거부</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <button @click="prevPage" :disabled="page===0">◀ 이전</button>
      <span>Page {{ page+1 }}</span>
      <button @click="nextPage" :disabled="!hasMore">다음 ▶</button>
    </div>

    <!-- 승인/거부 사유 모달 -->
    <div v-if="showModal" class="modal-backdrop">
      <div class="modal-card">
        <h2>{{ modalAction==='approve' ? '승인 사유' : '거부 사유' }}</h2>
        <textarea v-model="remark" placeholder="사유를 입력하세요"></textarea>
        <div class="modal-actions">
          <button class="btn save" @click="confirmAction">확인</button>
          <button class="btn cancel" @click="closeModal">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const applications = ref([])
const campaigns    = ref([])    // 캠페인 필터용 리스트
const filterStatus   = ref('')
const filterCampaign = ref('')
const page         = ref(0)
const size         = ref(10)
const hasMore      = ref(false)

// Modal state
const showModal   = ref(false)
const modalId     = ref(null)
const modalAction = ref('approve')  // 'approve' or 'reject'
const remark      = ref('')

const statusText = (s) =>
    s==='PENDING' ? '대기' :
        s==='APPROVED'? '승인됨' : '거부됨'

async function fetchCampaigns() {
  // 캠페인 목록을 API에서 가져와야 합니다
  const res = await axios.get('http://localhost:8000/user/Experiences')
  campaigns.value = res.data
}

async function fetchApplications() {
  try {
    const params = {
      status: filterStatus.value || undefined,
      campaignId: filterCampaign.value || undefined,
      page: page.value,
      size: size.value
    }
    const res = await axios.get(
        'http://localhost:8000/user/Experiences',
        { params, withCredentials: true }
    )
    applications.value = res.data.content
    hasMore.value      = !res.data.last
  } catch (e) {
    console.error('신청 목록 조회 실패:', e)
  }
}

function prevPage() {
  if (page.value>0) {
    page.value--
    fetchApplications()
  }
}
function nextPage() {
  if (hasMore.value) {
    page.value++
    fetchApplications()
  }
}

function openModal(id, action) {
  modalId.value     = id
  modalAction.value = action
  remark.value      = ''
  showModal.value   = true
}

async function confirmAction() {
  try {
    const url = `http://localhost:8000/user/Experiences/${modalId.value}/${modalAction.value}`
    const body = { remark: remark.value }
    const res = await axios.put(url, body, { withCredentials: true })
    showModal.value = false
    fetchApplications()
  } catch (e) {
    console.error('승인/거부 처리 실패:', e)
  }
}

function closeModal() {
  showModal.value = false
}

onMounted(() => {
  fetchCampaigns()
  fetchApplications()
})
</script>

<style scoped>
.admin-applications-page {
  max-width: 1000px;
  margin: 40px auto;
  padding: 20px;
}
h1 {
  text-align: center;
  margin-bottom: 16px;
  color: #2c3e50;
}

.filter-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
.filter-bar select {
  padding: 6px 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.table-card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
}
table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
}
thead {
  background: #2c3e50;
}
thead th {
  color: #fff;
  padding: 12px 16px;
  text-align: left;
}
tbody tr:nth-child(even) {
  background: #f9f9f9;
}
tbody tr:hover {
  background: #eef1f5;
}
td {
  padding: 12px 16px;
  color: #34495e;
}

.badge {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
  color: #fff;
}
.badge.pending   { background: #f1c40f; }
.badge.approved  { background: #27ae60; }
.badge.cancelled { background: #e74c3c; }

.btn {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  color: #fff;
  font-size: 14px;
  cursor: pointer;
  transition: transform 0.1s, background 0.2s;
}
.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.btn.approve {
  background: #27ae60;
}
.btn.approve:hover:not(:disabled) {
  background: #219150;
  transform: translateY(-1px);
}
.btn.reject {
  background: #e74c3c;
}
.btn.reject:hover:not(:disabled) {
  background: #cf3c30;
  transform: translateY(-1px);
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin: 16px 0;
}
.pagination button {
  padding: 6px 12px;
  border: 1px solid #ccc;
  background: #fff;
  border-radius: 4px;
  cursor: pointer;
}
.pagination button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.modal-backdrop {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0,0,0,0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}
.modal-card {
  width: 360px;
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
}
.modal-card h2 {
  margin-bottom: 12px;
  color: #2c3e50;
}
.modal-card textarea {
  width: 100%;
  height: 80px;
  padding: 8px;
  border: 1px solid #d1d8e0;
  border-radius: 4px;
  resize: none;
  margin-bottom: 16px;
}
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}
.modal-actions .save {
  background: #27ae60;
  color: #fff;
}
.modal-actions .cancel {
  background: #bdc3c7;
  color: #2c3e50;
}
</style>
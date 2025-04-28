<template>
  <div class="business-approval-page">
    <div class="header">
      <h1>관리자 페이지 - 사업체 승인</h1>
    </div>

    <!-- 신청 현황 -->
    <div class="approval-section">
      <h2>신청 현황</h2>
      <table>
        <thead>
        <tr>
          <th>가게 이름</th>
          <th>업종</th>
          <th>모집 기간</th>
          <th>모집 인원</th>
          <th>신청일</th>
          <th>상태</th>
          <th>액션</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="business in businesses" :key="business.id">
          <td>{{ business.name }}</td>
          <td>{{ business.industry }}</td>
          <td>{{ business.recruitmentPeriod }}</td>
          <td>{{ business.maxPeople }}</td>
          <td>{{ business.applicationDate }}</td>
          <td :class="['status-cell', business.status.toLowerCase()]">
            {{ business.status === 'APPROVED' ? '승인됨' : business.status === 'CANCELLED' ? '취소됨' : '대기 중' }}
          </td>
          <td>
            <button @click="approveBusiness(business.id)" :disabled="business.status === 'APPROVED'">승인</button>
            <button @click="cancelBusiness(business.id)" :disabled="business.status === 'CANCELLED'">취소</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 사업체 등록 버튼 -->
    <div class="add-business-btn">
      <button @click="showRegistrationForm = !showRegistrationForm">사업체 등록</button>
    </div>

    <!-- 사업체 등록 폼 (정중앙 카드 형식) -->
    <div v-if="showRegistrationForm" class="business-registration-card">
      <h3>사업체 등록</h3>
      <form @submit.prevent="createBusiness">
        <label for="name">가게 이름:</label>
        <input type="text" v-model="newBusiness.name" id="name" required />

        <label for="industry">업종:</label>
        <select v-model="newBusiness.industry" id="industry" required>
          <option value="한식">한식</option>
          <option value="일식">일식</option>
          <option value="양식">양식</option>
        </select>

        <label for="recruitmentPeriod">모집 기간:</label>
        <input type="date" v-model="newBusiness.startDate" required />
        <input type="date" v-model="newBusiness.endDate" required />

        <label for="maxParticipants">모집 인원:</label>
        <input type="number" v-model="newBusiness.maxParticipants" required />

        <div class="form-actions">
          <button type="submit">사업체 등록</button>
          <button type="button" @click="cancelForm">뒤로가기</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const businesses = ref([]);
const newBusiness = ref({
  name: '',
  industry: '한식',
  startDate: '',
  endDate: '',
  maxParticipants: 0,
});

const showRegistrationForm = ref(false);

const fetchBusinesses = async () => {
  try {
    const res = await axios.get('http://localhost:8000/user/businesses');
    businesses.value = res.data;
  } catch (err) {
    console.error('사업체 목록 조회 실패:', err);
  }
};

const approveBusiness = async (id) => {
  try {
    await axios.put(`http://localhost:8000/user/businesses/${id}/approve`, {}, {
      withCredentials: true  // 쿠키와 자격 증명을 포함한 요청
    });
    await fetchBusinesses();  // 승인 후 사업체 목록 갱신
  } catch (err) {
    console.error('사업체 승인 실패:', err);
  }
};

const cancelBusiness = async (id) => {
  try {
    await axios.put(`http://localhost:8000/user/businesses/${id}/cancel`, {}, {
      withCredentials: true  // 쿠키와 자격 증명을 포함한 요청
    });
    await fetchBusinesses();  // 취소 후 사업체 목록 갱신
  } catch (err) {
    console.error('사업체 취소 실패:', err);
  }
};


const createBusiness = async () => {
  try {
    await axios.post('http://localhost:8000/user/businesses', newBusiness.value);
    await fetchBusinesses();
    newBusiness.value = { name: '', industry: '한식', startDate: '', endDate: '', maxParticipants: 0 };
    showRegistrationForm.value = false;
  } catch (err) {
    console.error('사업체 등록 실패:', err);
  }
};

const cancelForm = () => {
  showRegistrationForm.value = false;
  newBusiness.value = { name: '', industry: '한식', startDate: '', endDate: '', maxParticipants: 0 };
};

onMounted(fetchBusinesses);
</script>

<style scoped>
.business-approval-page {
  padding: 20px;
  background-color: #f9f9f9;
}

.header {
  text-align: center;
  background-color: #2c3e50;
  color: white;
  padding: 10px;
}

.approval-section {
  margin-top: 20px;
  padding: 20px;
  background-color: white;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.approval-section h2 {
  margin-bottom: 10px;
}

.approval-section table {
  width: 100%;
  border-collapse: collapse;
}

.approval-section th, .approval-section td {
  padding: 10px;
  border: 1px solid #ccc;
}

.status-cell {
  font-weight: bold;
  text-align: center;
}
.status-cell.pending {
  color: #f39c12;
}
.status-cell.approved {
  color: #27ae60;
}
.status-cell.cancelled {
  color: #e74c3c;
}

.add-business-btn {
  text-align: center;
  margin-top: 20px;
}

.add-business-btn button {
  background-color: #3498db;
  color: white;
  padding: 10px 20px;
  border: none;
  font-size: 16px;
  cursor: pointer;
}

.add-business-btn button:hover {
  background-color: #2980b9;
}

.business-registration-card {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  z-index: 10;
  width: 400px;
  max-width: 100%;
}

.business-registration-card h3 {
  text-align: center;
  margin-bottom: 20px;
}

.business-registration-card label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
}

.business-registration-card input,
.business-registration-card select {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.business-registration-card button {
  background-color: #2ecc71;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 4px;
}

.business-registration-card button:hover {
  background-color: #27ae60;
}

.business-registration-card .form-actions {
  display: flex;
  justify-content: space-between;
}

.business-registration-card .form-actions button {
  width: 48%;
}

.business-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
}
</style>

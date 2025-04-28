<template>
  <div class="user-list">
    <h1>사용자 관리</h1>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>닉네임</th>
        <th>이메일</th>
        <th>캠페인 신청</th>
        <th>권한</th>
        <th>액션</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.id }}</td>
        <td>{{ user.nickname }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.campaignApplied ? '✔️' : '✖️' }}</td>
        <td>{{ user.role }}</td>
        <td>
          <button class="btn edit" @click="startEdit(user)">수정</button>
          <button class="btn delete" @click="deleteUser(user.id)">삭제</button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="selectedUser" class="edit-card">
      <h2>사용자 수정</h2>
      <label>
        닉네임
        <input v-model="selectedUser.nickname" />
      </label>
      <label>
        이메일
        <input v-model="selectedUser.email" />
      </label>
      <label>
        권한
        <select v-model="selectedUser.role">
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
        </select>
      </label>
      <label class="checkbox-label">
        <input type="checkbox" v-model="selectedUser.campaignApplied" />
        캠페인 신청 여부
      </label>

      <div class="actions">
        <button class="btn save" @click="saveUser">저장</button>
        <button class="btn cancel" @click="cancelEdit">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const users = ref([])
const selectedUser = ref(null)

const fetchUsers = async () => {
  try {
    const res = await axios.get('http://localhost:8000/user/users')
    users.value = res.data
  } catch (err) {
    console.error('사용자 목록 조회 실패:', err)
  }
}

const deleteUser = async (id) => {
  if (!confirm('정말 삭제하시겠습니까?')) return
  try {
    await axios.delete(`http://localhost:8000/user/users/${id}`)
    await fetchUsers()
  } catch (err) {
    console.error('삭제 실패:', err)
  }
}

const startEdit = (user) => {
  selectedUser.value = { ...user }
}

const cancelEdit = () => {
  selectedUser.value = null
}

const saveUser = async () => {
  try {
    await axios.put(
        `http://localhost:8000/user/users/${selectedUser.value.id}`,
        selectedUser.value
    )
    await fetchUsers()
    selectedUser.value = null
  } catch (err) {
    console.error('수정 실패:', err)
  }
}

onMounted(fetchUsers)
</script>

<style scoped>
.user-list {
  max-width: 1000px;
  margin: 40px auto;
  padding: 24px;
  background: #fafafa;
  border-radius: 8px;
  box-shadow: 0 6px 20px rgba(0,0,0,0.05);
}

.user-list h1 {
  margin-bottom: 16px;
  color: #2c3e50;
  text-align: center;
  font-size: 24px;
}

table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: inset 0 0 0 1px #e0e0e0;
}

thead {
  background: #2c3e50;
}

thead th {
  color: #fff;
  padding: 12px 16px;
  text-align: left;
  font-weight: 500;
}

tbody tr {
  transition: background 0.2s;
}

tbody tr:nth-child(even) {
  background: #f7f7f7;
}

tbody tr:hover {
  background: #eef1f5;
}

td {
  padding: 12px 16px;
  color: #34495e;
}

.btn {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background 0.2s, transform 0.1s;
}

.btn:hover {
  transform: translateY(-1px);
}

.btn.edit {
  background: #3498db;
  color: #fff;
}

.btn.delete {
  background: #e74c3c;
  color: #fff;
}

.btn.edit:hover {
  background: #2980b9;
}

.btn.delete:hover {
  background: #c0392b;
}

/* 수정 폼 */
.edit-card {
  position: fixed;
  top: 50%;
  left: 50%;
  width: 380px;
  padding: 24px;
  transform: translate(-50%, -50%);
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  z-index: 20;
}

.edit-card h2 {
  margin-bottom: 20px;
  color: #2c3e50;
  text-align: center;
}

.edit-card label {
  display: block;
  margin-bottom: 14px;
  font-weight: 500;
  color: #2c3e50;
}

.edit-card input,
.edit-card select {
  width: 100%;
  margin-top: 6px;
  padding: 8px 10px;
  border: 1px solid #d1d8e0;
  border-radius: 4px;
  font-size: 14px;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 8px;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.actions .btn.save {
  background: #27ae60;
  color: #fff;
}

.actions .btn.save:hover {
  background: #219150;
}

.actions .btn.cancel {
  background: #bdc3c7;
  color: #2c3e50;
}

.actions .btn.cancel:hover {
  background: #aeb6bf;
}
</style>
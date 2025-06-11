import { useState, useEffect } from 'react'
import { Container, Typography, CircularProgress, Alert } from '@mui/material'
import type { User } from './types/user'
import { fetchUsers, deleteUser } from './services/api'
import UserTable from './components/UserTable'

function App() {
  const [users, setUsers] = useState<User[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const loadUsers = async () => {
      try {
        console.log('Fetching users...')
        const data = await fetchUsers()
        console.log('Users fetched:', data)
        setUsers(data)
        setError(null)
      } catch (err) {
        console.error('Error fetching users:', err)
        setError('Failed to load users. Please try again later.')
      } finally {
        setLoading(false)
      }
    }

    loadUsers()
  }, [])

  const handleDeleteUser = async (id: number) => {
    try {
      await deleteUser(id)
      setUsers(users.filter(user => user.id !== id))
      setError(null)
    } catch (err) {
      setError('Failed to delete user. Please try again later.')
    }
  }

  console.log('Current state:', { users, loading, error })

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        User Management
      </Typography>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      {loading ? (
        <CircularProgress />
      ) : (
        <UserTable users={users} onDeleteUser={handleDeleteUser} />
      )}
    </Container>
  )
}

export default App

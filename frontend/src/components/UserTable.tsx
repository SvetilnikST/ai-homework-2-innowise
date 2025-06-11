import { useState } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Typography,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import type { User } from '../types/user';
import UserModal from './UserModal';

interface UserTableProps {
  users: User[];
  onDeleteUser: (id: number) => void;
}

const UserTable = ({ users, onDeleteUser }: UserTableProps) => {
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  const handleRowClick = (user: User) => {
    setSelectedUser(user);
  };

  const handleCloseModal = () => {
    setSelectedUser(null);
  };

  return (
    <>
      <TableContainer component={Paper} sx={{ mt: 2 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name/Email</TableCell>
              <TableCell>Address</TableCell>
              <TableCell>Phone</TableCell>
              <TableCell>Website</TableCell>
              <TableCell>Company</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.map((user) => (
              <TableRow
                key={user.id}
                hover
                onClick={() => handleRowClick(user)}
                sx={{ cursor: 'pointer' }}
              >
                <TableCell>
                  <Typography variant="subtitle2">{user.name}</Typography>
                  <Typography variant="body2" color="text.secondary">
                    {user.email}
                  </Typography>
                </TableCell>
                <TableCell>
                  {`${user.address.street}, ${user.address.city}`}
                </TableCell>
                <TableCell>{user.phone}</TableCell>
                <TableCell>
                  <a
                    href={`https://${user.website}`}
                    target="_blank"
                    rel="noopener noreferrer"
                    onClick={(e) => e.stopPropagation()}
                  >
                    {user.website}
                  </a>
                </TableCell>
                <TableCell>{user.company.name}</TableCell>
                <TableCell>
                  <IconButton
                    onClick={(e) => {
                      e.stopPropagation();
                      onDeleteUser(user.id);
                    }}
                    color="error"
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <UserModal user={selectedUser} onClose={handleCloseModal} />
    </>
  );
};

export default UserTable; 
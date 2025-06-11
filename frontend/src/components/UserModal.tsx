import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Box,
  Link,
} from '@mui/material';
import type { User } from '../types/user';

interface UserModalProps {
  user: User | null;
  onClose: () => void;
}

const UserModal = ({ user, onClose }: UserModalProps) => {
  if (!user) return null;

  const mapUrl = `https://www.google.com/maps?q=${user.address.geo.lat},${user.address.geo.lng}`;

  return (
    <Dialog open={!!user} onClose={onClose} maxWidth="md" fullWidth>
      <DialogTitle>
        <Typography variant="h5">{user.name}</Typography>
        <Typography variant="subtitle1" color="text.secondary">
          {user.username}
        </Typography>
      </DialogTitle>
      <DialogContent>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
          <Box>
            <Typography variant="h6">Contact Information</Typography>
            <Typography>Email: {user.email}</Typography>
            <Typography>Phone: {user.phone}</Typography>
            <Typography>
              Website:{' '}
              <Link href={`https://${user.website}`} target="_blank" rel="noopener noreferrer">
                {user.website}
              </Link>
            </Typography>
          </Box>

          <Box>
            <Typography variant="h6">Address</Typography>
            <Typography>
              {user.address.street}, {user.address.suite}
            </Typography>
            <Typography>
              {user.address.city}, {user.address.zipcode}
            </Typography>
            <Link href={mapUrl} target="_blank" rel="noopener noreferrer">
              View on Map
            </Link>
          </Box>

          <Box>
            <Typography variant="h6">Company</Typography>
            <Typography>{user.company.name}</Typography>
            <Typography color="text.secondary">{user.company.catchPhrase}</Typography>
            <Typography variant="body2" color="text.secondary">
              {user.company.bs}
            </Typography>
          </Box>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Close</Button>
      </DialogActions>
    </Dialog>
  );
};

export default UserModal; 
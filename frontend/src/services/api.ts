import type { User } from '../types/user';

const API_URL = 'https://jsonplaceholder.typicode.com';

export const fetchUsers = async (): Promise<User[]> => {
  try {
    console.log('Making API request to:', `${API_URL}/users`);
    const response = await fetch(`${API_URL}/users`);
    console.log('API Response status:', response.status);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const data = await response.json();
    console.log('API Response data:', data);
    return data;
  } catch (error) {
    console.error('Error in fetchUsers:', error);
    throw error;
  }
};

export const deleteUser = async (id: number): Promise<void> => {
  try {
    console.log('Making DELETE request for user:', id);
    const response = await fetch(`${API_URL}/users/${id}`, {
      method: 'DELETE',
    });
    console.log('Delete response status:', response.status);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
  } catch (error) {
    console.error('Error in deleteUser:', error);
    throw error;
  }
}; 
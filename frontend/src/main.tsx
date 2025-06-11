import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { ThemeProvider, createTheme } from '@mui/material'
import CssBaseline from '@mui/material/CssBaseline'
import './index.css'
import App from './App.tsx'

const theme = createTheme({
  palette: {
    mode: 'light',
    background: {
      default: '#f5f5f5',
    },
  },
})

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <App />
    </ThemeProvider>
  </StrictMode>,
)

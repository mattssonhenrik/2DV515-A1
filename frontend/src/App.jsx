import { useState } from 'react'
import MainPage from './components/mainpage/MainPage.jsx'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <h1>Lets go</h1>
      <MainPage />
    </>
  )
}

export default App

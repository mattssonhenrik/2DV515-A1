import React, { useState, useEffect } from 'react'
import styles from './MainPage.module.css'

const MainPage = () => {
    const [users, setUsers] = useState([])
    const [selectedUserId, setSelectedUserId] = useState("")
    const [resultCount, setResultCount] = useState(3)
    const [recommendations, setRecommendations] = useState([])
    const [isEuclidean, setIsEuclidean] = useState(true)

    // Loada användarna
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const res = await fetch("http://localhost:8080/api/data/users")
                const data = await res.json()

                setUsers(data)
                if (data.length > 0) {
                    setSelectedUserId(data[0].id)
                }
            } catch (err) {
                console.error("Failed to load users", err)
            }
        }

        fetchUsers()
    }, [])

    /// Databas reload (delete, sedan -> load)
    const handleReloadDB = async () => {
        try {
            await fetch("http://localhost:8080/api/data/reload", { method: "POST" })
        } catch (err) {
            alert("Error resetting DB")
            console.error(err)
        }
    }

    // Rekommendationerna
    const handleFindMovies = async () => {
        if (!selectedUserId) return

        const url = `http://localhost:8080/api/euclidean/recommendations?userId=${selectedUserId}&isEuclidean=${isEuclidean}&results=${resultCount}`
        console.log("SENDING REQUEST:", url)
        try {
            const res = await fetch(url)
            const data = await res.json()
            setRecommendations(data)
        } catch (err) {
            console.error("Error fetching movies:", err)
        }
    }

    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h2>Movie Recommender</h2>
                <button onClick={handleReloadDB} className={styles.reloadBtn}>
                    Reload Database Data
                </button>
            </div>

            <div className={styles.controls}>
                <label>
                    User:
                    <select
                        value={selectedUserId}
                        onChange={(e) => setSelectedUserId(e.target.value)}
                    >
                        {users.map(user => (
                            <option key={user.id} value={user.id}>
                                {user.id}: {user.name}
                            </option>
                        ))}
                    </select>
                </label>

                <label>
                    Similarity Mode:
                    <select
                        value={isEuclidean}
                        onChange={(e) => setIsEuclidean(e.target.value === 'true')}
                    >
                        <option value='true'>Euclidean</option>
                        <option value='false'>Pearson</option>
                    </select>
                </label>

                <label>
                    Results:
                    <input
                        type="number"
                        value={resultCount}
                        onChange={(e) => setResultCount(e.target.value)}
                        className={styles.numInput}
                    />
                </label>

                <button onClick={handleFindMovies} className={styles.findBtn}>
                    Find recommended movies
                </button>
            </div>

            <table className={styles.table}>
                <thead>
                    <tr>
                        <th>Movie</th>
                        <th>ID</th>
                        <th>Score</th>
                    </tr>
                </thead>
                <tbody>
                    {recommendations.map((movie) => (
                        <tr key={movie.id}>
                            <td className={styles.leftAlign}>{movie.title}</td>
                            <td>{movie.id}</td>
                            <td>{movie.score.toFixed(4)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default MainPage
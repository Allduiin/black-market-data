import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRouter } from 'next/router';
import Sidebar from '../components/Sidebar';
import Table from '../components/Table';

const Home = () => {
    const router = useRouter();
    const { city: queryCity } = router.query;
    const [city, setCity] = useState(queryCity || 'Thetford');
    const [data, setData] = useState([]);

    useEffect(() => {
        if (queryCity) {
            setCity(queryCity);
            fetchData(queryCity);
        }
    }, [queryCity]);

    const fetchData = async (city) => {
        try {
            const endpoint = city === 'all' ? '/weapons/get-all' : `/weapons/get-data?city=${city}`;
            const response = await axios.get(`http://172.17.0.3:8080${endpoint}`);
            setData(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const reloadAllData = async () => {
        try {
            await axios.get('http://172.17.0.3:8080/weapons/reload-all');
            if (city) {
                fetchData(city);
            }
        } catch (error) {
            console.error('Error reloading data:', error);
        }
    };

    useEffect(() => {
        if (city) {
            fetchData(city);
        }
    }, [city]);

    return (
        <div className="container">
            <Sidebar currentCity={city} />
            <div className="content">
                <h1>{city === 'all' ? 'All Cities' : `${city} Weapons`}</h1>
                <button onClick={reloadAllData}>Reload All Data</button>
                <Table data={data} />
            </div>
            <style jsx>{`
                .container {
                    display: flex;
                }
                .content {
                    flex: 1;
                    padding: 20px;
                }
                button {
                    margin-bottom: 20px;
                    padding: 10px 20px;
                    background-color: #0070f3;
                    color: white;
                    border: none;
                    cursor: pointer;
                }
                button:hover {
                    background-color: #005bb5;
                }
            `}</style>
        </div>
    );
};

export default Home;
